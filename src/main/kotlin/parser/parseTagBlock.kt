package parser

import node.XMLParsableType
import node.XMLTag
import node.XMLTagName
import utils.XMLParserError
import utils.XMLParserLocation

fun parseTagBlock(xml: String, inputLocation: XMLParserLocation): ParseResult<XMLTag> {
    var location = inputLocation.clone()

    var char = location.getCurrentChar(xml)

    // check for the opening
    if (char != '<') {
        throw XMLParserError(location,"Expected a tag block to start with (<) but got ($char)")
    }

    var isSelfClosing = false
    var didClose = false
    var tag : XMLTagName? = null

    location.next(xml)

    while (!location.isEndOfXml(xml)) {
        char = location.getCurrentChar(xml)

        if (tag == null) {
            // we need to parse the whole tag name here
            val result = parseTagName(xml, location)

            tag = result.value
            location = result.location

            continue
        }

        location.skipSpacesAndNewLines(xml)

        char = location.getCurrentChar(xml)

        // check if we reached the end of the tag
        if (char == '/') {
            // we expect the next character to self close the tag
            char = location.next(xml)
            if (char != '>') {
                throw XMLParserError(location, "Expected a self closing tag, but got ($char)")
            }

            isSelfClosing = true
            break
        }

        if (char == '>' && !didClose ) {
            didClose = true
            continue
        }

        // check for attributes
    }

    if (tag == null)  {
        throw XMLParserError(location, "Unexpected State: tag is null")
    }

    val block = XMLTag(XMLParsableType.Element, "", null, )
    val result = ParseResult<XMLTag>(block, location)

    return result
}