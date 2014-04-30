package com.jmcm.logosapp.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Context;
import android.os.StrictMode;
import android.util.Log;
import android.widget.Toast;

import com.jmcm.logosapp.data.context.LogotipoB;
import com.jmcm.logosapp.data.entities.Folder;

public class Synchronizer {
	
	public Socket client;
	private Context context;
	
	private static final int TAM_BUFFER = 20000;
	
	private BufferedReader entrada;
	
	public Synchronizer(String IP, int port, Context context)
	{
		this.context = context;
		try{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
			
			client = new Socket(IP, port);
			entrada = new BufferedReader(new InputStreamReader(client.getInputStream()));
			Toast.makeText(this.context, "Conectado", Toast.LENGTH_SHORT).show();
		}catch(Exception e){
			Log.e("Error al iniciar el Socket","Fallo inicio del Socket",e);
			if (e.getMessage().contains("No route to host"))
				Toast.makeText(this.context, "VUELVA A INTENTARLO", Toast.LENGTH_LONG).show();
			else
				Toast.makeText(this.context, "ENCIENDA EL WIFI", Toast.LENGTH_LONG).show();
		}
	}
	
	public List<LogotipoB> synchronize(Date lastUpdate) throws Exception
	{
		if (client != null && client.isConnected())
		{
			List<LogotipoB> logos = new ArrayList<LogotipoB>();
			
			sendMensaje(formarSolicitudActualizacion(lastUpdate));
			Log.i("Enviando mensaje","Solicitud de Sincronizacion");
			int cantLogotipos = receiveInt();
			Log.d("Cantidad de Logotipos",cantLogotipos+"");
			sendMensaje(formarAcuseRecibo());
			for (int i = 0; i < cantLogotipos; i++) {
				int cantBytesLogotipoI = receiveInt();
				Log.d("Cantidad de Bytes",cantBytesLogotipoI+"");
				sendMensaje(formarAcuseRecibo());
				LogotipoInterface logotipo = recibirLogotipo(cantBytesLogotipoI);
				Log.d("Logotipo Nro " +i +" Recibido", "Logotipo : "+logotipo.name);
				sendMensaje(formarAcuseRecibo());
				
				LogotipoB l = new LogotipoB();
				l.setName(logotipo.name);
			    l.setFolder(logotipo.folder);
			    l.setStitches(logotipo.stitches);
			    l.setColors(logotipo.colors);
			    l.setStops(logotipo.stops);
			    l.setWidth(logotipo.width);
			    l.setHeight(logotipo.height);
			    l.setImage(logotipo.image);
			    l.setLastUpdate(logotipo.lastUpdate);
				
				logos.add(l);
			}
			return logos;
		}
		return null;
	}
	
	public LogotipoInterface recibirLogotipo(int bytesLogotipo)
	{
		byte[] resFinal = new byte[0];
		int contador= 0;
		while (contador < bytesLogotipo)
		{
			byte[] aki = new byte[TAM_BUFFER];
			int leidos = 0;
			try {
				leidos = client.getInputStream().read(aki);
			} catch (IOException e) {
				Log.e("Error al recibir Logotipo", "FALLO: " + e.getMessage(),e);
				return null;
			}
			resFinal = unir(resFinal, aki, leidos);
			contador += leidos;
		}
		try{
			return LogotipoInterface.deserialize(resFinal);
		}catch(Exception e)
		{
			Log.e("Error al deserealizar","Mensaje : " + e.getMessage(),e);
			return null;
		}
	}
	
	private byte[] unir(byte[] a, byte[] b, int lb)
	{
		byte[] res = new byte[a.length + lb];
		System.arraycopy(a, 0, res, 0, a.length);
		System.arraycopy(b, 0, res, a.length, lb);
		return res;
	}
	
	//begin_region METODOS PARA FORMAR LOS MENSAJES SOCKET
	private String formarSolicitudActualizacion(Date lastUpdate) {
		String cad = lastUpdate.getTime()+"";
//		for(Folder folder : folders)
//			cad += folder.getName()+"=";
		return cad;
	}
	
	private String formarAcuseRecibo()
	{
		return "ESTO ES UN ACUSE DE RECIBO - jaja XD";
	}
	//end_region

	//begin_region SENDER Y RECEIVER SOCKET
	public void sendMensaje(String mensaje) throws Exception
	{
		mensaje += "\n";
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
		out.write(mensaje);
		out.flush();
	}
	
	public int receiveInt() throws Exception
	{
		return Integer.parseInt(entrada.readLine());
	}
	
	//end_region
}
