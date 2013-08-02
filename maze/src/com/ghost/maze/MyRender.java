/**
 * 
 */
package com.ghost.maze;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
import rajawali.BaseObject3D;
import rajawali.Camera;
import rajawali.lights.PointLight;
import rajawali.materials.DiffuseMaterial;
import rajawali.math.Number3D;
import rajawali.parser.AParser.ParsingException;
import rajawali.parser.ObjParser;
import rajawali.primitives.Cube;
import rajawali.primitives.Plane;
import rajawali.renderer.RajawaliRenderer;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-19
 */
public class MyRender extends RajawaliRenderer {

	private int map[][];

	/**
	 * 单向光
	 */
	private PointLight mLight;

	/**
	 * 展示物体
	 */
	private BaseObject3D mObjectGroup;

	/**
	 * 相机旋转记录
	 */
	private Number3D mAccValues;

	/**
	 * @param context
	 */
	public MyRender(Context context) {
		super(context);
		/**
		 * 设置帧率为60
		 */
		setFrameRate(60);
		mAccValues = new Number3D();
		map = new int[][] {
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
				{ 1, 0, 0, 1, 1, 1, 1, 0, 0, 1 },
				{ 1, 0, 0, 0, 1, 1, 1, 0, 1, 1 },
				{ 1, 1, 0, 1, 0, 1, 1, 0, 0, 1 },
				{ 1, 1, 0, 0, 0, 0, 0, 0, 0, 1 },
				{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, };
	}

	protected void initScene() {

		mLight = new PointLight();
		mLight.setColor(0.9f, 0.9f, 0.9f);
		mLight.setPower(2);

		/**
		 * 设置天空盒
		 */
		setSkybox(R.drawable.posz, R.drawable.posx, R.drawable.negz,
				R.drawable.negx, R.drawable.posy, R.drawable.negy);

		DiffuseMaterial diffuse = new DiffuseMaterial();
		diffuse.setUseColor(true);

		mObjectGroup = new Plane(map.length * 2, map[0].length * 2, 0, 1, 1);
		mObjectGroup.addLight(mLight);
		mObjectGroup.setPosition(map.length - 1, -1, map[0].length - 1);
		mObjectGroup.setMaterial(diffuse);
		mObjectGroup.setColor(0x8B7B8B);
		addChild(mObjectGroup);

		Cube cube = new Cube(2);
		cube.addLight(mLight);
		cube.setMaterial(diffuse);
		cube.setColor(0xFCFCFC);
		for (int i = 0; i < map.length; ++i) {
			for (int j = 0; j < map[i].length; ++j) {
				if (map[i][j] == 1) {
					BaseObject3D temp = (BaseObject3D) cube.clone();
					temp.setPosition(i * 2, 0, j * 2);
					addChild(temp);
				}
			}
		}

		mCamera = new Camera();
		mCamera.setPosition(0, 2, 12);
		mCamera.setFarPlane(1000);
		mLight.setPosition(mCamera.getPosition());

		// -- create a chase camera
		// the first parameter is the camera offset
		// the second parameter is the interpolation factor
		// mCamera = new ChaseCamera(new Number3D(0, 2, 12), .1f);
		// // -- tell the camera which object to chase
		// ((ChaseCamera) mCamera).setObjectToChase(mObjectGroup);
		// // -- set the far plane to 1000 so that we actually see the sky
		// sphere
		// mCamera.setFarPlane(1000);

	}

	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
	}

	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);

//		mCamera.setRotation(mAccValues);
	}

	public void moveHouse(int command) {
		switch (command) {
		case 0:
			mObjectGroup.setRotY(mObjectGroup.getRotY() - 0.5f);
			break;
		case 1:
			mObjectGroup.setRotY(mObjectGroup.getRotY() + 0.5f);
			break;
		}
	}

	public void moveCamera(int command) {
		switch (command) {
		case 0:
			mCamera.setZ(mCamera.getZ() - 0.1f);
			break;
		case 1:
			mCamera.setZ(mCamera.getZ() + 0.1f);
			break;
		case 2:
			mCamera.setX(mCamera.getX() - 0.1f);
			break;
		case 3:
			mCamera.setX(mCamera.getX() + 0.1f);
			break;
		case 4:
			mCamera.setY(mCamera.getY() + 0.1f);
			break;
		case 5:
			mCamera.setY(mCamera.getY() - 0.1f);
			break;
		}
		mLight.setPosition(mCamera.getPosition());
	}

	public void setAccelerometerValues(float x, float y, float z) {
		mAccValues.setAll(x, -y, -z);
	}

}
