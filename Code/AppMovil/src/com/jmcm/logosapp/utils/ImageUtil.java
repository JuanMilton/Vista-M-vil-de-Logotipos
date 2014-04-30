package com.jmcm.logosapp.utils;

import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImageUtil {
	
	public static byte[] toBytes(Bitmap img)
	{
		byte[] send;
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
    	img.compress(Bitmap.CompressFormat.PNG, 100, stream);
    	send = stream.toByteArray();
		return send;
	}
	
	public static Bitmap toBitmap(byte[] img)
	{
		return BitmapFactory.decodeByteArray(img, 0, img.length);
	}
}
