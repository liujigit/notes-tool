package one.platform.plugin.ui;

import com.intellij.designer.LightFillLayout;
import com.intellij.openapi.actionSystem.*;
import one.platform.plugin.config.NoteIcons;
import org.apache.commons.collections.CollectionUtils;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.LinkedList;
import java.util.Objects;

/**
 * @author liuji
 * @date 2024/01/11 23:16
 **/
public class NotePanel extends JPanel {

    private final LinkedList<NoteContent> contents;
    private final JcefNoteEditorPanel editorPanel;

    private int curIndex;


    public NotePanel() {
        this(new LinkedList<>(),0);
    }

    public NotePanel(LinkedList<NoteContent> contents, int curIndex) {
        this.contents = contents;
        this.curIndex = curIndex;

        this.setLayout(new LightFillLayout());
        this.initToolBar();

        this.editorPanel = new JcefNoteEditorPanel();
        this.add(editorPanel.getJComponent());

        this.showCur();
    }

    public static NotePanel getInstance() {
        return new NotePanel();
    }

    public synchronized void showCur() {
        if(CollectionUtils.isEmpty(contents)) {
            this.addNete();
        } else {
            if(curIndex < 0) {
                this.curIndex = this.contents.size() -1 + this.curIndex;;
            }

            if(curIndex >= this.contents.size()) {
                this.curIndex = this.curIndex - this.contents.size();
            }
            this.editorPanel.showPage(contents.get(curIndex).getId());
        }
    }

    public void addNete() {
        int nextId = this.getNextIdIndex();
        contents.add(new NoteContent(nextId));
        this.curIndex = this.contents.size() -1;
        this.editorPanel.showPage(contents.get(curIndex).getId());
    }


    private int getNextIdIndex() {
        if(contents.isEmpty()){
            return 0;
        }
        return contents.getLast().getId() + 1;
    }

    public void removeCurPanel() {
        contents.remove(curIndex);
        this.curIndex = Math.max(0,this.curIndex-1);
        this.showCur();
    }

    public void preNote() {
        this.curIndex = this.curIndex - 1;
        this.showCur();
    }

    public void nextNote() {
        this.curIndex = this.curIndex + 1;
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
