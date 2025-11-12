package parser

import node.XMLTag
import node.XMLTagType
import utils.XMLParserError
import utils.XMLParserLocation
import utils.isValidTagChar
import utils.isValidTagStartChar

fun parseTagName(xml: String, inputLocation: XMLParserLocation): ParseResult<XMLTag> {
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

        return ParseResult(XMLTag(tag, XMLTagType.ProcessingInstruction), location)
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

            return ParseResult( XMLTag("DOCTYPE", XMLTagType.DocType), location)
        }

        if (char == '['){
            for (c in "[CDATA[") {
                if (c != char) {
                    throw XMLParserError(location, "Expected the rest of the CDATA block but got something else")
                }

                char = location.next(xml)
            }

            return ParseResult( XMLTag("[CDATA[", XMLTagType.CDATA), location)
        }


        if (char == '-'){
            // this should be a comment tag
            char = location.next(xml)

            if (char != '-') {
                throw XMLParserError(location, "Expected the end of a comment tag but got ($char)")
            }

            val next = location.peek(xml)
            if (next == ' ' || next == '\t' || next == '\n') {
                return ParseResult(XMLTag("!--", XMLTagType.Comment), location)
            }

            throw XMLParserError(location, "Expected a space-like character after the comment tag but go ($char)")
        }

        throw XMLParserError(location, "Invalid tag name")
    }

    // check start of the tag
    if (!isValidTagStartChar(char)){
        throw XMLParserError(location, "Invalid start of the tag")
    }

    while(!location.isEndOfXml(xml) && isValidTagChar(char)) {
        char = location.next(xml)

        if (isValidTagChar(char)) {
            tag += char
        }
    }

    if (tag.contains(':')) {
        return ParseResult(XMLTag(tag, XMLTagType.Namespace), location)
    }

    return ParseResult(XMLTag(tag, XMLTagType.Standard), location)
}