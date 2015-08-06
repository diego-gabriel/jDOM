package DOM.token;

import DOM.DOM_Object;
import DOM.DOM_ObjectContent;

public class HTMLToken extends DOMToken {

    private final DOM_Object htmlObject;
    private final DOM_ObjectContent content;

    public HTMLToken(DOM_ObjectContent con, DOM_Object obj) {
        htmlObject = obj;
        content = con;
    }

    public DOM_Object getDOM_Object() {
        return htmlObject;
    }

    public DOM_Object getDOM_ObjectContent() {
        return content;
    }
}
