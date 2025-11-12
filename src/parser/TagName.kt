package parser

import node.XMLTag
import node.XMLTagType
import utils.XMLParserError
import utils.XMLParserLocation
import utils.isValidTagChar
import utils.isValidTagStartChar

fun parseTagName(xml: String, pLocation: XMLParserLocation): XMLTag {
    val location = pLocation.clone()
    var char: Char = location.getCurrentChar(xml)

    // check start of the tag
    if (!isValidTagStartChar(char)){
        throw XMLParserError(location, "Invalid start of the tag")
    }
    var tag: String = "" + char

    while(!location.isEndOfXml(xml) && isValidTagChar(char)) {
        char = location.next(xml)
        tag += char
    }

    return XMLTag(tag, XMLTagType.Standard)
}