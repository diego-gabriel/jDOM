package DOM;

import DOM.interfaces.DOM_unclosable;
import DOM.token.DOMToken;
import DOM.token.HTMLToken;
import DOM.token.AttrToken;
import DOM.token.ObjectContentToken;
import DOM.token.StyleToken;
import java.util.*;

public class DOM_Object {

    protected static ArrayList<StyleEntry> styleBuffer;

    protected ArrayList<DOM_Object> children;
    protected HashMap<String, String> styles;
    protected HashMap<String, String> attribute;
    protected String tagName;
    protected DOM_Object parent;
    protected boolean closed;

    public DOM_Object(String htmlSource) {
        DOMParser.prepareToParseHtmlSource(htmlSource, loadTags());
        initDOM_Object(null, null);
        build(false);
        propagateStyles();
    }

    public DOM_Object(String tag, DOM_Object father) {
        initDOM_Object(tag, father);
        build(true);
    }

    public DOM_Object(String tag, DOM_Object father, boolean a) {
        initDOM_Object(tag, father);
    }

    private void initDOM_Object(String tag, DOM_Object father) {
        children = new ArrayList<DOM_Object>();
        styles = new HashMap<String, String>();
        attribute = new HashMap<String, String>();
        parent = father;
        tagName = tag;
        closed = false;
    }

    private void build(boolean attr) {
        DOMToken token;

        while (attr && (token = DOMParser.nextAttrToken()) != null) {
            addAttribute((AttrToken) token);
        }

        if (!(this instanceof DOM_unclosable)) {
            close_tag();
        }
    }

    private void close_tag() {
        DOMToken token;
        while ((token = DOMParser.nextToken(this)) != null) {
            if (token instanceof StyleToken) {
                addStyle((StyleToken) token); //procesar todo el tag <style>
                children.add(((StyleToken) token).getStyleObject());
            } else if (token instanceof AttrToken) {
                addAttribute((AttrToken) token);
            } else if (token instanceof HTMLToken) {
                HTMLToken tok = (HTMLToken) token;
                children.add(tok.getDOM_ObjectContent());
                children.add(tok.getDOM_Object());
            } else {
                ObjectContentToken content = (ObjectContentToken) token;
                children.add(content.getObjectContent());
            }
        }

    }

    private void addStyle(StyleToken token) {
        if (parent != null) {
            parent.addStyle(token);
        } else {
            styleBuffer = token.getStyles();
        }
    }

    private void addAttribute(AttrToken token) {
        attribute.put(token.getAttrName(), token.getAttrValue());
    }

    protected void propagateStyles() {
        if (styleBuffer != null) {
            for (StyleEntry style : DOM_Object.styleBuffer) {
                if (style.getTarget().equals(tagName)) {
                    styles.put(style.getAttrName(), style.getAttrValue());
                }
            }
        }

        for (DOM_Object obj : children) {
            if (!(obj instanceof DOM_ObjectContent)) {
                obj.propagateStyles();
            }
        }
    }

    public String render() {
        return render(0);
    }

    protected String render(int tabs) {
        String res = "";
        if (tagName != null && tagName.length() >= 1) {
            String attr = renderAttributes();
            res = renderTabs(tabs) + "<" + tagName + (attr.equals("") ? "" : " ") + attr + ">\n";
            
            for (int i = 0; i < children.size(); i++) {
                res = res + children.get(i).render(tabs+1);
            }
            
            res = res + renderTabs(tabs) + "</" + tagName + ">\n";
        } else {

            for (int i = 0; i < children.size(); i++) {
                res = res + children.get(i).render(tabs);
            }
        }
        return res;
    }
    
    protected String renderTabs(int t){
        return t == 0 ? "" : "\t"+renderTabs(t-1    );
    }
        
    private String renderAttributes() {
        String attr = "";

        for (Map.Entry value : attribute.entrySet()) {
            attr = attr + value.getKey() + " = '" + value.getValue() + "' ";
        }
        return attr;
    }

    public String getTag() {
        return tagName;
    }

    public boolean isClosed() {
        return closed;
    }

    public void close() {
        closed = true;
    }
    
    public List<DOM_Object> getByTagName(String tag){
        return getByTagName(tag, new ArrayList<DOM_Object>());
    }
    
    private List<DOM_Object> getByTagName(String tag, ArrayList<DOM_Object> list){
        if (tag.equals(tagName))
            list.add(this);
        
        for (DOM_Object child : children){
            child.getByTagName(tag,list);
        }
        
        return list;
    }

    private HashSet<String> loadTags() {
        HashSet<String> allowedTags = new HashSet<String>();
        allowedTags.add("html");
        allowedTags.add("head");
        allowedTags.add("title");
        allowedTags.add("style");
        allowedTags.add("body");
        allowedTags.add("p");
        allowedTags.add("pre");
        allowedTags.add("code");
        allowedTags.add("h1");
        allowedTags.add("h2");
        allowedTags.add("a");
        allowedTags.add("img");
        allowedTags.add("li");
        allowedTags.add("ul");
        allowedTags.add("td");
        allowedTags.add("th");
        allowedTags.add("tr");
        allowedTags.add("table");
        allowedTags.add("br");
        allowedTags.add("div");

        return allowedTags;
    }

    public static DOM_Object recognizeDOM_Object(String tag, DOM_Object father) {
        DOM_Object res = null;

        if (tag.equals("html")) {
            res = new DOM_html(father);
        }
        if (tag.equals("head")) {
            res = new DOM_head(father);
        }
        if (tag.equals("title")) {
            res = new DOM_title(father);
        }
        if (tag.equals("style")) {
            res = new DOM_style(father);
        }
        if (tag.equals("body")) {
            res = new DOM_body(father);
        }
        if (tag.equals("p")) {
            res = new DOM_p(father);
        }
        if (tag.equals("pre")) {
            res = new DOM_pre(father);
        }
        if (tag.equals("code")) {
            res = new DOM_code(father);
        }
        if (tag.equals("h1")) {
            res = new DOM_h1(father);
        }
        if (tag.equals("h2")) {
            res = new DOM_h2(father);
        }
        if (tag.equals("a")) {
            res = new DOM_a(father);
        }
        if (tag.equals("img")) {
            res = new DOM_img(father);
        }
        if (tag.equals("li")) {
            res = new DOM_li(father);
        }
        if (tag.equals("ul")) {
            res = new DOM_ul(father);
        }
        if (tag.equals("td")) {
            res = new DOM_td(father);
        }
        if (tag.equals("tr")) {
            res = new DOM_tr(father);
        }
        if (tag.equals("th")) {
            res = new DOM_th(father);
        }
        if (tag.equals("table")) {
            res = new DOM_table(father);
        }
        if (tag.equals("br")) {
            res = new DOM_br(father);
        }
        if (tag.equals("div")) {
            res = new DOM_div(father);
        }

        return res;
    }
}
