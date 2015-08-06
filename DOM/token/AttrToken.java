package DOM.token;

public class AttrToken extends DOMToken {

    private String attrName;
    private String attrValue;

    public AttrToken(String name, String value) {
        attrName = name;
        attrValue = value;
    }

    public String getAttrName() {
        return attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }
}
