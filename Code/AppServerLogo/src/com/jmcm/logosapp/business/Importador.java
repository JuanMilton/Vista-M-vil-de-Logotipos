/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.business;

import com.jmcm.logosapp.server.LogotipoInterface;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.*;
import javax.imageio.ImageIO;

/**
 *
 * @author Juan
 */
public class Importador {
    
    private static final String CARPETA_RAIZ = "BACKUP";

    public String[] getFilesCSV(String path)
    {
        File f = new File(path);
        ArrayList<String> fs = new ArrayList<String>();
        String[] fss = f.list();
        for (int i = 0; i < fss.length; i++) {
            if (fss[i].contains(".csv"))
                fs.add(path+fss[i]);
        }
        return fs.toArray(new String[]{});
    }
    
    public List<LogotipoInterface> importar(String path, Date dt) throws Exception {

        String[] paths = getFilesCSV(path);

        List<LogotipoInterface> logotipos = new ArrayList<>();
        
        for (int i = 0; i < paths.length; i++) {
            BufferedReader r = new BufferedReader(new FileReader(paths[i]));
            String line = r.readLine();
            while (line != null) {
                LogotipoInterface log = analizarLogotipo(path, line, dt);
                if (log != null) {
                    logotipos.add(log);
                }
                line = r.readLine();
            }
        }
        return logotipos;
    }

    private LogotipoInterface analizarLogotipo(String path, String value, Date dt) throws Exception {
        if (!value.equals("Design,Location,Stitches,Colors,Stops,Width,Height,ThumbNail,")) {
            String[] parts = value.split(",");
            File fsss = new File(parts[1]);//path + "TrueView_" + parts[0] + "-EMB-tn.png");
            Date fdt = new Date(fsss.lastModified());
            
            Date fdt2 = new Date(com.jmcm.logosapp.util.Util.getCreationDate(path + "TrueView_" + parts[0] + "-EMB-tn.png"));
            if (fdt2.compareTo(dt) > 0 || fdt.compareTo(dt) > 0) {
                BufferedImage image = ImageIO.read(new File(path + "TrueView_" + parts[0] + "-EMB-tn.png"));
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(image, "png", baos);
                byte[] img  = baos.toByteArray();
                
                File f = new File(parts[1]);
                Date fdts = new Date(f.lastModified());
//                if (fdts.getTime() != 0)
                {
                    LogotipoInterface l = new LogotipoInterface();
                    l.name = parts[0];
                    l.folder = obtenerFolder(parts[1]);
//                    if (l.folder.length() == 0)
//                        return null;
                    l.stitches = Integer.parseInt(parts[2]);
                    l.colors = Integer.parseInt(parts[3]);
                    l.stops = Integer.parseInt(parts[4]);
                    l.width = Float.parseFloat(parts[5].replace("\"", "") + "."+parts[6].replace("\"", ""));
                    l.height = Float.parseFloat(parts[7].replace("\"", "") + "."+parts[8].replace("\"", ""));
                    l.image = img;
                    l.lastUpdate = fdts.getTime();
                    return l;
                }
            }
        }
        return null;
    }
    
    public String obtenerFolder(String path)
    {
        String[] pats = path.split("\\\\");
        boolean sw = false;
        String cad = "";
        for(String p : pats)
        {
            if (sw)
                cad += p+"\\";
            if (!sw && p.equalsIgnoreCase("BACKUP"))
                sw = true;
        }
        if (!cad.isEmpty())
            return cad.substring(0, cad.length()-1);
        return path;
    }
}
