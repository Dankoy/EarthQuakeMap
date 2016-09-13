package quakemap;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.geo.Location;
import de.fhpotsdam.unfolding.providers.AbstractMapProvider;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class TwoMaps extends PApplet {

	private static final long serialVersionUID = 1L;
	
	// Variable is using to find out if you are working with or without Internet connection
	private static final boolean offline = false;
	
	// This is where to find the local tiles, for working without an Internet connection
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	UnfoldingMap map1;
	
	public void setup() {
		
		// This sets the background color for the Applet.  
		size(800, 600);
		
		this.background(200, 200, 200);
		
		// Provider for map
		AbstractMapProvider provider = new Google.GoogleTerrainProvider();
		int zoomLevel = 10;
		
		if (offline) {
			provider = new MBTilesMapProvider(mbTilesString);
			zoomLevel = 3;
		}
		
		map1 = new UnfoldingMap(this, 50, 50, 350, 500, provider);
		map1.zoomAndPanTo(zoomLevel, new Location(47.441812f, 7.7644f));
		
		// Makes the map interactive
		MapUtils.createDefaultEventDispatcher(this, map1);
		
	}
	
	public void draw() {
		map1.draw();
	}
	
}
