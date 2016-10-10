import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JList;
import javax.swing.JOptionPane;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SERGIO
 */
public class Master {
    //File [] archivos;
    
    public static void archivosGIF(File[] archivo, Lista gif){
        String direccion = "";
        File[] archivos;
        for(int cont = 0; cont < archivo.length; cont++){
            if (archivo[cont].isDirectory() == true){
                archivos = archivo[cont].listFiles();
                archivosGIF(archivos,gif);
            }
            else{
                direccion = archivo[cont].getAbsolutePath();
                gif.insertar(direccion);
            }
        }
    }
    
}
