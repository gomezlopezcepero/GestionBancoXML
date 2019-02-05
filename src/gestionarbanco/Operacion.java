/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionarbanco;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author Paco
 */
public class Operacion {
    
    Document doc;
    
    ManejadorSAX sh;
    SAXParser parser;
    File ficheroXML;
    
    XPathExpression exp;
    Element element;
    Document XMLDoc;
    XPath xpath;
    
    
    
    
    
     public String[][] abrir_txt(File archivo)
    {
        
      String[][] arrayLineas = new String[100][6];
      int cont=0, cant=0;
        
       // FileReader fr = null;
        BufferedReader br = null;
        InputStreamReader fr = null;

        try {
         // Apertura del fichero y creacion de BufferedReader para poder
            // hacer una lectura comoda (disponer del metodo readLine()).
            
           // fr = new FileReader(archivo);
           
           fr= new InputStreamReader(new FileInputStream(archivo), "windows-1252");
            br = new BufferedReader(fr);
            
            // Lectura del fichero
            String linea;
            while ((linea = br.readLine()) != null) {
               
                cont++;
                
                if(cont==1){
                   arrayLineas[cant][0] = linea.substring(2, 21);
                   arrayLineas[cant][1] = linea.substring(22);
                }
                if(cont==2){
                     arrayLineas[cant][2]=linea;
                }
                
                 if(cont==3){
                     String[] parts = linea.split("-");
                  arrayLineas[cant][3]=parts[0];
                  arrayLineas[cant][4]=parts[1];  
                }
                
                if(cont==4){
                    arrayLineas[cant][5]=linea;
                    cant++;
                    cont=0;
                }
            }
            
           
            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (null != fr) {
                    fr.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
       
       return arrayLineas; 
    }
    
     
     
     
     public int guardarDOMcomoFILE()
    {
        try{
            
        String filePath = new File("operaciones.xml").getAbsolutePath();
        File f = new File(filePath);
        filePath= f.getParent();
            
        File archivo_xml = new File(filePath,"operaciones.xml");
        
        OutputFormat format = new OutputFormat(doc);
        format.setIndenting(true); 
        format.setEncoding("UTF-8");
        
        XMLSerializer serializer = new XMLSerializer(new FileOutputStream(archivo_xml), format);

        
        
        serializer.serialize(doc);
        return 0;
         }catch(Exception e) {
           
           return -1;
        }
    }
    
     
     
     public int abrir_XML_DOM()
    {
        doc=null;
       
        try{
            DocumentBuilderFactory factory=DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder=factory.newDocumentBuilder();
            
            DOMImplementation implementation = builder.getDOMImplementation();
            doc = implementation.createDocument(null, "operacionesBancarias", null);
            doc.setXmlVersion("1.0");
            
            
            return 0;
                                                            
        }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }

     
     
    public int annadirDOM(String anno, String numcuenta, String propietario, String tipo, String cantidad,  String saldo){
        
        try{
           
            
            
        Node nsaldo=doc.createElement("saldo");
        Node nsaldo_text=doc.createTextNode(saldo);
        nsaldo.appendChild(nsaldo_text);
        
        Node ncantidad=doc.createElement("cantidad");
        Node ncantidad_text=doc.createTextNode(cantidad);
        ncantidad.appendChild(ncantidad_text);
        
        Node ntipo=doc.createElement("tipo");
        Node ntipo_text=doc.createTextNode(tipo);
        ntipo.appendChild(ntipo_text);
            
        Node npropietario=doc.createElement("propietario");
        Node npropietario_text=doc.createTextNode(propietario);
        npropietario.appendChild(npropietario_text);

        Node nnumcuenta=doc.createElement("numcuenta");
        Node nnumcuenta_text=doc.createTextNode(numcuenta);
        nnumcuenta.appendChild(nnumcuenta_text);   
        
        
        Node noperacion=doc.createElement("Operacion");
        ((Element)noperacion).setAttribute("fechaahora",anno );
        
       
 
        noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(nnumcuenta);
        noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(npropietario);
        noperacion.appendChild(doc.createTextNode("\n"));
       noperacion.appendChild(ntipo);
       noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(ncantidad);
        noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(nsaldo);
        

        Node raiz=doc.getChildNodes().item(0);
        raiz.appendChild(noperacion);
        noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(doc.createTextNode("\n"));
        noperacion.appendChild(doc.createTextNode("\n"));

        
        return 0;
         }catch(Exception e){
            e.printStackTrace();
            return -1;
        }
    }



    
     public int abrir_XML_SAX(File fichero) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            //Se crea un objeto SAXParser para interpretar el documento XML.
            parser = factory.newSAXParser();
            //Se crea un instancia del manejador que será el que recorra el documento XML secuencialmente
            sh = new ManejadorSAX();
            ficheroXML = fichero;
            return 0;

        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
     
     
     
     public String recorrerSAXyMostrar() {

         try {
            parser.parse(ficheroXML, sh);
            return sh.cadena_resultado;
        } catch (SAXException e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error al parsear con SAX";
        }
    }

     
     
     
     class ManejadorSAX extends DefaultHandler {
int cent=0;
    int ultimoelement;

    String cadena_resultado = "";

    public ManejadorSAX() {
        ultimoelement = 0;
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
        if (qName.equals("Operacion")) {
             cent++;
      
            cadena_resultado = cadena_resultado + "Operación Número "+cent;
            cadena_resultado = cadena_resultado + "\n  "+"-------------------";
            
             
            cadena_resultado = cadena_resultado + "\n"+ "Fecha de transacción: " + atts.getValue(atts.getQName(0)) + "\n ";
            ultimoelement = 1;

        } else if (qName.equals("numcuenta")) {
            ultimoelement = 2;
            cadena_resultado = cadena_resultado + "\n "  + "Número de cuenta: ";
        } else if (qName.equals("propietario")) {
            ultimoelement = 3;
            cadena_resultado = cadena_resultado + " " + "Propietario: ";
        }else if (qName.equals("tipo")) {
            ultimoelement = 3;
            cadena_resultado = cadena_resultado + " " + "Tipo de transacción: ";
        }else if (qName.equals("cantidad")) {
            ultimoelement = 3;
            cadena_resultado = cadena_resultado + " " + "Cantidad: ";
        }else if (qName.equals("saldo")) {
            ultimoelement = 3;
            cadena_resultado = cadena_resultado + "\n " + "Saldo: ";
        }
    }
    //Cuando en este ejemplo se detecta el final de un elemento <libro>, se pone un línea discontinua en la salida.

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (qName.equals("Operacion")) {
           
            cadena_resultado = cadena_resultado + "\n  ===================";
        }
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (ultimoelement == 2) {
            for (int i = start; i < length + start; i++) {
                cadena_resultado = cadena_resultado + ch[i];
            }
        } else if (ultimoelement == 3) {
            for (int i = start; i < length + start; i++) {
                cadena_resultado = cadena_resultado + ch[i];
            }
        }
    }

}
     
     
     

    public int abrir_file_DOM_XPath()
    {
        //Abre un fichero XML para crear un DOM
        try {
            String filePath = new File("operaciones.xml").getAbsolutePath();
            //El fichero XML que se abre es LibrosXML.xml 
            xpath = XPathFactory.newInstance().newXPath() ;
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            XMLDoc = factory.newDocumentBuilder().parse(new InputSource(new
            FileInputStream(filePath)));
            //Al llegar aquí ya se ha creado la estructura DOM para se consultada
            return 0;
        }
        catch (Exception ex) {
            System.out.println("Error: " + ex.toString());
            return -1;
        }
        
    }
    public String Ejecutar_XPath(String txtconsulta)
    {
        //Ejecuta la consulta txtconsulta y devuelve el resultado como un String.
        String salida="";
        try {
             //Compila la consulta 
             exp = xpath.compile(txtconsulta);
             //Evaluate  evalua la expresión devuelta por compile y devuelve el resultado (Lista de nodos)    
             Object result=  exp.evaluate(XMLDoc, XPathConstants.NODESET);
             NodeList nodeList = (NodeList) result;
             int cont=0;
             String[]Operecions={"Número de cuenta: ","propietario: ","tipo de transacción: ","cantidad: ","saldo: "};
            for (int i = 0; i < nodeList.getLength(); i++) {
                salida = salida + "\n" + Operecions[cont] + nodeList.item(i).getChildNodes().item(0).getNodeValue();
                
                cont++;
                
                if(cont==5){
                    cont=0;
                    salida = salida + "\n" + "=====================" ;
                }
            }
            
            return salida;
        }
        catch (Exception ex) {
            return "Error: " + ex.toString();
        }
        
    }
    
     


    
}




