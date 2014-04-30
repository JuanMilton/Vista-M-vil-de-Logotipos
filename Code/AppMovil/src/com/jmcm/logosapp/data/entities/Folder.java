package com.jmcm.logosapp.data.entities;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

/***
 * Entidad Folder de la base de datos dentro de cada folder existe muchos
 * logotipos
 * 
 * @author Juan
 * 
 */
@Table(name = "tFolder")
public class Folder extends Entity {

	// begin_region FIELDS
	@TableField(name = "name", datatype = DATATYPE_TEXT, required = true, maxLength = 80)
	private String name;

	// end_region

	// begin_region PROPERTIES
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// end_region
}
