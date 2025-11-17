package parser

import node.XMLTextNode
import utils.XMLParserError
import utils.XMLParserLocation

fun parseTextNode(xml: String, inputLocation: XMLParserLocation): ParseResult<XMLTextNode> {
    val location = inputLocation.clone()
    var char = location.getCurrentChar(xml)

    val text: StringBuilder = StringBuilder("")

    while (char != '<' || !location.isEndOfXml(xml)) {
        text.append(char)

        if (!location.isEndOfXml(xml)) {
            char = location.next(xml)
            continue
        }

        break
    }

    var value = text.toString().trim()
    if (value.isEmpty()) {
        throw XMLParserError(location, "Empty text node is not allowed")
    }

    value = value
        .replace("&lt;","<")
        .replace("&gt;",">")
        .replace("&amp;","&")
        .replace("&apos;","'")
        .replace("&quot;","\"")

    val node = XMLTextNode(value)

    return ParseResult(node, location)
}