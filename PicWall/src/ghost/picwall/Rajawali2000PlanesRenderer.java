/**
 * 
 */
package ghost.picwall;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.BaseObject3D;
import rajawali.animation.Animation3D;
import rajawali.animation.CatmullRomPath3D;
import rajawali.animation.TranslateAnimation3D;
import rajawali.lights.DirectionalLight;
import rajawali.math.Number3D;
import rajawali.renderer.RajawaliRenderer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.animation.AccelerateDecelerateInterpolator;


/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-18
 */
public class Rajawali2000PlanesRenderer extends RajawaliRenderer {
	private PlanesGalore mPlanes;
	private PlanesGaloreMaterial mMaterial; 
	private long mStartTime;
	private TranslateAnimation3D mCamAnim;
	
	public Rajawali2000PlanesRenderer(Context context) {
		super(context);
		setFrameRate(60);
	}

	protected void initScene() {
		DirectionalLight light = new DirectionalLight(0, 0, 1);
		mCamera.setPosition(0, 0, -16);

		mPlanes = new PlanesGalore();
		mMaterial = (PlanesGaloreMaterial)mPlanes.getMaterial();
		mPlanes.addLight(light);
		mPlanes.setDoubleSided(true);
		mPlanes.setZ(4);
		
		Bitmap b = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.flickrpics);
		mPlanes.addTexture(mTextureManager.addTexture(b));
		addChild(mPlanes);
		
		BaseObject3D empty = new BaseObject3D();
		addChild(empty);
		
		CatmullRomPath3D path = new CatmullRomPath3D();
		path.addPoint(new Number3D(-4, 0, -20));
		path.addPoint(new Number3D(2, 1, -10));
		path.addPoint(new Number3D(-2, 0, 10));
		path.addPoint(new Number3D(0, -4, 20));
		path.addPoint(new Number3D(5, 10, 30));
		path.addPoint(new Number3D(-2, 5, 40));
		path.addPoint(new Number3D(3, -1, 60));
		path.addPoint(new Number3D(5, -1, 70));
		
		mCamAnim = new TranslateAnimation3D(path);
		mCamAnim.setDuration(20000);
		mCamAnim.setRepeatCount(Animation3D.INFINITE);
		mCamAnim.setRepeatMode(Animation3D.REVERSE);
		mCamAnim.setTransformable3D(mCamera);
		mCamAnim.setInterpolator(new AccelerateDecelerateInterpolator());
		
		mCamera.setLookAt(new Number3D(0,0,30));		
	}
	
	public void onSurfaceCreated(GL10 gl, EGLConfig config) {
		((RajawaliExampleActivity) mContext).showLoader();
		super.onSurfaceCreated(gl, config);
		((RajawaliExampleActivity) mContext).hideLoader();
		mStartTime = System.currentTimeMillis();
		mCamAnim.start();
	}
	
	public void onDrawFrame(GL10 glUnused) {
		super.onDrawFrame(glUnused);
		mMaterial.setTime((System.currentTimeMillis() - mStartTime) / 1000f);
	}
}

