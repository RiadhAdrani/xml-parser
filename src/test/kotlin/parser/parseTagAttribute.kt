import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parseTagAttribute
import utils.XMLParserError
import utils.XMLParserLocation
import kotlin.test.assertEquals


class MainTest {
    val location = XMLParserLocation(0,0,0)

    @Test
    fun testValueWithDoubleQuotes() {

        val xml = "id=\"123\""
        val result = parseTagAttribute(xml, location)

        assertEquals(result.value.value, "123")
        assertEquals(result.value.name, "id")

        assertEquals(result.location.index, 7)
        assertEquals(result.location.column, 7)
        assertEquals(result.location.line, 0)
    }

    @Test
    fun testValueSingleQuote() {

        val xml = "id='123'"
        val result = parseTagAttribute(xml, location)

        assertEquals(result.value.value, "123")
        assertEquals(result.value.name, "id")

        assertEquals(result.location.index, 7)
        assertEquals(result.location.column, 7)
        assertEquals(result.location.line, 0)
    }

    @Test
    fun testValueEmptyDoubleQuote() {

        val xml = "id=\"\""
        val result = parseTagAttribute(xml, location)

        assertEquals(result.value.value, "")
        assertEquals(result.value.name, "id")
    }

    @Test
    fun testValueEmptySingleQuote() {

        val xml = "id=''"
        val result = parseTagAttribute(xml, location)

        assertEquals(result.value.value, "")
        assertEquals(result.value.name, "id")
    }

    @Test
    fun testInvalidName() {

        val xml = "!id='123'"
        val exception = assertThrows<XMLParserError>{
            parseTagAttribute(xml, location)
        }

        assertEquals(exception.message, "Invalid attribute name starting with (!)")
    }

    @Test
    fun testMissingEqualSign() {
        val xml = "id '123"

        val exception = assertThrows<XMLParserError>{
            parseTagAttribute(xml, location)
        }

        assertEquals(exception.message, "Expected equal sign (=) after attribute name but got (')")
    }

    @Test
    fun testMissingStartingQuotes() {
        val xml = "id=123'"

        val exception = assertThrows<XMLParserError>{
            parseTagAttribute(xml, location)
        }

        assertEquals(exception.message, "Expected attribute value to be start with a quote (') or (\") but got (1)")
    }

    @Test
    fun testMissingEndingQuotes() {
        val xml = "id='123"

        val exception = assertThrows<XMLParserError>{
            parseTagAttribute(xml, location)
        }

        assertEquals(exception.message, "End of xml content reached")
    }


}