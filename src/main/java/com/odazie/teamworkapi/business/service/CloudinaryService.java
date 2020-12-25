package com.odazie.teamworkapi.business.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.GifRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class CloudinaryService {

    private final Cloudinary cloudinaryConfig;
    private final GifRepository gifRepository;

    public CloudinaryService(Cloudinary cloudinaryConfig, GifRepository gifRepository) {
        this.cloudinaryConfig = cloudinaryConfig;
        this.gifRepository = gifRepository;
    }

    public String uploadFile(MultipartFile gif) {
        try {
            File uploadedFile = convertMultiPartToFile(gif);
            Map uploadResult = cloudinaryConfig.uploader().upload(uploadedFile, ObjectUtils.emptyMap());
            boolean isDeleted = uploadedFile.delete();

            if (isDeleted){
                System.out.println("File successfully deleted");
            }else
                System.out.println("File doesn't exist");
            return  uploadResult.get("url").toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public void saveGifToDB(String imageUrl, String title, User currentUser){
        Gif gif = new Gif();

        gif.setImageUrl(imageUrl);
        gif.setTitle(title);
        gif.setUser(currentUser);
        currentUser.addGif(gif);
        getGifRepository().save(gif);
    }

    public GifRepository getGifRepository() {
        return gifRepository;
    }
}
