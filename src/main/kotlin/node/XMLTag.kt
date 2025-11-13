package node

class XMLTag(
    type: XMLParsableType,
    xml: String,
    parent: XMLEntity? = null,
    var children: MutableList<XMLEntity> = mutableListOf(),
    val attributes: MutableList<XMLAttribute> = mutableListOf()
): XMLEntity(type, xml, parent ) {}