package com.jmcm.logosapp.view;

import com.jmcm.view.logosapp.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FolderAdapter extends ArrayAdapter<String> {
	private final Activity context;
	private final String[] web;
	private final Bitmap folderImage;
	private final Bitmap logoImage;
	private final Boolean[] folders;

	public FolderAdapter(Activity context, String[] web,Boolean[] folders, Bitmap folder_image, Bitmap logo) {
		super(context, R.layout.list_single, web);
		this.context = context;
		this.web = web;
		this.folderImage = folder_image;
		this.logoImage = logo;
		this.folders = folders;
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = context.getLayoutInflater();
		View rowView = inflater.inflate(R.layout.list_single, null, true);
		
		TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
		ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
		txtTitle.setText(web[position]);
		txtTitle.setTextSize(35);
		if (folders[position])
			imageView.setImageBitmap(folderImage);
		else
			imageView.setImageBitmap(logoImage);
		return rowView;
	}
}
