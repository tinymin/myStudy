package from.wiki.strategy.sample2;

import from.wiki.strategy.sample2.animal.Animal;
import from.wiki.strategy.sample2.animal.Eagle;
import from.wiki.strategy.sample2.animal.Tiger;
import from.wiki.strategy.sample2.animal.Turtle;

public class PlayAnimal {
	
	public static void main(String[] args){			// main 함수 실행
		
		Animal tiger = new Tiger();				// Animal 클래스를 상속받은 Tiger 객체 생성
		
		tiger.display();						// Tiger의 run() 함수 실행
		tiger.performCry();						// Tiger의 performCry() 함수 실행
		tiger.performFly();						// Tiger의 performFly() 함수 실행		
		
		System.out.println("------------------");
		
		Animal eagle = new Eagle();				// Animal 클래스를 상속받은 Eagle 객체 생성
		
		eagle.display();						// Eagle의 run() 함수 실행
		eagle.performCry();						// Eagle의 performCry() 함수 실행
		eagle.performFly();						// Eagle의 performFly() 함수 실행
		
		System.out.println("------------------");
		
		Animal turtle = new Turtle();			// Animal 클래스를 상속받은 Turtle 객체 생성
		
		turtle.display();						// Turtle의 run() 함수 실행
		turtle.performCry();					// Turtle의 performCry() 함수 실행		
		turtle.performFly();					// Turtle의 performFly() 함수 실행
	}

}
