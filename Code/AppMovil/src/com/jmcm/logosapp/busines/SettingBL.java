package com.jmcm.logosapp.busines;

import java.util.Date;

import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.logosapp.data.entities.Setting;

import android.content.Context;
import android.util.Log;

public class SettingBL {
	
	LogosDataContext dao;
	
	public SettingBL(Context context)
	{
		try {
			dao = new LogosDataContext(context);
			dao.settings.save();
			dao.settings.fill();
			
			if (dao.settings.size() == 0) {
				Setting setting = new Setting();
				setting.setLastUpdate(new Date(0));
				setting.setIp("192.168.1.102");
				setting.setPuerto(60728);
				if (!save(setting))
					Log.e("No se pudo almacenar SETTING","SettingBL");
			}else
				Log.i("DATO EXISTENTE : ","" + dao.settings.get(0));
		} catch (Exception e) {
			Log.e("Error al iniciar el acceso a la BD", e.getMessage(),e);
		}
	}

	public boolean save(Setting setting) {
		if (dao.settings != null)
		{
			if (dao.settings.size() > 0)
			{
				dao.settings.removeAll();
				Log.i("Se ha eliminado el anterior registro de Configuracion","Se ha eliminado el anterior registro de Configuracion");
			}
			dao.settings.add(setting);
			try{
				dao.settings.save();
				
				Log.i("Se ha guardado correctamente en la BD","[DATES] LastUpdate:" + setting.getLastUpdate()+", IP : " + setting.getIp()+", PUERTO : " + setting.getPuerto());
				return true;
			}catch(Exception e)
			{
				Log.e("Error al guardar en la BD : Setting",e.getMessage(),e);
			}
		}else
			Log.e("El DAO Setting no esta disponible","Referencia a objeto es null dao.setting");
		return false;
	}
	
	public Setting getSetting()
	{
		if (dao.settings != null)
		{
			try{
				Log.d("Setting.length : " + dao.settings.size(),"Setting.length : " + dao.settings.size());
				return dao.settings.get(0);
			}catch (Exception e) {
				Log.e("Error al obtener Setting : dao.settings.get",e.getMessage(),e);
			}
		}else
			Log.e("El DAO Setting no esta disponible","Referencia a objeto es null dao.setting");
		return null;
	}
	
}
