package one.platform.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.ui.tabs.TabInfo;
import com.intellij.ui.tabs.impl.JBTabsImpl;

/**
 * @author liuji
 * @date 2023/12/17 21:36
 **/
public class NotePane extends JBTabsImpl {
    public NotePane(Project project) {
        super(project);
        this.addTab(this.initTabInfo());
    }

    private TabInfo initTabInfo(){
        return new TabInfo(new NoteView())
                .setText("note1");
    }
}
