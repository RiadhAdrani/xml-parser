package utils

fun isAlphaCharacter(char: Char): Boolean {
    return char.isLetter()
}

fun isNumericalCharacter(char: Char): Boolean {
    return char.isDigit()
}

/**
 * checks if a character is valid for the tag name
 *
 * use `isValidTagStartChar` to check for the first tag character
 */
fun isValidTagChar(char: Char): Boolean {
    return isValidTagStartChar(char) || isNumericalCharacter(char) || char == '-' || char == '.'
}

/**
 * checks if the given character is valid as the beginning of a tag
 */
fun isValidTagStartChar(char: Char): Boolean {
    return isAlphaCharacter(char) || char == '_' || char == ':'
}

fun isValidAttributeNameStartChar(char: Char): Boolean {
    return isAlphaCharacter(char) || char == '_' || char == ':'
}

fun isValidAttributeNameChar(char: Char): Boolean {
    return isValidAttributeNameStartChar(char) || isNumericalCharacter(char) || char == '-' ||  char == '.'
}

fun isValidTextNodeChar(char: Char): Boolean {
    return char != '<' && char != '&'
}