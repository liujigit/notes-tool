package one.platform.plugin.handle;

import one.platform.plugin.constant.Constants;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefResourceHandler;
import org.cef.handler.CefResourceRequestHandlerAdapter;
import org.cef.network.CefRequest;

/**
 * @author liuji
 * @date 2024/01/11 21:48
 **/
public class LocalResourceRequestHandler extends CefResourceRequestHandlerAdapter {

    public CefResourceHandler getResourceHandler(CefBrowser browser, CefFrame frame, CefRequest request) {
        String url = request.getURL();
        System.out.println(url);
        if(url.startsWith(Constants.LOCALHOST)) {
            url = url.replace(Constants.LOCALHOST,"");
        }else {
            return null;
        }

        return new LocalStaticResourceHandler(url);
    }
}
