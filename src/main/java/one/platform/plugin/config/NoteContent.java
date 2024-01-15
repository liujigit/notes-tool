package one.platform.plugin.config;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author liuji
 * @date 2024/01/14 14:53
 **/
public class NoteContent {
    private int id;

    private String title;

    private String content;

    public NoteContent(int id) {
        this.id = id;
        this.title = "Note-"+ LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
