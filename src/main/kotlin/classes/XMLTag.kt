package classes

class XMLTag(
    type: XMLParsableType,
    xml: String,
    val name: XMLTagName,
    val children: MutableList<XMLEntity> = mutableListOf(),
    val attributes: MutableList<XMLAttribute> = mutableListOf()
): XMLEntity(type, xml) {}