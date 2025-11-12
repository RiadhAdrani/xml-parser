package node

enum class XMLNodeType {
    Prolog,
    DocType,
    Root,
    Processing,
    Element,
    Text,
    Comment,
    CData,
    EntityRef,
    Namespace
}