package one.platform.plugin.config;

import one.platform.plugin.constant.Constants;

import java.util.LinkedList;

/**
 * @author liuji
 * @date 2024/01/15 23:40
 **/
public class NotesConfig {

    private LinkedList<NoteContent> contents;
    private int curIndex;
    private boolean sysTheme;
    private String theme;
    private boolean sysLang;
    private String lang;

    public NotesConfig() {
        this.contents = new LinkedList<>();
        this.curIndex = 0;
        this.sysTheme = true;
        this.sysLang = true;
        this.theme = Constants.SYS_THEME;
        this.lang = Constants.SYS_LANG;
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

    public boolean isSysTheme() {
        return sysTheme;
    }

    public void setSysTheme(boolean sysTheme) {
        this.sysTheme = sysTheme;
    }

    public boolean isSysLang() {
        return sysLang;
    }

    public void setSysLang(boolean sysLang) {
        this.sysLang = sysLang;
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

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String theme(){
        return this.isSysTheme() ? Constants.SYS_THEME : this.theme;
    }

    public String lang(){
        return this.isSysLang() ? Constants.SYS_LANG : this.lang;
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
        return this.contents.get(this.getCurIndex());
    }
}
