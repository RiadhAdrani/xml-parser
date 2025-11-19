package classes

class XMLTag(
    type: XMLParsableType,
    xml: String,
    parent: XMLEntity? = null,
    val name: XMLTagName,
    val children: MutableList<XMLEntity> = mutableListOf(),
    val attributes: MutableList<XMLAttribute> = mutableListOf()
): XMLEntity(type, xml, parent) {}