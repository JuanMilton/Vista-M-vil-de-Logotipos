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
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.CheckBox;
import android.widget.Gallery;
import android.widget.Gallery.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Logotipos2Activity extends Activity{

	
	LogosDataContext dc;
	LogotipoB logotipo;
	
	
	//begin_region COMPONENTES_VIEW
		ZoomFunctionality image_view;
		TextView label_nombre;
		TextView label_puntadas;
		TextView label_location;
		TextView label_ancho_alto;
		TextView label_colores;
	//end_region
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_logotipos2);
		Bundle t = getIntent().getExtras();
		String logotipoP = t.getString("logo");
//		Toast.makeText(getBaseContext(), logotipoP, Toast.LENGTH_SHORT).show();
		try{
			if (logotipoP == null || logotipoP.isEmpty())
			{
				Toast.makeText(getBaseContext(), "Ningun logotipo seleccionado", Toast.LENGTH_SHORT).show();
				this.finish();
			}
			LogosDataContext dc = new LogosDataContext(this);
			Cursor c = dc.buscarLogotipo(logotipoP);
			if (c.moveToNext())
			{
				logotipo = new LogotipoB();
				logotipo.setColors(c.getInt(c.getColumnIndex("colors")));
				logotipo.setFolder(c.getString(c.getColumnIndex("folder")));
				logotipo.setHeight(c.getFloat(c.getColumnIndex("height")));
				logotipo.setImage(c.getBlob(c.getColumnIndex("image")));
				logotipo.setLastUpdate(c.getLong(c.getColumnIndex("lastUpdate")));
				logotipo.setName(c.getString(c.getColumnIndex("name")));
				logotipo.setStitches(c.getInt(c.getColumnIndex("stitches")));
				logotipo.setStops(c.getInt(c.getColumnIndex("stops")));
				logotipo.setWidth(c.getFloat(c.getColumnIndex("width")));
				
			}
		}catch(Exception e)
		{
			Log.e("Error al iniciar BD Logotipos","Message : " + e.getMessage(),e);
			this.finish();
		}
		
		
		initializeComponent();
	}

	private void initializeComponent()
	{
		LinearLayout ll = (LinearLayout)findViewById(R.id.logotipos2_image_layout);
		image_view = new ZoomFunctionality(this);
		image_view.setImageBitmap(ImageUtil.toBitmap(logotipo.getImage()));
		image_view.setMaxZoom(4f);

		ll.addView(image_view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
		
		
		
		label_nombre = (TextView)findViewById(R.id.logotipos2_name_value);
		label_puntadas = (TextView)findViewById(R.id.logotipos2_stitches_value);
		label_ancho_alto = (TextView)findViewById(R.id.logotipos2_width_height_value);
		label_colores = (TextView)findViewById(R.id.logotipos2_colores_value);
		label_location = (TextView)findViewById(R.id.logotipos2_location_value);
		
		label_nombre.setText(logotipo.getName());
		label_puntadas.setText(logotipo.getStitches()+"");
		label_ancho_alto.setText((Util.round(logotipo.getWidth()/10,2))+" X " + (Util.round(logotipo.getHeight()/10,2)));
		label_colores.setText(logotipo.getColors()+"");
		label_location.setText(eliminarNombre(logotipo.getFolder()));
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
		i.putExtra("logo", logotipo);
		startActivity(i);
	}
	
	
}
