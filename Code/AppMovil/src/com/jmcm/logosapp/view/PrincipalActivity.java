package com.jmcm.logosapp.view;

import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jmcm.logosapp.busines.SettingBL;
import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.logosapp.data.context.LogotipoB;
import com.jmcm.logosapp.data.entities.Logotipo;
import com.jmcm.logosapp.data.entities.Setting;
import com.jmcm.logosapp.server.Synchronizer;
import com.jmcm.view.logosapp.R;

public class PrincipalActivity extends Activity {

	public SettingBL settingBL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);

		settingBL = new SettingBL(this);
//		Toast.makeText(getBaseContext(), "LAST UPDATE: " + settingBL.getSetting().getLastUpdate().toString(), Toast.LENGTH_LONG).show();
//		cargarDatos();
	}
	
	
	
	private void cargarDatos()
	{
		TextView tv = (TextView)findViewById(R.id.principal_tx_last_update);
		tv.setText(settingBL.getSetting().getLastUpdate().toString());
	}
	
	
	
	
	// begin_region Eventos de los botones

	@Override
	protected void onResume() {
		settingBL = new SettingBL(this);
		super.onResume();
	}



	public void onActualizarClick(View view) {
		Synchronizer s = new Synchronizer(settingBL.getSetting().getIp(),settingBL.getSetting().getPuerto(), this);
		if (s.client == null) return;
		try {
			 
			LogotipoB[] logos = s.synchronize(new Date(settingBL.getSetting().getLastUpdate().getTime())).toArray(new LogotipoB[]{});
//			LogotipoB[] logos = s.synchronize(new Date(0)).toArray(new LogotipoB[]{});
			java.util.Date t = new java.util.Date();
			if (logos.length == 0)
			{
				Toast.makeText(getBaseContext(), "NO HAY DISEÑOS NUEVOS", Toast.LENGTH_LONG).show();
				return;
			}
			Log.d("Descarga finalizada","Descarga finalizada");
			guardar(logos,t);
			Toast.makeText(getBaseContext(), "CORRECTO", Toast.LENGTH_SHORT).show();
//			Intent i = new Intent(this,ActualizarActivity.class);
//			i.putExtra("logos", logos);
//			startActivity(i);
		} catch (Exception e) {
			Log.e("Error al sincronizar","MENSAJE : " + e.getMessage(), e);
		}
	}
	
	private void guardar(LogotipoB[] logos, java.util.Date lastUpdate)
	{
		try{
			LogosDataContext dc = new LogosDataContext(this);
			dc.logotipos.save();
			
			for (int i = logos.length-1; i >= 0; i--) {
				
				dc.eliminarLogotipo(logos[i].getFolder());
				
				Logotipo l = new Logotipo();
				l.setName(logos[i].getName());
				l.setColors(logos[i].getColors());
				l.setHeight((double)logos[i].getHeight());
				l.setImage(logos[i].getImage());
				l.setLastUpdate(new Date(logos[i].getLastUpdate()));
				l.setFolder(logos[i].getFolder());
				l.setStitches(logos[i].getStitches());
				l.setStops(logos[i].getStops());
				l.setWidth((double)logos[i].getWidth());
				dc.logotipos.add(l);
			}
			
			dc.logotipos.save();
			
//			Toast.makeText(getBaseContext(), "Guardado correctamente", Toast.LENGTH_SHORT).show();
			
			SettingBL settingBL = new SettingBL(this);
			Setting stt = new Setting();
			stt.setIp(settingBL.getSetting().getIp());
			stt.setPuerto(settingBL.getSetting().getPuerto());
			stt.setLastUpdate(lastUpdate);
			settingBL.save(stt);
			
		}catch(Exception e)
		{
			Toast.makeText(getBaseContext(), "Error al guardar : " + e.getMessage(), Toast.LENGTH_LONG).show();
		}
	}
	

	public void onRecientesClick(View view) {
		Intent i = new Intent(this,LogotiposActivity.class);
		i.putExtra("folder", "");
		startActivity(i);
		
//		Intent i = new Intent(this, PaginatorActivity.class);
//		startActivity(i);
		
	}

	public void onFoldersClick(View view) {
		Intent i = new Intent(this,FoldersActivity.class);
		i.putExtra("navegacion", "");
		startActivity(i);
	}


	// end_region

}
