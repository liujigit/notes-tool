package one.platform.plugin.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.*;
import com.intellij.util.ThreeState;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author liuji
 * @date 2023/12/18 22:03
 **/
@Service
@State(name = "NotesSetting",storages = {@Storage(value = "notes-config.xml")})
public final class NotesState implements PersistentStateComponent<NotesConfig> {

    private NotesConfig notesConfig;

    private NotesState() {
        this.notesConfig = new NotesConfig();
    }

    public NotesConfig getNotesConfig(){
        return notesConfig;
    }

    @Override
    @Nullable
    public NotesConfig getState() {
        return notesConfig;
    }

    @Override
    public void loadState(@NotNull NotesConfig notesConfig) {
//        XmlSerializerUtil.copyBean(notesConfig, this.notesConfig);
        this.notesConfig = notesConfig;
    }

   public static NotesConfig getInstance() {
        return ApplicationManager.getApplication().getService(NotesState.class).getState();
    }
}
