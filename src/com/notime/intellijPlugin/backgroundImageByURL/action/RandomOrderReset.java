package com.notime.intellijPlugin.backgroundImageByURL.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.notime.intellijPlugin.backgroundImageByURL.BackgroundService;
import com.notime.intellijPlugin.backgroundImageByURL.ImagesHandlerSingleton;
import com.notime.intellijPlugin.backgroundImageByURL.RandomBackgroundTask;
import com.notime.intellijPlugin.backgroundImageByURL.ui.Settings;

public class RandomOrderReset extends AnAction {
    
    @Override
    public void actionPerformed(AnActionEvent e) {
        ImagesHandlerSingleton.instance.resetRandomImageList();
        PropertiesComponent prop = PropertiesComponent.getInstance();
        if (prop.getBoolean(Settings.AUTO_CHANGE, false)) {
            BackgroundService.restart();
        } else {
            new RandomBackgroundTask().run();
        }
    }
}
