package study

import org.assertj.core.api.Assertions
import org.assertj.core.api.AssertionsForClassTypes.assertThat
import org.assertj.core.api.AssertionsForClassTypes.assertThatNoException
import org.junit.jupiter.api.Test
import studyclasses.OtherPerson
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.memberProperties

@Suppress("NonAsciiCharacters")
class ClassStudyTest {

    @Test
    fun `클래스는 기본적으로 public이다`() {
        val person = OtherPerson()

        Assertions.assertThat(person).isNotNull
    }

    @Test
    fun `생성자 매개변수가 멤버 변수가 되지는 않는다`() {
        class Person(name: String)

        val person = Person("안녕")
        val hasProperty = person::class.members.any { it.name == "name" }

        assertThat(hasProperty).isFalse()
    }

    @Test
    fun `생성자 매개변수에 val이나 var을 사용해 멤버 변수로 만든다`() {
        class Person(val name: String)

        val person = Person("안녕")
        val hasProperty = person::class.members.any { it.name == "name" }

        assertThat(hasProperty).isTrue()
    }

    @Test
    fun `멤버변수를 읽는다`() {
        class Person(val name: String)

        val person = Person("안녕")

        assertThat(person.name).isEqualTo("안녕")
    }

    @Test
    fun `val 멤버변수는 재할당 불가하다`() {
        class Person(val name: String)

        val person = Person("안녕")
        val property = person::class.memberProperties.first { it.name == "name" }

        assertThat(property).isNotInstanceOf(KMutableProperty::class.java)
    }

    @Test
    fun `var 멤버변수는 setter가 생성된다`() {
        class Person(var name: String)

        val person = Person("안녕")
        val property = person::class.memberProperties.first { it.name == "name" }

        assertThat(property).isInstanceOf(KMutableProperty::class.java)
        assertThatNoException().isThrownBy { person.name = "하세요" }
    }
}
