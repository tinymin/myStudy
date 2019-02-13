package from.wiki.strategy.sample2.animal;


import from.wiki.strategy.sample2.cry.CryNoWay;
import from.wiki.strategy.sample2.fly.FlyNoway;

public class Turtle extends Animal{		// Animal 상속
	
	public Turtle(){			// Turtle 객체 생성자
			
		cry = new CryNoWay();	// Animal의 cry 변수에 Cry 인터페이스의 CryNoWay를 연결한다.
		
		fly = new FlyNoway();	// Animal의 fly 변수에 Fly 인터페이스의 FlyNoWay를 연결한다.
		
	}
	
	public void display(){			 // run 함수 구현
		
		System.out.println("거북이");	// 거북이 출력
	}

}
