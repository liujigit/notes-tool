package one.platform.plugin.handle;

import one.platform.plugin.constant.Constants;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.cef.misc.IntRef;
import org.cef.misc.StringRef;
import org.cef.network.CefResponse;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.stream.Stream;

/**
 * @author liuji
 * @date 2024/01/11 21:58
 **/
public class LocalStaticResourceHandler extends StCefResourceHandlerAdapter{
    private final String file;

    public LocalStaticResourceHandler(String uri) {
        int index = uri.indexOf("?");
        String params = null;
        if(index >= 0 && uri.startsWith(Constants.INDEX_HTML)) {
            this.file = uri.substring(0,index);
            params = uri.substring(index);
        }else {
            this.file = uri;
        }

        InputStream fileInputStream = LocalStaticResourceHandler.class.getResourceAsStream(file);
        try {
            String text = new String(IOUtils.toByteArray(fileInputStream), StandardCharsets.UTF_8);
            html = this.indexHtml(text,params);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private String indexHtml(String text,String params){
        if(StringUtils.isBlank(params) || !Constants.INDEX_HTML.equals(file)){
            return text;
        }
        String id = Stream.of(params.split("&"))
                .map(item ->item.split("=",2))
                .filter(item ->"id".equals(item[0]))
                .findFirst()
                .orElse(new String[]{"id","-1"})[1];
        text = text.replaceAll(Constants.ORIGINAL_HOST_PRE,Constants.LOCALHOST_PRE);
        return text.replace(Constants.NOTE_ID,String.format(Constants.NOTE_ID_FORMAT,id));
    }

    @Override
    public void getResponseHeaders(CefResponse response, IntRef responseLength, StringRef redirectUrl) {
        responseLength.set(html.length());
        String ext = file.substring(file.lastIndexOf('.') + 1);
        switch (ext) {
            case "html":
                response.setMimeType("text/html");
                break;
            case "js":
                response.setMimeType("text/javascript; charset=utf-8");
                break;
            case "css":
                response.setMimeType("text/css; charset=utf-8");
                break;
            default:
                break;
        }
        response.setStatus(200);
    }

    private String options(String id){
        return "{\n" +
                "        height: '100%',\n" +
                "        width:'auto',\n" +
                "        mode: 'wysiwyg',  //sv, ir, wysiwyg\n" +
                "        lang: 'zh_CN', // zh_CN,en_US" +
                "        theme: 'dark', //dark、classic\n" +
                "        icon: 'ant', // material,ant\n" +
                "        toolbar:null,\n" +
                "        cdn:'http://inner/vditor/static',\n" +
                "        outline: {\n" +
                "            enable: false,\n" +
                "            position: 'left',\n" +
                "        },\n" +
                "        cache:{\n" +
                "            enable:true,\n" +
                "            id:'vditor-"+id+"'\n" +
                "        },\n" +
                "        debugger: false,\n" +
                "        typewriterMode: true,\n" +
                "        placeholder: '',\n" +
                "        preview: {\n" +
                "            theme:{\n" +
                "                current:'dark'\n" +
                "            },\n" +
                "            markdown: {\n" +
                "                toc: false,\n" +
                "                mark: false,\n" +
                "                footnotes: true,\n" +
                "                autoSpace: true,\n" +
                "            },\n" +
                "            math: {\n" +
                "                engine: 'KaTeX',\n" +
                "            },\n" +
                "        },\n" +
                "        toolbarConfig: {\n" +
                "            pin: true,\n" +
                "        },\n" +
                "        counter: {\n" +
                "            enable: true,\n" +
                "            type: 'text',\n" +
                "        },\n" +
                "        tab: '\\t',\n" +
                "    }";
    }
}
