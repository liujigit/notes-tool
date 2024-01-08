package one.platform.plugin;

import com.google.common.base.Supplier;
import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.*;
import com.intellij.openapi.startup.InitProjectActivity;
import com.intellij.openapi.startup.ProjectActivity;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.*;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import one.platform.plugin.ui.NotePanel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.text.Element;

import static com.intellij.formatting.commandLine.FormatterStarterKt.readSettings;

/**
 * @author liuji
 * @date 2024/01/08 21:07
 **/
public class NoteApplication implements ProjectActivity, Disposable {
    private String enotes = "";
    private Element notesElement;
    public static final Key<String> KEY_PANELID = new Key<>("panelid");
    public static final String PROPERTY_FILELOCATION = "com.jsrana.plugins.quicknotes.filelocation";

    @Nullable
    @Override
    public Object execute(@NotNull Project project, @NotNull Continuation<? super Unit> continuation) {
        notesElement = readSettings();
        ProjectManager.getInstance().addProjectManagerListener(new VetoableProjectManagerListener() {
            @Override
            public boolean canClose(@NotNull Project project) {
                final NotePanel quickNotesPanel = new NotePanel(notesElement);
                final ToolWindowManager twm = ToolWindowManager.getInstance(project);

                Runnable task1 = new Runnable() {
                    @Override
                    public void run() {
//                        Use ToolWindowFactory and toolWindow extension point
                        ToolWindowFactory.
                        RegisterToolWindowTask task = new RegisterToolWindowTask("Quick Notes", ToolWindowAnchor.RIGHT,);
                        RegisterToolWindowTaskBuilder taskBuilder = new RegisterToolWindowTaskBuilder("");
                        Supplier<RegisterToolWindowTask> build = taskBuilder::build;
                        twm.registerToolWindow("Quick Notes",taskBuilder::build);
                        ToolWindow toolWindow = twm.registerToolWindow("Quick Notes", false, ToolWindowAnchor.RIGHT);
                        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
                        Content content = contentFactory.createContent(quickNotesPanel.getRootComponent(), "", false);
                        toolWindow.getContentManager().addContent(content);
                        toolWindow.setIcon(QuickNotesIcon.QUICKNOTES);
                        project.putUserData(quicknoteskey, quickNotesPanel);
                    }
                };
                twm.invokeLater(task1);
            }

        });
    }


    @Override
    public void dispose() {
        notesElement = readSettings();
        ProjectManager.getInstance().addProjectManagerListener(new VetoableProjectManagerListener() {
            Key quicknoteskey = new Key("quicknotesid");

            public void projectOpened(final Project project) {
                final QuickNotesPanel quickNotesPanel = new QuickNotesPanel(notesElement);
                final ToolWindowManager twm = ToolWindowManager.getInstance(project);

                Runnable task1 = new Runnable() {
                    @Override
                    public void run() {
                        ToolWindow toolWindow = twm.registerToolWindow("Quick Notes", false, ToolWindowAnchor.RIGHT);
                        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
                        Content content = contentFactory.createContent(quickNotesPanel.getRootComponent(), "", false);
                        toolWindow.getContentManager().addContent(content);
                        toolWindow.setIcon(QuickNotesIcon.QUICKNOTES);
                        project.putUserData(quicknoteskey, quickNotesPanel);
                    }
                };
                twm.invokeLater(task1);
            }

            public void projectClosed(final Project project) {
                // clear locks
                QuickNotesManager.getInstance().clearLocks((QuickNotesPanel) project.getUserData(quicknoteskey));

                // save data
                if (QuickNotesManager.saveSettings(notesElement)) {
                    enotes = "";
                } else {
                    enotes = new XMLOutputter().outputString(notesElement);
                }
            }
        });
    }


}
