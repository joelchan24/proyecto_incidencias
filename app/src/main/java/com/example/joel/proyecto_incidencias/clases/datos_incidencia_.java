package com.example.joel.proyecto_incidencias.clases;

/**
 * Created by JOEL on 11/04/2018.
 */

public class datos_incidencia_ {
    private String incidencia ;
    private String cometario_admin;
    private String foto;

    public datos_incidencia_(String incidencia, String cometario_admin, String foto) {
        this.incidencia = incidencia;
        this.cometario_admin = cometario_admin;
        this.foto = foto;
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
