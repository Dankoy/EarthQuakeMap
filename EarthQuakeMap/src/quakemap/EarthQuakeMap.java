package quakemap;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import processing.core.PApplet;

public class EarthQuakeMap extends PApplet {

	private static final long serialVersionUID = 1L;
	
	private static final boolean offline = false;
	
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	private UnfoldingMap map;
	
	
	public void setup() {
		
		size(950, 600, P2D);
		
		if (offline) {
			map = new UnfoldingMap(this, 50, 50, 850, 500, new MBTilesMapProvider(mbTilesString));
			
		}
		else {
			map = new UnfoldingMap(this, 50, 50, 850, 500, new Google.GoogleMapProvider());
		}
		
		map.zoomToLevel(2);
		MapUtils.createDefaultEventDispatcher(this, map);
		
	}
	
	public void draw() {
		background(10);
		map.draw();
	}
	
}
