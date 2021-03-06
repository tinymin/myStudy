# Chapter 7. 연산자 오버로딩과 기타 관례

> **스터디 날짜**
>> 2018-11-07(수)<br/>2018-11-14(수)

- 코틀린의 관례<sup>convention</sup>란 어떤 클래스 안에 plus라는 이름의 특별한 메소드를 정의하면 해당 인스턴스에 대해 + 연산자를 사용할 수 있는 기법을 말한다.
- Point 클래스를 통해 관례를 정의하여 산술 연산을 사용하는 예제를 살펴보자.

## 7.1 산술 연산자 오버로딩

### 7.1.1 이항 산술 연산 오버로딩

```kotlin
/* plus 메소드를 정의하여 + 연산자 구현하기 */
data class Point(val x: Int, val y: Int) {
    operator fun plus(other: Point) : Point {
        return Point(x + other.x, y + other.y)
    }
}

>>> val p1 = Point(10, 20)
>>> val p2 = Point(30, 40)
>>> println( p1 + p2)
Point(x=40, y=60)
```

- plus 함수 앞에 `operator` 키워드를 붙여야 관례를 따르는 함수임을 명확히 할 수 있다.
- plus 함수를 정의하고 operator 키워드를 쓰지 않는 경우 "operator modifier is required..." 오류가 발생한다.
- 연산자를 멤버 함수 대신 `확장 함수`로 정의하 수도 있다.

```kotlin
operator fun Point.plus(other: Point) : Point {
   return Point(x + other.x, y + other.y)
}
```

- 코틀린에서는 개발자가 직접 연산자를 만들어 사용할 수 없고, 미리 정해둔 연산자만 오버로딩할 수 있다. 아래 표는 연산자와 대응하는 함수 이름이다.
- 연산 우선순위는 수학과 동일하게 *, /, % 가 먼저이다.

| 연산 우선순위 | 식 | 함수 이름 |
|:---:|:---:|:---:|
| 1 | a * b | times |
| 1 | a / b | div |
| 1 | a % b | mod(1.1부터 rem) |
| 2 | a + b | plus |
| 2 | a - b | minus |

- 연산자를 정의할 때 두 피연산자가 같은 타입일 필요는 없다.

```kotlin
/* 다른 타입의 연산자 정의 */
operator fun Point.times(scale: Double) : Point {
    return Point((x * scale).toInt(), (y * scale).toInt())
}

>>> val p = Point(10, 20)
>>> println(p * 1.5)
Point(x=15, y=30)
```

- 관례를 통해 정의한 연산자는 교환 법칙을 지원하지 않는다. 이는 위 예제 기준으로 `p * 1.5`는 가능하지만 `1.5 * p`는 **불가능**하다는 의미다. `1.5 * p`가 가능하려면 1.5의 타입인 Double에 대해 Double.plus를 정의해야 한다.
- 연산자 함수의 반환 타입이 두 피연산자와 일치할 필요는 없다.

```kotlin
operator fun Char.times(count: Int) : String {
    return toString().repeat(count)
}

>>> println('a' * 3)
aaa
```

### 7.1.2 복합 대입 연산자 오버로딩

- plus와 같은 연산자를 오버로딩하면 + 연산자와 함께 `+= 연산자`도 자동으로 지원한다. +=와 같은 연산자를 복합 대입<sup>compound assignment</sup> 연잔라고 한다.

```kotlin
>>> var p = Point(1, 2)
>>> p += Point(3, 4)
>>> println(p)
Point(x=4, y=6)
```

- 컬렉션에 원소를 추가하는 경우에도 `+= 연산자`를 사용할 수 있다.

```kotlin
>>> val numbers = ArrayList<Int>()
>>> numbers += 42
>>> println(numbers[0])
42
```

- `plusAssign 함수`를 정의하면 `+= 연산자`를 사용할 수 있다. 다른 복합 대입 연산자도 minusAssign, timesAssign 등의 이름을 사용한다.

```kotlin
operator fun <T> MutableCollection<T>.plusAssign(element: T) {
    this.add(element)
}
```

- 이론적으로는 코드에 있는 +=를 plus와 plusAssign 양쪽으로 구현할 수 있으나, 어떤 클래스에 plus와 plusAssign을 동시에 정의한다면 컴파일러 오류가 발생한다. 따라서 두 함수는 동시에 정의하면 안 된다.
- 코틀린 표준 라이브러리는 컬렉션에 대해 2가지 접근 방법을 미리 구현해 두었다. +, -는 항상 `새로운 컬렉션을 반환`하며, +=, -= 연산자는 `변경 가능한 컬렉션`에서는 객체의 상태를 변환 시키고, `읽기 전용 컬렉션`에서는 +=, -=를 적용한 복사본을 반환한다.

### 7.1.3 단항 연산자 오버로딩

```kotlin
/* 단항 연산자 정의하기 */
operator fun Point.unaryMinus() : Point {
    return Point(-x, -y)
}

>>> val p = Point(10, 20)
>>> println(-p)
Point(x=-10, y=-20)
```

- 각 단항 산술 연산자의 함수 이름은 아래와 같다.

| 식 | 함수 이름 |
|:---:|:---:|
| +a | unaryPlus |
| -a | unaryMinus |
| !a | not |
| ++a, a++ | inc |
| --a, a-- | dec |

- BigDecial 클래스의 ++ 연산자 오버로딩 예제이다.

```kotlin
operator fun BigDecimal.inc() = this + BigDecimal.ONE

>>> var bd = BigDecimal.ZERO
>>> println(bd++)
0
>>> println(++bd)
2
```

## 7.2 비교 연산자 오버로딩

- 코틀린에서는 모든 객체에 대해 `== 연산자`를 직접 사용할 수 있다.

### 7.2.1 동등성 연산자: equals
                                         
- 코틀린은 == 연산자를 equals 메소드로 컴파일한다. != 연산자 역시 equals로 컴파일 된다.
- 클래스 정의에 `data`라는 표시가 붙어있으면 컴파일러가 자동으로 equals를 생성해 준다.
- 직접 equals를 구현한다면 다음과 같은 코드가 된다.

```kotlin
class Point(val x: Int, val y: Int) {
    override fun equals(obj: Any?) : Boolean {
        if (obj === this) return true
        if (obj !is Point) return false
        return obj.x == x && obj.y == y
    }
}

>>> println(Point(10, 20) == Point(10, 20))
true
>>> println(Point(10, 20) != Point(5, 5))
true
>>> println(null == Point(5, 5))
false
```

- `=== 연산자`는 두 피연산자가 서로 같은 객체를 가르키는지 비교한다. === 연산자는 오버로딩 불가능하다.
- equals는 Any에 정의된 메소드이므로 override가 필요하다.
- Any의 equals에는 operator가 붙어 있기 떄문에 하위 클래스의 오버라이드하는 메소드 앞에는 operator를 생략해도 된다.
- `Any에서 상속받은 equals가 확장 함수보다 우선순위가 높기 때문에` equals를 확장 함수로 정의 할 수 없다. 

### 7.2.2 순서 연산자: compareTo

- 코틀린에서도 자바와 동일한 Comparator 인터페이스를 지원한다. 비교연산자 >, <, >=, <= 는 compareTo 호출로 컴파일 된다. compareTo는 Int를 리턴한다.

```kotlin
/* compareTo 메소드 구현하기 */
class Person(val firstName: String, val lastName: String) : Comparable<Person> {
    override fun compareTo(other: Person) : Int {
        return compareValuesBy(this, other, Person::lastName, Person::firstName)
    }
}

>>> val p1 = Person("Alice", "Smith")
>>> val p2 = Person("Bob", "Johnson")
>>> println(p1 < p2)
false
```

## 7.3 컬렉션과 범위에 대해 쓸 수 있는 관례

### 7.3.1 인덱스로 원소에 접근: get과 set

```kotlin
mutableMap[key] = newValue
```

- 코틀린의 인덱스 연산자도 관례를 따른다. 인덱스 연산자는 get, set 메소드로 변환된다.
- Point 클래스에 get, set 메소드를 추가하는 예제를 보다. 예제에서 p[0]은 X 좌표, p[1]은 Y 좌표를 의미한다.

```kotlin
operator fun Point.get(index: Int): Int {
    return when(index) {
        0 -> x
        1 -> y
        else ->
            throw IndexOutOfBoundsException("Invalid coordinate ${index}")
    }
}

>>> val p = Point(10, 20)
>>> println(p[1])
20
```

- get 메소드를 만들고 `operator 변경자`를 붙이면 된다.
- 2차원 행렬이나 배열을 표현하는 클래스에 operator fun get(rowIndex: Int, colIndex: Int)를 정의하면 matrix[row, col]로 그 메소드를 호출 할 수 있다.
- 컬렉션 원소를 쓰고 싶을 때는 set 메소드를 정의하면 된다. 앞서 예제의 Point 클래스는 불변 클래스이므로, 다른 클래스를 만들어 예제를 사용한다.

```kotlin
data class MutablePoint(var x: Int, var y: Int)

operator fun MutablePoint.set(index: Int, value: Int) {
    when(index) {
        0 -> x = value
        1 -> y = value
        else ->
            throw INdexOutOfBoundsException("Invalid coordinate ${index}")
    }
}

>>> val p = MutablePoint(10, 20)
>>> p[1] = 42
>>> println(p)
MutablePoint(x=10, y=42)
```

### 7.3.2 in 관례

- in 연산자와 대응하는 함수는 contains 다.

```kotlin
data class Rectangle(val upperLeft: Point, val lowerRight: Point)

operator fun Rectangle.contains(p: Point) : Boolean {
    reutnr p.x in upperLeft.x until lowerRight.x &&
        p.y in upperLeft.y until lowerRight.y
}

>>> val rect = Rectangle(Point(10, 20), Point(50, 50))
>>> println(Point(20, 30) in rect)
true
>>> println(Point(5, 5) in rect)
false
```

### 7.3.3 rangeTo 관례

- 범위를 만드려면 `..` 구문을 사용하면 된다. 1..10은 1부터 10까지 모든 수가 들어있는 범위를 가리킨다.
- `..` 연산자는 rangeTo 함수를 간략하게 표현하는 방법이다.
- 코틀린 표준 라이브러리에는 모든 Comparable 객체에 대해 적용 가능한 rangeTo 함수가 들어있다.

```kotlin
>>> val now = LocalDate.now()
>>> val vacation = now..now.plusDays(10)
>>> println(now.plusWeeks(1) in vacation)
true
```

- `0..n.forEach {}`와 같은 식은 컴파일 불가능하다. 범위 연산자는 우선순위가 낮아서 범위의 메소드를 호출하려면 범위를 괄호로 둘러싸야 한다.

```kotlin
>>> val n = 9
>>> (0..n).forEach { print(it) }
0123456789
```

### 7.3.4 for 루프를 위한 iterator 관례

- 코틀린의 for 루프에 사용되는 in 의미는 for (x in list) { ... } 를 예로 들면, list.iterator()를 호출하여 이터레이터를 통해 hasNext와 next 호출을 반복하는 식으로 변환된다.
- `in` 또한 관례이므로 iterator 메소드를 확장 함수로 정의히여 시용할 수 있다.

```kotlin
operator fun CharSequence.iterator() : CharIterator

>>> for (c in "abc")
```

- 클래스 안에 직접 iterator 메소드를 구현할 수 있다. 다음 예제를 보자.

```kotlin
operator fun ClosedRange<LocalDate>.iterator() : Iterator<LocalDate> =
    object : Iterator<LocalDate> {
        var current = start
        override fun hasNext() =
            current <= endInclusive
        override fun next() = current.apply {
            current = plusDays(1)
        }
}

>>> val newYear = LocalDate.ofYearDay(2017, 1)
>>> val daysOff = newYear.minusDays(1)..newYear
>>> for (dayOff in dayOff) { println(dayOff) }
2016-12-31
2017-01-01
```

# 7.4 구조 분해 선언과 component 함수

- 구조 분해 선언<sup>destructuring declaration</sup>을 사용하면 복합적인 값을 분해해서 여러 변수를 한꺼번에 초기화 할 수 있다.

```kotlin
>>> val p = Point(10, 20)
>>> val (x, y) = p
>>> println(x)
10
>>> println(y)
20
```

- 구조 분해 선언의 각 변수를 초기화하기 위해 componentN 이라는 함수를 호출하며, 여기서 N은 구조 분해 선언에 있는 변수 위치에 따라 붙는 번호이다.
- data 클래스의 주 생성자에 들어있는 프로퍼티는 컴파일러가 자동으로 componentN 함수를 만들어 준다.

```kotlin
class Point(val x: Int, val y: Int) {
    operator fun component1() = x
    operator fun component2() = y
}
```
### 7.4.1 구조 분해 선언과 루프

- Map의 원소에 대한 구조 분해 예제는 다음과 같다.

```kotlin
/* 구조 분해 선언을 사용해 Map 이터레이션 하기 */
fun printEntrise(map: Map<String, String>) {
    for ((key, value) in map) {
        println("${key} -> ${value}")
    }
}

>>> val map = mapOf("Orcale" to "Java", "JetBrains" to "Kotlin")
>>> printEntries(map)
Oracle -> Java
JetBrains -> Kotlin
```

## 7.5 프로퍼티 접근자 로직 재활용: 위임 프로퍼티

- 코틀린의 관례에 중 **위임 프로퍼티**<sup>delegated property</sup>는 가장 독특하면서 강력한 기능을 제공한다.
- 프로퍼티 위임을 사용하면 자신의 값을 필드가 아니라 DB테이블이나 브라우저 세션, Map 등에 저장할 수 있다.
- 위임은 객체가 직접 작업을 수행하지 않고 다른 도우미 객체가 그 작업을 처리하게 맡기는 디자인 패턴을 말한다. 이 때 작업을 처리하는 도우미 객체를 위임 객체<sup>delegate</sup>라고 한다.

### 7.5.1 위임 프로퍼티 소개

- 위임 프로퍼티의 일반적인 문법은 다음과 같다.

```kotlin
class Foo {
    var p: Type by Delegate()
}
``` 

```kotlin
class Foo {
    private val delegate = Delegate()
    var p: Type
    set(value: Type) = delegate.setValue(..., value)
    get() = delegate.getValue(...)
}
```

- 프로퍼티 위임 관례를 따르는 Delegate 클래스는 getValue와 setValue 메소드를 제공해야 한다. 물론 변경 가능한 프로퍼티만 setValue를 사용한다.

```kotlin
class Delegate {
    operator fun getValue(...) { ... }
    operator fun setValue(..., value: Type) { ... }
}

class Foo {
    var p: Type by Delegate() /* <-- "by" 키워드는 프로퍼티와 위임 객체를 연결한다. */
}

>>> val foo = Foo()
>>> val oldValue = foo.p
>>> foo.p = newValue
```

### 7.5.2 위임 프로퍼티 사용: by lazy()를 사용한 프로퍼티 초기화 지연

- **지연 초기화**<sup>lazy initialization</sup>는 객체 일부분을 초기화하지 않고 있다가, 필요한 경우 초기화를 할 때 흔히 쓰이는 패턴이다.
- 예를 들어 person 클래스가 이메일 리스트를 제공하고, 이메일은 DB에 들어있으며 불러오려면 시간이 오래걸린다고 가정한다. 그래서 이메일 프로퍼티의 값을 최초로 사용할 때 단 한 번만 DB에서 가져오고 싶다. 이메일을 가져오는 loadEmails 함수가 있다고 하자.

```kotlin
class Email { ... }
fun loadEmails(person: Person) : List<Emial> {
    println("${person.name}의 이메일을 가져옴")
    return listOf( ... )
}
```

- 위 코드에 대해 지연 초기화를 구현한 클래스를 예제는 아래와 같다.

```kotlin
class Person(val name: String) {
    private var _emails: List<Email>? = null
    val emails: List<Email>
        get() {
            if(_emails == null) {
                _emails = loadEmails(this)
            }
            return _emails!!
        }
}

>>> val p = Person("Alice")
>>> p.emails
Load emails for Alice
>>> p.emails
```

- 위 코드에서는 뒷받침하는 프로퍼티<sup>backing property</sup>라는 기법을 사용했다. _emails라는 프로퍼티는 값을 저장하고, emails는 _emails 프로퍼티에 대한 읽기 연산을 제공한다.
- 위 코드는 Thread-safe 하지 않으므로 정상 동작을 보장할 수 없다.
- 위임 프로퍼티를 사용하면 위 코드를 좀 더 간단하게 작성할 수 있다. 이를 가능하게 하는 표준 라이브러리 함수가 lazy 이다.

```kotlin
class Person(val name: String) {
    val emails by lazy { loadEmails(this) }
}
```

### 7.5.3 위임 프로퍼티 구현

```kotlin
/* PropertyChangeSupport를 사용하기 위한 도우미 클래스 */
open class PropertyChangeAware {
    protected val changeSupport = PropertyChangeSupport(this)

    fun addPropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.addPropertyChangeListener(listener)
    }

    fun removePropertyChangeListener(listener: PropertyChangeListener) {
        changeSupport.removePropertyChangeListener(listener)
    }
}


/* ObservablueProperty를 프로퍼티 위임에 사용할 수 있게 작성 */
class ObservableProperty(
        var propValue: Int,
        val changeSupport: PropertyChangeSupport
) {
    operator fun getValue(p: Person_7_23, prop: KProperty<*>) : Int = propValue
    operator fun setValue(p: Person_7_23, prop: KProperty<*>, newValue: Int) {
        val oldValue = propValue
        propValue = newValue
        changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}

/* 위임 프로퍼티를 통해 프로퍼티 변경 통지 받기 */
class Person(val name: String, age: Int, salary: Int) : PropertyChangeAware() {
    var age: Int by ObservableProperty(age, changeSupport)
    var salary: Int by ObservableProperty(salary, changeSupport)
}
```

- `by` 키워드를 사용해 위임 객체를 지정하면 코틀린 컴파일러가 자동으로 위임처리를 해준다.
- by 오른쪽에 오는 객체를 **위임 객체**<sup>delegate</sup>라고 부른다.
- 코틀린 표준 라이브러리를 사용한다면 위 Person 클래스를 아래와 같이 변경할 수 있다.

```kotlin
class Person (val name: String, age: Int, salary: Int) : PropertyChangeAware() {
    private val observer = {
        prop: Kproperty<*>, oldValue: Int, newValue: Int ->
            changeSupport.firePropertyChange(prop.name, oldValue, newValue)
    }
}
```

### 7.5.5 프로퍼티 값을 맵에 저장

- **확장 가능한 객체**<sup>expando object</sup>는 위임 프로퍼티를 활용하여 자신의 프로퍼티를 동적으로 정의할 수 있는 객체를 만드는 것을 말한다.
- Map을 통해 처리하는 프로퍼티를 통해 필수 정보를 제공하는 예를 아래 코드에서 살펴보자.

```kotlin
/* 값을 Map에 저장하는 위임 프로퍼티 사용하기 */
class Person {
    private val _attributes = hashMapOf<String, String>()
    fun setAttribute(attrName: String, value: String) {
        _attributes[attrName] = value
    }
    val name: String by _attributes
}
```
