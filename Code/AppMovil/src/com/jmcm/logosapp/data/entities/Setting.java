package com.jmcm.logosapp.data.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

/***
 * Clase para almacenar la configuracion de Fecha de Ultima actualizacion y
 * extras para el sistema Propiedades del sistema
 * 
 * @author Juan
 * 
 */
@Table(name = "tSetting")
public class Setting extends Entity {

	// begin_region FIELDS
	@TableField(name = "lastUpdate", datatype = DATATYPE_DATE, required = true, maxLength = 50)
	private Date lastUpdate;

	@TableField(name = "ip", datatype = DATATYPE_TEXT, maxLength = 20)
	private String ip;
	
	@TableField(name = "puerto", datatype = DATATYPE_INTEGER)
	private int puerto;

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPuerto() {
		return puerto;
	}

	public void setPuerto(int puerto) {
		this.puerto = puerto;
	}
	
	// end_region

	// begin_region PROPERTIES
	
	
	
	// end_region
}
