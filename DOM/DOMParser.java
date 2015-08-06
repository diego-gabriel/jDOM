package DOM;

import DOM.token.*;
import java.util.ArrayList;
import java.util.HashSet;

public class DOMParser {

    private static String htmlBuffer;
    private static HashSet<String> htmlTags;
    private static int index;

    public static void prepareToParseHtmlSource(String htmlSource, HashSet<String> tags) {
        index = 0;
        htmlBuffer = htmlSource;
        htmlTags = tags;
    }

    public static DOMToken nextToken(DOM_Object objectOwner) {
        DOMToken res = null;
        String preToken = "";
        boolean notTag = false;

        while (!objectOwner.isClosed() && index < htmlBuffer.length() && res == null) {
            char k = htmlBuffer.charAt(index);
            index++;
            if (k == '\n' || k == '\t') {
                continue;
            }
            preToken = preToken + k;

            if (preToken.charAt(preToken.length() - 1) == '<') {

                String subToken = completeTag();
                if (htmlTags.contains(subToken)) {
                    if (subToken.equals("style")) {
                        DOM_style obj = new DOM_style(objectOwner);
                        res = new StyleToken(obj);
                    } else {
                        DOM_Object obj = DOM_Object.recognizeDOM_Object(subToken, objectOwner);
                        res = new HTMLToken(new DOM_ObjectContent(preToken.substring(0, preToken.length() - 1), objectOwner), obj);
                    }
                    preToken = "";
                } else if (subToken.length() >= 2 && subToken.charAt(0) == '/' && subToken.substring(1).equals(objectOwner.getTag())) {
                    index++;
                    objectOwner.close();
                    res = new ObjectContentToken(new DOM_ObjectContent(preToken.substring(0, preToken.length() - 1), objectOwner));
                    preToken = "";
                } else {
                    preToken = preToken + subToken;
                }
            }
        }

        if (preToken.length() > 0) {
            res = new ObjectContentToken(new DOM_ObjectContent(preToken, objectOwner));
        }
        return res;
    }

    public static AttrToken nextAttrToken() {
        AttrToken res = null;
        String preToken = "";
        String name = "";
        String value = "";
        boolean searchName, searchValue;
        searchValue = false;
        searchName = true;
        char com = 0;
        char k;
        while (index < htmlBuffer.length() && res == null && htmlBuffer.charAt(index) != '>') {
            k = htmlBuffer.charAt(index);
            index++;
            if (k == ' ') {
                continue;
            }

            if (searchName) {
                if (k == '=') {
                    name = preToken;
                    preToken = "";
                    searchName = false;
                } else {
                    preToken = preToken + k;
                }
            } else if (searchValue) {
                if (k == com) {
                    value = preToken;
                    preToken = "";
                    searchValue = false;
                    res = new AttrToken(name, value);
                } else {
                    preToken = preToken + k;
                }

            } else {
                if (k == '"' || k == '\'') {
                    com = k;
                    searchValue = true;
                }
            }

        }
        if (res == null) {
            index++;
        }

        return res;
    }

    public static ArrayList<StyleEntry> parseStyle(String text) {
        ArrayList<StyleEntry> res = new ArrayList<StyleEntry>();
        String name = "", value = "", target = "";
        String buffer = "";
        boolean searchValue = false, searchName = false, searchTarget = true;
        text = delWhites(text);

        char k;
        for (int i = 0; i < text.length(); i++) {
            k = text.charAt(i);
            if (searchTarget && k == '{') {
                target = buffer;
                buffer = "";
                searchTarget = false;
                searchName = true;
            } else if (searchName && k == ':') {
                name = buffer;
                buffer = "";
                searchName = false;
                searchValue = true;
            } else if (searchName && k == '}') {
                buffer = "";
                searchName = false;
                searchTarget = true;
            } else if (searchValue && k == ';') {
                value = buffer;
                buffer = "";
                searchValue = false;
                searchName = true;
                res.add(new StyleEntry(target, name, value));
            } else {
                buffer = buffer + k;
            }

        }

        return res;
    }

    private static String delWhites(String cad) {
        String res = "";
        char k;
        for (int i = 0; i < cad.length(); i++) {
            k = cad.charAt(i);
            if (k != ' ' && k != '\n' && k != '\t') {
                res = res + k;
            }
        }
        return res;
    }

    public static String deleteTabs(String cad) {
        String res = "";
        char k;
        for (int i = 0; i < cad.length(); i++) {
            k = cad.charAt(i);
            if (k != '\n' && k != '\t') {
                res = res + k;
            }
        }
        return res;
    }

    private static String completeTag() {
        String tok = "";
        while (index < htmlBuffer.length() && htmlBuffer.charAt(index) != ' ' && htmlBuffer.charAt(index) != '>') {
            tok = tok + htmlBuffer.charAt(index);
            index++;
        }
        return tok;
    }

    public static String extractTagOnly(String name_tag, String source) {
        String res = "";
        String tag = "<" + name_tag;
        int i = 0;
        int length = tag.length();

        while (!source.substring(i, i + length).equals(tag)) {
            i++;
        }

        while (source.charAt(i) != '>') {
            i++;
        }
        i++;

        tag = "</" + name_tag + ">";
        length = tag.length();

        do {
            res = res + source.charAt(i);
            i++;
        } while (!source.substring(i, Math.min(source.length(), i + length)).equals(tag) && i < source.length());

        return res;
    }

}
