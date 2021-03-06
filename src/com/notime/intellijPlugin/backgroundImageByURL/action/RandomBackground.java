package com.notime.intellijPlugin.backgroundImageByURL.action;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.notime.intellijPlugin.backgroundImageByURL.BackgroundService;
import com.notime.intellijPlugin.backgroundImageByURL.RandomBackgroundTask;
import com.notime.intellijPlugin.backgroundImageByURL.ui.Settings;

/**
 * Author: Lachlan Krautz
 * Date:   21/07/16
 */
public class RandomBackground extends AnAction {
    
    public RandomBackground() {
        super("Random Background Image");
        PropertiesComponent prop = PropertiesComponent.getInstance();
        if (prop.getBoolean(Settings.AUTO_CHANGE, false)) {
            BackgroundService.start();
        }
    }
    
    @Override
    public void actionPerformed(AnActionEvent evt) {
        PropertiesComponent prop = PropertiesComponent.getInstance();
        if (prop.getBoolean(Settings.AUTO_CHANGE, false)) {
            BackgroundService.restart();
        } else {
            new RandomBackgroundTask().run();
        }
    }
    
}
