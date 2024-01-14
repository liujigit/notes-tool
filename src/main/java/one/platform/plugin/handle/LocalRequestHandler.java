package one.platform.plugin.handle;

import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefRequestHandlerAdapter;
import org.cef.handler.CefResourceRequestHandler;
import org.cef.misc.BoolRef;
import org.cef.network.CefRequest;

/**
 * @author liuji
 * @date 2024/01/11 21:46
 **/
public class LocalRequestHandler extends CefRequestHandlerAdapter {

    @Override
    public CefResourceRequestHandler getResourceRequestHandler(CefBrowser browser, CefFrame frame, CefRequest request,
                                                               boolean isNavigation, boolean isDownload,
                                                               String requestInitiator, BoolRef disableDefaultHandling) {

        return new LocalResourceRequestHandler();
    }
}
