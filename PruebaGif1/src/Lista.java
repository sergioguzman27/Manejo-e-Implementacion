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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SERGIO
 */
public class Lista {
    int tamano;
    private Nodo inicio = new Nodo();
    private Nodo fin = new Nodo();

    public Lista() {
        inicio.setSiguiente(null);
        fin.setAnterior(null);
        
    }

    public int getTamano() {
        return tamano;
    }
    /**
     * Fucnion que lee los datos basicos del GIF (Los que comparten 87a y 89a)
     * Compara si el archivo es un GIF
     * Llama a funcionprimera version si es 87a
     * LLama a funcion segundaversion si es 89a
     * @param direccion Direccion del GIF
     * @return Retorna el nodo ya con los datos guardados del GIF
     */
    public Nodo leerArchivoGif(String direccion){
        String doc = direccion;
        String asignatura = "", creacion = "", modificacion = "";
        String version = "";
        Nodo gif = new Nodo();
        try (RandomAccessFile archivo = new RandomAccessFile(doc, "r")) {
            for(int cont = 0; cont < 3; cont++){
                asignatura += (char)archivo.read(); //Concateno los primeros 3 bytes
            }
            System.out.println(asignatura); //Imprimo la asignatura ejem "GIF"
            if ("GIF".equals(asignatura)){ // Comparo si es GIF
                //Espacio para el nombre del GIF
                archivo.seek(3);
                for(int cont = 0; cont <3; cont++){
                    version += (char)archivo.read();    // Concateno el numero de version si es 87a o 89a
                }
                gif.setVersion(version);                //Se le asigna el valor de la version al gif
                System.out.println("Version " + version);
                                //Muestro la fecha de creacion y modificacion
                creacion = fechacreacion(doc);
                modificacion = fechamodificacion(doc);
                gif.setFechacreacion(creacion);
                gif.setFechamodificacion(modificacion);
                System.out.println("Fecha de Creacion " + creacion);
                System.out.println("Fecha de Modificacion " + modificacion);
                leermasdatos(doc,gif);                  //Se llama a la fucnion para leer los demas datos que quedan
                if (("87a".equals(version))){   //Verificico si es 87a
                    gif.setComentarios("Comentarios no dispobile en esta version");
                }
                else if (("89a".equals(version))) { // Verifico si es 89a
                }
                return gif;
            }
            else{
                System.out.println("No es GIF");
                return null;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DatosGif.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    /**
     * Funcion Insertar que va intresando a la lista los datos del Gif
     * Llama a leerArchivosGIf que retorna el nodo que tiene guardado la informacion del GIF
     * Ese nodo GIF va asignando al nuevo nodo que se agregue a la lista
     * @param direccion Direccion del GIF.
     */
    public void insertar (String direccion){
        Nodo gif = leerArchivoGif(direccion);
        Nodo nuevo = new Nodo();
        Nodo iterador = inicio.getSiguiente();
        if(gif != null){
            if (inicio.getSiguiente()== null){
                nuevo.setAnterior(inicio);
                //Asigna datos del gif
                nuevo.setNombre(gif.getNombre());
                nuevo.setVersion(gif.getVersion());
                nuevo.setAncho(gif.getAncho());
                nuevo.setAltura(gif.getAltura());
                nuevo.setCantidadcolores(gif.getCantidadcolores());
                nuevo.setCompresion(gif.getCompresion());
                nuevo.setColorfondo(gif.getColorfondo());
                nuevo.setCantidadimagenes(gif.getCantidadimagenes());
                nuevo.setFechacreacion(gif.getFechacreacion());
                nuevo.setFechamodificacion(gif.getFechamodificacion());
                nuevo.setComentarios(gif.getComentarios());
                nuevo.inciarformatonumerico(nuevo.getCantidadimagenes());
                //Ciclo llevar el arreglo de formato numerico
                for(int cont = 0; cont < nuevo.getCantidadimagenes(); cont++){
                    nuevo.setFormatonumerico(gif.getFormatonumerico(cont), cont); //Asigna el formato numerico al arreglo
                }
                //Enlaza los nodos
                inicio.setSiguiente(nuevo);
                nuevo.setSiguiente(fin);
                fin.setAnterior(nuevo);
            }
            else {
                iterador = fin.getAnterior();                                   //Para encontrar el ultimo nodo que hay
                //Asigna datos del gif
                nuevo.setNombre(gif.getNombre());
                nuevo.setVersion(gif.getVersion());
                nuevo.setAncho(gif.getAncho());
                nuevo.setAltura(gif.getAltura());
                nuevo.setCantidadcolores(gif.getCantidadcolores());
                nuevo.setCompresion(gif.getCompresion());
                nuevo.setColorfondo(gif.getColorfondo());
                nuevo.setCantidadimagenes(gif.getCantidadimagenes());
                nuevo.setFechacreacion(gif.getFechacreacion());
                nuevo.setFechamodificacion(gif.getFechamodificacion());
                nuevo.setComentarios(gif.getComentarios());
                nuevo.inciarformatonumerico(nuevo.getCantidadimagenes());
                //Ciclo llevar el arreglo de formato numerico
                for(int cont = 0; cont < nuevo.getCantidadimagenes(); cont++){
                    nuevo.setFormatonumerico(gif.getFormatonumerico(cont), cont); //Asigna el formato numerico al arreglo
                }
                //Enlaza los nodos
                nuevo.setAnterior(iterador);
                iterador.setSiguiente(nuevo);
                nuevo.setSiguiente(fin);
                fin.setAnterior(nuevo);
            }
            tamano++;
        }
    }
    /**
     * Funcion que va leyendo y guardando los datos
     * Roberto es la funcion para leer los demas datos que faltan.
     * @param direccion Direccion del GIF
     * @param gif Nodo donde se almacenan los datos de un GIF
     */
    public void leermasdatos(String direccion, Nodo gif){
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
            gif.setAncho(anch);                                         //Se asigna al nodo el ancho
            gif.setAltura(larg);                                        //Se asigna al nodo la altura
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
    /**
     * Funcion que devuelve la fecha de creacion
     * @param direccion Direccion del GIF
     * @return Retorna la fecha
     * @throws IOException 
     */
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
    /**
     * Funcion que retorna la fecha de modificacion
     * @param direccion Direccion del GIF
     * @return Retorna la fecha
     * @throws IOException 
     */
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
}
