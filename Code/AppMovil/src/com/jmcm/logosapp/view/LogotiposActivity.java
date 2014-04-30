package com.jmcm.logosapp.view;

import java.util.LinkedList;

import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.logosapp.data.context.LogotipoB;
import com.jmcm.logosapp.data.entities.Logotipo;
import com.jmcm.logosapp.utils.ImageUtil;
import com.jmcm.logosapp.utils.Util;
import com.jmcm.view.logosapp.R;
import com.mobandme.ada.ObjectSet;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class LogotiposActivity extends Activity{

	LinkedList<Bitmap> fotos;
	LogosDataContext dc;
	LinkedList<LogotipoB> logotipos;
	
	//begin_region COMPONENTES_VIEW
		Gallery galeria;
		TextView label_nombre;
		TextView label_puntadas;
		TextView label_location;
		TextView label_ancho_alto;
		TextView label_colores;
	//end_region
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logotipos);
		Bundle t = getIntent().getExtras();
		String folder = t.getString("folder");
		
		try{
			dc = new LogosDataContext(this);
			dc.logotipos.save();
			cargarLogotipos(folder);
		}catch(Exception e)
		{
			Log.e("Error al iniciar BD Logotipos","Message : " + e.getMessage(),e);
			this.finish();
		}
		fotos = new LinkedList<Bitmap>();
		for (int i = 0; i < logotipos.size(); i++) 
		{
			fotos.add(ImageUtil.toBitmap(logotipos.get(i).getImage()));
		}
		
		initializeComponent();
	}
	
	private void cargarLogotipos(String folder) {
		logotipos = new LinkedList<LogotipoB>();
		Cursor c = dc.obtenerRecientes();
		while (c.moveToNext())
		{
			LogotipoB logotipo = new LogotipoB();
			logotipo.setColors(c.getInt(c.getColumnIndex("colors")));
			logotipo.setFolder(c.getString(c.getColumnIndex("folder")));
			logotipo.setHeight(c.getFloat(c.getColumnIndex("height")));
			logotipo.setImage(c.getBlob(c.getColumnIndex("image")));
			logotipo.setLastUpdate(c.getLong(c.getColumnIndex("lastUpdate")));
			logotipo.setName(c.getString(c.getColumnIndex("name")));
			logotipo.setStitches(c.getInt(c.getColumnIndex("stitches")));
			logotipo.setStops(c.getInt(c.getColumnIndex("stops")));
			logotipo.setWidth(c.getFloat(c.getColumnIndex("width")));
			
			logotipos.add(logotipo);
		}
	}

	private void initializeComponent()
	{
		galeria = (Gallery)findViewById(R.id.logotipos_gallery);
		galeria.setAdapter(new ImageAdapter(this,fotos));
		galeria.setOnItemClickListener(new OnItemClickListener(){
			public void onItemClick(AdapterView parent, View v, int position, long id){
				cambiarDatos(position);
			}
		});
		
		
		label_nombre = (TextView)findViewById(R.id.logotipos_name_value);
		label_puntadas = (TextView)findViewById(R.id.logotipos_stitches_value);
		label_ancho_alto = (TextView)findViewById(R.id.logotipos_width_height_value);
		label_colores = (TextView)findViewById(R.id.logotipos_colores_value);
		label_location = (TextView)findViewById(R.id.logotipos_location_value);
		
	}
	
	private void cambiarDatos(int position) {
		label_nombre.setText(logotipos.get(position).getName());
		label_puntadas.setText(logotipos.get(position).getStitches()+"");
		label_ancho_alto.setText((Util.round(logotipos.get(position).getWidth()/10,2))+" X " + (Util.round(logotipos.get(position).getHeight()/10,2)));
		label_colores.setText(logotipos.get(position).getColors()+"");
		label_location.setText(eliminarNombre(logotipos.get(position).getFolder()));
	}
	
	private String eliminarNombre(String cad)
	{
		int i = cad.length()-1;
		while (i >= 0 && cad.charAt(i) != '\\')
			i--;
		return cad.substring(0,i);
	}
	
	
	public void eventVerLogotipo(View sd)
	{
		Intent i = new Intent(this,ImageActivity.class);
		i.putExtra("logo", logotipos.get(galeria.getSelectedItemPosition()));
		startActivity(i);
	}
	
}
