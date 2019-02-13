package from.wiki.strategy.sample2.animal;

import from.wiki.strategy.sample2.cry.Cry;
import from.wiki.strategy.sample2.fly.Fly;

public abstract class Animal {		//Animall 상속
	
	/**
	 * @uml.property  name="fly"
	 * @uml.associationEnd  
	 */
	protected Fly fly;				// Fly 인터페이스 선언
	/**
	 * @uml.property  name="cry"
	 * @uml.associationEnd  
	 */
	protected Cry cry;				// Cry 인터페이스 선언
	
	public Animal(){				// Animal 생성자
		
	}

	public void performFly(){		// Fly 인터페이스에 연결된 객체의 fly() 함수 실행
		fly.fly();
	}
	
	public void performCry(){		// Cry 인터페이스에 연결된 객체의 Cry() 함수 실행
		cry.cry();
	}
	
	public void move(){				// move() 함수 구현
		System.out.println("움직인다.");	// 움직인다 출력
	}
	
	public abstract void display();		// run() 함수 추상
	
	
	/**
	 * @return
	 * @uml.property  name="fly"
	 */
	public Fly getFly() {
		return fly;
	}

	/**
	 * @param fly
	 * @uml.property  name="fly"
	 */
	public void setFly(Fly fly) {
		this.fly = fly;
	}


	/**
	 * @return
	 * @uml.property  name="cry"
	 */
	public Cry getCry() {
		return cry;
	}



	/**
	 * @param cry
	 * @uml.property  name="cry"
	 */
	public void setCry(Cry cry) {
		this.cry = cry;
	}


}
