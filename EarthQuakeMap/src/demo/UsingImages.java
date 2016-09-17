package demo;

import processing.core.PApplet;
import processing.core.PImage;

public class UsingImages extends PApplet {

	private static final long serialVersionUID = 1L;
	
	PImage backgroundImg;
	
	public void setup() {
		size(800, 600);
		background(255);
		stroke(0);
		backgroundImg = loadImage("basel-old-town.jpg", "jpg");
	}
	
	public void draw() {
		backgroundImg.resize(0, height);	// resize loaded image to full height of canvas
		image(backgroundImg, 0, 0);			// display image
		
		int[] colors = sunColorSeconds(second());	// calculate color for sun
		fill(colors[0], colors[1], colors[2]);	// fill the sun with color depending on time
		ellipse(width-100, height/6, 50, 50);	// draw the sun
	}
	
	public int[] sunColorSeconds(float seconds) {
		int[] rgb = new int[3];
		
		/* Scale the brightness of the yellow based on the seconds.  0 seconds 
		* is bright yellow.  30 seconds is black.
		*/ 
		float diff = Math.abs(30-seconds);
		
		float ratio = diff/30;
		rgb[0] = (int)(255*ratio);
		rgb[1] = (int)(255*ratio);
		rgb[2] = 0;
		
		return rgb;
		
	}
	
	public static void main (String[] args) {
		//Add main method for running as application
		PApplet.main(new String[] {"--present", "UsingImages"});
	}
	
}
