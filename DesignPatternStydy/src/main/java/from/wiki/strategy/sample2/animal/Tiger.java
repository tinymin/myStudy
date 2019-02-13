package from.wiki.strategy.sample2.animal;


import from.wiki.strategy.sample2.cry.TigerCry;
import from.wiki.strategy.sample2.fly.FlyNoway;

public class Tiger extends Animal{		// Animal 상속
	
	public Tiger(){					// Tiger 객체 생성자
		
		cry = new TigerCry();		// Animal의 cry 변수에 Cry 인터페이스의 TigerCry를 연결한다.
		
		fly = new FlyNoway();		// Animal의 fly 변수에 Fly 인터페이스의 FlyNoWay를 연결한다.
	}
	
	public void display(){             // run 함수 구현
		System.out.println("호랑이");     // 호랑이 출력
	}

}
