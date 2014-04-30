package com.jmcm.logosapp.view;

import java.util.Collections;

import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.logosapp.data.context.LogotipoB;
import com.jmcm.logosapp.utils.ImageUtil;
import com.jmcm.view.logosapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class PaginatorActivity extends Activity{

	LogosAdapter logs;
	GridView g;
	String folder;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_paginador);
		
		Bundle b = getIntent().getExtras();
		folder = b.getString("navegacion");
		
		logs = new LogosAdapter(this);
		
//		Toast.makeText(getBaseContext(), "CANTIDAD : " + logs.getCount(), Toast.LENGTH_SHORT).show();
		
		logs.load(folder);
		
		
		g = (GridView) findViewById(R.id.paginador_imagenes);
        g.setAdapter(logs);
        
//        g.setOnTouchListener(new OnTouchListener() {
//			
//			@Override
//			public boolean onTouch(View v, MotionEvent event) {
//				int eventaction = event.getAction();
//                float x = event.getX();
//                float y = event.getY();
//			    switch (eventaction) {
//			        case MotionEvent.ACTION_DOWN: 
//			            Log.d("DOWN",x+":"+y);
//			            break;
//
//			        case MotionEvent.ACTION_MOVE:
//			            // finger moves on the screen
//			            break;
//
//			        case MotionEvent.ACTION_UP:   
//			             Log.d("UP",x+":"+y);
//			            break;
//			    }
//			    return false;
//			}
//		});
        
        g.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                    int position, long id) {
            	mostrarLogotipo(position);
            }
        });
	}
	
	private void mostrarLogotipo(int pos)
	{
		LogotipoB logo = logs.logos[pos];
		
		Intent i = new Intent(this, Logotipos2Activity.class);
		i.putExtra("logo", logo.getFolder());
		startActivity(i);
	}
	
	public void onSiguiente(View s)
	{
//		Log.d("1 NEXT : ",""+logs.next);
//		Log.d("1 PREVIOUS : ",""+logs.previous);
		
		if (logs.nextElements(folder))
			g.setAdapter(logs);
//		Log.d("2 NEXT : ",""+logs.next);
//		Log.d("2 PREVIOUS : ",""+logs.previous);
	}
	
	public void onAnterior(View s)
	{
//		Log.d("1 NEXT : ",""+logs.next);
//		Log.d("1 PREVIOUS : ",""+logs.previous);
		if (logs.previousElements(folder))
			g.setAdapter(logs);
//		Log.d("2 NEXT : ",""+logs.next);
//		Log.d("2 PREVIOUS : ",""+logs.previous);
	}
	
	
	public class LogosAdapter extends BaseAdapter {
		
		private final byte COUNT = 15;
//		private int index = 1;
		private long cantLogotiposLeidos;
		public long next;
		public long previous;
		
		LogotipoB[] logos;
		LogosDataContext dao;
		
		public void load(String folder)
		{
//			this.index = index;
			Cursor c = dao.getNextElements(0, COUNT, folder);
			
			
			logos = new LogotipoB[COUNT];
			int pos = 0;
			cantLogotiposLeidos = 0;
			while (c.moveToNext())
			{
				LogotipoB logotipo = new LogotipoB();
				
				logotipo.setId(c.getLong(c.getColumnIndex("ID")));
//				logotipo.setColors(c.getInt(c.getColumnIndex("colors")));
				logotipo.setFolder(c.getString(c.getColumnIndex("folder")));
//				logotipo.setHeight(c.getFloat(c.getColumnIndex("height")));
				logotipo.setImage(c.getBlob(c.getColumnIndex("image")));
//				logotipo.setLastUpdate(c.getLong(c.getColumnIndex("lastUpdate")));
//				logotipo.setName(c.getString(c.getColumnIndex("name")));
//				logotipo.setStitches(c.getInt(c.getColumnIndex("stitches")));
//				logotipo.setStops(c.getInt(c.getColumnIndex("stops")));
//				logotipo.setWidth(c.getFloat(c.getColumnIndex("width")));
				
				logos[pos] = logotipo;
				pos++;
				cantLogotiposLeidos++;
			}
			previous = next;
			next = maximoID(logos);
		}
		
		public boolean nextElements(String folder)
		{
			Cursor c = dao.getNextElements((int) next, COUNT,folder);
			
			
			int pos = 0;
			
			boolean hayElementos = false;
			while (c.moveToNext())
			{
				if (!hayElementos)
				{
					logos = new LogotipoB[COUNT];
					cantLogotiposLeidos = 0;
				}
				hayElementos = true;
				LogotipoB logotipo = new LogotipoB();
				
				logotipo.setId(c.getLong(c.getColumnIndex("ID")));
//				logotipo.setColors(c.getInt(c.getColumnIndex("colors")));
				logotipo.setFolder(c.getString(c.getColumnIndex("folder")));
//				logotipo.setHeight(c.getFloat(c.getColumnIndex("height")));
				logotipo.setImage(c.getBlob(c.getColumnIndex("image")));
//				logotipo.setLastUpdate(c.getLong(c.getColumnIndex("lastUpdate")));
//				logotipo.setName(c.getString(c.getColumnIndex("name")));
//				logotipo.setStitches(c.getInt(c.getColumnIndex("stitches")));
//				logotipo.setStops(c.getInt(c.getColumnIndex("stops")));
//				logotipo.setWidth(c.getFloat(c.getColumnIndex("width")));
				
				logos[pos] = logotipo;
				pos++;
				cantLogotiposLeidos++;
			}
			if (hayElementos)
			{
				previous = next;
				next = maximoID(logos);
			}
			return hayElementos;
		}
		
		public boolean previousElements(String folder)
		{
			Cursor c = dao.getPreviousElements((int) previous, COUNT, folder);
			
			
			int pos = 0;
			
			boolean hayElementos = false;
			while (c.moveToNext())
			{
				if (!hayElementos)
				{
					logos = new LogotipoB[COUNT];
					cantLogotiposLeidos = 0;
				}
				hayElementos = true;
				LogotipoB logotipo = new LogotipoB();
				
				logotipo.setId(c.getLong(c.getColumnIndex("ID")));
//				logotipo.setColors(c.getInt(c.getColumnIndex("colors")));
				logotipo.setFolder(c.getString(c.getColumnIndex("folder")));
//				logotipo.setHeight(c.getFloat(c.getColumnIndex("height")));
				logotipo.setImage(c.getBlob(c.getColumnIndex("image")));
//				logotipo.setLastUpdate(c.getLong(c.getColumnIndex("lastUpdate")));
//				logotipo.setName(c.getString(c.getColumnIndex("name")));
//				logotipo.setStitches(c.getInt(c.getColumnIndex("stitches")));
//				logotipo.setStops(c.getInt(c.getColumnIndex("stops")));
//				logotipo.setWidth(c.getFloat(c.getColumnIndex("width")));
				
				logos[pos] = logotipo;
				
				pos++;
				cantLogotiposLeidos++;
			}
			if (hayElementos)
			{
				invertir(logos);
				next = previous;
				previous = menorID(logos)-1;			
			}
			return hayElementos;
		}
		
		private void invertir(LogotipoB[] logs)
		{
			int i = 0;
			int j = logs.length-1;
			while (i < j)
			{
				LogotipoB aux = logs[i];
				logs[i] = logs[j];
				logs[j] = aux;
				i++;
				j--;
			}
		}
		
		private long menorID(LogotipoB[] logs) {
			long max = 1000000;
			for(LogotipoB l : logs)
			{
				if (l != null && l.getId() < max)
					max = l.getId();
			}
			return max;
		}
		
		private long maximoID(LogotipoB[] logs) {
			long max = 0;
			for(LogotipoB l : logs)
			{
				if (l != null && l.getId() > max)
					max = l.getId();
			}
			return max;
		}

		public long cantidad()
		{
			return dao.getCantidad();
		}
		
        public LogosAdapter(Context c) {
        	try {
				dao = new LogosDataContext(c);
			} catch (Exception e) {
				e.printStackTrace();
			}
            mContext = c;
        }

        public int getCount() {
            return COUNT;
        }

        public Object getItem(int position) {
            return position;
        }

        public long getItemId(int position) {
            return position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
        	
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(180, 180));
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setPadding(2, 2, 2, 2);
            } else {
                imageView = (ImageView) convertView;
            }
            if (position < cantLogotiposLeidos)
            	imageView.setImageBitmap(ImageUtil.toBitmap(logos[position].getImage()));

            return imageView;
        }

        private Context mContext;

    }

	
}
