package node

abstract class XMLEntity(
    val type: XMLParsableType,
    val xml: String,
    val parent : XMLEntity? = null
) {}