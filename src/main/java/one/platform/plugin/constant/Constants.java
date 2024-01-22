package one.platform.plugin.constant;

import java.util.*;

/**
 * @author liuji
 * @date 2024/01/11 21:50
 **/
public class Constants {
    public static final String STATIC_PATH = "/static";
    public static final String LOCALHOST = "http://inner/vditor";
    public static final String LOCALHOST_PRE = LOCALHOST + STATIC_PATH;
    public static final String INDEX_HTML = STATIC_PATH + "/index.html";

    public static final String ORIGINAL_HOST_PRE = "https://unpkg.com/vditor@3.9.8";

    public static final String NOTE_VALUE_PLACEHOLDERS = "value:''";
    public static final String NOTE_VALUE_FORMAT= "value:'%s'";


    public static final String NOTE_ID = "note-id";
    public static final String NOTE_ID_FORMAT = "note-%s";

    public static final String SYS_THEME = "dark";
    public static final String SYS_LANG = "zh_CN";


    public static final String[] THEMES = {"dark","light"};

    public static final String[] LANG_LIST = {"zh_CN"};
//    public static final List<String> LANG_LIST = Arrays.asList("zh_CN","zh_TW","en_US","fr_FR","pt_BR","ru_RU","sv_SE",
//            "ja_JP","ko_KR");
}
