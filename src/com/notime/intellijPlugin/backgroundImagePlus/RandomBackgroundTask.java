package com.notime.intellijPlugin.backgroundImagePlus;

import com.intellij.ide.util.PropertiesComponent;
import com.notime.intellijPlugin.backgroundImagePlus.ui.Settings;

import java.io.File;
import java.util.Arrays;

/**
 * Author: Allan de Queiroz
 * Date:   07/05/17
 * Modified by He Lili   Date: 19/10/18
 */
public class RandomBackgroundTask implements Runnable {

    public String[] picExt = new String[]{ ".jpg", ".jpeg", ".png", ".svg" };

    public static boolean ifLinkIsDirect(String s, String[] extn) {
        return Arrays.stream(extn).anyMatch(s.split(",")[0]::endsWith);
    }
    
    @Override
    public void run() {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        String filePath = prop.getValue(Settings.PATH_TO_FILE);
        String[] radioButton = prop.getValue(Settings.RADIO_BUTTON).split(",");
        boolean keepSameImage = prop.getBoolean(Settings.KEEP_SAME_IMAGE);
        String image = null;
        for (int i = 0; i < radioButton.length; i++) {
            String type = radioButton[i];
            if (filePath == null || filePath.isEmpty()) {
                NotificationCenter.notice("Path to file with URLs isn't set");
                return;
            }
            File file = new File(filePath);
            if (!file.exists()) {
                NotificationCenter.notice("Path to file with URLs isn't set");
                return;
            }
            if (i == 0 || !keepSameImage) {
                image = ImagesHandlerSingleton.instance.getRandomImage(filePath);
            }
            if (image == null) {
                NotificationCenter.notice("Image link wasn't found");
                return;
            }
            if (!ifLinkIsDirect(image, picExt)) {
                NotificationCenter.notice(
                        "Picture link must refer direct to an image, please pay attention to this URL: " + image
                );
            }
            String lastImage = prop.getValue(type);
            if (lastImage != null && lastImage.contains(",")) {
                prop.setValue(type, image + lastImage.substring(lastImage.indexOf(",")));
            } else {
                prop.setValue(type, image);
            }
        }
    }
    
}
