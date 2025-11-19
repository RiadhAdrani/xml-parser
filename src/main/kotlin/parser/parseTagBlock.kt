package parser

import classes.XMLParseResult
import classes.XMLTag
import classes.XMLTagName
import classes.XMLParserError
import classes.XMLParserLocation
import classes.XMLTagType

fun parseTagBlock(xml: String, inputLocation: XMLParserLocation): XMLParseResult<XMLTag> {
    val location = inputLocation.clone()
    val char = location.getCurrentChar(xml)

    // check for the opening
    if (char != '<') {
        throw XMLParserError(location,"Expected a tag block to start with (<) but got ($char)")
    }

    var tag : XMLTagName? = null

    location.next(xml)

    var result : XMLParseResult<XMLTag>? = null

    while (!location.isEndOfXml(xml)) {
        if (tag == null) {
            // we need to parse the whole tag name here
            val result = parseTagName(xml, location)

            tag = result.value
            location.applyFrom(result.location)

            continue
        }

        // according to the type of the tag we parse what's next

        if (tag.type == XMLTagType.Standard || tag.type == XMLTagType.Namespace) {
            result = parseStandardTagBlock(xml, location, tag)

            break
        }

        throw XMLParserError(location, "Parsing tag type (${tag.type}) is not implemented")
    }

    if (result == null) {
        throw XMLParserError(location, "Unexpected state: tag block was not parsed")
    }

    return result
}