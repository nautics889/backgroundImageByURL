package com.notime.intellijPlugin.backgroundImageByURL;

import org.apache.commons.collections.CollectionUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: He Lili
 * Date:   2018/10/22
 */
public enum ImagesHandlerSingleton {
    
    /**
     * 实例
     */
    instance;

    private List<String> randomImageList = null;
    
    private String lastFilePath = null;
    
    public String getRandomImage(String filePath) {
        // 文件夹改动或者完成了一次循环
        if (!filePath.equals(lastFilePath) || CollectionUtils.isEmpty(randomImageList)) {
            randomImageList = this.getRandomImageList(filePath);
        }
        lastFilePath = filePath;
        return CollectionUtils.isEmpty(randomImageList) ? null : randomImageList.remove(0);
    }
    
    public void resetRandomImageList() {
        randomImageList = lastFilePath == null ? null : this.getRandomImageList(lastFilePath);
    }
    
    private List<String> getRandomImageList(String filePath) {
        if (filePath.isEmpty()) {
            return null;
        }
        List<String> images = new ArrayList<>();
        collectImages(images, filePath);
        Collections.shuffle(images);
        return images;
    }
    
    private void collectImages(List<String> images, String filePath) {
        File file = new File(filePath);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                images.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
