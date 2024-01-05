package one.platform.plugin;

import com.intellij.ui.components.JBScrollPane;
import com.intellij.ui.components.JBTextArea;
import com.intellij.ui.tabs.impl.JBTabsImpl;
import org.apache.commons.collections.CollectionUtils;

import javax.swing.text.Document;
import javax.swing.text.html.HTMLDocument;
import java.util.List;

/**
 * @author liuji
 * @date 2023/12/17 21:41
 **/
public class NoteView extends JBScrollPane {

    private final JBTextArea textArea;

    public NoteView() {
        this(null);
    }

    public NoteView(List<String> lines) {
        super();
        textArea = new JBTextArea();
        this.loadData(lines);
    }

    private Document init(){
        HTMLDocument document = new HTMLDocument();
        return document;
    }

    public void loadData(List<String> lines) {
        if(CollectionUtils.isEmpty(lines)) {
            return;
        }
        for (String line : lines) {
            textArea.append(line);
            textArea.append("\r\n");
        }
    }

    public String getText() {
        return textArea.getText();
    }
}
