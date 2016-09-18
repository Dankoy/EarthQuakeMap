package quakemap;

import java.util.ArrayList;
import java.util.List;

import de.fhpotsdam.unfolding.UnfoldingMap;
import de.fhpotsdam.unfolding.data.PointFeature;
import de.fhpotsdam.unfolding.marker.Marker;
import de.fhpotsdam.unfolding.marker.SimplePointMarker;
import de.fhpotsdam.unfolding.providers.Google;
import de.fhpotsdam.unfolding.providers.MBTilesMapProvider;
import de.fhpotsdam.unfolding.utils.MapUtils;
import parsing.ParseFeed;
import processing.core.PApplet;

public class EarthQuakeMap extends PApplet {

	private static final long serialVersionUID = 1L;
	
	private static final boolean offline = false;
	
	public static String mbTilesString = "blankLight-1-3.mbtiles";
	
	private UnfoldingMap map;
	
	private String earthquakesURL = "http://earthquake.usgs.gov/earthquakes/feed/v1.0/summary/2.5_week.atom";
	
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
		
		List<Marker> markers = new ArrayList<Marker>();
		
		List<PointFeature> earthquakes = ParseFeed.parseEarthquake(this, earthquakesURL);
		
		for(PointFeature eq: earthquakes) {
			markers.add(new SimplePointMarker(eq.getLocation(), eq.getProperties()));
		}
		
		map.addMarkers(markers);
		
	}
	
	public void draw() {
		background(10);
		map.draw();
	}
	
}
