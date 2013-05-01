package project.android.imageprocessing.filter.processing;

import project.android.imageprocessing.filter.GroupFilter;
import project.android.imageprocessing.filter.colour.GreyScaleFilter;

/**
 * This uses the full Canny process to highlight one-pixel-wide edges
 * blurSize: A multiplier for the prepass blur size, ranging from 0.0 on up
 * upperThreshold: Any edge with a gradient magnitude above this threshold will pass and show up in the final result
 * lowerThreshold: Any edge with a gradient magnitude below this threshold will fail and be removed from the final result
 * @author Chris Batt
 */
public class CannyEdgeDetectionFilter extends GroupFilter {
	public CannyEdgeDetectionFilter(float blurSize, float lowerThreshold, float upperThreshold) {
		GreyScaleFilter grey = new GreyScaleFilter();
		GaussianBlurFilter blur = new GaussianBlurFilter(blurSize);
		DirectionalSobelEdgeDetectionFilter sobel = new DirectionalSobelEdgeDetectionFilter();
		DirectionalNonMaximumSuppressionFilter suppression = new DirectionalNonMaximumSuppressionFilter(upperThreshold, lowerThreshold);
		WeakPixelInclusionFilter weakPixel = new WeakPixelInclusionFilter();
		grey.addTarget(blur);
		blur.addTarget(sobel);
		sobel.addTarget(suppression);
		suppression.addTarget(weakPixel);
		weakPixel.addTarget(this);
		
		registerInitialFilter(grey);
		registerFilter(blur);
		registerFilter(sobel);
		registerFilter(suppression);
		registerTerminalFilter(weakPixel);
	}
}