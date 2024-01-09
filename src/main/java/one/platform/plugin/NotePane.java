package one.platform.plugin;

import com.intellij.openapi.project.Project;
import com.intellij.ui.components.JBTabbedPane;
import one.platform.plugin.config.NoteConfig;
import one.platform.plugin.config.NoteIcons;
import one.platform.plugin.config.NoteItemConfig;
import one.platform.plugin.config.NoteState;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.util.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author liuji
 * @date 2023/12/17 21:36
 **/
public class NotePane extends JBTabbedPane {

//    private static final Map<String,NotePane> PANE_MAP = new ConcurrentHashMap<>();
//
//    public static NotePane getInstance(Project project) {
//        String name = project.getName();
//        if(!PANE_MAP.containsKey(name)){
//            PANE_MAP.put(name,new NotePane(project));
//        }
//        return PANE_MAP.get(name);
//    }

    private static NotePane INSTANCE;

    public static NotePane getInstance(Project project) {
        if(INSTANCE == null) {
            INSTANCE = new NotePane(project);
        }
        return INSTANCE;
    }

    private NotePane(Project project) {
        super(SwingConstants.TOP,JTabbedPane.WRAP_TAB_LAYOUT);
        try {
            this.initTabs(project);
        } catch (Exception e){
            e.printStackTrace();
        }
        this.addAncestorListener(new AncestorListener() {
            public void ancestorAdded(AncestorEvent event) {
            }

            public void ancestorRemoved(AncestorEvent event) {
                NotePane notePane = NotePane.getInstance(null);
                List<NoteItemConfig> itemConfigs = notePane.getTabConfigs();
                NoteState instance = NoteState.getInstance();
                instance.setConfig(new NoteConfig(itemConfigs));
                instance.loadState(instance);
            }

            public void ancestorMoved(AncestorEvent event) {
            }
        });
    }

    private void initTabs(Project project){
        NoteConfig config = NoteState.getInstance().getConfig();
        List<NoteItemConfig> tabs = config.getTabs();
        if(CollectionUtils.isEmpty(tabs)){
            return;
        }
        List<NoteItemConfig> list = tabs.stream()
                .sorted(Comparator.comparing(NoteItemConfig::getIndex))
                .collect(Collectors.toList());
        NoteView noteView;
        for (NoteItemConfig tab : list) {
            noteView = new NoteView(project,tab.getContent());
            this.addTabView(tab.getName(),noteView);

            if(tab.isCur()) {
                this.select(noteView,true);
            }
        }
    }

    public void select(NoteView noteView,boolean b){
        if(b) {
            this.setSelectedComponent(noteView);
        }
    }

    public List<NoteItemConfig> getTabConfigs() {
        int tabCount = this.getTabCount();
        if(tabCount == 0) {
            return Collections.emptyList();
        }
        int selectedIndex = this.getSelectedIndex();
        return Stream.iterate(0,i -> i+1)
                .limit(tabCount)
                .map(i -> {
                    NoteItemConfig config = new NoteItemConfig();
                    config.setName(this.getTitleAt(i));
                    config.setIndex(i);
                    config.setCur(Objects.equals(selectedIndex,i));
                    config.setContent(Collections.singletonList(this.getTabView(i).getText()));
                    return config;
                }).collect(Collectors.toList());
    }

    public int getTabSize(){
        return this.getTabCount();
    }

    public NoteView getTabView(int index){
        return (NoteView) this.getComponentAt(index);
    }

    public void addTabView(String title,NoteView noteView){
        this.addTab(title, NoteIcons.NOTE_ICON,noteView,null);
    }
}
