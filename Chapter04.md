# Chapter 4. 클래스, 객체, 인터페이스

> 스터디 날짜 : 2018-10-17(수)


- 자바와 달리 인터페이스에 프로퍼티를 생성할 수 있다.
- 코틀린의 선언은 기본적으로 `final`이며 `public`이다.
- 코틀린 중첩 클래스는 외부 클래스를 참조할 수 없다.
- 클래스를 data로 선언하면 컴파일러가 일부 표준 메소드를 생성해준다.
- `object` 키워드를 사용하면 싱클턴 클래스, 동반 객체<sup>companion object</sup>, 객체 식<sup>object expression(자바의 익명 클래스)</sup>를 선언할 수 있다.

## 4.1 클래스 계층 정의

- 코틀린에는 새로운 접근제한자인 `sealed`가 추가 되었다. `sealed`는 클래스 상속을 제한한다.

### 4.1.1 코틀린 인터페이스(Interface)

- 코틀린 인터페이스는, Java8의 디폴트 메소드와 비슷하게, 구현되어 있는 메소드도 정의 할 수 있다.
- 인터페이스에는 아무런 상태(field)도 들어갈 수 없다.
- 인터페이스를 선언하는 키워드는 `interface`이다.

 ```kotlin
 /* 간단한 인터페이스 선언하기 */
interface Clickable {
    fun click()
}

/* 단순한 인터페이스 구현하기 */
class Button : Clickable {
    override fun click() = println("I was clicked")
}

>>> Button().click()
I was clicked
``` 

- 자바에서는 extends, implements를 사용하지만 코틀린은 콜론(:) 뒤에 인터페이스 또는 클래스의 이름을 붙인다.
- 자바와 동일하게 인터페이스는 제한없이 구현할 수 있지만, 클래스는 오직 1개만 상속 가능하다.
- `override` 키워드는 자바의 @override와 동일한 역할을 한다. 대신 코틀린은 `override` 변경자<sup>modifier</sup>를 꼭 사용해야 한다.
- 자바의 경우, 부모 클래스와 자식 클래스 간의 같은 이름의 메소드를 허용하고 있지만, 코틀린의 경우는 `override` 변경자를 사용하지 않으면 <font color="red">컴파일 오류가 발생</font>한다.
- 디폴트 메소드의 경우, 자바에서는 default 키워드를 붙이지만, 코틀린은 그냥 본문을 구현하면 된다.

```kotlin
/* 디폴트 메소드 정의 하기 */
interface  Clickable {
    fun click()
    fun showOff() = println("I'm clickable!") /* 디폴트 메소드 */
}

/* Clickable과 동일한 메소드를 구현하는 다른 메소드 */
interface Focusable {
    fun setFocus(b: Boolean) = println("I ${if(b) "got" else "lost"} focus.")
    fun showOff() = println("I'm focusable!")
}
```

- 위 예제의 `Clickable`과 `Focusable`을 동시에 구현했을 때 `showOff` 메소드는 둘 중 어느 것도 선택되지 않으며, 반드시 `override`를 통해 구현을 해야한다. 구현하지 않는 경우 아래와 같은 컴파일 오류가 발생한다.

```kotlin
The class 'Button' must
override public open fun showOff() because it inherits
many implementations of it.
``` 

- 부모의 구현된 메소드를 호출 하려면  자바와 마찬가지로 `super`를 사용한다.
- super<Clickable>.showOff() 처럼 꺾쇠 괄호를 사용한다.

```kotlin
class Button : Clickable, Focusable {
    override fun click() = println("I was clicked")
    override fun showOff() {
        /* 부모 타입의 이름을 꺾쇠 괄호에 넣어서 'super'를 지정하면
           부모의 메소드를 호출 할 수 있다. */
        super<Clickable>.showOff()
        super<Focusable>.showOff()
    }
}
```

### 4.1.2 open, final, abstract 변경자: 기본적으로 final

- 코틀린의 클래스와 메소드는 기본적으로 `final`이다.
- 클래스의 상속을 허용하려면 클래스 앞에 `open` 변경자를 붙여야 한다.
- 오버라이드를 허용하고 싶은 메소드나 프로퍼티 앞에도 `open` 변경자를 붙여야 한다.
- 부모 클래스의 메소드를 자식 클래스에서 오버라이드 한 경우, 해당 메소드는 기본적으로 열려있다.

```kotlin
/* 열린 메소드를 포함하는 열린 클래스 정의하기 */
open class RichButton : Clickable { /* 이 클래스는 열려있다. 상속 가능하다 */
    fun disable() { } /* open 변경자가 없기 때문에 final 이다. 자식 클래스에서 override 불가하다. */
    open fun animate() { } /* open 이므로 override 가능하다. */
    override fun click() { } /* override 한 메소드는 기본적으로 모두 열려있다. */
}
```

-  오버라이드 한 메소드를 다시 오버라이드 하지 못 하게 금지하려면 메소드 앞에 `final`을 명시한다.

```kotlin
open class RichButton : Clackable {
    final override fun click() { }
}
```

- 클래스의 기본적인 상속 가능 상태를 `final`로 정의하는 경우 `스마트 캐스트`를 사용할 수 있다. 스마트 캐스트는 타입 검사 뒤에 변경될 수 없는 변수에만 적용 가능하다. 이는 프로퍼티를 `final`로 만들어야 한다는 의미이다.

- 코틀린에서도 클래스를 `abstract`로 선언할 수 있다.
- `abstract`로 선언한 추상 클래스는 객체로 만들 수 없다.
- 추상 클래스에는 구현이 없는 추상 멤버가 있으며, 이는 항상 열려있다. 따라서 추상 멤버 앞에는 `open` 변경자를 명시할 필요가 없다.

```kotlin
/* 추상 클래스 정의하기 */
abstract class Animated {
    /* 메소드 구현이 없기 때문에 이는 추상 메소드이다. */
    abstract fun animae()
    
    /* 추상 클래스에 속한 메소드라고 해도 추상 메소드가 아닌 메소드는 기본적으로 final 이다.
     * open을 사용하면 오버라이드를 허용할 수 있다. */
    open fun stopAnimating() { }
    
    /* open이 없는 비추상 메소드이므로 final이다. */
    fun animateTwice() { }
}

```

- 인터페이스 멤버는 항상 열려 있으므로 final로 변경 불가능하며, open, abstract를 사용하지 않는다.
- 인터페이스 멤버에 본문이 없으면 자동으로 추상 멤버가 되며, `abstract` 키워드는 생략 가능하다. 
- 코틀린의 상속 제어 변경자는 아래 표와 같다.

| 변경자        | 이 변경자가 붙은 멤버는... | 설명  |
|:-------------|:-------------|:-----|
| final | 오버라이드 할 수 없음 | 클래스 멤버의 기본 변경자다.
| open | 오버라이드 할 수 없음 | 반드시 open을 명시해야 오버라이드 할 수 있다.
| abstract | 반드시 오버라이드 해야 함 | 추상 클래스의 멤버에만 이 변경자를 붙일 수 있다. 추상 멤버에는 구현이 있으면 안 된다.
| override | 상위 클래스나 상위 인스턴스의 멤버를 오버라이드하는 중 | 오버라이드하는 멤버는 기본적으로 열려있다. 하위 클래스의 오버라이드를 금지하려면 final을 명시해야 한다.


