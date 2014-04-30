/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;

/**
 *
 * @author Juan
 */
public class Util {
    
    public static long getCreationDate(String dir) throws Exception
    {
        Path path = Paths.get(dir);
        BasicFileAttributes attributes = 
                Files.readAttributes(path, BasicFileAttributes.class);
        FileTime creationTime = attributes.creationTime();
        return creationTime.toMillis();
    }
    
}
