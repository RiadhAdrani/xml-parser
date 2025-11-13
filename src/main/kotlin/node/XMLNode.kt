package node

class XMLNode(
    val type: XMLParsableType,
    val xml: String,
    val attributes: MutableList<XMLAttribute>,
    val children: MutableList<XMLNode>,
    val parent : XMLNode? = null
) {
}