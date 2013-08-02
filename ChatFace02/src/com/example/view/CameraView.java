/**
 * 
 */
package com.example.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.Size;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.data.Common;
import com.example.interfaces.VideoSocketConnection;
import com.example.servers.ChatService;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback,
		Camera.PreviewCallback {
	private static final String iP = null;
	private SurfaceHolder holder;
	private Camera camera;

	private static int ratio;

	private static int skipFrame;

	private int frameStep;
	private String ip;

	private VideoSocketConnection connection;

	public CameraView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public CameraView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public CameraView(Context context) {
		super(context);
		init();
	}

	/**
	 * 程序统一初始化
	 */
	private void init() {
		holder = getHolder();// 生成Surface Holder
		holder.addCallback(this);
	}

	public void surfaceCreated(SurfaceHolder holder) {// Surface生成事件的处理
		connection = new VideoSocketConnection();
		connection.setIP(iP);
		camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
		try {
			camera.setPreviewDisplay(holder);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		camera.setPreviewCallback(this);
	
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {// Surface改变事件的处理
		if (camera != null) {
			Camera.Parameters parameters = camera.getParameters();
			camera.lock();
			camera.setDisplayOrientation(90);
			camera.setParameters(parameters);// 设置参数
			camera.startPreview();// 开始预览
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {// Surface销毁时的处理
		camera.setPreviewCallback(null);
		camera.stopPreview();
		camera.release();
		camera = null;

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {// 屏幕触摸事件
		if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下时自动对焦
			camera.autoFocus(null);
		}
		return true;
	}

	public static void setRatio(int param) {
		ratio = param;
	}

	public static void setSkipFrame(int skip) {
		skipFrame = skip;
	}

	public void startView() {
		if (camera != null) {
			camera.setPreviewCallback(this);
			camera.startPreview();
		}
	}

	public void stopView() {
		if (camera != null) {
			camera.setPreviewCallback(null);
			camera.stopPreview();
		}
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	@Override
	public void onPreviewFrame(byte[] data, Camera camera) {
		if (frameStep == skipFrame) {
			Size size = camera.getParameters().getPreviewSize();
			try {
				YuvImage image = new YuvImage(data, ImageFormat.NV21,
						size.width, size.height, null);
				if (image != null) {
					ByteArrayOutputStream stream = new ByteArrayOutputStream();
					image.compressToJpeg(
							new Rect(0, 0, size.width, size.height), ratio,
							stream);
					byte[] sendData = stream.toByteArray();
					connection.sendDatas(sendData);
					
					DecodeView.setBytes(sendData);
					stream.close();
				}
			} catch (Exception ex) {
				Log.e("Sys", "Error:" + ex.getMessage());
			}
		}
		frameStep++;
		if (frameStep > skipFrame) {
			frameStep = 0;
		}

	}
	


}
