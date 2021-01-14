package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.service.CloudinaryGifService;
import com.odazie.teamworkapi.business.service.UserService;
import com.odazie.teamworkapi.data.entity.Article;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@RestController
@RequestMapping("api/v1")
@ApiOperation(value = "", authorizations = { @Authorization(value="Bearer ") })
@Api(description = "Operations pertaining to GIFs")
public class GifRestController {


    private final CloudinaryGifService cloudinaryGifService;
    private final UserService userService;

    public GifRestController(CloudinaryGifService cloudinaryGifService, UserService userService) {
        this.cloudinaryGifService = cloudinaryGifService;
        this.userService = userService;
    }


    @PreAuthorize("hasRole('EMPLOYEE')")
    @PostMapping("/gifs")
    public ResponseEntity<LinkedHashMap<String, Object>> uploadGif(@RequestParam("gifFile") MultipartFile gifFile, Authentication authentication, @RequestParam("title") String title) throws IOException {
        User currentUser = userService.findUserByEmail(authentication.getName());
        String url = cloudinaryGifService.uploadFile(gifFile);
        cloudinaryGifService.saveGifToDB(url, title , currentUser);

        // I am Think about proper error handling in the future that's why it takes status
        LinkedHashMap<String, Object> jsonResponse = cloudinaryGifService.modifyJsonResponse("create", url);
        return new ResponseEntity<>(jsonResponse,HttpStatus.CREATED);
    }

    @DeleteMapping("gifs/{gifId}")
    public ResponseEntity<LinkedHashMap<String, Object>> deleteGif(@PathVariable Long gifId, Authentication authentication){
        User currentUser = userService.findUserByEmail(authentication.getName());
        Gif gif = cloudinaryGifService.findGifByIdAndUser(gifId, currentUser);

        if(gif == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        cloudinaryGifService.deleteGif(gif, currentUser);
        LinkedHashMap<String, Object> jsonResponse = cloudinaryGifService.modifyJsonResponse("delete", null);

        return new ResponseEntity<>(jsonResponse, HttpStatus.ACCEPTED);
    }


    @GetMapping("gifs/{gifId}")
    public ResponseEntity<LinkedHashMap<String, Object>> getASpecificGif(@PathVariable Long gifId){
        Gif gif = cloudinaryGifService.findGifById(gifId);

        if(gif == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        LinkedHashMap<String, Object> jsonResponseSpec = cloudinaryGifService.modifyJsonResponse("get", gif.getImageUrl());

        return new ResponseEntity<>(jsonResponseSpec, HttpStatus.OK);
    }






}
