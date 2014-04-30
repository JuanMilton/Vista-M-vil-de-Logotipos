package com.jmcm.logosapp.data.context;

import android.os.Parcel;
import android.os.Parcelable;

public class LogotipoB implements Parcelable{
	
	private long id;
	private String name;
    private String folder;
    private int stitches;
    private int colors;
    private int stops;
    private float width;
    private float height;
    private byte[] image;
    private long lastUpdate;
    
    public LogotipoB()
    {
    	
    }
    
    public LogotipoB(Parcel p)
	{
    	
    	this.name = p.readString();
    	this.folder = p.readString();
    	this.stitches = p.readInt();
    	this.colors = p.readInt();
    	this.stops = p.readInt();
    	this.width = p.readFloat();
    	this.height = p.readFloat();
    	this.image = (byte[])p.readValue(ClassLoader.getSystemClassLoader());
    	this.lastUpdate = p.readLong();
    	this.id = p.readLong();
	}
    
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
	public float getWidth() {
		return width;
	}
	public void setWidth(float width) {
		this.width = width;
	}
	public float getHeight() {
		return height;
	}
	public void setHeight(float height) {
		this.height = height;
	}
	public byte[] getImage() {
		return image;
	}
	public void setImage(byte[] image) {
		this.image = image;
	}
	public long getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(long lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.name);
		dest.writeString(this.folder);
		dest.writeInt(this.stitches);
		dest.writeInt(this.colors);
		dest.writeInt(this.stops);
		dest.writeFloat(this.width);
		dest.writeFloat(this.height);
		dest.writeValue(this.image);
		dest.writeLong(this.lastUpdate);
		dest.writeLong(this.id);
		
	}
	
	public static final LogotipoB.Creator<LogotipoB> CREATOR = new LogotipoB.Creator<LogotipoB>() { 
		public LogotipoB createFromParcel(Parcel in) { return new LogotipoB(in); }   
		public LogotipoB[] newArray(int size) { return new LogotipoB[size]; } 
	}; 
    
    
    
}
