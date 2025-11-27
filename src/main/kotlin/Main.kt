import parser.parseTagAttribute
import parser.parseTagBlock
import classes.XMLParserLocation

fun main() {
    val xml = "<!-- comment goes <!-- here -->"
    val location = XMLParserLocation(0,0,0)

    val tag = parseTagBlock(xml, location)

    print(tag)
}