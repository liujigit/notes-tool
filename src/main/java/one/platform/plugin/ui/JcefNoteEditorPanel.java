package one.platform.plugin.ui;

import com.intellij.ui.jcef.JBCefApp;
import com.intellij.ui.jcef.JBCefBrowser;
import com.intellij.ui.jcef.JBCefClient;
import com.intellij.ui.jcef.JCEFHtmlPanel;
import com.intellij.util.ui.UIUtil;
import one.platform.plugin.constant.Constants;
import one.platform.plugin.handle.LocalRequestHandler;
import one.platform.plugin.handle.LocalStaticResourceHandler;
import one.platform.plugin.handle.MessageRouterHandler;
import org.apache.commons.compress.utils.IOUtils;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.browser.CefMessageRouter;
import org.cef.handler.CefWindowHandler;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @author liuji
 * @date 2024/01/10 21:50
 **/
public class JcefNoteEditorPanel {


    private JComponent editorPane;

    private JBCefBrowser jbCefBrowser;

    private JBCefClient jbCefClient;

//    private JCEFHtmlPanel panel;

    public JcefNoteEditorPanel() {
        this.init();
    }

    private void init(){
        final boolean isDark = UIUtil.isUnderDarcula();
        this.editorPane = initComponent(isDark);
//        this.getViewport().add(editorPane, BorderLayout.CENTER);
    }


    public JComponent getJComponent(){
        return editorPane;
    }



    public void reload(){
//        String html = this.html();
//        jbCefBrowser.loadHTML(html);
        jbCefBrowser.getCefBrowser().reloadIgnoreCache();
    }

    public void printMd(){
    }

    public void openDev(){
        jbCefBrowser.openDevtools();
    }

    public void showPage(int id) {
        String url = this.url(id);
        jbCefBrowser.loadURL(url);

//        String html = this.html(id);
//        jbCefBrowser.loadHTML(html);
    }

    private JComponent initComponent(boolean isDark){
        if (!JBCefApp.isSupported()) {
            throw new RuntimeException("不支持jcef");
        }
        jbCefBrowser = JCEFHtmlPanel.createBuilder()
                .setCreateImmediately(true)
                .setEnableOpenDevToolsMenuItem(true)
                .setMouseWheelEventEnable(true)
                .setOffScreenRendering(true)
                .build();
        jbCefClient = jbCefBrowser.getJBCefClient();
        jbCefClient.addRequestHandler(new LocalRequestHandler(), jbCefBrowser.getCefBrowser());

        CefMessageRouter.CefMessageRouterConfig config = new CefMessageRouter.CefMessageRouterConfig();
        config.jsQueryFunction = "cefQuery";// 定义方法
        config.jsCancelFunction = "cefQueryCancel";// 定义取消方法
        final CefMessageRouter cefMessageRouter = CefMessageRouter.create(config);
        cefMessageRouter.addHandler(new MessageRouterHandler(),false);
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
        return Constants.LOCALHOST + Constants.INDEX_HTML+"?id="+id;
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
