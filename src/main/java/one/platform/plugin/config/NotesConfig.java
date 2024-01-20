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
    private String theme;
    private String lang;

    public NotesConfig() {
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
        return this.curIndex;
    }

    private int curIndex(int curIndex) {
        if(curIndex < 0) {
            if(this.contents.isEmpty()) {
                this.addNote();
            } else {
                curIndex = this.contents.size() + curIndex;
            }
        }

        if(curIndex >= this.contents.size()) {
            curIndex = curIndex - this.contents.size();
            this.curIndex(curIndex);
        }
        return curIndex;
    }

    public void setCurIndex(int curIndex) {
        this.curIndex = this.curIndex(curIndex);
    }

    public int size(){
        return this.contents.size();
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

    public NoteContent addNote(){
        int nextId = this.nextId();
        final NoteContent content = new NoteContent(nextId);
        contents.add(content);
        this.setCurIndex(contents.size() -1);
        return content;
    }

    public void removeCur(){
        contents.remove(this.getCurIndex());
        this.setCurIndex(Math.max(0,this.getCurIndex()-1));
    }

    public int preNote(){
        this.setCurIndex(this.getCurIndex() - 1);
        return this.getCurIndex();
    }

    public int nextNote() {
        this.setCurIndex(this.getCurIndex() + 1);
        return this.getCurIndex();
    }

    public NoteContent curNote(){
        final NoteContent content = this.contents.get(this.getCurIndex());
        System.out.println(this.curIndex);
        System.out.println(content.getId());
        System.out.println(content.getContent());
        System.out.println("--------------------");
        return content;
    }
}
