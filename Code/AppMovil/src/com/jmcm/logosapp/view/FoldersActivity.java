package com.jmcm.logosapp.view;

import java.util.LinkedList;

import com.jmcm.logosapp.data.context.LogosDataContext;
import com.jmcm.view.logosapp.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class FoldersActivity extends Activity {

	
	LinkedList<String> items;
	LinkedList<Boolean> folders;
	
	LinkedList<String> originItems;
	LinkedList<Boolean> originFolders;
	
	
	LogosDataContext dc;
	String navegacion;
	int contador;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_folders);

		Bundle t = getIntent().getExtras();
		navegacion= t.getString("navegacion"); // tiene que contener como separador a lo ultimo de la cadena
		
		try {
			dc = new LogosDataContext(this);
			Cursor c = dc.listarLogotiposFolders(navegacion);
			items = new LinkedList<String>();
			folders = new LinkedList<Boolean>();
			originItems = new LinkedList<String>();
			originFolders = new LinkedList<Boolean>();
			while (c.moveToNext())
			{
				String item = getItem(c.getString(0), navegacion);
				if (! items.contains(item))
				{
					Boolean isFold = isFolder(c.getString(0), navegacion);
					originItems.add(item);
					originFolders.add(isFold);
					
					items.add(item);
					folders.add(isFold);
				}
			}
			mostrarLista();

		} catch (Exception e) {
			Log.e("Error al iniciar BD Logotipos",
					"Message : " + e.getMessage(), e);
			this.finish();
		}

		contador = 0;

		initializeComponent();

	}
	
	private String getItem(String cad, String nivel)
	{
		cad = cad.replace(nivel, "");
                int i = 0;
                while (i < cad.length() && cad.charAt(i)!='\\')
                    i++;
                return cad.substring(0, i);
	}
	
	private boolean isFolder(String cad, String nivel)
	{
		cad = cad.toUpperCase();
		nivel = nivel.toUpperCase();
		cad = cad.replace(nivel, "");
		return cad.contains("\\");
	}

	private void mostrarLista() {
		ordenar();
		ListView lv = (ListView) findViewById(R.id.folders_list);
		FolderAdapter adapter = new FolderAdapter(FoldersActivity.this,
				items.toArray(new String[]{}), folders.toArray(new Boolean[]{}), BitmapFactory.decodeResource(this.getResources(), R.drawable.cliente), BitmapFactory.decodeResource(this.getResources(), R.drawable.logo_cliente));

		lv.setAdapter(adapter);

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				lanzarActividad(items.get(arg2));
			}
		});
		
		
	}

	private void lanzarConfiguracion() {
		Intent i = new Intent(this, ConfigurationActivity.class);
		startActivity(i);
	}

	private void initializeComponent() {
		EditText ed = (EditText) findViewById(R.id.folders_buscar);
		ed.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View arg0) {
				contador++;

				if (contador == 3) {
					contador = 0;
					lanzarConfiguracion();
				}
				return false;
			}
		});
		ed.addTextChangedListener(new TextWatcher() {

			@Override
			public void afterTextChanged(Editable arg0) {
				filtrar(arg0.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}

		});
	}

	public void filtrar(String cad) {
		cad = cad.toUpperCase();
		items = new LinkedList<String>();
		folders = new LinkedList<Boolean>();
		for (int i = 0; i<originItems.size();i++) {
			String c = originItems.get(i);
			if (c.toUpperCase().startsWith(cad))
			{
				items.add(c);
				folders.add(originFolders.get(i));
			}
		}

		mostrarLista();
	}

	private void lanzarActividad(final String item) {
		if (item.toUpperCase().endsWith(".EMB"))
		{
			Intent i = new Intent(this, Logotipos2Activity.class);
			i.putExtra("logo", this.navegacion + item);
			startActivity(i);
		}else
		{
			DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
			    @Override
			    public void onClick(DialogInterface dialog, int which) {
			        switch (which){
			        case DialogInterface.BUTTON_POSITIVE:
			        	Intent is = new Intent(FoldersActivity.this, PaginatorActivity.class);
						is.putExtra("navegacion", navegacion + item +"\\");
						startActivity(is);
			            break;

			        case DialogInterface.BUTTON_NEGATIVE:
			        	Intent i = new Intent(FoldersActivity.this, FoldersActivity.class);
						i.putExtra("navegacion", navegacion + item +"\\");
						startActivity(i);
			            break;
			        }
			    }
			};

			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setMessage("¿Quiere ver los logotipos?").setPositiveButton("SI", dialogClickListener)
			    .setNegativeButton("NO", dialogClickListener).show();
		}
	}

	private void ordenar() {
		for (int i = 0; i < items.size(); i++) {
			for (int j = 0; j < items.size() - 1; j++) {
				if (items.get(i).compareTo(items.get(j)) < 0) {
					String aux = items.get(i);
					items.set(i, items.get(j));
					items.set(j, aux);
					Boolean aux2 = folders.get(i);
					folders.set(i, folders.get(j));
					folders.set(j, aux2);
				}
			}
		}
		separarCarpetasLogotipos();
	}
	
	public void separarCarpetasLogotipos()
    {
        int i = 1;
        while ( i < folders.size())
        {
            if (i > 0 && !folders.get(i-1) && folders.get(i))
            {
                String aux = items.get(i);
                items.set(i, items.get(i-1));
                items.set(i-1, aux);
                Boolean aux2 = folders.get(i);
                folders.set(i, folders.get(i-1));
                folders.set(i-1, aux2);
                i-=2;
            }
            i++;
        }
    }

}
