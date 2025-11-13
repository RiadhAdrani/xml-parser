package utils

class XMLParserLocation(var line: Int, var column: Int, var index: Int) {

    fun clone(): XMLParserLocation {
        return XMLParserLocation(line, column, index)
    }

    fun isEndOfXml(xml: String): Boolean {
        return (index + 1) >= xml.length
    }

    /**
     * Get the char at the current index
     */
    fun getCurrentChar(xml: String): Char {
        if (index >= xml.length) {
            throw XMLParserError(this, "Could not get current character")
        }

        return xml[index]
    }

    fun peek(xml: String): Char? {
        if (isEndOfXml(xml)) {
            return null
        }

        return xml[index + 1]
    }

    fun next(xml: String): Char {
        if (isEndOfXml(xml)) {
            throw XMLParserError(this, "End of xml content reached")
        }

        val current = getCurrentChar(xml)

        index++

        if (current == '\n') {
            column = 0
            line++
        } else {
            column++
        }

        return getCurrentChar(xml)
    }

    fun skipSpaces(xml: String): Int {
        if (isEndOfXml(xml)) {
            return index
        }

        var char = getCurrentChar(xml)

        while(char == ' ' || char == '\t') {
            char = next(xml)
        }

        return index
    }

    fun skipNewLines(xml: String): Int {
        if (isEndOfXml(xml)) {
            return index
        }

        var char = getCurrentChar(xml)

        while(char == '\n') {
            char = next(xml)
        }

        return index
    }

    fun skipSpacesAndNewLines(xml: String) {
        var prevIndex = index

        while (true) {
            skipSpaces(xml)
            skipNewLines(xml)

            if (prevIndex == index) {
                prevIndex = index

                break
            }
        }
    }
}