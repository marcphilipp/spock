import org.spockframework.mock.CannotCreateMockException
import spock.lang.Requires
import spock.lang.Specification

class MockingIntegrationSpec extends Specification {

  static final String TEST_TYPE = System.getProperty("org.spockframework.mock.testType", "plain");

  def "can mock interface"() {
    given:
    def list = Mock(List)

    when:
    list.add(1)

    then:
    1 * list.add(1)
  }

  @Requires({ TEST_TYPE != "plain" })
  def "can mock class when cglib or byte-buddy are present"() {
    given:
    def list = Mock(ArrayList)

    when:
    list.add(1)

    then:
    1 * list.add(1)
  }

  @Requires({ TEST_TYPE == "plain" })
  def "cannot mock class without cglib and byte-buddy"() {
    when:
    Mock(ArrayList)

    then:
    thrown(CannotCreateMockException)
  }

}
