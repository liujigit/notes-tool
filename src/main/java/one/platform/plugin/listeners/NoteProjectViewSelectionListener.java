package one.platform.plugin.listeners;

import com.intellij.ide.projectView.ProjectViewSelectionListener;
import com.intellij.ide.projectView.impl.ProjectViewCurrentPaneProvider;
import com.intellij.ui.mac.WindowTabsComponent;

/**
 * @author liuji
 * @date 2023/12/20 23:12
 **/
public class NoteProjectViewSelectionListener implements ProjectViewSelectionListener {
    @Override
    public void onChanged() {
        System.out.println("change");
        // ProjectViewSelectionTopicKt
    }
}
