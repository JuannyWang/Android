package rajawali.materials;

import rajawali.lights.ALight;

public class DiffuseMaterial extends AAdvancedMaterial {
	protected static final String mVShader = 
		"precision mediump float;\n" +
		"uniform mat4 uMVPMatrix;\n" +
		"uniform mat3 uNMatrix;\n" +
		"uniform mat4 uMMatrix;\n" +
		"uniform mat4 uVMatrix;\n" +
		
		"attribute vec4 aPosition;\n" +
		"attribute vec3 aNormal;\n" +
		"attribute vec2 aTextureCoord;\n" +
		"attribute vec4 aColor;\n" +
		
		"varying vec2 vTextureCoord;\n" +
		"varying vec3 N;\n" +
		"varying vec4 V;\n" +
		"varying vec4 vColor;\n" +
		
		M_FOG_VERTEX_VARS +
		"%LIGHT_VARS%" +
		M_SKELETAL_ANIM_VERTEX_VARS + 
		
		"\n#ifdef VERTEX_ANIM\n" +
		"attribute vec4 aNextFramePosition;\n" +
		"attribute vec3 aNextFrameNormal;\n" +
		"uniform float uInterpolation;\n" +
		"#endif\n\n" +
		
		"void main() {\n" +
		
		M_SKELETAL_ANIM_VERTEX_MATRIX +
		
		"	vec4 position = aPosition;\n" +
		"	float dist = 0.0;\n" +
		"	vec3 normal = aNormal;\n" +
		"	#ifdef VERTEX_ANIM\n" +
		"	position = aPosition + uInterpolation * (aNextFramePosition - aPosition);\n" +
		"	normal = aNormal + uInterpolation * (aNextFrameNormal - aNormal);\n" +
		"	#endif\n" +
		
		"#ifdef SKELETAL_ANIM\n" +
		"	gl_Position = uMVPMatrix * TransformedMatrix * position;\n" +
		"#else\n" +
		"	gl_Position = uMVPMatrix * position;\n" +
		"#endif\n" +
		"	vTextureCoord = aTextureCoord;\n" +
		"#ifdef SKELETAL_ANIM\n" +
		"	N = normalize(uNMatrix * mat3(TransformedMatrix) * normal);\n" +
		"#else\n" +
		"	N = normalize(uNMatrix * normal);\n" +
		"#endif\n" +
		"	V = uMMatrix * position;\n" +
		"#ifndef TEXTURED\n" +
		"	vColor = aColor;\n" +
		"#endif\n" +
		
		"%LIGHT_CODE%" +
		
		M_FOG_VERTEX_DENSITY +
		"}";
		
	protected static final String mFShader =
		"precision mediump float;\n" +

		"varying vec2 vTextureCoord;\n" +
		"varying vec3 N;\n" +
		"varying vec4 V;\n" +
		"varying vec4 vColor;\n" +
 
		"uniform sampler2D uDiffuseTexture;\n" +
		"uniform vec4 uAmbientColor;\n" +
		"uniform vec4 uAmbientIntensity;\n" +
		
		M_FOG_FRAGMENT_VARS +		
		"%LIGHT_VARS%" +
		
		"void main() {\n" +
		"	float intensity = 0.0;\n" +
		"   float power = 0.0;\n" +
		"	float NdotL = 0.0;\n" +
		"	float dist = 0.0;\n" +
		"	vec3 Kd = vec3(0.0);\n" +
		"	vec3 L = vec3(0.0);\n" +
		"#ifdef TEXTURED\n" +
		"	gl_FragColor = texture2D(uDiffuseTexture, vTextureCoord);\n" +
		"#else\n" +
	    "	gl_FragColor = vColor;\n" +
	    "#endif\n" +

	    "%LIGHT_CODE%" +
		"	vec3 ambient = uAmbientIntensity.rgb * uAmbientColor.rgb;\n" +
		"	vec3 diffuse = Kd * gl_FragColor.rgb;\n" +
		"	gl_FragColor.rgb = ambient + diffuse;\n" +
		M_FOG_FRAGMENT_COLOR +		
		"}";
	
	public DiffuseMaterial() {
		this(false);
	}
	
	public DiffuseMaterial(String vertexShader, String fragmentShader, boolean isAnimated) {
		super(vertexShader, fragmentShader, isAnimated);
	}
	
	public DiffuseMaterial(boolean isAnimated) {
		this(mVShader, mFShader, isAnimated);
	}
	
	public DiffuseMaterial(int parameters) {
		super(mVShader, mFShader, parameters);
	}
	
	public DiffuseMaterial(String vertexShader, String fragmentShader) {
		super(vertexShader, fragmentShader);
	}
	
	public void setShaders(String vertexShader, String fragmentShader) {
		StringBuffer fc = new StringBuffer();
		StringBuffer vc = new StringBuffer();

		for(int i=0; i<mLights.size(); ++i) {
			ALight light = mLights.get(i);
			
			if(light.getLightType() == ALight.POINT_LIGHT) {
				vc.append("dist = distance(V.xyz, uLightPosition").append(i).append(");\n");
				vc.append("vAttenuation").append(i).append(" = 1.0 / (uLightAttenuation").append(i).append("[1] + uLightAttenuation").append(i).append("[2] * dist + uLightAttenuation").append(i).append("[3] * dist * dist);\n");
				fc.append("L = normalize(uLightPosition").append(i).append(" - V.xyz);\n");
			} else if(light.getLightType() == ALight.SPOT_LIGHT) {
				vc.append("dist = distance(V.xyz, uLightPosition").append(i).append(");\n");
				vc.append("vAttenuation").append(i).append(" = (uLightAttenuation").append(i).append("[1] + uLightAttenuation").append(i).append("[2] * dist + uLightAttenuation").append(i).append("[3] * dist * dist);\n");
				fc.append("L = normalize(uLightPosition").append(i).append(" - V.xyz);\n");
				fc.append("vec3 spotDir").append(i).append(" = normalize(-uLightDirection").append(i).append(");\n");
				fc.append("float spot_factor = dot( L, spotDir").append(i).append(" );\n");
				fc.append("if( uSpotCutoffAngle").append(i).append(" < 180.0 ) {\n");
					fc.append("if( spot_factor >= cos( radians( uSpotCutoffAngle").append(i).append(") ) ) {\n");
						fc.append("spot_factor = (1.0 - (1.0 - spot_factor) * 1.0/(1.0 - cos( radians( uSpotCutoffAngle").append(i).append("))));\n");
						fc.append("spot_factor = pow(spot_factor, uSpotFalloff").append(i).append("* 1.0/spot_factor);\n");
					fc.append("}\n");
					fc.append("else {\n");
						fc.append("spot_factor = 0.0;\n");
					fc.append("}\n");
					fc.append("L = vec3(L.y, L.x, L.z);\n");
					fc.append("}\n");
			} else if(light.getLightType() == ALight.DIRECTIONAL_LIGHT) {
				vc.append("vAttenuation").append(i).append(" = 1.0;\n");
				fc.append("L = normalize(-uLightDirection").append(i).append(");\n");
			}

			fc.append("NdotL = max(dot(N, L), 0.1);\n");
			fc.append("power = uLightPower").append(i).append(" * NdotL * vAttenuation").append(i).append(";\n");
			fc.append("intensity += power;\n"); 
			
			if(light.getLightType() == ALight.SPOT_LIGHT)
				fc.append("Kd.rgb += uLightColor").append(i).append(" * spot_factor;\n");
			else
				fc.append("Kd.rgb += uLightColor").append(i).append(" * power;\n");
		}
		
		super.setShaders(vertexShader.replace("%LIGHT_CODE%", vc.toString()), fragmentShader.replace("%LIGHT_CODE%", fc.toString()));
	}
}