/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SERGIO
 */
public class Nodo {
    String nombre;
    String version;
    int ancho;
    int altura;
    int cantidadcolores;
    String compresion;
    String[] formatonumerico;
    String colorfondo;
    int cantidadimagenes;
    String fechacreacion;
    String fechamodificacion;
    String comentarios;
    Nodo siguiente;
    Nodo anterior;

    public Nodo() {
        this.nombre = "";
        this.version = "";
        this.altura = 0;
        this.ancho = 0;
        this.cantidadcolores = 0;
        this.compresion = "";
        this.colorfondo = "";
        this.cantidadimagenes = 0;
        this.fechacreacion = "";
        this.fechamodificacion = "";
        this.comentarios = "";
        this.siguiente = null;
        this.anterior = null;
    }
    /**
     * Inicializa el arreglo String iniciarformatonumerico
     * @param n Tamaño del arreglo
     */
    public void inciarformatonumerico(int n){
        String[] formatonumerico = new String[n];
    }

    public String getNombre() {
        return nombre;
    }
    
    public String getVersion() {
        return version;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAltura() {
        return altura;
    }

    public int getCantidadcolores() {
        return cantidadcolores;
    }

    public String getCompresion() {
        return compresion;
    }

    public String getFormatonumerico(int n) {
        return formatonumerico[n];
    }

    public String getColorfondo() {
        return colorfondo;
    }

    public int getCantidadimagenes() {
        return cantidadimagenes;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public String getFechamodificacion() {
        return fechamodificacion;
    }

    public String getComentarios() {
        return comentarios;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public Nodo getAnterior() {
        return anterior;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    public void setVersion(String version) {
        this.version = version;
    }

    public void setAncho(int ancho) {
        this.ancho = ancho;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }

    public void setCantidadcolores(int cantidadcolores) {
        this.cantidadcolores = cantidadcolores;
    }

    public void setCompresion(String compresion) {
        this.compresion = compresion;
    }

    public void setFormatonumerico(String formatonumerico, int n) {
        this.formatonumerico[n] = formatonumerico;
    }

    public void setColorfondo(String colorfondo) {
        this.colorfondo = colorfondo;
    }

    public void setCantidadimagenes(int cantidadimagenes) {
        this.cantidadimagenes = cantidadimagenes;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public void setFechamodificacion(String fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }

    public void setAnterior(Nodo anterior) {
        this.anterior = anterior;
    }
    
}
