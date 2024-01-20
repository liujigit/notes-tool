package one.platform.plugin;

import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import com.intellij.ui.content.ContentManager;
import one.platform.plugin.ui.NotePanel;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

/**
 * @author liuji
 * @date 2024/01/08 21:07
 **/
public class NoteToolWindow implements ToolWindowFactory,DumbAware {
    private Key<JComponent> noteId = new Key<>("noteId");
    /**
     * @param project
     * @param toolWindow
     */
    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        JComponent panel = NotePanel.getInstance();
        ContentFactory contentFactory = ContentFactory.getInstance();
        Content content = contentFactory.createContent(panel, "", false);
        ContentManager contentManager = toolWindow.getContentManager();
        contentManager.addContent(content);
        project.putUserData(noteId,panel);
    }

    /**
     * @param toolWindow
     */
    @Override
    public void init(@NotNull ToolWindow toolWindow) {
        ToolWindowFactory.super.init(toolWindow);
    }
}
