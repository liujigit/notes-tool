package one.platform.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author liuji
 * @date 2023/12/17 21:30
 **/
public class NoteToolWindow implements ToolWindowFactory {

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {

        NotePane notePane = new NotePane(project);
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content note = contentFactory.createContent(notePane, "Note", true);
        ContentManager contentManager = toolWindow.getContentManager();

        contentManager.addContent(note);
    }
}
