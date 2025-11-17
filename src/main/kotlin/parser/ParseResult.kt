package parser

import utils.XMLParserLocation

data class ParseResult<T>(val value: T, val location: XMLParserLocation) {}