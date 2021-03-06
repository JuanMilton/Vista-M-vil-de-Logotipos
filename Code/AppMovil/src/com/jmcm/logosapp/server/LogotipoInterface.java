package com.jmcm.logosapp.server;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


public class LogotipoInterface implements Serializable {
    
    public String name;
    public String folder;
    public int stitches;
    public int colors;
    public int stops;
    public float width;
    public float height;
    public byte[] image;
    public long lastUpdate;
    
    
    public static byte[] serialize(LogotipoInterface obj) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(out);
        os.writeObject(obj);
        return out.toByteArray();
    }
    public static LogotipoInterface deserialize(byte[] data) throws IOException, ClassNotFoundException {
        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (LogotipoInterface)is.readObject();
    }
}
