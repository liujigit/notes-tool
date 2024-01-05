package one.platform.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import one.platform.plugin.NotePane;
import one.platform.plugin.NoteView;
import one.platform.plugin.config.NoteIcons;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

/**
 * @author liuji
 * @date 2023/12/18 22:23
 **/
public class RemoveNoteTabAction extends AnAction {

    public RemoveNoteTabAction() {
        super(NoteIcons.REMOVE_TAB_ICON);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent action) {
        NotePane notePane = NotePane.getInstance(action.getProject());

        int index = notePane.getSelectedIndex();
        notePane.remove(index);
        int tabSize = notePane.getTabSize();
        if(tabSize != 0){
            NoteView tabView = notePane.getTabView(Math.min(index,notePane.getTabSize() - 1));
            notePane.select(tabView,true);
        }
    }
}
