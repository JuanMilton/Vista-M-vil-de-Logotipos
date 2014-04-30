/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.test;

import com.jmcm.logosapp.server.Server;
import com.jmcm.logosapp.util.Parameters;
import java.awt.TrayIcon;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.Scanner;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 *
 * @author Juan
 */
public class Main {
    public static void main(String[] args) throws Exception {
        
        
        Server server = new Server(Parameters.puerto);
        server.start();
        JDialog dialog = new JDialog();
        JOptionPane.showMessageDialog(dialog, "Todo esta funcionando correctamente","FUNCIONANDO", TrayIcon.MessageType.WARNING.ordinal());
    }
    
    
}
