package com.notime.intellijPlugin.backgroundImageByURL.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.notime.intellijPlugin.backgroundImageByURL.BackgroundService;
import com.notime.intellijPlugin.backgroundImageByURL.ui.Settings;
import org.intellij.images.editor.actions.SetBackgroundImageAction;

/**
 * Author: He Lili
 * Date:   2018/10/24
 */
public class SetBackgroundImage extends SetBackgroundImageAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        BackgroundService.stop();
        super.actionPerformed(e);
        PropertiesComponent prop = PropertiesComponent.getInstance();
        if (prop.getBoolean(Settings.AUTO_CHANGE, false)) {
            BackgroundService.start();
        }
    }

}
