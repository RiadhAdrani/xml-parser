import parser.parseTagAttribute
import parser.parseTagBlock
import classes.XMLParserLocation

fun main() {
    val xml = "<tag name='value'><text/> node  x<close/></tag>"
    val location = XMLParserLocation(0,0,0)

    val tag = parseTagBlock(xml, location)

    print(tag)
}