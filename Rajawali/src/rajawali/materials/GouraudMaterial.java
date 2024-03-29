package rajawali.materials;

import rajawali.lights.ALight;
import android.graphics.Color;
import android.opengl.GLES20;


public class GouraudMaterial extends AAdvancedMaterial {
	protected static final String mVShader = 
		"precision mediump float;\n" +

		"uniform mat4 uMVPMatrix;\n" +
		"uniform mat3 uNMatrix;\n" +
		"uniform mat4 uMMatrix;\n" +
		"uniform mat4 uVMatrix;\n" +
		"uniform vec4 uAmbientColor;\n" +
		"uniform vec4 uAmbientIntensity;\n" +
		
		"attribute vec4 aPosition;\n" +
		"attribute vec3 aNormal;\n" +
		"attribute vec2 aTextureCoord;\n" +
		"attribute vec4 aColor;\n" +
		
		"varying vec2 vTextureCoord;\n" +
		"varying float vSpecularIntensity;\n" +
		"varying float vDiffuseIntensity;\n" +
		"varying vec3 vLightColor;\n" +
		"varying vec4 vColor;\n" +
		
		M_FOG_VERTEX_VARS +
		"%LIGHT_VARS%" +
		
		"\n#ifdef VERTEX_ANIM\n" +
		"attribute vec4 aNextFramePosition;\n" +
		"attribute vec3 aNextFrameNormal;\n" +
		"uniform float uInterpolation;\n" +
		"#endif\n\n" +
		
		"void main() {\n" +
		"	vec4 position = aPosition;\n" +
		"	vec3 normal = aNormal;\n" +
		"	#ifdef VERTEX_ANIM\n" +
		"	position = aPosition + uInterpolation * (aNextFramePosition - aPosition);\n" +
		"	normal = aNormal + uInterpolation * (aNextFrameNormal - aNormal);\n" +
		"	#endif\n" +
		
		"	gl_Position = uMVPMatrix * position;\n" +
		"	vTextureCoord = aTextureCoord;\n" +
		
		"	vec3 E = -vec3(uMMatrix * position);\n" +
		"	vec3 N = normalize(uNMatrix * normal);\n" +
		"	vec3 L = vec3(0.0);\n" +
		"	float dist = 0.0;\n" +
		"	float attenuation = 1.0;\n" +
		"	float NdotL = 0.0;\n" +

		"%LIGHT_CODE%" +
		"	vSpecularIntensity = clamp(vSpecularIntensity, 0.0, 1.0);\n" +
		"#ifndef TEXTURED\n" +
		"	vColor = vec4(vLightColor, 1.0) * aColor;\n" +
		"#endif\n" +
		M_FOG_VERTEX_DENSITY +
		"}";
		
	protected static final String mFShader = 
		"precision mediump float;\n" +

		"varying vec2 vTextureCoord;\n" +
		"varying float vSpecularIntensity;\n" +
		"varying float vDiffuseIntensity;\n" +
		"varying vec3 vLightColor;\n" +
		"varying vec4 vColor;\n" +
		
		"uniform sampler2D uDiffuseTexture;\n" +
		"uniform sampler2D uSpecularTexture;\n" +
		"uniform sampler2D uAlphaTexture;\n" +
		"uniform vec4 uAmbientColor;\n" +
		"uniform vec4 uAmbientIntensity;\n" + 
		"uniform vec4 uSpecularColor;\n" +
		"uniform vec4 uSpecularIntensity;\n" +
		
		M_FOG_FRAGMENT_VARS +	
		"%LIGHT_VARS%" +
		
		"void main() {\n" +
		"#ifdef TEXTURED\n" +
		"	vec4 diffuse = vec4(vLightColor, 1.0) * vDiffuseIntensity * texture2D(uDiffuseTexture, vTextureCoord);\n" +
		"#else\n" +
	    "	vec4 diffuse = vDiffuseIntensity * vColor;\n" +
	    "#endif\n" +
	    
		"#ifdef SPECULAR_MAP\n" +
		"   vec4 specular = uSpecularColor * vSpecularIntensity * uSpecularIntensity * texture2D(uSpecularTexture, vTextureCoord);\n" +
		"#else\n" +
		"	vec4 specular = uSpecularColor * vSpecularIntensity * uSpecularIntensity;\n" + 
		"#endif\n" +
		
		"	vec4 ambient = uAmbientIntensity * uAmbientColor;\n" + 	    
		"	gl_FragColor = diffuse + specular + ambient;\n" +
		
		"#ifdef ALPHA_MAP\n" +
		"	float alpha = texture2D(uAlphaTexture, vTextureCoord).r;\n" +
		"	gl_FragColor.a = alpha;\n" +
		"#else\n" +
		"	gl_FragColor.a = diffuse.a;\n" +
		"#endif\n" +
		M_FOG_FRAGMENT_COLOR +
		"}";
	
	protected int muSpecularColorHandle;
	protected int muSpecularIntensityHandle;
	protected float[] mSpecularColor;
	protected float[] mSpecularIntensity;
	
	public GouraudMaterial() {
		this(false);
	}
	
	public GouraudMaterial(boolean isAnimated) {
		super(mVShader, mFShader, isAnimated);
		mSpecularColor = new float[] { 1.0f, 1.0f, 1.0f, 1.0f };
		mSpecularIntensity = new float[] { 1f, 1f, 1f, 1.0f };
	}
	
	public GouraudMaterial(float[] specularColor) {
		this();
		mSpecularColor = specularColor;
	}

	@Override
	public void useProgram() {
		super.useProgram();
		GLES20.glUniform4fv(muSpecularColorHandle, 1, mSpecularColor, 0);
		GLES20.glUniform4fv(muSpecularIntensityHandle, 1, mSpecularIntensity, 0);
	}
	
	public void setSpecularColor(float[] color) {
		mSpecularColor = color;
	}
	
	public void setSpecularColor(float r, float g, float b, float a) {
		mSpecularColor[0] = r;
		mSpecularColor[1] = g;
		mSpecularColor[2] = b;
		mSpecularColor[3] = a;
	}
	
	public void setSpecularColor(int color) {
		setSpecularColor(Color.red(color) / 255f, Color.green(color) / 255f, Color.blue(color) / 255f, Color.alpha(color) / 255f);
	}
	
	public void setSpecularIntensity(float[] intensity) {
		mSpecularIntensity = intensity;
	}
	
	public void setSpecularIntensity(float r, float g, float b, float a) {
		mSpecularIntensity[0] = r;
		mSpecularIntensity[1] = g;
		mSpecularIntensity[2] = b;
		mSpecularIntensity[3] = a;
	}
	
	public void setShaders(String vertexShader, String fragmentShader)
	{
		StringBuffer vc = new StringBuffer();
		vc.append("float power = 0.0;\n");

		for(int i=0; i<mLights.size(); ++i) {
			ALight light = mLights.get(i);

			if(light.getLightType() == ALight.POINT_LIGHT) {
				vc.append("L = normalize(uLightPosition").append(i).append(" + E);\n");
				vc.append("dist = distance(-E, uLightPosition").append(i).append(");\n");
				vc.append("attenuation = 1.0 / (uLightAttenuation").append(i).append("[1] + uLightAttenuation").append(i).append("[2] * dist + uLightAttenuation").append(i).append("[3] * dist * dist);\n");
			} else if(light.getLightType() == ALight.SPOT_LIGHT) {
				vc.append("dist = distance(-E, uLightPosition").append(i).append(");\n");
				vc.append("attenuation = (uLightAttenuation").append(i).append("[1] + uLightAttenuation").append(i).append("[2] * dist + uLightAttenuation").append(i).append("[3] * dist * dist);\n");
				vc.append("L = normalize(uLightPosition").append(i).append(" + E);\n");
				vc.append("vec3 spotDir").append(i).append(" = normalize(-uLightDirection").append(i).append(");\n");
				vc.append("float spot_factor = dot( L, spotDir").append(i).append(" );\n");
				vc.append("if( uSpotCutoffAngle").append(i).append(" < 180.0 ) {\n");
					vc.append("if( spot_factor >= cos( radians( uSpotCutoffAngle").append(i).append(") ) ) {\n");
						vc.append("spot_factor = (1.0 - (1.0 - spot_factor) * 1.0/(1.0 - cos( radians( uSpotCutoffAngle").append(i).append("))));\n");
						vc.append("spot_factor = pow(spot_factor, uSpotFalloff").append(i).append("* 1.0/spot_factor);\n");
					vc.append("}\n");
					vc.append("else {\n");
						vc.append("spot_factor = 0.0;\n");
					vc.append("}\n");
					vc.append("L = vec3(L.y, L.x, L.z);\n");
					vc.append("}\n");
			}  else if(light.getLightType() == ALight.DIRECTIONAL_LIGHT) {
				vc.append("L = normalize(-uLightDirection").append(i).append(");");
			}
			vc.append("NdotL = max(dot(N, L), 0.1);\n");
			vc.append("power = NdotL * attenuation * uLightPower").append(i).append(";\n");
			vc.append("vDiffuseIntensity += power;\n");
			if(light.getLightType() == ALight.SPOT_LIGHT){
				vc.append("vLightColor += uLightColor").append(i).append(" * spot_factor;\n");
				vc.append("vSpecularIntensity += pow(NdotL, 6.0) * spot_factor;\n");
			}
			else{
				vc.append("vLightColor += power * uLightColor").append(i).append(";\n");
				vc.append("vSpecularIntensity += pow(NdotL, 6.0) * attenuation * uLightPower").append(i).append(";\n");
			}
		}
		
		super.setShaders(vertexShader.replace("%LIGHT_CODE%", vc.toString()), fragmentShader);
		muSpecularColorHandle = getUniformLocation("uSpecularColor");
		muSpecularIntensityHandle = getUniformLocation("uSpecularIntensity");
	}
}