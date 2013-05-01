package project.android.imageprocessing.filter.effect;

import project.android.imageprocessing.filter.BasicFilter;
import android.graphics.PointF;
import android.opengl.GLES20;

/**
 * Creates a swirl distortion on the image
 * radius: The radius from the center to apply the distortion
 * center: The center of the image (in normalized coordinates from 0 - 1.0) about which to twist
 * angle: The amount of twist to apply to the image
 * @author Chris Batt
 */
public class SwirlFilter extends BasicFilter {
	protected static final String UNIFORM_CENTER = "u_Center";
	protected static final String UNIFORM_RADIUS = "u_Radius";
	protected static final String UNIFORM_ANGLE = "u_Angle";
	
	private int centerHandle;
	private int radiusHandle;
	private int angleHandle;
	private float radius;
	private PointF center;
	private float angle;
	
	public SwirlFilter(PointF center, float radius, float angle) {
		this.center = center;
		this.radius = radius;
		this.angle = angle;
	}
		
	@Override
	protected String getFragmentShader() {
		return 
				 "precision mediump float;\n" 
				+"uniform sampler2D "+UNIFORM_TEXTURE0+";\n"  
				+"varying vec2 "+VARYING_TEXCOORD+";\n"	
				+"uniform vec2 "+UNIFORM_CENTER+";\n"
				+"uniform float "+UNIFORM_RADIUS+";\n"
				+"uniform float "+UNIFORM_ANGLE+";\n"
				
		  		+"void main(){\n"
			    +"   highp vec2 textureCoordinateToUse = "+VARYING_TEXCOORD+";\n"
			    +"   highp float dist = distance("+UNIFORM_CENTER+", "+VARYING_TEXCOORD+");\n"
			    +"   if (dist < "+UNIFORM_RADIUS+") {\n"
			    +"     textureCoordinateToUse -= "+UNIFORM_CENTER+";\n"
			    +"     highp float percent = ("+UNIFORM_RADIUS+" - dist) / "+UNIFORM_RADIUS+";\n"
			    +"     highp float theta = percent * percent * "+UNIFORM_ANGLE+" * 8.0;\n"
			    +"	   highp float s = sin(theta);\n"
			    +"     highp float c = cos(theta);\n"
			    +"     textureCoordinateToUse = vec2(dot(textureCoordinateToUse, vec2(c, -s)), dot(textureCoordinateToUse, vec2(s, c)));\n"
			    +"     textureCoordinateToUse += "+UNIFORM_CENTER+";\n"
	     		+"   }\n"
			    +"   gl_FragColor =  texture2D("+UNIFORM_TEXTURE0+", textureCoordinateToUse);\n"
		  		+"}\n";
	}
	
	@Override
	protected void initShaderHandles() {
		super.initShaderHandles();
		centerHandle = GLES20.glGetUniformLocation(programHandle, UNIFORM_CENTER);
		radiusHandle = GLES20.glGetUniformLocation(programHandle, UNIFORM_RADIUS);
		angleHandle = GLES20.glGetUniformLocation(programHandle, UNIFORM_ANGLE);
	}
	
	@Override
	protected void passShaderValues() {
		super.passShaderValues();
		GLES20.glUniform2f(centerHandle, center.x, center.y);
		GLES20.glUniform1f(radiusHandle, radius);
		GLES20.glUniform1f(angleHandle, angle);
	}
}