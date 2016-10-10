import java.awt.Color;
import java.io.File;
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
    public Nodo leerArchivoGif(String direccion){
        String doc = direccion;
        File archivoF=new File(direccion);   // Se crea un objeto de tipo FILE para obtener el nombre del GIF
        String asignatura = "", creacion = "", modificacion = "";
        String version = "";
        Nodo gif = new Nodo();
        gif.setNombre(archivoF.getName());//Se inseta el nombre del archivo en el atributo nombre
        archivoF=null;
        try (RandomAccessFile archivo = new RandomAccessFile(doc, "r")) {
            System.out.println(gif.getNombre());
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
    public void color(Nodo nodo, String packedB, String direccion)
    {
        try(RandomAccessFile archivo=new RandomAccessFile(direccion, "r")){
            
            
        } catch (IOException ex) {
            Logger.getLogger(Lista.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Funcion Insertar que va intresando a la lista los datos del Gif
     * Llama a leerArchivosGIf que retorna el nodo que tiene guardado la informacion del GIF
     * Ese nodo GIF va asignando al nuevo nodo que se agregue a la lista
     * @param direccion Direccion del GIF.
     */
    public void insertar (String direccion){
        Nodo gif = leerArchivoGif(direccion);   //Asigna datos del gif desde el archivo
        Nodo iterador = inicio.getSiguiente();
        if(gif != null){
            if (inicio.getSiguiente()== fin){
                gif.setAnterior(inicio);
                gif.setSiguiente(fin);
       
                /*//Ciclo llevar el arreglo de formato numerico
                for(int cont = 0; cont < gif.getCantidadimagenes(); cont++){
                    gif.setFormatonumerico(gif.getFormatonumerico(cont), cont); //Asigna el formato numerico al arreglo
                }*/
                //Enlaza los nodos
                inicio.setSiguiente(gif);
                fin.setAnterior(gif);
            }
            else {
                iterador = fin.getAnterior();                                   //Para encontrar el ultimo nodo que hay
                //Asigna datos del gif
                
                //Ciclo llevar el arreglo de formato numerico
                /*for(int cont = 0; cont < nuevo.getCantidadimagenes(); cont++){
                    nuevo.setFormatonumerico(gif.getFormatonumerico(cont), cont); //Asigna el formato numerico al arreglo
                }*/
                //Enlaza los nodos
                gif.setAnterior(iterador);
                iterador.setSiguiente(gif);
                gif.setSiguiente(fin);
                fin.setAnterior(gif);
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
                ancho = Integer.toHexString(archivo.readUnsignedByte()) + ancho;    //La cadena se concatena en hexadecimal primero
                //ancho = archivo.read() + ancho;
                //anch = anch + archivo.read();
            }
            archivo.seek(8);                                            //Nos lleva a la posicion donde comienza la altura
            for(int cont = 0; cont <2; cont++){
                largo = Integer.toHexString(archivo.readUnsignedByte()) + largo;    //Se concatena en hexadecimal
            }
            anch = Integer.parseInt(ancho, 16);                         //Se convierte a decimal
            larg = Integer.parseInt(largo, 16);                         //Se convierte a decimal
            gif.setAncho(anch);                                         //Se asigna al nodo el ancho
            gif.setAltura(larg);                                        //Se asigna al nodo la altura
            System.out.println("Ancho " + anch + " pixeles");
            System.out.println("Altura " + larg + " pixeles");
            //Encontrar le Packed Field
            pack = archivo.read();
            packed = Integer.toBinaryString(pack);
            System.out.println("Packed Field " + packed);
            
            if(packed.length()==8)//Si la cadena packedByte es menor a 8, quiere decir que
                //el primer bit de los 8 es 0, es decir no hay tabla de color
            {
                /**
                * utilizando los ultimos tres caracteres del Packed Byte, se convierte en entero
                * y se utiliza 2^(n+1) para hallar el numero de colores
                */
                gif.setCantidadcolores((int) Math.pow(2,Integer.parseInt(packed.substring(5,8), 2)+1));
                System.out.println("Cantidad de colores: "+gif.getCantidadcolores());
                /**
                * Se busca el color de fondo
                * Si el primer caracter del Packed Byte es 1, hay tabla de colores
                */
                int index=archivo.readUnsignedByte();//Se busca la posicion del color de fondo en la tabla de colores
                archivo.seek(13+index*3);/**Se coloca en la posicion 13 + posicion*3, 
                *para colocorse en la posicion del color de fondo, se suma 13, porque son los bytes inciales
                * Se multiplica la posicion del color de fondo *3 porque cada color esta formado por 3 bytes
                */
                int r=archivo.readUnsignedByte(),g=archivo.readUnsignedByte(),b=archivo.readUnsignedByte();
                //se leen 3 bytes consecutivos para obtener el codigo RGB (en entero) del color de fondo
                gif.setColorfondo(new Color(r,g,b));//Se envia un color de fondo con los enteros rgb
                System.out.println("Color de fondo: "+Integer.toHexString(gif.getColorfondo().getRed())+Integer.toHexString(gif.getColorfondo().getGreen())+Integer.toHexString(gif.getColorfondo().getBlue()));
                archivo.seek(13+gif.getCantidadcolores()*3);//Se coloca en la posicion 13 + cantidad de colores*3
                //para estar en el byte siguiente a la tabla de colores, para empezar a leer los cloques internos del gif 
                
            }
            else
            {
                gif.setCantidadcolores(0);//si no hay tabla de colores, no hay color de fondo
                gif.setColorfondo(new Color(0,0,0));//Se le da el color por defecto 0 (en entero) o #000000 (Negro)
                archivo.seek(13);/**
                 * Se colocan en la posicion 13, que es la ultima posicion del descriptor de pantalla logica;
                 */
            }
            String B =Integer.toHexString(archivo.readUnsignedByte());  //cadena que lee los bytes de forma hexadecimal
            int saltarB, formatoNumerico=0, contadorImagenes=0; 
            String comentario=""; //comienza la cadena para almacenar el comentario
            while(!"3b".equals(B)) //Empieza la lectura de los bloques posteriores y acaba en el caracter (en hexadecimal) 3b
            {
                if("21".equals(B))//Si el byte en hexadecimal es un bloque, entonces es un bloque
                {
                    B =Integer.toHexString(archivo.readUnsignedByte());//lee el siguiente byte a la etiqueta
                    if(B.equals("fe"))//fe indica que es un comentario
                    {
                        B =Integer.toHexString(archivo.readUnsignedByte());//se lee el siguiente byte para
                        //conocer cuantos bytes ocupa el comentario
                        while(!B.equals("0"))//Cuando el byte leido sea cero, finalizo el bloque
                        {
                            saltarB=Integer.parseInt(B, 16);//Almacena el numero de bytes que ocupa el comentario      
                            for(int i=0;i<saltarB;i++)
                            {
                                B =Integer.toHexString(archivo.readUnsignedByte());//Lee cada byte del comentario
                                comentario+=(char)Integer.parseInt(B, 16);//se concatenan los bytes del comentario
                            }
                            B =Integer.toHexString(archivo.readUnsignedByte());//se lee el byte siguiente al ultimo
                            //caracter del comentario
                        }
                    }
                    else if(B.equals("ff") || B.equals("1"))//Las etiquetas ff y 1, indican un bloque de aplicacion
                        //o una extension de texto
                    {
                        B =Integer.toHexString(archivo.readUnsignedByte());//se lee el siguiente byte que indica 
                        //cuantos bytes ocupa  el bloque
                        while(!B.equals("0"))
                        {
                            saltarB=Integer.parseInt(B,16);//se almacena los bytes que deben saltarse
                            archivo.skipBytes(saltarB);//salta esa cantidad
                            B =Integer.toHexString(archivo.readUnsignedByte());//lee el siguiente byte
                            // si este es 0, no hay sub-bloques, no es necesario seguir saltando
                            //si es diferente de 0, debe repetirse el ciclo de salto
                        }
                        
                    }
                    else if(B.equals("f9"))//Indica que es un bloque de contro grafico
                    {
                        B=Integer.toHexString(archivo.readUnsignedByte());//Se lee el siguiente byte que indica
                        //cuntos bytes ocupa el bloque
                        while(!B.equals("0"))
                        {
                            saltarB=Integer.parseInt(B,16);//se almacena el numero de bytes como entero
                            archivo.skipBytes(saltarB);//se saltan la antidad de bytes que se indica
                            B =Integer.toHexString(archivo.readUnsignedByte());
                            //se lee el siguiente byte, si este es igual a 0, indica que se acabo el bloque
                            //si es diferente a 0, debe repetirse el ciclo
                        }
                        B =Integer.toHexString(archivo.readUnsignedByte());//se lee el siguiente byte
                        if(B.equals("2c"))//si es 2c, indica un descriptor de imagen
                        {
                            archivo.skipBytes(8);//Se saltan 8 bytes, para llegar al packed byte del descriptor de imagen
                            contadorImagenes++;//Se suma 1 al cotador de imagenes
                            String packedB=Integer.toBinaryString(archivo.readUnsignedByte());
                            //se almacen en packed byte como una cadena bynaria
                            if(packedB.length()==8){//si el tamaño es 8 quiere decir que el primero de 8 bit es 1, existe una tabla local de colores
                                int tamañoTabla=(int) Math.pow(2,Integer.parseInt(packedB.substring(5,8),2)+1);
                                //el tamaño de la tabla esta dado por 2^(n+1) donde n es el numero entero obtenido a partir de los
                                //ultimos 3 bits del packed
                                archivo.skipBytes(tamañoTabla*3);//se salta la tabla local de colores
                            }
                            archivo.readUnsignedByte();//se lee el siguiente byte, el primer byte del image data
                            B =Integer.toHexString(archivo.readUnsignedByte());//Se lee el siguiente byte que indica 
                            //la longitud
                            int[] arreglo=new int[contadorImagenes];// se crea un arreglo del tamaño de cantidad de imagenes
                            for(int i=0;i<gif.getFormatonumerico().length;i++)//Se transcribe el arreglo de formato numerico del gif al nuevo
                            {
                                arreglo[i]=gif.getFormatonumerico()[i];//se copia posicion por posicion
                            }
                            //como el contador de imagenes a aumentado en 1, el arreglo local tendra una posicion mas que el gif
                            formatoNumerico=0; // se inicializa en 0 para comenzar el conteo
                            while(!B.equals("0"))
                            {        
                                saltarB=Integer.parseInt(B, 16);//se almacena la longitud como int
                                formatoNumerico=formatoNumerico+1+saltarB;//se suma el numero de bytes de longitud
                                archivo.skipBytes(saltarB);//se salta esta cantidad para continuar el conteo
                                B =Integer.toHexString(archivo.readUnsignedByte()); //se lee el siguiente byte para determinar
                                //si el bloque ha acabado, si el byte es 0-acabo, si no vuelve a repetirse el ciclo
                            }
                            arreglo[contadorImagenes-1]=formatoNumerico;//se le asigna a la ultima posicion del arreglo el
                            //formato numerico de la nueva imagen
                            gif.setFormatonumerico(arreglo);// se setea el nuevo arreglo como arreglo de formato numerico
                        }
                    }
                }
                B =Integer.toHexString(archivo.readUnsignedByte());//se lee el siguiente byte despues de leer los bloques
            }
            gif.setCantidadimagenes(contadorImagenes);
            gif.setComentarios(comentario);
            System.out.println("Cantidad de imagenes: "+gif.getCantidadimagenes());
            for(int i=0;i<gif.getFormatonumerico().length;i++)
            {
                System.out.println("Imagen "+(i+1)+"   "+gif.getFormatonumerico()[i]);
            }
            System.out.println("Comentarios: "+gif.getComentarios());
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
