///**
// * Copyright 2009 Jitendra Rana, jsrana@gmail.com
// * <p>
// * Licensed under the Apache License, Version 2.0 (the "License");
// * you may not use this file except in compliance with the License.
// * You may obtain a copy of the License at
// * <p>
// * http://www.apache.org/licenses/LICENSE-2.0
// * <p>
// * Unless required by applicable law or agreed to in writing, software
// * distributed under the License is distributed on an "AS IS" BASIS,
// * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// * See the License for the specific language governing permissions and
// * limitations under the License.
// */
//package one.platform.plugin.manager;
//
//
//import one.platform.plugin.ui.NotePanel;
//import one.platform.plugin.ui.NotePanelTest;
//
//import java.util.HashMap;
//
///**
// * Quick Notes Panel
// *
// * @author Jitendra Rana
// */
//public class QuickNotesManager {
//    private HashMap<String, NotePanel> panelMap;
//    private int index = 0;
//    private static final QuickNotesManager MANAGER = new QuickNotesManager();
//    public static final String VERSION = "v3.3";
//
//
//    /**
//     * Do not instantiate QuickNotesManager.
//     */
//    private QuickNotesManager() {
//        panelMap = new HashMap<>();
//    }
//
//    /**
//     * Getter for property 'instance'.
//     *
//     * @return Value for property 'instance'.
//     */
//    public static QuickNotesManager getInstance() {
//        return MANAGER;
//    }
//
//    /**
//     * Getter for property 'nextPanelID'.
//     *
//     * @return Value for property 'nextPanelID'.
//     */
//    public String getNextPanelID() {
//        return "panel_" + index++;
//    }
//
//    /**
//     * @param panelid
//     */
//    public void syncQuickNotePanels(String panelid) {
//        if (panelid != null) {
//            for (String id : panelMap.keySet()) {
//                if (id != null && !panelid.equals(id)) {
//                    NotePanel qnp = panelMap.get(id);
//                    int index = qnp.getSelectedNoteIndex();
//                    if (index == qnp.getElement().getChildren().size()) {
//                        index--;
//                    }
//                    qnp.selectNote(index, false);
//                }
//            }
//        }
//    }
//}
