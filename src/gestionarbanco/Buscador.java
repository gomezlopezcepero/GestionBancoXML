/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestionarbanco;

/**
 *
 * @author Paco
 */
public class Buscador {
    
    public static void main(String[] args) {
        
        Operacion cxPath= new Operacion();
        
         if(cxPath.abrir_file_DOM_XPath()!=-1){
            String salida= cxPath.Ejecutar_XPath("//numcuenta[.='ES11-1111-2222-3333-4444-5555']|//numcuenta[.='ES11-1111-2222-3333-4444-5555']/following-sibling::*");
            
            System.out.println(salida);
         }
        
    }
    
}
