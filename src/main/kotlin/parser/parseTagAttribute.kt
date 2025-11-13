package parser

import node.XMLAttribute
import utils.XMLParserError
import utils.XMLParserLocation
import utils.isValidAttributeNameChar
import utils.isValidAttributeNameStartChar

fun parseTagAttribute(xml: String, inputLocation: XMLParserLocation): ParseResult<XMLAttribute> {
    var location = inputLocation.clone()
    var char = location.getCurrentChar(xml)
    var previousChar = char

    if (!isValidAttributeNameStartChar(char)) {
        throw XMLParserError(location, "Invalid attribute name starting with ($char)")
    }

    var name = "$char"
    var value = ""

    var nameDone = false
    var equalDone = false
    var valueQuote: Char? = null

    while(true) {
        previousChar = char
        char = location.next(xml)

        if (!nameDone) {
            if (isValidAttributeNameChar(char)) {
                name += char
                continue
            }

            // we already have one character from the start, so we can set the name as done
            nameDone = true
        }

        if (!equalDone) {
            // skip white spaces
            location.skipSpacesAndNewLines(xml)
            char = location.getCurrentChar(xml)

            if (char != '=') {
                throw XMLParserError(location, "Expected equal sign (=) after attribute name but got ($char)")
            }

            equalDone = true
            location.skipSpacesAndNewLines(xml)
            continue
        }

        if (valueQuote == null) {
            if (char != '\'' && char != '"') {
                throw XMLParserError(location, "Expected attribute value to be start with a quote (') or (\") but got ($char)")
            }

            valueQuote = char
            continue
        }

        if (char != valueQuote || previousChar == '\\') {
            value += char
            continue
        }

        break
    }

    val attributes = XMLAttribute(name, value)
    return ParseResult<XMLAttribute>(attributes, location)
}