package one.platform.plugin.config;

import java.util.ArrayList;
import java.util.List;

/**
 * StorageConfig
 * @author liuji
 * @date 2023/12/18 22:00
 **/
public class NoteConfig {
    private List<NoteItemConfig> tabs;

    public NoteConfig() {
        this.tabs = new ArrayList<>();
    }

    public NoteConfig(List<NoteItemConfig> tabs) {
        this.tabs = tabs;
    }

    public List<NoteItemConfig> getTabs() {
        return tabs;
    }

    public void setTabs(List<NoteItemConfig> tabs) {
        this.tabs = tabs;
    }
}
