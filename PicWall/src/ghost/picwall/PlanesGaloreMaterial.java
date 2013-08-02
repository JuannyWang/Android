/**
 * 
 */
package ghost.picwall;

import rajawali.materials.AMaterial;
import rajawali.math.Number3D;
import android.opengl.GLES20;

/**
 * @author 玄雨
 * @qq 821580467
 * @date 2013-4-18
 */
public class PlanesGaloreMaterial extends AMaterial {
	protected static final String mVShader = 
		"uniform mat4 uMVPMatrix;\n" +
		"uniform vec3 uCamPos;\n" +
		"uniform float uTime;" +

		"attribute vec4 aPosition;\n" +
		"attribute vec2 aTextureCoord;\n" +
		"attribute vec4 aColor;\n" +
		"attribute vec4 aPlanePosition;\n" +
		"attribute float aRotationSpeed;\n" +

		"varying vec2 vTextureCoord;\n" +
		"varying float vFog;\n" +

		"void main() {\n" +
		"	float rotation = uTime * aRotationSpeed;" +
		// -- first rotate around the z axis
		"	mat4 mz = mat4(1.0);" +
		"	mz[0][0] = cos(rotation);" +
		"	mz[0][1] = sin(rotation);" +
		"	mz[1][0] = -sin(rotation);" +
		"	mz[1][1] = cos(rotation);" +

		// -- then rotate around the y axis
		"	mat4 my = mat4(1.0);" +
		"	my[0][0] = cos(rotation);" +
		"	my[0][2] = -sin(rotation);" +
		"	my[2][0] = sin(rotation);" +
		"	my[2][2] = cos(rotation);" +

		// -- rotate the vertex before translating it
		"	vec4 rotPos = aPosition * mz * my;" +
		// -- now translate it to the plane position
		"	gl_Position = uMVPMatrix * (rotPos + aPlanePosition);\n" +
		"	vTextureCoord = aTextureCoord;\n" +
		"	float pdist = length(uCamPos - gl_Position.xyz);\n" +
		// -- quick & dirty fog :O
		"	vFog = 1.0 - ((1.0 / 50.0) * pdist);" +
		"}\n";
	
	protected static final String mFShader = 
		"precision mediump float;\n" +

		"varying vec2 vTextureCoord;\n" +
		"uniform sampler2D uDiffuseTexture;\n" +
		"varying float vFog;\n" +

		"void main() {\n" +
		"	gl_FragColor = texture2D(uDiffuseTexture, vTextureCoord);\n" +
		"	gl_FragColor.rgb *= vFog;" +
		"}\n";
	
	protected float[] mCamPos;
	
	protected int muCamPosHandle;
	protected int muTimeHandle;
	protected int maPlanePositionHandle;
	protected int maRotationSpeedHandle;
	
	public PlanesGaloreMaterial() {
		super(mVShader, mFShader, false);
		mCamPos = new float[3];
		setShaders(mUntouchedVertexShader, mUntouchedFragmentShader);
	}
	
	public PlanesGaloreMaterial(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader, false);
		setShaders(mUntouchedVertexShader, mUntouchedFragmentShader);
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		super.setShaders(vertexShader, fragmentShader);
		muTimeHandle = getUniformLocation("uTime");
		muCamPosHandle = getUniformLocation("uCamPos");
		maPlanePositionHandle = getAttribLocation("aPlanePosition");
		maRotationSpeedHandle = getAttribLocation("aRotationSpeed");
	}
	
	public void setPlanePositions(final int planePosBufferHandle) {
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, planePosBufferHandle);
		GLES20.glEnableVertexAttribArray(maPlanePositionHandle);
		fix.android.opengl.GLES20.glVertexAttribPointer(maPlanePositionHandle, 3, GLES20.GL_FLOAT,
				false, 0, 0);
	}
	
	public void setRotationSpeeds(final int rotSpeedsBufferHandle) {
		GLES20.glBindBuffer(GLES20.GL_ARRAY_BUFFER, rotSpeedsBufferHandle);
		GLES20.glEnableVertexAttribArray(maRotationSpeedHandle);
		fix.android.opengl.GLES20.glVertexAttribPointer(maRotationSpeedHandle, 1, GLES20.GL_FLOAT,
				false, 0, 0);
	}

	public void setCameraPosition(Number3D cameraPos) {
		mCamPos[0] = cameraPos.x; mCamPos[1] = cameraPos.y; mCamPos[2] = cameraPos.z;
		GLES20.glUniform3fv(muCamPosHandle, 1, mCamPos, 0);
	}
	
	public void setTime(float time) {
		GLES20.glUniform1f(muTimeHandle, time);
	}
}
