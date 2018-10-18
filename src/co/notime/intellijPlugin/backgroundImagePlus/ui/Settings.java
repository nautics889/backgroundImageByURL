package co.notime.intellijPlugin.backgroundImagePlus.ui;

import co.notime.intellijPlugin.backgroundImagePlus.BackgroundService;
import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.fileChooser.FileChooserDescriptor;
import com.intellij.openapi.fileChooser.FileChooserDescriptorFactory;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.ui.ComboBox;
import com.intellij.openapi.ui.TextBrowseFolderListener;
import com.intellij.openapi.ui.TextFieldWithBrowseButton;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;

/**
 * Author: Lachlan Krautz
 * Date:   22/07/16
 */
public class Settings implements Configurable {
    
    public static final String FOLDER = "BackgroundImagesFolder";
    public static final String AUTO_CHANGE = "BackgroundImagesAutoChange";
    public static final String INTERVAL = "BackgroundImagesInterval";
    public static final String OPACITY = "BackgroundImagesOpacity";
    public static final String TIME_UNIT = "BackgroundImagesTimeUnit";
    public static final Integer OPACITY_SPINNER_DEFAULT = 15;
    public static final Integer INTERVAL_SPINNER_DEFAULT = 0;
    public static final Integer TIME_UNIT_DEFAULT = 1;
    
    private TextFieldWithBrowseButton imageFolder;
    private JPanel rootPanel;
    private JSpinner intervalSpinner;
    private JSpinner opacitySpinner;
    private JCheckBox autoChangeCheckBox;
    
    @SuppressWarnings("unused")
    private JLabel opacityLabel;
    private JComboBox timeUnitBox;
    
    @Nls
    @Override
    public String getDisplayName() {
        return "Background Image";
    }
    
    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }
    
    @Nullable
    @Override
    public JComponent createComponent() {
        FileChooserDescriptor descriptor = FileChooserDescriptorFactory.createSingleFolderDescriptor();
        imageFolder.addBrowseFolderListener(new TextBrowseFolderListener(descriptor) {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                String current = imageFolder.getText();
                if (!current.isEmpty()) {
                    fc.setCurrentDirectory(new File(current));
                }
                fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                fc.showOpenDialog(rootPanel);
                
                File file = fc.getSelectedFile();
                String path = file == null
                        ? ""
                        : file.getAbsolutePath();
                imageFolder.setText(path);
            }
        });
        autoChangeCheckBox.addActionListener(e -> intervalSpinner.setEnabled(autoChangeCheckBox.isSelected()));
        return rootPanel;
    }
    
    @Override
    public boolean isModified() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        String storedFolder = prop.getValue(FOLDER);
        String uiFolder = imageFolder.getText();
        if (storedFolder == null) {
            storedFolder = "";
        }
        return !storedFolder.equals(uiFolder)
                || opacityModified(prop)
                || intervalModified(prop)
                || timeUnitModified(prop)
                || prop.getBoolean(AUTO_CHANGE) != autoChangeCheckBox.isSelected();
    }
    
    private boolean intervalModified(PropertiesComponent prop) {
        int storedInterval = prop.getInt(INTERVAL, INTERVAL_SPINNER_DEFAULT);
        int uiInterval = ((SpinnerNumberModel) intervalSpinner.getModel()).getNumber().intValue();
        return storedInterval != uiInterval;
    }
    
    private boolean opacityModified(PropertiesComponent prop) {
        int opacity = ((SpinnerNumberModel) opacitySpinner.getModel()).getNumber().intValue();
        int storedOpacity = prop.getInt(OPACITY, OPACITY_SPINNER_DEFAULT);
        return storedOpacity != opacity;
    }
    
    private boolean timeUnitModified(PropertiesComponent prop) {
        int timeUnit = timeUnitBox.getSelectedIndex();
        int storedTimeUnit = prop.getInt(TIME_UNIT, TIME_UNIT_DEFAULT);
        return storedTimeUnit != timeUnit;
    }
    
    @Override
    public void apply() throws ConfigurationException {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        
        boolean autoChange = autoChangeCheckBox.isSelected();
        int interval = ((SpinnerNumberModel) intervalSpinner.getModel()).getNumber().intValue();
        int opacity = ((SpinnerNumberModel) opacitySpinner.getModel()).getNumber().intValue();
        int timeUnit = timeUnitBox.getSelectedIndex();
    
        prop.setValue(FOLDER, imageFolder.getText());
        prop.setValue(INTERVAL, interval, INTERVAL_SPINNER_DEFAULT);
        prop.setValue(AUTO_CHANGE, autoChange);
        prop.setValue(OPACITY, opacity, OPACITY_SPINNER_DEFAULT);
        prop.setValue(TIME_UNIT, timeUnit, TIME_UNIT_DEFAULT);
        intervalSpinner.setEnabled(autoChange);
        
        if (autoChange && interval > 0) {
            BackgroundService.start();
        } else {
            BackgroundService.stop();
        }
    }
    
    @Override
    public void reset() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        imageFolder.setText(prop.getValue(FOLDER));
        intervalSpinner.setValue(prop.getInt(INTERVAL, INTERVAL_SPINNER_DEFAULT));
        autoChangeCheckBox.setSelected(prop.getBoolean(AUTO_CHANGE, false));
        intervalSpinner.setEnabled(autoChangeCheckBox.isSelected());
        opacitySpinner.setValue(prop.getInt(OPACITY, OPACITY_SPINNER_DEFAULT));
        timeUnitBox.setSelectedIndex(prop.getInt(TIME_UNIT, TIME_UNIT_DEFAULT));
    }
    
    @Override
    public void disposeUIResources() {
    }
    
    private void createUIComponents() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        intervalSpinner = new JSpinner(new SpinnerNumberModel(prop.getInt(INTERVAL, INTERVAL_SPINNER_DEFAULT), 0, 1000, 5));
        opacitySpinner = new JSpinner(new SpinnerNumberModel(prop.getInt(INTERVAL, OPACITY_SPINNER_DEFAULT), 0, 100, 1));
        timeUnitBox = new ComboBox<>();
        timeUnitBox.addItem("SECONDS");
        timeUnitBox.addItem("MINUTES");
        timeUnitBox.addItem("HOURS");
        timeUnitBox.addItem("DAYS");
        timeUnitBox.setSelectedIndex(prop.getInt(TIME_UNIT, TIME_UNIT_DEFAULT));
    }
    
}