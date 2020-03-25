package com.notime.intellijPlugin.backgroundImagePlus;

import org.apache.commons.collections.CollectionUtils;

import javax.activation.MimetypesFileTypeMap;
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
    
    private MimetypesFileTypeMap typeMap = new MimetypesFileTypeMap();
    
    private List<String> randomImageList = null;
    
    private String lastFolder = null;
    
    public String getRandomImage(String filePath) {
        // 文件夹改动或者完成了一次循环
        if (!filePath.equals(lastFolder) || CollectionUtils.isEmpty(randomImageList)) {
            randomImageList = this.getRandomImageList(filePath);
        }
        lastFolder = filePath;
//        while (CollectionUtils.isNotEmpty(randomImageList) && !isImage(new File(randomImageList.get(0)))) {
//            randomImageList.remove(0);
//        }
        return CollectionUtils.isEmpty(randomImageList) ? null : randomImageList.remove(0);
    }
    
    public void resetRandomImageList() {
        randomImageList = lastFolder == null ? null : this.getRandomImageList(lastFolder);
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
        NotificationCenter.notice("Everything is OK! " + file.toPath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                images.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
//    private boolean isImage(File file) {
//        String[] parts = typeMap.getContentType(file).split("/");
//        return parts.length != 0 && "image".equals(parts[0]);
//    }
    
}
