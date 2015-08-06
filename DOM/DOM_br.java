/*
 * SRI-Bot
 * Information Retrieval - UMSS 1-2015
 */
package DOM;

import DOM.interfaces.DOM_unclosable;

/**
 *
 * @author dibriel
 */
public class DOM_br extends DOM_Object implements DOM_unclosable{
    public DOM_br(DOM_Object father) {
        super("br", father);
    }
}
