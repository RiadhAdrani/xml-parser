package classes

class XMLParserError(val location: XMLParserLocation, message: String): Exception(message) {}