package DOM;

import DOM.interfaces.DOM_unclosable;
import java.util.*;

public class DOM_img extends DOM_Object implements DOM_unclosable {

    public DOM_img(DOM_Object father) {
        super("img", father);
    }

    @Override
    protected String render(int tabs) {
        String res = "";
        String attr = "";

        for (Map.Entry value : attribute.entrySet()) {
            if (((String) value.getKey()).equals("src")) {
                attr = attr + value.getKey() + " = 'file:" + value.getValue() + "' ";
            } else {
                attr = attr + value.getKey() + " = '" + value.getValue() + "' ";
            }
        }
        res = renderTabs(tabs) + res + "<" + tagName + " " + attr + ">\n";

        return res;
    }
}
