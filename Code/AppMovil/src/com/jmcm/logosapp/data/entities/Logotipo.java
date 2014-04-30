package com.jmcm.logosapp.data.entities;

import java.util.Date;

import com.mobandme.ada.Entity;
import com.mobandme.ada.annotations.Table;
import com.mobandme.ada.annotations.TableField;

/***
 * Entidad Logotipo, que se utilizara para la base de datos
 * 
 * @author Juan
 * 
 */
@Table(name = "tLogotipo")
public class Logotipo extends Entity {

	// begin_region FIELDS

	@TableField(name = "name", datatype = DATATYPE_TEXT, required = true, maxLength = 30)
	private String name;

	@TableField(name = "folder", datatype = DATATYPE_TEXT, required = false, maxLength = 40)
	private String folder;

	@TableField(name = "stitches", datatype = DATATYPE_INTEGER, required = true)
	private int stitches;

	@TableField(name = "colors", datatype = DATATYPE_INTEGER, required = true)
	private int colors;

	@TableField(name = "stops", datatype = DATATYPE_INTEGER, required = true)
	private int stops;

	@TableField(name = "width", datatype = DATATYPE_DOUBLE, required = true)
	private double width;

	@TableField(name = "height", datatype = DATATYPE_DOUBLE, required = true)
	private double height;

	@TableField(name = "image", datatype = DATATYPE_BLOB, required = true)
	private byte[] image;

	@TableField(name = "lastUpdate", datatype = DATATYPE_DATE, required = true)
	private Date lastUpdate;
	

	// end_region

	// begin_region PROPERTIES

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public int getStitches() {
		return stitches;
	}

	public void setStitches(int stitches) {
		this.stitches = stitches;
	}

	public int getColors() {
		return colors;
	}

	public void setColors(int colors) {
		this.colors = colors;
	}

	public int getStops() {
		return stops;
	}

	public void setStops(int stops) {
		this.stops = stops;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	// end_region

}
