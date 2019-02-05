/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionarbanco;

import java.io.File;



public class Lector {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
     // TODO code application logic here
    
    
   
     Operacion gesSAX = new Operacion();
    
        String filePath = new File("operaciones.txt").getAbsolutePath();
        File f = new File(filePath);
        filePath= f.getParent();
     
        f = new File(filePath, "operaciones.xml");

        if (!f.exists()) {
            System.out.println("Fichero " + f.getName() + " no existe");
        } else if (gesSAX.abrir_XML_SAX(f) == -1) {
            System.out.println("Error al abrir el archivo para SAX");
        } else {
            System.out.println("Operaciones leídas con SAX");
            System.out.println("===================");

            String salida = gesSAX.recorrerSAXyMostrar();
            
            System.out.println("Datos de las operaciones con estructuras SAX: ");
            System.out.println("===================");
            System.out.println(salida);
        }
     
     
    
    }
    
}
