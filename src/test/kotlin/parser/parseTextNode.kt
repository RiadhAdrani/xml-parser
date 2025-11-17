import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parser.parseTagAttribute
import parser.parseTextNode
import utils.XMLParserError
import utils.XMLParserLocation
import kotlin.test.assertEquals


class ParseTextNodeTests {
    val location = XMLParserLocation(0,0,0)

    @Test
    fun shouldParseSimpleText() {
        val xml = "text"
        val result = parseTextNode(xml, location)

        assertEquals(result.value.value, "text")

        assertEquals(result.location.index, 3)
        assertEquals(result.location.column, 3)
        assertEquals(result.location.line, 0)
    }

    @Test
    fun shouldTrimSpaces() {
        val xml = " text "
        val result = parseTextNode(xml, location)

        assertEquals(result.value.value, "text")
    }

    @Test
    fun shouldStopWhenFacingLessThanCharacter() {
        val xml = "text<"
        val result = parseTextNode(xml, location)

        assertEquals(result.value.value, "text")
    }

    @Test
    fun shouldThrowWhenEmpty() {
        val exception = assertThrows<XMLParserError>{
            parseTextNode(" ", location)
        }

        assertEquals(exception.message, "Empty text node is not allowed")
    }

    @Test
    fun shouldDecodeSpecialCharacters() {
        val xml = "&lt;&gt;&amp;&apos;&quot;"
        val result = parseTextNode(xml, location)

        assertEquals(result.value.value, "<>&'\"")
    }
}