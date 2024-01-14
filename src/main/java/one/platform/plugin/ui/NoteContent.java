package one.platform.plugin.ui;

/**
 * @author liuji
 * @date 2024/01/14 14:53
 **/
public class NoteContent {
    private int id;

    private String content;

    public NoteContent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
