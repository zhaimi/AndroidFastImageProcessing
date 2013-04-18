package project.android.imageprocessing.filter.colour;

import project.android.imageprocessing.filter.BasicFilter;

/**
 * A grey scale filter extension of the BasicFilter.  
 * The grey scale filter is implemented by setting red, green and blue to the average of red, green and blue.
 * The alpha value of the input is preserved.
 * @author Chris Batt
 */
public class GreyScaleFilter extends BasicFilter {
	@Override
	protected String getFragmentShader() {
		return 
				 "precision mediump float;\n" 
				+"uniform sampler2D "+UNIFORM_TEXTURE0+";\n"  
				+"varying vec2 "+VARYING_TEXCOORD+";\n"	
				
		  		+"void main(){\n"
		  		+"   vec4 color = texture2D("+UNIFORM_TEXTURE0+","+VARYING_TEXCOORD+");\n"	
				+"   float grey =  color.r/3.0 + color.g/3.0 + color.b/3.0;"
				+"   gl_FragColor = vec4(grey, grey, grey, color.a);"
		  		+"}\n";	
	}
}