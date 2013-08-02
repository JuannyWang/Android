package com.manor.util;

import java.io.IOException;
import java.util.List;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;

public class CameraCallback2 implements Callback {
	
	private SurfaceHolder holder;
	private Camera camera;
	private int limitwidth, limitheight;
	public int width, height;
	private PictureCallback callback;

		
	public CameraCallback2(SurfaceHolder holder, int width,
			int height, PictureCallback callback) {
		super();
		this.holder = holder;
		this.holder.addCallback(this);
		this.holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		this.limitwidth = width;
		this.limitheight = height;
		this.callback = callback;
	}

	@Override
	public void surfaceChanged(SurfaceHolder sholder, int format, int w, int h) {
		// TODO Auto-generated method stub
		Parameters parameters = camera.getParameters();
		parameters.setPictureFormat(PixelFormat.JPEG);
		
		Size size = parameters.getPreviewSize();
		Size size2 = parameters.getPictureSize();
        List<Size> sizes = parameters.getSupportedPreviewSizes();
        List<Size> sizes2 = parameters.getSupportedPictureSizes();
        if(sizes != null)
        {
           System.out.println("not null");
	       size = getOptimalPreviewSize(
	                sizes, (double) size.width / size.height);
	       System.out.println(size.width + " "+size.height);
	       parameters.setPreviewSize(size.width, size.height);
	        
        }
        if(sizes2 != null)
        {
           System.out.println("not null");
	       size2 = getOptimalPictureSize(
	                sizes2, (double) size2.width / size2.height);
	       System.out.println(size2.width + " "+size2.height);
	       parameters.setPictureSize(size2.width, size2.height);
	        
        }
        
        size = parameters.getPreviewSize();
        width = size.width;
        height = size.height;
        
        System.out.println("preview: " + size.width + " " + size.height);
        
		camera.setParameters(parameters);
		camera.startPreview();
	}
	
	private Size getOptimalPreviewSize(List<Size> sizes, double d) {
		// TODO Auto-generated method stub
		Size optsize = sizes.get(sizes.size() - 1);
		for(Size s: sizes)
		{
			System.out.println("for each:");
			System.out.println("sizes: " + s.width + " " + s.height);
			if(optsize.width < s.width && s.width <= limitwidth && s.height <= limitheight)
			{
				
				optsize = s;
			}
		}
		return optsize;
	}
	
	private Size getOptimalPictureSize(List<Size> sizes, double d) {
		// TODO Auto-generated method stub
		Size optsize = sizes.get(sizes.size() - 1);
		for(Size s: sizes)
		{
			System.out.println("for each:");
			System.out.println("picture sizes: " + s.width + " " + s.height);
			if(optsize.width < s.width && s.width <= 1200 && s.height <= 900)
			{
				
				optsize = s;
			}
		}
		System.out.println("opt pic size: " + optsize.width + " " + optsize.height);
		return optsize;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		camera = Camera.open();
		try
		{
			camera.setPreviewDisplay(holder);
		}
		catch(IOException ioe)
		{
			camera.release();
			camera = null;
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;
		System.out.println("Camera destroyed");
	}
	
	public void focus()
	{
		if(camera != null)
		{
			camera.autoFocus(cb);
		}
		else
			Log.e("camera", "camera is null");
	}
	
	private AutoFocusCallback cb = new AutoFocusCallback() {
		
		@Override
		public void onAutoFocus(boolean success, Camera camera2) {
			// TODO Auto-generated method stub
			if(success)
			{
				try
				{
					camera.takePicture(null, null, callback);
					camera.stopPreview();
				}catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		}
	};
	
}
