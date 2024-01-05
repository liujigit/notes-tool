package one.platform.plugin.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import one.platform.plugin.NotePane;
import one.platform.plugin.config.NoteConfig;
import one.platform.plugin.config.NoteIcons;
import one.platform.plugin.config.NoteItemConfig;
import one.platform.plugin.config.NoteState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * @author liuji
 * @date 2023/12/18 22:33
 **/
public class NoteSaveAction extends AnAction {

    public NoteSaveAction() {
        super(NoteIcons.SAVE_ICON);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        NotePane notePane = NotePane.getInstance(event.getProject());
        List<NoteItemConfig> itemConfigs = notePane.getTabConfigs();
        NoteState instance = NoteState.getInstance();
        instance.setConfig(new NoteConfig(itemConfigs));
        instance.loadState(instance);
//        ApplicationManager.getApplication().saveAll();
//        ApplicationManager.
    }
}
