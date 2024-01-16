package one.platform.plugin.ui;

import com.intellij.designer.LightFillLayout;
import com.intellij.openapi.actionSystem.*;
import com.intellij.openapi.components.ServiceManager;
import one.platform.plugin.config.NoteContent;
import one.platform.plugin.config.NoteIcons;
import one.platform.plugin.config.NotesConfig;
import one.platform.plugin.config.NotesState;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;

/**
 * @author liuji
 * @date 2024/01/11 23:16
 **/
public class NotePanel extends JPanel {

    private final JcefNoteEditorPanel editorPanel;

    private final JLabel labelNoteTitle;
    private final JLabel labelIndex;

    public NotePanel() {
        this.setLayout(new FlowLayout(FlowLayout.LEADING, 0, 0));

        labelIndex = new JLabel();
        labelNoteTitle = new JLabel();

        this.add(labelIndex);
        this.add(labelNoteTitle);;

        this.initToolBar();

        this.editorPanel = new JcefNoteEditorPanel();
        this.add(editorPanel.getJComponent());

        this.showCur();
    }

    private NotesConfig getConfig() {
        return ServiceManager.getService(NotesState.class).getNotesConfig();
    }

    public static NotePanel getInstance() {
        return new NotePanel();
    }

    public synchronized void showCur() {
        if(CollectionUtils.isEmpty(this.getConfig().getContents())) {
            this.addNete();
        } else {
            NoteContent content = this.getConfig().curNote();
            this.editorPanel.showPage(content);
            this.labelIndex.setText(String.format("[%s / %s] ",this.getConfig().getCurIndex() + 1, this.getConfig().size()));
            this.labelNoteTitle.setText(this.getConfig().curNote().getTitle());
        }
    }

    public synchronized void addNete() {
        editorPanel.saveCurPage();
        NoteContent content = this.getConfig().addNote();
        this.showCur();
    }

    public synchronized void removeCurPanel() {
        this.getConfig().removeCur();
        this.showCur();
    }

    public synchronized void preNote() {
        editorPanel.saveCurPage();
        this.getConfig().preNote();
        this.showCur();
    }

    public synchronized void nextNote() {
        editorPanel.saveCurPage();
        this.getConfig().nextNote();
        this.showCur();
    }


    private void initToolBar(){
        final ActionManager actionManager = ActionManager.getInstance();
        final DefaultActionGroup dag = new DefaultActionGroup();
        dag.addSeparator();
        dag.add(new AnAction("刷新", "Reload", NoteIcons.RELOAD) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                editorPanel.reload();
            }
        });
        dag.add(new AnAction("打印", "Print", NoteIcons.SEARCH) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                editorPanel.printMd();
            }
        });

        dag.add(new AnAction("保存", "Save", NoteIcons.SAVE) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                editorPanel.saveCurPage();
            }
        });

        dag.add(new AnAction("Devtool", "Devtool", NoteIcons.DEVTOOL) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                editorPanel.openDev();
            }
        });


        dag.add(new AnAction("Pre", "Pre", NoteIcons.PRE) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                preNote();
            }
        });

        dag.add(new AnAction("NEXT", "NEXT", NoteIcons.NEXT) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                nextNote();
            }
        });

        dag.add(new AnAction("新增", "Add", NoteIcons.ADD) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                addNete();
            }
        });

        dag.add(new AnAction("删除", "Remove", NoteIcons.REMOVE_TAB_ICON) {
            @Override
            public void actionPerformed(@NotNull AnActionEvent event) {
                removeCurPanel();
            }
        });


        final ActionToolbar actionToolbar = actionManager.createActionToolbar("Notes", dag, true);
        actionToolbar.setTargetComponent(this);
        actionToolbar.setReservePlaceAutoPopupIcon(false);

        final JComponent actionToolbarComponent = actionToolbar.getComponent();
        actionToolbar.setReservePlaceAutoPopupIcon(false);
        this.add(actionToolbarComponent);
    }
}
