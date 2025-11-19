package parser

import classes.XMLParseResult
import classes.XMLTagName
import classes.XMLTagType
import classes.XMLParserError
import classes.XMLParserLocation
import utils.isValidTagChar
import utils.isValidTagStartChar

fun parseTagName(xml: String, inputLocation: XMLParserLocation): XMLParseResult<XMLTagName> {
    val location = inputLocation.clone()
    var char: Char = location.getCurrentChar(xml)

    var tag: String = "" + char

    if (char == '?') {
        // we should match a processing instruction
        while(!location.isEndOfXml(xml) && isValidTagChar(char)) {
            char = location.next(xml)

            if (isValidTagChar(char)){
                tag += char
            }
        }

        return XMLParseResult(XMLTagName(tag, XMLTagType.ProcessingInstruction), location)
    }

    if (char == '!') {
        // could be a comment or cdata section or a doctype
        char = location.next(xml)

        if (char == 'D'){
            // should match CDATA block
            for (c in "DOCTYPE") {
                if (c != char) {
                    throw XMLParserError(location, "Expected the rest of the DOCTYPE block but got something else")
                }

                char = location.next(xml)
            }

            return XMLParseResult(XMLTagName("DOCTYPE", XMLTagType.DocType), location)
        }

        if (char == '['){
            for (c in "[CDATA[") {
                if (c != char) {
                    throw XMLParserError(location, "Expected the rest of the CDATA block but got something else")
                }

                char = location.next(xml)
            }

            return XMLParseResult(XMLTagName("[CDATA[", XMLTagType.CDATA), location)
        }


        if (char == '-'){
            // this should be a comment tag
            char = location.next(xml)

            if (char != '-') {
                throw XMLParserError(location, "Expected the end of a comment tag but got ($char)")
            }

            val next = location.peek(xml)
            if (next == ' ' || next == '\t' || next == '\n') {
                return XMLParseResult(XMLTagName("!--", XMLTagType.Comment), location)
            }

            throw XMLParserError(location, "Expected a space-like character after the comment tag but go ($char)")
        }

        throw XMLParserError(location, "Invalid tag name")
    }

    // check start of the tag
    if (!isValidTagStartChar(char)){
        throw XMLParserError(location, "Invalid start of the tag with character ($char)")
    }

    while(!location.isEndOfXml(xml) && isValidTagChar(char)) {
        char = location.next(xml)

        if (isValidTagChar(char)) {
            tag += char
        }
    }

    if (tag.contains(':')) {
        return XMLParseResult(XMLTagName(tag, XMLTagType.Namespace), location)
    }

    return XMLParseResult(XMLTagName(tag, XMLTagType.Standard), location)
}