package com.example.joel.proyecto_incidencias.clases;

/**
 * Created by JOEL on 11/04/2018.
 */

public class datos_incidencia_ {
    private String id_usuario;

    private double latitud;
    private double longitu;
    private  String direccion;
    private String id_incidencia;
    private String incidencia ;
    private String cometario_admin;
    private String foto;

    public int getId_punto() {
        return id_punto;
    }

    public void setId_punto(int id_punto) {
        this.id_punto = id_punto;
    }

    private int id_punto;

    public double getLatitud() {
        return latitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public  String comentario;

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitu() {
        return longitu;
    }

    public void setLongitu(double longitu) {
        this.longitu = longitu;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getId_incidencia() {
        return id_incidencia;
    }

    public void setId_incidencia(String id_incidencia) {
        this.id_incidencia = id_incidencia;
    }


    public datos_incidencia_(String incidencia, String cometario_admin, String foto,double latitud,double longitu,String direecion,String id_incidencia,String comentario,int id_punto) {
        this.incidencia = incidencia;
        this.cometario_admin = cometario_admin;
        this.foto = foto;

        this.latitud=latitud;
        this.longitu=longitu;
        this.direccion=direecion;
        this.id_incidencia=id_incidencia;
        this.comentario=comentario;
this.id_punto=id_punto;

    }
    public String getIncidencia() {
        return incidencia;
    }

    public void setIncidencia(String incidencia) {
        this.incidencia = incidencia;
    }

    public String getCometario_admin() {
        return cometario_admin;
    }

    public void setCometario_admin(String cometario_admin) {
        this.cometario_admin = cometario_admin;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }







}
