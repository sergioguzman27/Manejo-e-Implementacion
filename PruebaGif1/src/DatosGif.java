import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SERGIO
 */
public class DatosGif {
    int numerogifs;

    public DatosGif(int numerogifs) {
        this.numerogifs = numerogifs;
    }
    public void leerArchivoGif(String direccion){
        String doc = direccion;
        String asignatura = "", creacion = "", modificacion = "";
        String version = "";
        try (RandomAccessFile archivo = new RandomAccessFile(doc, "r")) {
            for(int cont = 0; cont < 3; cont++){
                asignatura += (char)archivo.read(); //Concateno los primeros 3 bytes
            }
            System.out.println(asignatura); //Imprimo la asignatura ejem "GIF"
            if ("GIF".equals(asignatura)){ // Comparo si es GIF
                archivo.seek(3);
                for(int cont = 0; cont <3; cont++){
                    version += (char)archivo.read();  // Concateno el numero de version si es 87a o 89a
                }
                System.out.println("Version " + version);
                if (("87a".equals(version))){   //Verificico si es 87a
                    primeraversion(doc); // Llamo a una funcion para la version de 87a
                }
                else if (("89a".equals(version))) { // Verifico si es 89a
                    segundaversion(doc); //Llamo a una funcion para 89a
                }
                //Muestro la fecha de creacion y modificacion
                creacion = fechacreacion(doc);
                modificacion = fechamodificacion(doc);
                System.out.println("Fecha de Creacion " + creacion);
                System.out.println("Fecha de Modificacion " + modificacion);
                //Sumo a la variable para llevar control de cuantos gif se han visto
                numerogifs++;
            }
            else{
                System.out.println("No es GIF");
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void primeraversion(String direccion){
         String ancho = "", largo = "", packed = "";
        int anch = 0, larg = 0, pack = 0;
        try (RandomAccessFile archivo = new RandomAccessFile(direccion, "r")) {
            //Encontrar las dimensiones del GIF
            archivo.seek(6);                                            //Nos lleva a la posicion donde comienza el ancho
            for(int cont = 0; cont < 2; cont++){
                ancho = Integer.toHexString(archivo.read()) + ancho;    //La cadena se concatena en hexadecimal primero
                //ancho = archivo.read() + ancho;
                //anch = anch + archivo.read();
            }
            archivo.seek(8);                                            //Nos lleva a la posicion donde comienza la altura
            for(int cont = 0; cont <2; cont++){
                largo = Integer.toHexString(archivo.read()) + largo;    //Se concatena en hexadecimal
            }
            anch = Integer.parseInt(ancho, 16);                         //Se convierte a decimal
            larg = Integer.parseInt(largo, 16);                         //Se convierte a decimal
            System.out.println("Ancho " + anch + " pixeles");
            System.out.println("Altura " + largo + " pixeles");
            //Encontrar le Packed Field
            pack = archivo.read();
            packed = Integer.toBinaryString(pack);
            System.out.println("Packed Field " + packed);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void segundaversion(String direccion){
        String ancho = "", largo = "", packed = "";
        int anch = 0, larg = 0, pack = 0;
        try (RandomAccessFile archivo = new RandomAccessFile(direccion, "r")) {
            //Encontrar las dimensiones del GIF
            archivo.seek(6);                                            //Nos lleva a la posicion donde comienza el ancho
            for(int cont = 0; cont < 2; cont++){
                ancho = Integer.toHexString(archivo.read()) + ancho;    //La cadena se concatena en hexadecimal primero
                //ancho = archivo.read() + ancho;
                //anch = anch + archivo.read();
            }
            archivo.seek(8);                                            //Nos lleva a la posicion donde comienza la altura
            for(int cont = 0; cont <2; cont++){
                largo = Integer.toHexString(archivo.read()) + largo;    //Se concatena en hexadecimal
            }
            anch = Integer.parseInt(ancho, 16);                         //Se convierte a decimal
            larg = Integer.parseInt(largo, 16);                         //Se convierte a decimal
            System.out.println("Ancho " + anch + " pixeles");           //Se imprime el ancho ya en decimal
            System.out.println("Altura " + larg + " pixeles");          //Se imprime la altura ya en decimal
            //Encontrar le Packed Field
            pack = archivo.read();                                      //Lee el Byte 9
            packed = Integer.toBinaryString(pack);                      //Lo convierte a binario y lo almacena en la cadena
            System.out.println("Packed Field " + packed);               //Imprime el binario 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public String fechacreacion(String direccion) throws IOException{
        Path file = Paths.get(direccion);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        String creacion = "", retorno = "";
        int cont = 0;
        creacion = attr.creationTime().toString();
        while(creacion.charAt(cont) != 'T'){
            retorno += (char)creacion.charAt(cont);
            cont++;
        }
        return retorno;
    }
    public String fechaacceso(String direccion) throws IOException{
        Path file = Paths.get(direccion);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        String acceso = "", retorno = "";
        int cont = 0;
        acceso = attr.lastAccessTime().toString();
        while(acceso.charAt(cont) != 'T'){
            retorno += (char)acceso.charAt(cont);
            cont++;
        }
        return retorno;
    }
    public String fechamodificacion(String direccion) throws IOException{
        Path file = Paths.get(direccion);
        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
        String modificacion = "", retorno = "";
        int cont = 0;
        modificacion = attr.lastModifiedTime().toString();
        while(modificacion.charAt(cont) != 'T'){
            retorno += (char)modificacion.charAt(cont);
            cont++;
        }
        return retorno;
    }
    public static void main(String ... args){
        DatosGif gif = new DatosGif(0);
        gif.leerArchivoGif("C:\\Users\\SERGIO\\Desktop\\GIF\\alimento.gif");
        System.out.println("Adios");
    }
    
}
