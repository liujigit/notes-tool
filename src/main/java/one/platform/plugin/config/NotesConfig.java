package one.platform.plugin.config;

import java.util.LinkedList;

/**
 * @author liuji
 * @date 2024/01/15 23:40
 **/
public class NotesConfig {

    private static final NotesConfig INSTANCE = new NotesConfig();

    private LinkedList<NoteContent> contents;
    private int curIndex;

    private NotesConfig() {
        this.contents = new LinkedList<>();
        this.curIndex = 0;
    }

    public static NotesConfig getInstance(){
        return INSTANCE;
    }

    public LinkedList<NoteContent> getContents() {
        return contents;
    }

    public void setContents(LinkedList<NoteContent> contents) {
        this.contents = contents;
    }

    public int getCurIndex() {
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = curIndex;
    }

    public void loadCurContent(String md) {
        this.contents.get(curIndex).setContent(md);
    }

    public int nextId() {
        if(this.getContents().isEmpty()){
            return 0;
        }
        return contents.getLast().getId() + 1;
    }
}
