package one.platform.plugin.handle;

import org.cef.callback.CefCallback;
import org.cef.handler.CefResourceHandlerAdapter;
import org.cef.misc.IntRef;
import org.cef.network.CefRequest;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * @author liuji
 * @date 2024/01/11 21:56
 **/
public class StCefResourceHandlerAdapter extends CefResourceHandlerAdapter {
    protected String html;

    int startPos = 0;

    @Override
    public boolean processRequest(CefRequest request, CefCallback callback) {
        startPos = 0;
        callback.Continue();
        return true;
    }

    @Override
    public boolean readResponse(byte[] dataOut, int bytesToRead, IntRef intRef, CefCallback callback) {
        byte[] bytes = html.getBytes(StandardCharsets.UTF_8);
        int length = bytes.length;
        if (startPos >= length) {
            return false;
        }

        int endPos = startPos + bytesToRead;
        byte[] dataToSend =
                (endPos > length) ? Arrays.copyOfRange(bytes, startPos, length) : Arrays.copyOfRange(bytes, startPos,
                        endPos);

        ByteBuffer result = ByteBuffer.wrap(dataOut);
        result.put(dataToSend);
        intRef.set(dataToSend.length);

        startPos = endPos;
        return true;
    }
}
