package project.android.imageprocessing.filter.colour;

import android.opengl.GLES20;
import project.android.imageprocessing.filter.BasicFilter;

public class OpacityFilter extends BasicFilter {
	private static final String UNIFORM_OPACITY = "u_Opacity";
	
	private int opacityHandle;
	private float opacity;
	
	public OpacityFilter(float opacity) {
		this.opacity = opacity;
	}
	
	@Override
	protected void initShaderHandles() {
		super.initShaderHandles();
		opacityHandle = GLES20.glGetUniformLocation(programHandle, UNIFORM_OPACITY);
	}
	
	@Override
	protected void passShaderValues() {
		super.passShaderValues();
		GLES20.glUniform1f(opacityHandle, opacity);
	}
	
	@Override
	protected String getFragmentShader() {
		return 
				"precision mediump float;\n" 
				+"uniform sampler2D "+UNIFORM_TEXTURE0+";\n"  
				+"varying vec2 "+VARYING_TEXCOORD+";\n"	
				+"uniform float "+UNIFORM_OPACITY+";\n"	
				
		  		+"void main(){\n"
		  		+"   vec4 color = texture2D("+UNIFORM_TEXTURE0+","+VARYING_TEXCOORD+");\n"
		  		+"   gl_FragColor = vec4(color.rgb, color.a * "+UNIFORM_OPACITY+");\n"
		  		+"}\n";	
	}
}