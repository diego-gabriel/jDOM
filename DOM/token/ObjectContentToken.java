package DOM.token;

import DOM.DOM_ObjectContent;

public class ObjectContentToken extends DOMToken {

    private final DOM_ObjectContent content;

    public ObjectContentToken(DOM_ObjectContent con) {
        content = con;
    }

    public DOM_ObjectContent getObjectContent() {
        return content;
    }

}
