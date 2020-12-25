package com.odazie.teamworkapi.webRestControllers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.odazie.teamworkapi.business.service.CloudinaryService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@RestController
public class GifRestController {


    private final CloudinaryService cloudinaryService;
    private final UserService userService;

    public GifRestController(CloudinaryService cloudinaryService, UserService userService) {
        this.cloudinaryService = cloudinaryService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/gifs")
    public ResponseEntity<Void> uploadGif(@RequestParam("gifFile") MultipartFile gifFile, Authentication authentication, @RequestParam("title") String title) throws IOException {
        User currentUser = userService.findUserByEmail(authentication.getName());
        String url = cloudinaryService.uploadFile(gifFile);
        cloudinaryService.saveGifToDB(url, title , currentUser);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }






}
