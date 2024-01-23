package one.platform.plugin.manager;

import one.platform.plugin.config.NotesState;
import one.platform.plugin.ui.JcefNoteEditorPanel;
import one.platform.plugin.ui.NotePanel;

/**
 * @author liuji
 * @date 2024/01/21 22:54
 **/
public class NotePanelManager {
    private static JcefNoteEditorPanel panel;

    public static void setPanel(JcefNoteEditorPanel panel) {
        NotePanelManager.panel = panel;
    }

    public static JcefNoteEditorPanel getPanel() {
        return panel;
    }
}
