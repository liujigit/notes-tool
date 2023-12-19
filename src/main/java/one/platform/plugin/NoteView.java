package one.platform.plugin;

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
public class NoteView extends JBTextArea {

    public NoteView() {
        this(null);
    }

    public NoteView(List<String> lines) {
        super();
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
            this.append(line);
            this.append("\r\n");
        }
    }
}
