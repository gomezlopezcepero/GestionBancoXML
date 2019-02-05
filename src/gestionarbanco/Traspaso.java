/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionarbanco;

import java.io.File;
import java.net.URL;

/**
 *
 * @author Paco
 */
public class Traspaso {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        String filePath = new File("operaciones.txt").getAbsolutePath();
        File f = new File(filePath);
        filePath= f.getParent();

        
        Operacion operacion = new Operacion();
        
        f = new File(filePath, "operaciones.txt");

        if (!f.exists()) {
            System.out.println("Fichero " + f.getName() + " no existe");
        } else {
         
            String[][] array= new String[100][6];
            
            array = operacion.abrir_txt(f);
           
            System.out.println("Leyendo fichero txt...");

            operacion.abrir_XML_DOM();
            
         
                  
             
            
            
            for (int i=0; i<array[i].length; i++){
                if(array[i][0]==null){
                        break;
                    }
                
            operacion.annadirDOM(array[i][0], array[i][1], array[i][2], array[i][3], array[i][4], array[i][5]);
            }
            
            System.out.println("Montando archivo xml...");
         
            int result= operacion.guardarDOMcomoFILE();
          
            if(result==0){
              System.out.println("Se han trasladado los datos del fichero txt a un archivo xml correctamente");
            }
           
        }
        
        
    }
    
}
