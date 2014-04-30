/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.business;

import java.util.*;
/**
 *
 * @author Juan
 */
public class Logotipo {
    
    public String name;
    public String location;
    public int stitches; ///puntadas
    public int colors;
    public int stops;
    public float width;
    public float height;
    public byte[] image;
    public Date lastUpdate;
    
    public Logotipo(String name, String location, int stitches, int colors, int stops, float width, float height, byte[] image, Date lastUpdate)
    {
        this.name = name;
        this.location = location;
        this.stitches = stitches;
        this.colors = colors;
        this.stops = stops;
        this.width = width;
        this.height = height;
        this.image = image;
        this.lastUpdate = lastUpdate;
    }
    
    @Override
    public String toString()
    {
        return "[Name : "+this.name+"\tLocation : "+this.location+"\tStitches : "+this.stitches+"\tColors : "+this.colors+"\tStops : "+this.stops+"\tWidth : "+this.width+"\tHeight : "+this.height+"\tImage : "+this.image.length+"\tLasUpdate : "+this.lastUpdate+"]";
    }
}
