package one.platform.plugin.manager;

import one.platform.plugin.ui.NotePanel;

/**
 * @author liuji
 * @date 2024/01/21 22:54
 **/
public class NotePanelManager {
    private static NotePanel panel;

    public static void setPanel(NotePanel panel) {
        NotePanelManager.panel = panel;
    }

    public static NotePanel getPanel() {
        return panel;
    }
}
