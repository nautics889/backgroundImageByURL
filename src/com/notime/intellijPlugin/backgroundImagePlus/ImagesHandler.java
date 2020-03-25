package com.notime.intellijPlugin.backgroundImagePlus;

import javax.activation.MimetypesFileTypeMap;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Author: Allan de Queiroz
 * Date:   07/05/17
 */
class ImagesHandler {
    
    private MimetypesFileTypeMap typeMap;
    
    ImagesHandler() {
        typeMap = new MimetypesFileTypeMap();
    }
    
    /**
     * @param folder folder to search for images
     * @return random image or null
     */
    String getRandomImage(String folder, String lastImage) {
        if (folder.isEmpty()) {
            return null;
        }
        List<String> images = new ArrayList<>();
        collectImages(images, folder);
        int count = images.size();
        if (count == 0) {
            return null;
        } else if (count == 1) {
            return images.get(0);
        }
        Random randomGenerator = new Random();
        String image;
        do {
            int index = randomGenerator.nextInt(images.size());
            image = images.get(index);
        } while (lastImage != null && lastImage.split(",")[0].equals(image));
        return image;
    }
    
    private void collectImages(List<String> images, String folder) {
        File file = new File(folder);
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                images.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private boolean isImage(File file) {
        String[] parts = typeMap.getContentType(file).split("/");
        return parts.length != 0 && "image".equals(parts[0]);
    }
    
}
