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
		backgroundImg = loadImage("basel-back.jpg", "jpg");
	}
	
	public void draw() {
		backgroundImg.resize(0, height);
		image(backgroundImg, 0, 0);
	}
	
}
