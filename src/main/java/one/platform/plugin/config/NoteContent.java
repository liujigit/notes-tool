package one.platform.plugin.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * @author liuji
 * @date 2024/01/14 14:53
 **/
public class NoteContent {
    private int id;

    private String title;

    private String content;

    private Long createTime;

    public NoteContent() {
        this(0);
    }

    public NoteContent(int id) {
        this.id = id;
        this.createTime = System.currentTimeMillis();
        this.title = "Note-" + this.createTimeStr();
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

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String createTimeStr() {
        return Instant.ofEpochMilli(this.createTime).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
