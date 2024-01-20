package one.platform.plugin.handle;

import com.intellij.openapi.components.ServiceManager;
import one.platform.plugin.config.NotesState;
import org.apache.commons.lang3.StringUtils;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.callback.CefQueryCallback;
import org.cef.handler.CefMessageRouterHandlerAdapter;

/**
 * @author liuji
 * @date 2024/01/15 22:50
 **/
public class NotesMessageRouterHandler extends CefMessageRouterHandlerAdapter {

    private static final String GET_NOTE_NAME = "getNote:";
    private static final String SET_NOTE_NAME = "setNote:";
    private static final String REMOVE_NOTE_NAME = "removeNoteContent:";
    private static final String POST_MD_SPLIT = "-:";


    @Override
    public boolean onQuery(CefBrowser browser, CefFrame frame, long queryId, String request, boolean persistent, CefQueryCallback callback) {
        if(StringUtils.isNotBlank(request)) {
            if(request.startsWith(GET_NOTE_NAME)) {
                String id = request.substring(GET_NOTE_NAME.length());
                this.getNote(id,callback);
            } else if(request.startsWith(SET_NOTE_NAME)) {
                String text = request.substring(SET_NOTE_NAME.length());
                this.setNote(text,callback);
            }else if(request.startsWith(REMOVE_NOTE_NAME)) {
                String id = request.substring(REMOVE_NOTE_NAME.length());
                this.removeNoteContent(id,callback);
            }else {
                return false;
            }
            return true;
        }
        return false;
    }

    private void getNote(String id,CefQueryCallback callback) {
        final String content = NotesState.getInstance().curNote().getContent();
        callback.success(content);
    }

    private void setNote(String text,CefQueryCallback callback) {
        final String[] split = text.split(POST_MD_SPLIT, 2);
        String id = split[0];
        String content = split[1];
        NotesState.getInstance().curNote().setContent(content);
        callback.success("setNote success");
    }

    private void removeNoteContent(String id,CefQueryCallback callback) {
        NotesState.getInstance().curNote().setContent("");
        callback.success("removeNoteContent success");
    }
}
