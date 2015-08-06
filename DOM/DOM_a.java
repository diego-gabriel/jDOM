package DOM;

import java.util.*;

public class DOM_a extends DOM_Object {

    private HashMap<String, String> hover, active;

    public DOM_a(DOM_Object father) {
        super("a", father);
        hover = new HashMap<String, String>();
        active = new HashMap<String, String>();
    }

    protected void propagateStyles() {
        if (styleBuffer != null) {
            for (StyleEntry style : DOM_Object.styleBuffer) {
                if (style.getTarget().equals(tagName)) {
                    if (style.getSpec() != null) {
                        if (style.getSpec().equals("hover")) {
                            hover.put(style.getAttrName(), style.getAttrValue());
                        }
                        if (style.getSpec().equals("active")) {
                            active.put(style.getAttrName(), style.getAttrValue());
                        }
                    } else {
                        styles.put(style.getAttrName(), style.getAttrValue());
                    }
                }
            }
        }

        for (DOM_Object obj : children) {
            if (!(obj instanceof DOM_ObjectContent)) {
                obj.propagateStyles();
            }
        }
    }
}
