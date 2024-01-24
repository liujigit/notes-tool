package one.platform.plugin.ui;

import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;
import one.platform.plugin.config.NoteContent;
import one.platform.plugin.config.NotesConfig;
import one.platform.plugin.config.NotesState;
import one.platform.plugin.constant.Constants;
import one.platform.plugin.handle.LocalRequestHandler;
import one.platform.plugin.handle.NotesMessageRouterHandler;
import one.platform.plugin.manager.NotePanelManager;
import org.apache.commons.compress.utils.IOUtils;
import org.cef.browser.CefMessageRouter;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * @author liuji
 * @date 2024/01/10 21:50
 **/
public class JcefNoteEditorPanel {


    private JComponent editorPane;

    private JBCefBrowser jbCefBrowser;

    private JBCefClient jbCefClient;

    public JcefNoteEditorPanel() {
        this.init();
        NotePanelManager.setPanel(this);
//        Disposer.register(editorPane);
    }

    private void init(){
        this.editorPane = initComponent();
    }

    public JComponent getJComponent(){
        return editorPane;
    }

    public void reload(){
        jbCefBrowser.getCefBrowser().reloadIgnoreCache();
    }




    public void saveCurPage() {
        jbCefBrowser.getCefBrowser().executeJavaScript("saveContent()","",1);
    }

    public void openDev(){
        jbCefBrowser.openDevtools();
    }

    public void showPage(NoteContent content) {
        String url = this.url(content.getId());
        jbCefBrowser.loadURL(url);
    }

    public void refresh(){
        this.showPage(NotesState.getInstance().curNote());
    }

    public void setValue(String url,String content){
        jbCefBrowser.getCefBrowser().executeJavaScript("setValue(\""+content+"\")",
                url,5);
    }

    public void setTheme(String theme){
        String javaScript = String.format("setCustomTheme('%s')",theme);
        jbCefBrowser.getCefBrowser().executeJavaScript(javaScript,
                "",6);
    }

    private JComponent initComponent(){
        if (!JBCefApp.isSupported()) {
            throw new RuntimeException("不支持jcef");
        }
        jbCefBrowser = new JBCefBrowser();
        jbCefClient = jbCefBrowser.getJBCefClient();
        jbCefClient.addRequestHandler(new LocalRequestHandler(), jbCefBrowser.getCefBrowser());

        CefMessageRouter.CefMessageRouterConfig config = new CefMessageRouter.CefMessageRouterConfig();
        config.jsQueryFunction = "noteQuery";// 定义方法
        config.jsCancelFunction = "noteQueryCancel";// 定义取消方法
        final CefMessageRouter cefMessageRouter = CefMessageRouter.create(config);
        cefMessageRouter.addHandler(new NotesMessageRouterHandler(),true);
        jbCefClient.getCefClient().addMessageRouter(cefMessageRouter);
//        CefBrowser devTools = jbCefBrowser.getCefBrowser().getDevTools();
//        JBCefBrowser devToolsBrowser = JBCefBrowser.createBuilder()
//                .setCefBrowser(devTools)
//                .setCreateImmediately(true)
//                .setMouseWheelEventEnable(true)
//                .setEnableOpenDevToolsMenuItem(true)
//                .setOffScreenRendering(true)
//                .setClient(jbCefBrowser.getJBCefClient())
//                .build();

        return jbCefBrowser.getComponent();
    }

    private String url(int id){
        final NotesConfig config = NotesState.getInstance();
        return Constants.LOCALHOST + Constants.INDEX_HTML+
                String.format("?id=%s&theme=%s&lang=%s",id,config.theme(),config.lang());
    }

    public String html(int id){
        try (InputStream fileInputStream = JcefNoteEditorPanel.class.getResourceAsStream(Constants.INDEX_HTML)) {
            final String htmlFormat = new String(IOUtils.toByteArray(fileInputStream), StandardCharsets.UTF_8);
            return htmlFormat.replace(Constants.NOTE_ID,String.format(Constants.NOTE_ID_FORMAT,id));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
