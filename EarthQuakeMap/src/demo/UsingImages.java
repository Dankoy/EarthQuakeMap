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
		backgroundImg.resize(0, height);
		image(backgroundImg, 0, 0);
		
		int[] colors = sunColorSeconds(second());
		fill(colors[0], colors[1], colors[2]);
		ellipse(width-100, height/6, 50, 50);
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
