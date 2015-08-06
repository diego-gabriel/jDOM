package DOM.token;

import java.util.ArrayList;
import DOM.StyleEntry;
import DOM.DOM_style;

public class StyleToken extends DOMToken {

    private ArrayList<StyleEntry> style;
    private DOM_style styleObject;

    public StyleToken(DOM_style obj) {
        styleObject = obj;
        style = obj.getStyleList();
    }

    public DOM_style getStyleObject() {
        return styleObject;
    }

    public ArrayList<StyleEntry> getStyles() {
        return style;
    }
}
