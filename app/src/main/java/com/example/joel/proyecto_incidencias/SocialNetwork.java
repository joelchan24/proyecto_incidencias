package com.example.joel.proyecto_incidencias;

/**
 * Created by JOEL on 17/03/2018.
 */

public class SocialNetwork {
    private String name;

    private int icon;

    public SocialNetwork(String nombre, int icono)
    {
        super();
        this.name = nombre;
        this.icon = icono;
    }

    public String getNombre()
    {
        return name;
    }

    public void setNombre(String nombre)
    {
        this.name = nombre;
    }

    public int getIcono()
    {
        return icon;
    }

    public void setIcono(int icono)
    {
        this.icon = icono;
    }
}
