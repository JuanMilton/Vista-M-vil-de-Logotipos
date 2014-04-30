package com.jmcm.logosapp.view;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.jmcm.logosapp.data.context.LogotipoB;
import com.jmcm.logosapp.utils.ImageUtil;

public class ImageActivity extends Activity {

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Bundle t = getIntent().getExtras();
		LogotipoB rr = (LogotipoB)t.getParcelable("logo");
		
		
		Bitmap bmp = ImageUtil.toBitmap(rr.getImage());

		ZoomFunctionality img = new ZoomFunctionality(this);
		img.setImageBitmap(bmp);
		img.setMaxZoom(4f);
		setContentView(img);
	}

	
	
}
