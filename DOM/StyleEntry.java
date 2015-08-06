package DOM;

public class StyleEntry {

    private String target;
    private String attrName;
    private String attrValue;
    private String special;

    public StyleEntry(String tar, String name, String val) {
        if (hasSpecial(tar)) {
            target = recoverAttr(tar);
            special = recoverSpec(tar);
        } else {
            target = tar;
            special = null;
        }

        attrName = name;
        attrValue = val;

        System.out.println("Style created: " + target + "{" + attrName + ": " + attrValue + ";}");
    }

    public String getSpec() {
        String res = "";
        if (special != null) {
            res = special;
        }
        return res;
    }

    public String getTarget() {
        return target;
    }

    public String getAttrName() {
        return attrName;
    }

    public String getAttrValue() {
        return attrValue;
    }

    private boolean hasSpecial(String cad) {
        boolean res = false;
        System.out.println(cad + " special");
        for (int i = 0; i < cad.length() && !res; i++) {
            res = cad.charAt(i) == ':';
        }

        return res;
    }

    private String recoverAttr(String cad) {
        String res = "";

        for (int i = 0; i < cad.length() && cad.charAt(i) != ':'; i++) {
            res = res + cad.charAt(i);
        }

        return res;
    }

    private String recoverSpec(String cad) {
        String res = "";
        int i = 0;

        while (cad.charAt(i) != ':') {
            i++;
        }
        i++;

        while (i < cad.length()) {
            res = res + cad.charAt(i);
            i++;
        }

        return res;
    }
}
