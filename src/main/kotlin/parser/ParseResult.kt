package parser

import utils.XMLParserLocation

class ParseResult<T>(val value: T, val location: XMLParserLocation) {}