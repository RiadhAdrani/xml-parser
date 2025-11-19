package classes

import classes.XMLParserLocation

data class XMLParseResult<T>(val value: T, val location: XMLParserLocation) {}