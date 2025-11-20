package parser

import classes.XMLParseResult
import classes.XMLTextNode
import classes.XMLParserError
import classes.XMLParserLocation

fun parseTextNode(xml: String, inputLocation: XMLParserLocation): XMLParseResult<XMLTextNode> {
    val location = inputLocation.clone()
    var char = location.getCurrentChar(xml)

    val text: StringBuilder = StringBuilder("")

    while (char != '<') {
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

    return XMLParseResult(node, location)
}