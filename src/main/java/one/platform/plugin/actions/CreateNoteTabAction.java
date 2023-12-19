package one.platform.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import one.platform.plugin.NotePane;
import one.platform.plugin.NoteView;
import org.jetbrains.annotations.NotNull;

/**
 * @author liuji
 * @date 2023/12/18 22:23
 **/
public class CreateNoteTabAction extends AnAction {

    @Override
    public void actionPerformed(@NotNull AnActionEvent action) {
        NotePane notePane = NotePane.getInstance(action.getProject());
        NoteView noteView = new NoteView();
        notePane.addTabView("Note"+notePane.getTabSize(),noteView);
        notePane.select(noteView,true);
    }
}
