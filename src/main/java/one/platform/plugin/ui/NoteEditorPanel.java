package one.platform.plugin.ui;

import com.intellij.util.ui.JBUI;
import org.jdom.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;


/**
 * @author liuji
 * @date 2024/01/09 21:51
 **/
public class NoteEditorPanel extends JScrollPane {

    public final static NoteEditorPanel INSTANCE = new NoteEditorPanel();

    private final InnerEditorPanel editorPane;

    private int caretPosition;

    public NoteEditorPanel() {
        editorPane = new InnerEditorPanel();
        this.getViewport().add(editorPane);
    }


    public void setText(String text) {
        editorPane.setText(text);
    }

    public void setCaretPosition(int caretPosition) {
        this.caretPosition = caretPosition;
    }

    public void addFocusListener(Element element){
        editorPane.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                super.focusGained(e);
                int len = element.getText().length();
                if (caretPosition >= len) {
                    caretPosition = len;
                }
                editorPane.setCaretPosition(caretPosition < 0 ? 0 : caretPosition);
            }
        });
    }


    static class InnerEditorPanel extends JTextArea {
        private static final Insets EDITOR_INSET_LINENUMBER = JBUI.insetsLeft(35);
        private static final Insets EDITOR_INSET_LINENUMBER_1000 = JBUI.insetsLeft(38);
        public InnerEditorPanel() {
//            this.setContentType("text/html");
//            this.setAutoscrolls(true);
//            this.setEditable(true);
            this.setOpaque(false);
            this.setMargin(EDITOR_INSET_LINENUMBER);
        }

        public void paint(Graphics g) {
            int lineCount = this.getLineCount();
            if (lineCount < 1000) {
                g.drawLine(30, 0, 30, getHeight());
                g.drawLine(32, 0, 32, getHeight());
                this.setMargin(EDITOR_INSET_LINENUMBER);
            } else {
                g.drawLine(34, 0, 34, getHeight());
                g.drawLine(36, 0, 36, getHeight());
                this.setMargin(EDITOR_INSET_LINENUMBER_1000);
            }
            super.paint(g);
        }
    }

}
