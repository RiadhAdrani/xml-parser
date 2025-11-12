import node.XMLTag
import parser.parseTagName
import utils.XMLParserLocation

fun parseTagBlock(xml: String, parentLocation: XMLParserLocation) {
    val location = parentLocation.clone()

    location.skipSpacesAndNewLines(xml)

    var didOpen = false
    var tag : XMLTag? = null
    var char: Char

    while (!location.isEndOfXml(xml)) {
        char = location.getCurrentChar(xml)

        if (!didOpen) {
            if (char == '<'){
                didOpen = true
                location.next(xml)
                continue
            }

            throw Error("Tag block should start with ")
        }

        if (tag == null) {
            // we need to parse the whole tag here
            tag = parseTagName(xml, location)
            continue
        }

        location.skipSpacesAndNewLines(xml)

        // check if we reached the end of the tag

        // check for attributes
    }
}


fun main() {
    val xml = "hello"
    val location = XMLParserLocation(0,0,0)

    val tag = parseTagName(xml, location)

    print(tag)
}