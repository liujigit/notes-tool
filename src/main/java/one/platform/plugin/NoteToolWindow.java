package one.platform.plugin;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.openapi.wm.ToolWindowManager;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import one.platform.plugin.actions.CreateNoteTabAction;
import one.platform.plugin.actions.NoteSaveAction;
import one.platform.plugin.actions.RemoveNoteTabAction;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Arrays;

/**
 * @author liuji
 * @date 2023/12/17 21:30
 **/
public class NoteToolWindow implements ToolWindowFactory, DumbAware {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        final ToolWindowManager twm = ToolWindowManager.getInstance(project);
        Key<JComponent> noteskey = new Key<>("note-id");
        twm.invokeLater(() -> {
            NotePane notePane = NotePane.getInstance(project);
            ContentFactory contentFactory = ContentFactory.getInstance();
            Content note = contentFactory.createContent(notePane, "Note", false);
            ContentManager contentManager = toolWindow.getContentManager();

            contentManager.addContent(note);

            toolWindow.setTitleActions(Arrays.asList(new CreateNoteTabAction(),
                    new RemoveNoteTabAction(),new NoteSaveAction()));

            project.putUserData(noteskey, notePane);
        });
    }
}
