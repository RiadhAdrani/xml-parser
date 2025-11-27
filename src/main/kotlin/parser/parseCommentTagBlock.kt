package parser

import classes.XMLParsableType
import classes.XMLParseResult
import classes.XMLParserError
import classes.XMLParserLocation
import classes.XMLTag
import classes.XMLTagName
import classes.XMLTagType
import classes.XMLTextNode

fun parseCommentTagBlock(xml: String, inputLocation: XMLParserLocation, tagName: XMLTagName): XMLParseResult<XMLTag> {
    val location = inputLocation.clone()

    if (tagName.type != XMLTagType.Comment) {
        throw XMLParserError(location, "Expected Comment tag type but got (${tagName.type})")
    }

    val text: StringBuilder = StringBuilder("")

    // we are stepping because the tag name parser stops at the last character of the name
    var char = location.next(xml)
    var closed = false

    while(true) {
        text.append(char)

        // check if the last 3 characters are (-->)
        if (text.length >= 3 && text.substring(text.length - 3) == "-->") {
            closed = true

            // remove the ending tag
            text.delete(text.length - 3, text.length)
            break
        }

        if (!location.isEndOfXml(xml)) {
            char = location.next(xml)
            continue
        }

        break
    }

    if (!closed) {
        throw XMLParserError(location, "Comment tag was not closed as we reached end of xml content")
    }

    val child = XMLTextNode(text.toString().trim())
    val node = XMLTag(XMLParsableType.Comment,"",tagName, mutableListOf(child))

    return XMLParseResult(node, location)
}