package one.platform.plugin.config;

import java.util.List;

/**
 * @author liuji
 * @date 2023/12/18 21:58
 **/
public class NoteItemConfig {
    private String name;
    private int index;
    private boolean cur;

    private List<String> content;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public List<String> getContent() {
        return content;
    }

    public void setContent(List<String> content) {
        this.content = content;
    }

    public boolean isCur() {
        return cur;
    }

    public void setCur(boolean cur) {
        this.cur = cur;
    }
}
