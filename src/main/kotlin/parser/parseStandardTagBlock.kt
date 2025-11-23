package parser

import classes.XMLAttribute
import classes.XMLEntity
import classes.XMLParsableType
import classes.XMLParseResult
import classes.XMLParserError
import classes.XMLParserLocation
import classes.XMLTag
import classes.XMLTagName
import classes.XMLTagType

fun parseStandardTagBlock(xml: String, inputLocation: XMLParserLocation, tagName: XMLTagName): XMLParseResult<XMLTag> {
    val location = inputLocation.clone()

    if (tagName.type != XMLTagType.Standard && tagName.type != XMLTagType.Namespace) {
        throw XMLParserError(location, "Expected Standard or Namespace tag type but got (${tagName.type})")
    }

    var isSelfClosing = false
    var openingTagClosed = false
    val attributes = mutableListOf<XMLAttribute>()
    val children= mutableListOf<XMLEntity>()

    var char: Char

    while (!location.isEndOfXml(xml)){
        if (!openingTagClosed) {
            // skip character between attributes
            location.skipSpacesAndNewLines(xml)
        }

        char = location.getCurrentChar(xml)

        // check if we reached the end of the tag
        if (char == '/') {
            // we expect the next character to self close the tag
            char = location.next(xml)
            if (char != '>') {
                throw XMLParserError(location, "Expected a self closing tag, but got ($char) after (/)")
            }

            openingTagClosed = true
            isSelfClosing = true

            location.next(xml)
            break
        }

        // opening tag closed
        if (char == '>' && !openingTagClosed ) {
            openingTagClosed = true
            location.next(xml)
            continue
        }

        // check for attributes
        if (!openingTagClosed) {
            val attrRes = parseTagAttribute(xml, location)

            location.applyFrom(attrRes.location)
            attributes.add(attrRes.value)

            // the previous function stops at the ending quote
            location.next(xml)

            continue
        }

        // we need to parse tag children
        if (char == '<') {
            val next = location.peek(xml)
            if (next == '/') {
                // closing tag
                location.next(xml,2)

                val closingTag = parseTagName(xml, location)

                if (closingTag.value.value != tagName.value || closingTag.value.type != tagName.type) {
                    throw XMLParserError(location, "Expected closing tag (${closingTag.value.value}) to match opening tag (${tagName.value})")
                }

                location.applyFrom(closingTag.location)

                // check closing chevron
                char = location.getCurrentChar(xml)
                if (char != '>') {
                    throw XMLParserError(location, "Expected closing tag to end with (>) but got ($char)")
                }

                break
            }

            // new tag
            val childResult = parseTagBlock(xml, location)

            children.add(childResult.value)
            location.applyFrom(childResult.location)

            continue
        }

        val textNode = parseTextNode(xml, location)

        location.applyFrom(textNode.location)
        children.add(textNode.value)
    }

    val type = if (tagName.type == XMLTagType.Namespace) XMLParsableType.Namespace else XMLParsableType.Element
    val tag = XMLTag(type,"",tagName,children,attributes)


    return XMLParseResult(tag, location)
}