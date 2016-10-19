package Entity;

import java.util.HashMap;

/**
 * Initialize Attractions
 * @version 1.0
 *
 */
public class Constants {

	public static final HashMap<String,Attraction> attracMap= new HashMap<String,Attraction>();
	
	public Constants(){
		Attraction spidermanEscape = new Attraction();
		spidermanEscape.setAttractType("Thrill Rides");
		spidermanEscape.setAttractName("Spiderman Escape");
		spidermanEscape.setAge(">=8");
		spidermanEscape.setHeight(">=100");
		attracMap.put("Spiderman Escape", spidermanEscape);
		
		Attraction iceAgeAdventure = new Attraction();
		iceAgeAdventure.setAttractType("Thrill Rides");
		iceAgeAdventure.setAttractName("Ice Age Adventure");
		iceAgeAdventure.setAge(">=8");
		iceAgeAdventure.setHeight("<=200");
		attracMap.put("Ice Age Adventure", iceAgeAdventure);
		
		Attraction canyonBlaster = new Attraction();
		canyonBlaster.setAttractType("Thrill Rides");
		canyonBlaster.setAttractName("Canyon Blaster");
		canyonBlaster.setAge(">=8");
		canyonBlaster.setHeight(">=120");
		attracMap.put("Canyon Blaster", canyonBlaster);
		
		Attraction Theatre = new Attraction();
		Theatre.setAttractType("Family Fun");
		Theatre.setAttractName("4D Theatre");
		Theatre.setAge("none");
		Theatre.setHeight("none");
		attracMap.put("4D Theatre", Theatre);
		
		Attraction flowRider = new Attraction();
		flowRider.setAttractType("Family Fun");
		flowRider.setAttractName("Flow Rider");
		flowRider.setAge("none");
		flowRider.setHeight(">=100");
		attracMap.put("Flow Rider", flowRider);
		
		Attraction carousel = new Attraction();
		carousel.setAttractType("Family Fun");
		carousel.setAttractName("Carousel");
		carousel.setAge("none");
		carousel.setHeight("<=100");
		attracMap.put("Carousel", carousel);
	}
	
	
}
