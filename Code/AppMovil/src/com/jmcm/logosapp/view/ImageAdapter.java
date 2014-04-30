package com.jmcm.logosapp.view;

import java.util.LinkedList;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter{
	
	private Context mContext;
	
	private LinkedList<Bitmap> images;
	
	public ImageAdapter(Context c, LinkedList<Bitmap> fotos)
	{
		this.mContext = c;		
		this.images = fotos;
	}
	
	public int getCount()
	{
		return images.size();
	}
	
	public Object getItem(int position)
	{
		return position;
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		ImageView imv = new ImageView(this.mContext);
		imv.setImageBitmap(images.get(arg0));
		imv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		imv.setLayoutParams(new Gallery.LayoutParams(450, 600));
		imv.setPadding(30, 0, 30, 0);
		return imv;
	}
}
