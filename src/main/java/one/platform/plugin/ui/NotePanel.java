package one.platform.plugin.ui;

import com.intellij.ui.components.JBPanel;

import javax.swing.*;
import javax.swing.text.Element;
import java.awt.*;

/**
 * @author liuji
 * @date 2024/01/08 22:12
 **/
public class NotePanel extends JPanel {

    private final Element element;

    public NotePanel(Element element) {
        this.element = element;
    }
}
