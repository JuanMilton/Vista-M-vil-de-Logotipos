/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmcm.logosapp.server;

import com.jmcm.logosapp.business.Importador;
import com.jmcm.logosapp.util.Parameters;
import java.net.*;
import java.io.*;
import java.util.Date;
import java.util.List;
/**
 *
 * @author Juan
 */
public class Server extends Thread{
    
    private ServerSocket server;
    private boolean runnable;
    private String PATH_IMPORTADOR = Parameters.path_importacion;
    
    public Server(int port)
    {
        try{
            server = new ServerSocket(port);
            runnable = true;
        }catch(Exception e)
        {
            e.printStackTrace();
            runnable = false;
        }
    }
    
    @Override
    public void run()
    {
        try{
            while (runnable)
            {
                Socket client = server.accept();
                try{
                    System.out.println("CLIENTE ACEPTADO " + client.getInetAddress().getHostName() + " - " + client.getInetAddress().getHostAddress());
                    BufferedReader entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    DataOutputStream salida = new DataOutputStream(client.getOutputStream());
                    sincronizarCliente(entrada,salida,client);
                    client.close();
                }catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
    private void sincronizarCliente(BufferedReader in, DataOutputStream out, Socket client) throws Exception
    {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        String solicitudActualizacion = in.readLine();
        System.out.println(solicitudActualizacion);
        long time = Long.parseLong(solicitudActualizacion);
        Date t = new Date(time);
        System.out.println("Date : " + t);
        
        Importador importador = new Importador();
        List<LogotipoInterface> logos = importador.importar(PATH_IMPORTADOR, t); /// SE IMPORTA TODOS LOS LOGOTIPOS EXISTENTES EN ESTA MAQUINA
        
        int cantLogotipos = logos.size();
        bw.write(cantLogotipos+"\n");
        bw.flush();
        
        String AR = in.readLine();
        System.out.println("" + AR + " 1");
        if (AR.equals("ESTO ES UN ACUSE DE RECIBO - jaja XD"))
        {
            if (cantLogotipos == 0) return;
            
            for (LogotipoInterface lo : logos)
            {
                byte[] logoo = LogotipoInterface.serialize(lo);
                int cantBytesLogoo = logoo.length;
                bw.write(cantBytesLogoo+"\n");
                bw.flush();
                AR = in.readLine();
                System.out.println("2 " + AR);
                if (AR.equals("ESTO ES UN ACUSE DE RECIBO - jaja XD"))
                {
                    out.write(logoo);
                    out.flush();
                    AR = in.readLine();
                    if (! AR.equals("ESTO ES UN ACUSE DE RECIBO - jaja XD"))
                        System.out.println("INCORRECTO ACUSE DE RECIBO");
                }
                else
                    System.out.println("INCORRECTO ACUSE DE RECIBO");
            }
        }else
            System.out.println("INCORRECTO ACUSE DE RECIBO");
    }
    
    public void detener()
    {
        try{
            runnable = false;
            server.close();
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    
}
