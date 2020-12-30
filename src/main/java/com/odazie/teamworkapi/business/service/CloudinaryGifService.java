package com.odazie.teamworkapi.business.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import com.odazie.teamworkapi.data.repository.GifRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class CloudinaryGifService {

    private final Cloudinary cloudinaryConfig;
    private final GifRepository gifRepository;

    public CloudinaryGifService(Cloudinary cloudinaryConfig, GifRepository gifRepository) {
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

    public LinkedHashMap<String, Object> modifyJsonResponse(String requestType, String imageUrl){
        LinkedHashMap<String, Object> jsonResponse = new LinkedHashMap<>();
        Gif gif = getGifRepository().findGifByImageUrl(imageUrl);
        if(requestType.equals("create")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("gifId", gif.getGifId().toString());
            data.put("message","GIF image successfully posted");
            data.put("createdOn", gif.getCreatedOn().toString());
            data.put("title", gif.getTitle());
            data.put("imageUrl", imageUrl);

            jsonResponse.put("data", data);
        }

        if(requestType.equals("delete")){
            jsonResponse.put("status", "success");
            LinkedHashMap<String, String > data = new LinkedHashMap<>();
            data.put("message","Gif post successfully deleted");
            jsonResponse.put("data", data);
        }


        //look at the else condition agian if need be. for better error handling.


        return jsonResponse;
    }

    public Gif findGifByIdAndUser(Long id, User currentUser){
        return getGifRepository().findGifByGifIdAndUser(id, currentUser);
    }

    public void deleteGif(Gif gif, User currentUser){
        currentUser.deleteGif(gif);
        getGifRepository().delete(gif);
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
