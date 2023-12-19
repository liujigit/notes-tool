package one.platform.plugin.config;

import com.intellij.openapi.components.*;
import com.intellij.openapi.project.Project;
import com.intellij.util.ThreeState;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author liuji
 * @date 2023/12/18 22:03
 **/
@Service
@State(name = "NoteState",category = SettingsCategory.TOOLS,
        storages = {@Storage(value = "note-config.xml",roamingType = RoamingType.PER_OS,useSaveThreshold = ThreeState.YES)})
public final class NoteState implements PersistentStateComponent<NoteState> {

    private NoteConfig noteConfig;

    private NoteState() {
        this.noteConfig = new NoteConfig();
    }

    public static NoteState getInstance(){
        NoteState service = ServiceManager.getService(NoteState.class);;
        return service == null ? new NoteState() : service;
    }
    @Override
    public @Nullable NoteState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull NoteState noteState) {
        XmlSerializerUtil.copyBean(noteState, this);
    }

    public NoteConfig getConfig() {
        if (null == noteConfig) {
            noteConfig = new NoteConfig();
        }
        return noteConfig;
    }

    public void setConfig(NoteConfig config) {
        this.noteConfig = config;
    }
}
