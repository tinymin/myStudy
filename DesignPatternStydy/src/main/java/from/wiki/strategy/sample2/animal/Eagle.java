package from.wiki.strategy.sample2.animal;


import from.wiki.strategy.sample2.cry.BirdCry;
import from.wiki.strategy.sample2.fly.FlyWithWings;

public class Eagle extends Animal {
	
	public Eagle(){
		
		cry = new BirdCry();
		
		fly = new FlyWithWings();
	}
	
	public void display(){
		
		System.out.println("독수리");
	}

}
