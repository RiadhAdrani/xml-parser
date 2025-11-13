import node.XMLTagName
import parser.parseTagAttribute
import parser.parseTagBlock
import parser.parseTagName
import utils.XMLParserLocation

fun main() {
    val xml = "hello='one'"
    val location = XMLParserLocation(0,0,0)

    val tag = parseTagAttribute(xml, location)

    print(tag)
}