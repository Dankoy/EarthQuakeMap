package quakemap;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class TwoMaps extends PApplet {

	private static final long serialVersionUID = 1L;
	
	UnfoldingMap map1;
	
	public void setup() {
		size(800, 600);
		this.background(200, 200, 200);
		
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		int zoomLevel = 10;
		
		map1 = new UnfoldingMap(this, 100, 50, 50, 50, provider);
		map1.zoomAndPanTo(zoomLevel, new Location(47.441812f, 7.7644f));
		MapUtils.createDefaultEventDispatcher(this, map1);
		
	}
	
	public void draw() {
		map1.draw();
	}
	
}
