package node

class XMLNode(
    val type: XMLNodeType,
    val xml: String,
    val attributes: MutableList<XMLAttribute>,
    val children: MutableList<XMLNode>,
    val parent : XMLNode? = null
) {}