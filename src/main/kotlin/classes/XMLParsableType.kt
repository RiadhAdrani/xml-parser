package classes

enum class XMLParsableType {
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