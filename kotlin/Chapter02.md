# Chapter 2. 코틀린 기초

> 스터디 날짜 : 2018-09-19(수)

## 2.1 기본 요소: 함수와 변수

### 2.1.1 Hello World!

```kotlin
fun main(args: Array<String>) {
    println("Hello, World!")
}
```

- 함수 선언 키워드는 `fun`.
- 파라미터 이름 뒤에 타입을 쓴다. (파스칼, 스위프트와 동일)
- 자바와 달리 함수를 클래스 안이 아닌, C++과 같이 최상위 수준에서 정의할 수 있다.
- 배열 처리를 위한 문법이 따로 존재하지 않음. (`String[] aa`에 사용되는 `[]`연산자가 없음)
- `System.out.println` 대신 `println`이라고 사용한다.
- 줄 끝에 세미콜론을 사용하지 않는다.

### 2.1.2 함수

<REPL 실행 예제>

```kotlin
$ kotlinc
Welcome to Kotlin version 1.1.4 (JRE 1.8.0)
type :help for help, :quit for quit

>>> fun max(a: Int, b: Int): Int {
...     return if (a > b) a else b
... }

>>> println(max(1, 2))
2
```

- 함수 선언은 `fun` 키워드로 시작.
- 함수의 리턴 타입은, 파라미터 변수와 마찬가지로, `:<타입>`으로 사용한다.
- 자바와 달리 코틀린에서 `if`는 문(statement)가 아니고 식(expression)이다. 따라서 정확하게 말하려면 우리가 흔히 말하는 `if문`이 아니라 `if식`이라고 해야 한다.
- 반면, 자바에서 `대입(assign, =)`은 식이었으나 코틀린에서는 문이 되었다. 따라서 아래와 같은 차이가 있다.

```java
/* 자바 */

/* line = bufReader.readLine() 으로 할당을 하면서
바로 != null로 비교 가능하다 */
while((line = bufReader.readLine()) != null) {
    ...
}
```

```kotlin
/* 코틀린 */

/* 대입(=)이 문이므로 아래 while 조건문 안의
(line = bufReader.readLine()) 자체가 컴파일 에러다 */
while((line = bufReader.readLine()) != null) { // <-- 이 문장은 오류다
    ...
}
```

#### 식이 본문인 함수

```kotlin
fun max(a: Int, b: Int): Int {
    return if (a > b) a else b
}

/* 위 함수를 간소화 하면 아래와 같다 */
fun main(a: Int, b: Int): Int = if (a > b) a else b

/* 위 간소화 된 함수에서 리턴 타입을 제거하고 한 번 더 간소화 할 수 있다 */
fun main(a: Int, b: Int) = if (a > b) a else b
```

- 함수의 본문을 중괄호로 감싸면 `블록이 본문인 함수`라고 부르고, 등호와 시그으로 이루어진 함수를 `식이 본문인 함수`라고 부른다.
- `식이 본문인 함수`는 `타입 추론`을 통해 리턴 타입을 컴파일러가 결정 가능하므로 리턴 타입 생략이 가능하다.

### 2.1.3 변수

아래 표현이 모두 가능하다.

```kotlin
val question = "PI"         /* 타입 생략 가능 */
val answer = 3.14           /* 타입 생략 가능 */
val answer: Int = 3         /* 타입 표기 */
val yearsToCompute = 7.5e6  /* 7.5 * 10^6 = 7500000.0 */

/* 초기화를 하지 않고 변수를 선언하는 경우 반드시 타입을 명시해야 한다. */
val answer: Int
answer = 42
```

#### 변경 가능한 변수와 변경 불가능한 변수

- `val(value에서 따옴)` - 변경 불가능(immutable) 참조를 저장하는 변수. 초기화 후에는 재대입이 불가능. 자바로 말하면 `final` 변수에 해당
- `var(variable에서 따옴)` - 변경 가능(mutable) 참조. 값은 바뀔 수 있다. 자바의 일반 변수에 해당
- 코틀린에서는 가능한 `val` 키워드를 사용해 불변 변수 사용을 권장. 나중에 꼭 필요한 경우에만 `var`로 변경하라
- `val` 변수는 정확히 한 번만 초기화 되어야 한다. 조건에 따라 다른 여러 값으로 초기화 가능하다. 아래 예제 참고.

```kotlin
val message: String
if (isSuccess()) {
    message = "Success"
}
else {
    message = "Failed"
}
```

- `val` 변수의 주의할 사항은 **참조 자체가 불변**이라는 점이다. **참조가 가르키는 객체 내부의 값은 변경 가능**하다. 아래 예 참고

```kotlin
val lang = arrayListOf("Java") /* 불변 참조를 선언 */
lang.add("Kotlin")             /* 참조가 아닌 객체 내부를 변경하므로 에러 아님 */
lang = arrayListOf("C")        /* 에러!! 참조를 변경 */
```

- `var` 변수는 값 변경은 가능하나, 초기화 시점의 변수 타입은 고정이다. 따라서 아래 예제는 오류를 발생한다.

```kotlin
var answer = 42
answer = "no answer"  /* 컴파일 에러!! Type mismatch */
```

### 2.1.4 더 쉽게 문자열 형식 지정: 문자열 템플릿

- 코틀린은 문자열 템플릿(String Template)을 지원한다. 아래 예제에서 println 안의 `$name`이 문자열 템플릿이다. 가능한 문자열 템플릿은 중괄호로 묶어 `${name}`처럼 사용하는 것이 좋다.

```kotlin
fun main(args: Array<String>) {
    val name = if(args.size > 0) args[0] else "Kotlin"
    println("Hello, $name")
    println("Hello, ${name}")  /* 중괄호 사용 */
}
```

## 2.2 클래스와 프로퍼티

- 자바빈 클래스인 Person을 자바, 코틀린 코드로 비교.

```java
/* 자바 */
public class Person {
    private final String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
```

```kotlin
/* 코틀린 */
class Person(val name: String)
```

### 2.2.1 프로퍼티

```kotlin
/* 클래스 안에서 변경 가능한 프로퍼티 선언 */
class Person (
    val nane: String,        /* 읽기전용 프로퍼티 */
    var isMarried: Boolean   /* 쓰기 가능한 프로퍼티 */
)

>>> val person("Bob", true)
>>> println(person.name)
Bob
>>> println(person.isMarried)
true
```

### 2.2.2 커스텀 접근자

```kotlin
class Rectangle(val height: Int, val width: Int) {
    val isSquare: Boolean
        get() { /* <-- 프로퍼티 게터 선언 */
            return height == width
        }
}

>>> val rect = Rectangle(41, 43)
>>> println(rect.isSquare)
false
```

## 2.3 선택 표현과 처리: enum과 when

### 2.3.1 enum 클래스 정의

```kotlin
/* 간단한 enum 선언 */
enum class Color {
    RED, ORANGE, YELLOW, GREEN, BLUE, INDIGO, VIOLET
}

/* 프로퍼티와 메소드가 있는 enum 선언 */
enum class Color(val r: Int, val g: Int, val b: Int) {
    RED(255, 0, 0),
    ORANGE(255, 165, 0),
    YELLOW(255, 255, 0),
    GREEN(0, 255, 0),
    BLUE(0, 0, 255),
    INDIGO(75, 0, 130),
    VIOLET(238, 130, 238);  /* 세미콜론 사용 필수 */

    fun rgb() = (r * 256 + g) * 256 + b
}
```

### 2.3.2 when으로 enum 클래스 다루기

- `when`은 `if`와 마찬가지로 `식`이다.
- 자바와 달리 `break`를 사용하지 않아도 된다.

```kotlin
/* 간단한 when 에제 */
fun getMnemonic(color: Color) =
    when(color) {
        Color.RED -> "Richard"
        Color.ORANGE -> "Of"
        Color.YELLOW -> "York"
        Color.GREEN -> "Gave"
        Color.BLUE -> "Battle"
        Color.INDIGO -> "In"
        Color.VIOLET -> "Vain"
    }

>>> println(getMnemonic(Color.BLUE))
Battle


/* 한 when 분기 안에 여러 값 사용 */
fun getWarmth(color: Color) =
    when(color) {
        Color.RED,
        Color.ORANGE,
        Color.YELLOW -> "warm"
        Color.GREEN -> "neutral"
        Color.BLUE,
        Color.INDIGI
        Color.VIOLET -> "cold"
    }

>>> println(getWarmth(Color.ORANGE))
warm
```

### 2.3.6 (스마트 캐스트) 리팩토링: if를 when으로 변경

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

## 2.4 대상을 이터레이션: while과 for 루프

### 2.4.1 while 루프

- 코틀린에는 `while`과 `do-while` 루프가 있고, 문법은 자바와 동일하다.

```kotlin
while (조건) {
    ...
}

do {
    ...
} while(조건)
```

### 2.4.2 수에 대한 이터레이션: 범위와 수열

- 코틀린에서는 `범위(range)`를 사용한다. 예) `val oneToTen = 1..10`

```kotlin
fun fizzByzz(i: Int) = when {
    i % 15 == 0 -> "FizzBuzz"
    i % 3 == 0 -> "Fizz"
    i % 5 == 0 -> "Buzz"
    else -> "$i "
}

>>> for (i in 1..100) {
...     print(fizzBuzz(i))
... }
1 2 Fizz 4 Buzz Fizz 7 ...


/* 증가 값을 갖고 범위 이터레이션하기 */
for (i in 100 downTo 1 step 2) {
    print(fizzBuzz(i))
}
```

### 2.4.3 맵에 대한 이터레이션

```kotlin
/* 맵 이터레이션 */
val binaryReps = TreeMap<Char, String>()

for (c in 'A'..'F') {
    val binary = Integer.toBinaryString(c.toInt())
    binaryReps[c] = binary
}

for ((letter, binary) in binaryReps) {
    println("$letter = $binary")
}

/* 출력 */
A = 1000001
B = 1000010
C = 1000011
...


/* 컬렉션 이터레이션 */
val list = arrayListOf("10", "11", "1001")
for ((index, element) in list.withIndex()) {
    println("$index: $element")
}

/* 출력 */
0: 10
1: 11
2: 1001
```

### 2.4.4 in으로 컬렉션 범위의 원소 검사

```kotlin
fun isLetter(c: Char) = c in 'a'..'z' || c in 'A'..'Z'
fun isNotDigit(c: Char) = c !in '0'..'9'

>>> println(isLetter('q'))
true
>>> println(isNotDigit('x'))
true
```

## 2.5 코틀린의 예외 처리

- 코트린의 `throw`는 `if`와 마찬가지로 `식`이다.
- 자바와 달리 `new` 키워드를 붙일 필요가 없다.
- 자바와 달리 함수가 던질 수 있는 예외를 명시할 필요가 없고, 처리하기 싫은 경우 생략 가능하다.

```kotlin
if (percentage !in 0..100) {
    throw IllegalArgumentException(
        "A percentage value must be be between 0 and 100: $percentage")
}
```

### 2.5.2 try를 식으로 사용

```kotlin
fun readNumber(reader: BufferedReader) {
    val num = try {
        Integer.parseInt(redaer.readLine())
    } catch (e: NumberFormatException) {
        reeturn
    }
    println(number)
}

>>> val reader = BufferedReader(StringReader("not a number"))
>>> readerNumber(reader)
            <-- 아무것도 출력되지 않음
```