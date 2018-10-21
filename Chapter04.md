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
- super&lt;Clickable&gt;.showOff() 처럼 꺾쇠 괄호를 사용한다.

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
- 부모 클래스의 메소드를 자식 클래스에서 오버라이드 한 경우, 해당 메소드는 기본적으로 열려있다. (여기서 `열려있다`라는 말의 의미는 `상속과 오버라이드 가능`하다는 의미다)

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
- 자바와 마찬가지로 `abstract`로 선언한 추상 클래스는 객체로 만들 수 없다.
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

### 4.1.3 가시성 변경자: 기본적으로 공개

- 가시성 변경자<sup>visibility modifier</sup>는 **코드 기반에 있는 선언에 대한 클래스 외부 접근을 제어**한다.
- 코틀린 가시성 변경자에는 public, protected, private 변경자가 있다.
- 코틀린에서 가시성 변경자를 생략하는 경우 모두 public 이다. 자바에서는 package-private로 설정 된다.
- 코틀린에는 `package-private`가 없고, 패키지는 네임스페이스 관리를 위한 용도로만 사용 된다.
- 코틀린에서 패키지 전용 가시성에 대안으로 `internal` 이라는 새로운 변경자를 도입했다. `internal`은 모듈 내부에서만 볼 수 있다는 뜻이다.
- 코틀린은 최상위 선언에 대해 `private`를 허용한다. 최상위 선언에는 클래스, 함수, 프로퍼티 등이 포함된다.
- 코틀린의 가시성 변경자는 아래와 같이 정리할 수 있다.

| 변경자 | 클래스 멤버 | 최상위 선언 |
|---|---|---|
| public(기본 가시성) | 모든 곳에서 볼 수 있다. | 모든 곳에서 볼 수 있다.
| internal | 같은 모듈 안에서만 볼 수 있다. | 같은 모듈 안에서만 볼 수 있다.
| protected | 하위 클래스 안에서만 볼 수 있다. | (최상위 선언에 적용할 수 없음)
| private | 같은 클래스 안애서만 볼 수 있다. | 같은 파일 안에서만 볼 수 있다.

```kotlin
/* 가시성 규칙 위반 예제 */

internal open class TalkativeButton : Focusable {
    private fun yell() = println("Hey!")
    protected fun whisper() = println("Let's talk!")
}

fun TalkativeButton.giveSpeech() {  /* 에러 : TalkativeButton은 `internal`이므로 `public`인 함수에서 접근 할 수 없다. */
    yell()  /* <-- 에러 : yell()은 private 이다. */
    whisper()  /* <-- 에러 : whisper()는 protected 이다. */
}
```

- 자바에서는 같은 패키지 내에서 `protected` 멤버에 접근할 수 있지만, 코틀린은 같은 패지키 내에서라도 `internal`인 경우에 접근 불가능하다.
- 코틀린에서의 protected 멤버는 오직 어떤 클래스나, 그 클래스를 상속한 클래스 안에서만 보인다.
- 클래스를 확장한 함수는 private나 protected 멤버에 저근할 수 없다.
- 코틀린의 public, protected, private는 자바 안에서 동일하게 유지된다. private 클래스의 경우 자바에서 package-private로 변경된다. internal의 경우는 public으로 변경 된다. 

### 4.1.4 내부 클래스와 중첩된 클래스: 기본적으로 중첩 클래스

- 자바와 마찬가지로 코틀린도 클래스 안에 다른 클래스를 선언할 수 있다.
- 자바와 다르게 코틀린의 중첩 클래스<sup>nested class</sup>는 명시적 선언이 없는 한 상위 클래스 인스턴스에 접근 불가능하다.

```kotlin
/* 직렬화 가능한 View 선언 */

interface State: Serializable

interface View {
    fun getCurrentState(): State
    fun restoreState(state: State) {}
}
```

```java
/* 자바 */
/* 자바에서 내부 클래스를 사용해 VIew 구현하기 */
public class Button implements View {
    @override
    public State getCurrentState() {
        return new ButtonState();
    }
    
    @override
    public void restoreState(State state) { /* ... */ }
    public class ButtonState implements State { /* ... */ }
}
```

- 위 자바 코드에서 ButtonState 클래스를 직렬화하면 java.io.NotSerializableException: Button 이라는 오류가 발생한다.
- ButtonState 클래스의 인터페이스인 State 가 Serializable 을 상속받고 있더라도 ButtonState 클래스는 중첩 클래스이므로 바깥쪽은 Button 클래스를 묵시적으로 포함한다. 따라서 Button은 Non-Serializable 이므로 Exception이 발생한다. 이를 해결하려면 ButtonState를 static으로 선언해야 한다. 자바에서는 중첩 클래스를 static으로 선언하면 바깥 클래스에 대한 묵시적 참조가 사라진다.
- 코틀린에서 중첩 클래스의 기본 동작 방식은 바로 위 자바와는 정반대로 동작한다.

```kotlin
/* 코틀린에서 중첩 클래스를 사용해 View 구현하기 */
class Button: View {
    override fun getCurrentState(): State = ButtonState()
    override fun restoreState(state: State) { /* ... */ }
    class ButtonState: State { /* ... */ }  /* <-- 이 클래스는 자바의 static nested class와 대응한다. */
}
```  

- 코틀린에서 중첩 클래스에 아무런 변경자<sup>modifier</sup>가 붙지 않으면 자바 static 중첩 클래스와 같다.
- 자바와 같이 내부 클래스에서 바깥쪽 클래스의 참조하게 하려면 `inner` 변경자를 붙여야 한다.
- 자바와 코틀린의 중첩 클래스<sup>nested class</sup>와 내부 클래스<sup>inner class</sup>의 관계는 아래 표와 같다.

| 클래스 OuterClass 안에 정의된 클래스 A | 자바 | 코틀린 |
|---|---|---|
| 중첩 클래스<sup>nested class</sup> (바깥쪽 클래스에 대한 참조를 저장하지 않음) | static class A | class A
| 내부 클래스<sup>inner class</sup> (바깥쪽 클래스에 대한 참조를 저장함) | class A | inner class A

- 코트린에서 바깥쪽 클래스의 인스턴스를 가리키려면 `this@Outer`라고 써야한다. (자바와는 다름)

```kotlin
class Outer {
    inner class Inner {
        fun getOuterReference(): Outer = this@Outer
    }
}
```

### 4.1.5 봉인된 클래스: 클래스 계층 정의 시 계층 확장 제한

- 2.3.6절에서 살펴본 코드를 다시 보자.

```kotlin
interface Expr
class Num(val value: Int) : Expr
class Sum(val left:Expr, val right:Expr) : Expr

fun eval(e : Expr): Int =
    when(e) {
        is Num -> e.value /* is에 의해  e가 자동으로 Num으로 캐스팅 된다 */
        is Sum -> eval(e.right) + eval(e.left)
        else -> throw IllegalArgumentException("Unknown expression")
    }
```

- 위 코드는 `else(디폴트 분기)` 절을 사용하도록 컴파일러가 강제한다.
- 위 코드에서 새로운 타입 검사에 대한 처리를 잊어버린다면 디폴트 분기가 선택되므로 심각한 버그가 발생할 수 있다.
- 이를 방지하기 위해 코틀린튼 `sealed` 클래스를 제공한다. 상위 클래스에 `sealed` 변경자를 붙이면 상위 클래스를 상속한 하위 클래스 정의를 제한할 수 있다. `sealed` 클래스의 하위 클래스를 정의할 때는 반드시 상위 클래스 안에 중첩시켜야 한다.

```kotlin
/* sealed 클래스로 식 표현하기 */
sealed class Expr {
    /* 기반 클래스의 모든 하위 클래스를 중첩 클래스로 나열한다. */
    class Num(val value: Int): Expr()
    class Sum(val left: Expr, val right: Expr): Expr()
}

fun eval(e: Expr): Int =
    when(e) {  /* <-- "when" 식이 sealed 클래스의 모든 하위 클래스를 검사하므로 else가 필요 없다 */
        is Expr.Num -> e.value
        is Expr.Sum -> eval(e.right) + eval(e.left)
    }
```
 
 - sealed로 표시된 클래스는 자동으로 open 이다.
 - sealed 클래스에 속한 값에 대해 디폴트 분기(else 처리)를 하지 않고 when 식을 사용하면, 나중에 sealed 클래스에 새로운 하위 클래스를 추가하여도 when 식이 컴파일 되지 않는다.
 - 내부적으로 Expr 클래스는 private 생성자를 가진다. 이 생성자는 내부에서만 호출 가능하다.
 - `sealed` 변경자는 인터페이스에는 붙일 수 없다.
 
 ## 4.2 뻔하지 않은 생성자와 프로퍼티를 갖는 클래스 선언
 
 - 코틀린의 생성자는 주<sup>primary</sup>생성자와 부<sup>secondary</sup>생성자가 있다.
 - 초기화 블록<sup>initialize block</sup>을 통해 초기화 로직을 추가할 수 있다.
 
 ### 4.2.1 클래스 초기화: 주 생성자와 초기화 블록
 
 ```kotlin
class User(val nickname: String)
```

- 위 코드에서 클래스 이름 뒤의 괄호로 둘러싸인 코드를 **주 생성자**<sup>primary constructor</sup>라고 부른다.
- 위 코드를 명시적으로 풀어 쓰면 아래와 같다.

```kotlin
class User constructor(_nickname: String) {  /* 파라미터가 하나만 있는 주 생성자 */
    val nickname: String
    init {  /* <-- 초기화 블록 */
        nickname = _nickname
    }
}
```

- `constructor` 키워드는 주 생성자나 부 생성자를 정의할 때 사용한다.
- `init` 키워드는 초기화 블록을 시작한다. 초기화 블록은 객체가 생성되는 시점에 실행되는 코드가 들어간다.


- 위 예제에서는 nickname 프로퍼티 초기화 코드를 nickname 프로퍼티 선언에 포함시킬 수 있으므로 초기화 코드를 생략할 수 있다.
- 주 생성자 앞에 별다른 Annotation이나 가시성 변경자가 없다면 `constructor` 키워드를 생략할 수 있다.
- 위 2가지를 적용하면 아래와 같이 코드를 변경할 수 있다. 

```kotlin
class User(_nickname: String) {  /* <-- 파라미터가 하나뿐인 주 생성자 */
    val nickname = _nickname  /* <-- 프로퍼티를 주 생성자의 파라미터로 초기화 한다. */
}
```

- 아래와 같이 초기화도 가능하다.

```kotlin
class User(val nickname: String)
```

- 위 3개 방식을로 선언한 User 클래스는 모두 동일하다.
- 아래와 같이 생성자 파라미터에도 디폴트 값을 정의할 수 있다.

```kotlin
class User(val nickname: String, val isSubscribed: Boolean = true)

>>> val hyun = User("현석")
>>> println(hyun.isSubscribed)
true

>>> val gye = User("계영", false)
>>> println(gye.isSubscribed)
false

>>> val hey = User("혜원", isSubscribed = false)
>>> println(hey.isSubscribed)
false
```

- 슈퍼 클래스를 초기화 하려면 슈퍼 클래스 이름 뒤에 괄호를 치고 생성자 인자를 넘긴다.

```kotlin
open class User(val nickname: String) { ... }
class Twitteruser(nickname: String) : User(nickname) { ... }
```

- 클래스 생성시 별도로 생성자를 정의하지 않으면 아무 것도 하지 않는 인자가 없는 디폴트 생성자를 만든다.
- Button의 생성자는 아무 인자도 받지 않지만, Button 클래스를 상속한 하위 클래스는 반드시 Button 클래스의 생성자를 호출해야 한다.

```kotlin
open class Button  /* <-- 인자가 없는 디폴트 생성자가 만들어진다. */
class RadioButton: Button()
```

- 위 규식으로 인해 슈퍼 클래스의 이름 뒤에는 꼭 빈 괄호가 들어간다. 생성자 인자가 있는 경우 괄호 안에 인자가 들어간다.
- 인터페이스는 생성자가 없기 때문에 인터페이스를 구현하는 경우 인터페이스 이름 뒤에는 아무 괄호도 없다.
- 이 괄호 여부를 보면 부모가 클래스인지 인터페이스인지 구분 할 수 있다.
- 어떤 클래스를 클래스 외부에서 인스턴스화하지 못 하게 막고 싶다면 모든 생성자를 private로 만들면 된다.

```kotlin
/* 인스턴스화 불가능하게 만들기 위해 주 생성자에 private를 붙인다. */
class Secretive private constructor() {}  /* <-- 이 클래스의 주 생성자는 비공개이다. */
```

### 4.2.2 부 생성자: 상위 클래스를 다른 방식으로 초기화

```kotlin
open class View {
    constructor(ctx: Context) {  /* 부 생성자 */
        // 코드
    }
    
    constructor(ctx: Context, attr: AttributeSet) {  /* 부 생성자 */
        // 코드
    }
}
```

- 부 생성자는 `constructor` 키워드로 시작한다.
- 이 클래스를 확장하면서 똑같이 부 생성자를 정의할 수 있다.

```kotlin
class MyButton: View {
    constructor(ctx: Context): super(ctx) {
        // ...
    }
    
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr) {
        // ...
    }
}
```

- 자바와 마찬가지로 this()를 통해 자신의 다른 생성자를 호출할 수 있다.

```kotlin
class MyButton: View {
    constructor(ctx: Context): this(ctx, MY_STYLE) {  /* <-- 다른 생성자에게 위임 */
        // ...
    }
    
    constructor(ctx: Context, attr: AttributeSet): super(ctx, attr) {
        // ...
    }
}
```

- 코틀린에서 부 생성자가 필요한 **주 된 이유**는 자바 상호운용성 때문이다.
- 또다른 부 생성자의 존재 이유는 4.4.2절에서 설명한다.

### 4.2.3 인터페이스에 선언된 프로퍼티 구현

- 코틀린 인터페이스에는 추상 프로퍼티를 생성할 수 있다.

```kotlin
interface User {
    val nickname: String
}
```

- 위 코드는 User 인터페이스를 구현하는 클래스가 nickname의 값을 얻을 수 있는 방법을 구현해야 한다는 의미다. 이 인터페이스를 구현하는 방법은 아래 예제와 같다.

```kotlin
/* 인터페이스의 프로퍼티 구현하기 */
interface PrivateUser(override val nickname: String) : User  /* <-- 주 생성자에 있는 프로퍼티 */

class SubscribingUser(val email: String) : User {
    override val nickname: String
        get() = email.substringBefore('@')  /* <-- 커스텀 Getter */
}

class FacebookUser(val accountId: Int) : User {
    override val nickname = getFacebookName(accountId)  /* <-- 프로퍼티 초기화 식 */
}

>>> println(PrivateUser("test@kotlinlang.org").nickname)
test@kotlinlang.org

>>> println(SubscribingUser("test@kotlinglang.org").nickname)
test
```