package one.platform.plugin.config;

import com.intellij.icons.AllIcons;
import com.intellij.ui.IconManager;

import javax.swing.*;

/**
 * @author liuji
 * @date 2023/12/25 23:24
 **/
public class NoteIcons {

    public static final Icon  NOTE_ICON = NoteIcons.loadIcon("META-INF/note.svg");

    public static final Icon  ADD_TAB_ICON = AllIcons.General.Add;

    public static final Icon  REMOVE_TAB_ICON = AllIcons.General.Remove;


    public static final Icon  SAVE_ICON = NoteIcons.loadIcon("img/menu-saveall.svg");



   // -------------------
    public static final Icon ARROW_LEFT = AllIcons.General.Add;
    public static final Icon ARROW_RIGHT = AllIcons.General.Add;
    public static final Icon ADD = AllIcons.General.Add;
    public static final Icon DELETE = AllIcons.Actions.GC;

    public static final Icon SETTINGS = AllIcons.General.Settings;

    public static final Icon TRASH = AllIcons.General.Add;
    public static final Icon SEARCH = AllIcons.Actions.Search;

    public static final Icon SAVE = AllIcons.General.Add;

    public static final Icon DEVTOOL = NoteIcons.loadIcon("img/openInToolWindow.svg");

    public static final Icon PRE = NoteIcons.loadIcon("img/left.svg");
    public static final Icon NEXT = NoteIcons.loadIcon("img/right.svg");

    public static final Icon RELOAD = AllIcons.Actions.Refresh;

    public static final Icon LIST = NoteIcons.loadIcon("img/listFiles.svg");
    public static final Icon SLIDERS = AllIcons.General.Add;

    public static Icon loadIcon(String path){
        return IconManager.getInstance().getIcon(path,NoteIcons.class);
    }
}
