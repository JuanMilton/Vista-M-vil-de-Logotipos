package com.jmcm.logosapp.data.context;

import android.content.Context;
import android.database.Cursor;

import com.jmcm.logosapp.data.entities.Logotipo;
import com.jmcm.logosapp.data.entities.Setting;
import com.mobandme.ada.ObjectContext;
import com.mobandme.ada.ObjectSet;

/***
 * DAO para accesar a los datos, y realizar todas las tareas basicas
 * 
 * @author Juan
 * 
 */
public class LogosDataContext extends ObjectContext {

//	public ObjectSet<Folder> folders;
	public ObjectSet<Logotipo> logotipos;
	public ObjectSet<Setting> settings;
	
	private Context context;

	public LogosDataContext(Context pContext) throws Exception {
		super(pContext);
		context = pContext;

//		this.folders = new ObjectSet<Folder>(Folder.class, this);
		this.logotipos = new ObjectSet<Logotipo>(Logotipo.class, this);
		this.settings = new ObjectSet<Setting>(Setting.class, this);

	}
	
	public Cursor getNextElements(int id, int cant, String navegacion)
	{
		return this.getReadableDatabase().rawQuery("select ID,image,folder from tLogotipo where ID > " +id +" and folder LIKE ? ORDER BY ID ASC LIMIT "+cant+";",  new String[] {navegacion + "%"});
	}
	
	public Cursor getPreviousElements(int id, int cant, String navegacion)
	{
		return this.getReadableDatabase().rawQuery("select ID,image,folder from tLogotipo where ID <= " +id +" and folder LIKE ? ORDER BY ID DESC LIMIT "+cant+";", new String[] {navegacion + "%"});
	}
	
	public long getCantidad()
	{
		return this.getReadableDatabase().rawQuery("select count(ID) from tLogotipo;", null).getLong(0);
	}
	
	public Cursor obtenerRecientes()
	{
		return this.getReadableDatabase().rawQuery("select * from tLogotipo ORDER BY ID DESC LIMIT 30", null);
	}
	
	
	
	public Cursor listarLogotiposFolders(String navegacion)
	{
		String sql = "select folder from tLogotipo where folder LIKE ?";
		return this.getReadableDatabase().rawQuery(sql, new String[] {navegacion + "%"});
	}

	public Cursor buscarLogotipo(String logotipoP) {
		String sql = "select * from tLogotipo where folder = ?";
		return this.getReadableDatabase().rawQuery(sql, new String[] {logotipoP});
	}
	
	public void eliminarLogotipo(String path)
	{
		this.getWritableDatabase().execSQL("delete from tLogotipo where folder = ?", new String[]{path});
	}
	
//	public Cursor cargarRecientes(int cantidad)
//	{
//		
//	}

}
