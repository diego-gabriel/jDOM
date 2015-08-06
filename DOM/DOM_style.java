package DOM;

import java.util.ArrayList;

public class DOM_style extends DOM_Object {

    private ArrayList<StyleEntry> styleHash;

    public DOM_style(DOM_Object father) {
        super("style", father);
        styleHash = DOMParser.parseStyle(((DOM_ObjectContent) children.get(0)).getContent());
    }

    public ArrayList<StyleEntry> getStyleList() {
        return styleHash;
    }
}
