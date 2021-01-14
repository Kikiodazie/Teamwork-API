package com.odazie.teamworkapi.serviceTests;

import com.cloudinary.Cloudinary;
import com.odazie.teamworkapi.business.service.CloudinaryGifService;
import com.odazie.teamworkapi.data.entity.Gif;
import com.odazie.teamworkapi.data.repository.GifRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;

class CloudinaryGifServiceShould {

    private GifRepository gifRepository;
    private CloudinaryGifService gifService;
    private Cloudinary cloudinary;

    @BeforeEach
    void setUp(){
        gifRepository = mock(GifRepository.class);
        gifService = new CloudinaryGifService(cloudinary, gifRepository);
    }

}
