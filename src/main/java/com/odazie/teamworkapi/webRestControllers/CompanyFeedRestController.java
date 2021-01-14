package com.odazie.teamworkapi.webRestControllers;

import com.odazie.teamworkapi.business.service.ArticleService;
import com.odazie.teamworkapi.business.service.CompanyFeedService;
import com.odazie.teamworkapi.business.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;

@RestController
@RequestMapping("api/v1")
@ApiOperation(value = "", authorizations = { @Authorization(value="Bearer ") })
@Api(description = "Operations pertaining to FEED")
public class CompanyFeedRestController {

    private final CompanyFeedService companyFeedService;

    public CompanyFeedRestController(CompanyFeedService companyFeedService) {
        this.companyFeedService = companyFeedService;
    }

    @GetMapping("/feed")
    public ResponseEntity<LinkedHashMap<String, Object>> getUserFeed(){

        LinkedHashMap<String, Object> feed = companyFeedService.getArticlesAndGifsByMostRecent();

        return new ResponseEntity<>(feed, HttpStatus.OK);
    }

}
