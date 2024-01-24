package one.platform.plugin.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import one.platform.plugin.config.NotesConfig;
import one.platform.plugin.config.NotesState;
import one.platform.plugin.constant.Constants;
import one.platform.plugin.manager.NotePanelManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.Objects;


/**
 * @author liuji
 * @date 2024/01/21 18:00
 **/
public class NoteSettingPanel extends JPanel implements SearchableConfigurable {

    private JLabel themeLabel;
    private JRadioButton sysThemeBut;
    private JRadioButton customThemeBut;
    private JComboBox<String> themeBox;

    private JLabel langLabel;
    private JRadioButton sysLangBut;
    private JRadioButton customLangBut;
    private JComboBox<String> langBox;


    public NoteSettingPanel() {
        this.initComponent();
        this.initLayout();
        this.loadConfig();
    }

    private void initComponent(){
        themeLabel = new JLabel("主题");
        sysThemeBut = new JRadioButton("系统"){
            public void setSelected(boolean b) {
                super.setSelected(b);
                themeBox.setVisible(!b);
            }
        };
        sysThemeBut.addActionListener(e -> {
            final boolean selected = sysThemeBut.isSelected();
            if(selected) {
                themeBox.setVisible(false);
            }
        });
        customThemeBut  = new JRadioButton("自定义"){
            public void setSelected(boolean b) {
                super.setSelected(b);
                themeBox.setVisible(b);
            }
        };

        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(sysThemeBut);
        themeGroup.add(customThemeBut);

        customThemeBut.addActionListener(e -> {
            final boolean selected = customThemeBut.isSelected();
            themeBox.setVisible(selected);
        });
        themeBox = new ComboBox<>(Constants.THEMES);
        themeBox.setVisible(false);
        themeBox.addActionListener(e-> {
            final String theme = String.valueOf(themeBox.getSelectedItem());
            if(customThemeBut.isSelected()) {
                NotePanelManager.getPanel().setTheme(theme);
            }
        });


        langLabel = new JLabel("语言");
        sysLangBut = new JRadioButton("系统"){
            public void setSelected(boolean b) {
                super.setSelected(b);
                themeBox.setVisible(!b);
            }
        };
        sysLangBut.addActionListener(e -> {
            final boolean selected = sysLangBut.isSelected();
            if(selected) {
                langBox.setVisible(false);
            }
        });
        customLangBut = new JRadioButton("自定义"){
            public void setSelected(boolean b) {
                super.setSelected(b);
                langBox.setVisible(b);
            }
        };
        customLangBut.addActionListener(e -> {
            final boolean selected = customLangBut.isSelected();
            langBox.setVisible(selected);
        });

        ButtonGroup langGroup = new ButtonGroup();
        langGroup.add(sysLangBut);
        langGroup.add(customLangBut);

        langBox = new ComboBox<>(Constants.LANG_LIST);
        langBox.setVisible(false);
    }


    private void initLayout(){
        GroupLayout layout = new GroupLayout(this);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        this.setLayout(layout);
        layout.setVerticalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addGap(10)
                                .addComponent(themeLabel)
                                .addComponent(sysThemeBut)
                                .addComponent(customThemeBut)
                                .addComponent(themeBox,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,20)
                                .addGap(10)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addGap(10)
                                .addComponent(langLabel)
                                .addComponent(sysLangBut)
                                .addComponent(customLangBut)
                                .addComponent(langBox,GroupLayout.PREFERRED_SIZE,GroupLayout.PREFERRED_SIZE,20)
                                .addGap(10)
                        ));

        layout.setHorizontalGroup(
                layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(themeLabel)
                                .addComponent(langLabel))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(sysThemeBut)
                                .addComponent(sysLangBut))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(customThemeBut)
                                .addComponent(customLangBut))
                        .addGroup(layout.createParallelGroup()
                                .addComponent(themeBox)
                                .addComponent(langBox)));
    }


    private void loadConfig(){
        final NotesConfig instance = NotesState.getInstance();
        System.out.println("NotesConfig:" + instance .isSysTheme() + " : " + instance.isSysLang() );
        sysThemeBut.setSelected(instance.isSysTheme());
        customThemeBut.setSelected(!instance.isSysTheme());
        themeBox.setSelectedItem(instance.getTheme());

        sysLangBut.setSelected(instance.isSysLang());
        customLangBut.setSelected(!instance.isSysLang());
        langBox.setSelectedItem(instance.getLang());
    }



    /**
     * @return
     */
    @Override
    public @NotNull @NonNls String getId() {
        return "one.platform.plugin.ui.NoteSettingPanel";
    }

    /**
     * @return
     */
    @Override
    public @NlsContexts.ConfigurableName String getDisplayName() {
        return "NoteTool";
    }

    /**
     * @return
     */
    @Override
    public @Nullable JComponent createComponent() {
        return this;
    }

    /**
     * @return
     */
    @Override
    public boolean isModified() {
        final NotesConfig instance = NotesState.getInstance();
        boolean isModifiedTheme = instance.isSysTheme() == sysThemeBut.isSelected() &&
                Objects.equals(instance.getTheme(),themeBox.getSelectedItem());
        boolean isModifiedLang = instance.isSysLang() == sysLangBut.isSelected() &&
                Objects.equals(instance.getLang(),langBox.getSelectedItem());
        return !(isModifiedTheme && isModifiedLang);
    }

    /**
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        final NotesConfig instance = NotesState.getInstance();
        instance.setSysTheme(sysThemeBut.isSelected());
        instance.setTheme(String.valueOf(themeBox.getSelectedItem()));
        instance.setSysLang(sysLangBut.isSelected());
        instance.setLang(String.valueOf(langBox.getSelectedItem()));
        NotePanelManager.getPanel().refresh();
    }

    /**
     *
     */
    @Override
    public void reset() {
        SearchableConfigurable.super.reset();
        this.loadConfig();
        NotePanelManager.getPanel().refresh();
    }

    /**
     *
     */
    @Override
    public void cancel() {
        SearchableConfigurable.super.cancel();
        this.loadConfig();
        NotePanelManager.getPanel().refresh();
    }
}
