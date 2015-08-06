package DOM;

public class DOM_ObjectContent extends DOM_Object {

    private String content;

    public DOM_ObjectContent(String text, DOM_Object father) {
        super("", father, false);
        content = text;
    }

    protected String render(int tabs) {
        return renderTabs(tabs) + content+"\n";
    }

    public String getContent() {
        return content;
    }
}
