package com.jmcm.logosapp.view;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.jmcm.logosapp.busines.SettingBL;
import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.logosapp.data.entities.Setting;
import com.jmcm.view.logosapp.R;

public class ConfigurationActivity extends Activity{
	
	LogosDataContext dc;
	EditText ed_ip;
	EditText ed_puerto;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_configuration);
		try{
			dc = new LogosDataContext(this);
//			setBL = new SettingBL(this);
			
			ed_ip = (EditText)findViewById(R.id.configuration_ed_ip);
			ed_puerto = (EditText)findViewById(R.id.configuration_ed_puerto);
			
			dc.settings.save();
			dc.settings.fill();
			if (dc.settings.size() > 0)
			{
				ed_ip.setText(dc.settings.get(0).getIp());
				ed_puerto.setText(dc.settings.get(0).getPuerto()+"");
			}
		}catch(Exception e)
		{
			Log.e("Error al obtener la informacion","Message : "+e.getMessage(),e);
			Toast.makeText(getBaseContext(), "No se pudo leer la informacion", Toast.LENGTH_LONG).show();
			this.finish();
		}
		
	}

	public void eventSave(View sd)
	{
		
		try{
			
			Setting s = new Setting();
			s.setLastUpdate(dc.settings.get(0).getLastUpdate());
			
			s.setIp(ed_ip.getText().toString());
			s.setPuerto(Integer.parseInt(ed_puerto.getText().toString()));
//			Toast.makeText(getBaseContext(), "IP : " + ed_ip.getText().toString()+", 	PUERTO : " + ed_puerto.getText().toString(), Toast.LENGTH_SHORT).show();
			dc.settings.fill();
			dc.settings.removeAll();			
			dc.settings.save();
			dc.settings.add(s);
			dc.settings.save();
			this.finish();
		}catch(Exception e)
		{
			Log.e("Error al guardar la configuracion en la base de datos","Message : "+e.getMessage(),e);
			Toast.makeText(getBaseContext(), "No se pudo guardar la informacion", Toast.LENGTH_LONG).show();
		}
		
	}
	
}
