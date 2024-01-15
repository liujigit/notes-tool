package one.platform.plugin.handle;

import one.platform.plugin.config.NotesConfig;
import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

/**
 * @author liuji
 * @date 2024/01/15 22:50
 **/
public class MessageRouterHandler extends CefMessageRouterHandlerAdapter {

    private static final String POST_MD_TITLE = "postMd:";
    private static final String POST_MD_SPLIT = "-:";


    @Override
    public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
        if(StringUtils.isNotBlank(request) && request.startsWith(POST_MD_TITLE)) {
            String content = request.substring(POST_MD_TITLE.length());
            final String[] split = content.split(POST_MD_SPLIT, 2);
            String id = split[0];
            String mdStr = split[1];
            System.out.println(id);
            NotesConfig.getInstance().loadCurContent(mdStr);
            callback.success(POST_MD_TITLE + " success!");
            return true;
        }
        return false;
    }
}
