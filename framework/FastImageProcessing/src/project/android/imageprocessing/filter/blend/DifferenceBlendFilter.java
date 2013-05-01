package project.android.imageprocessing.filter.blend;

import project.android.imageprocessing.filter.MultiInputFilter;

/**
 * Applies a difference blend of two images.
 * @author Chris Batt
 */
public class DifferenceBlendFilter extends MultiInputFilter {
	public DifferenceBlendFilter() {
		super(2);
	}
	
	@Override
	protected String getFragmentShader() {
		return 
				 "precision mediump float;\n" 
				+"uniform sampler2D "+UNIFORM_TEXTURE0+";\n"  
				+"uniform sampler2D "+UNIFORM_TEXTUREBASE+1+";\n" 
				+"varying vec2 "+VARYING_TEXCOORD+";\n"	
				
		  		+"void main(){\n"
		  		+"   vec4 color1 = texture2D("+UNIFORM_TEXTURE0+","+VARYING_TEXCOORD+");\n"
		  		+"   vec4 color2 = texture2D("+UNIFORM_TEXTUREBASE+1+","+VARYING_TEXCOORD+");\n"
		  		+"   gl_FragColor = vec4(abs(color2.rgb - color1.rgb), color1.a);\n"
		  		+"}\n";	
	}
}