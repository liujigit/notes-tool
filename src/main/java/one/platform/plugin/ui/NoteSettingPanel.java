package one.platform.plugin.ui;

import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.util.NlsContexts;
import one.platform.plugin.config.NotesConfig;
import one.platform.plugin.config.NotesState;
import one.platform.plugin.constant.Constants;
import one.platform.plugin.manager.NotePanelManager;
import org.apache.commons.lang3.StringUtils;
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

    private NotesConfig config;

    public NoteSettingPanel() {
        this.loadConfig();
        this.themePanel();
        this.langPanel();
    }

    public void themePanel(){

        JComboBox<String> comboBox = new ComboBox<>(Constants.THEMES);
        comboBox.setSelectedItem(StringUtils.isBlank(config.getTheme()) ?
                Constants.SYS_THEME:config.getTheme());
        comboBox.setVisible(false);
        comboBox.addActionListener(e-> {
            final String theme = String.valueOf(comboBox.getSelectedItem());
            config.setTheme(theme);
            NotePanelManager.getPanel().setTheme(theme);
        });

        JRadioButton defaultTheme = new JRadioButton("系统",config.isSysTheme());
        defaultTheme.addActionListener(e -> {
            final boolean selected = defaultTheme.isSelected();
            if(selected) {
                comboBox.setVisible(false);
                config.setSysTheme(true);
            }
        });
        JRadioButton customTheme = new JRadioButton("自定义",!config.isSysTheme());

        customTheme.addActionListener(e -> {
            final boolean selected = customTheme.isSelected();
            if(selected) {
                comboBox.setVisible(true);
                config.setSysTheme(false);
            }
        });

        ButtonGroup themeGroup = new ButtonGroup();
        themeGroup.add(defaultTheme);
        themeGroup.add(customTheme);

        JPanel themePanel = new JPanel();
        themePanel.add(new JLabel("主题"));
        themePanel.add(defaultTheme);
        themePanel.add(customTheme);
        themePanel.add(comboBox);

        this.add(themePanel);
    }


    public void langPanel(){

        JComboBox<String> comboBox = new ComboBox<>(Constants.LANG_LIST);
        comboBox.setSelectedItem(StringUtils.isBlank(config.getLang()) ?
                Constants.SYS_LANG:config.getLang());
        comboBox.setVisible(false);
        comboBox.addActionListener(e-> {
            final String theme = String.valueOf(comboBox.getSelectedItem());
            config.setLang(theme);
        });

        JRadioButton defaultLang = new JRadioButton("系统",config.isSysLang());
        defaultLang.addActionListener(e -> {
            final boolean selected = defaultLang.isSelected();
            if(selected) {
                comboBox.setVisible(false);
                config.setSysLang(true);
            }
        });
        JRadioButton customLang = new JRadioButton("自定义",!config.isSysLang());

        customLang.addActionListener(e -> {
            final boolean selected = customLang.isSelected();
            if(selected) {
                comboBox.setVisible(true);
                config.setSysLang(false);
            }
        });

        ButtonGroup langGroup = new ButtonGroup();
        langGroup.add(defaultLang);
        langGroup.add(customLang);

        JPanel langPanel = new JPanel();
        langPanel.add(new JLabel("语言"));
        langPanel.add(defaultLang);
        langPanel.add(customLang);
        langPanel.add(comboBox);

        this.add(langPanel);
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
        this.loadConfig();
        return this;
    }

    /**
     * @return
     */
    @Override
    public boolean isModified() {
        final NotesConfig instance = NotesState.getInstance();
        boolean isModifiedTheme = instance.isSysTheme() == config.isSysTheme() &&
                Objects.equals(instance.getTheme(),config.getTheme());
        boolean isModifiedLang = instance.isSysLang() == config.isSysLang() &&
                Objects.equals(instance.getLang(),config.getLang());
        return !(isModifiedTheme && isModifiedLang);
    }

    /**
     * @throws ConfigurationException
     */
    @Override
    public void apply() throws ConfigurationException {
        final NotesConfig instance = NotesState.getInstance();
        instance.setSysLang(config.isSysLang());
        instance.setLang(config.getLang());
        instance.setSysTheme(config.isSysTheme());
        instance.setTheme(config.getTheme());
        final NotePanel component = NotePanelManager.getPanel();
    }

    /**
     *
     */
    @Override
    public void reset() {
        SearchableConfigurable.super.reset();
        this.loadConfig();
        this.resetUi();
    }

    public void resetUi() {

    }

    private void loadConfig(){
        final NotesConfig instance = NotesState.getInstance();
        this.config = new NotesConfig();
        config.setSysLang(instance.isSysLang());
        config.setLang(instance.getLang());
        config.setSysTheme(instance.isSysTheme());
        config.setTheme(instance.getTheme());
    }

    /**
     *
     */
    @Override
    public void cancel() {
        SearchableConfigurable.super.cancel();
        this.loadConfig();
    }
}
