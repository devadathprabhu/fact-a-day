package com.devadath.factaday.controller;

/*
 * @author Devadath Prabhu
 * @created Jul 16, 2021
 */

import com.devadath.factaday.services.Tweet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class TweetController {

    @Autowired
    private Tweet tweet;

    @RequestMapping(path="/publish-fact", method = RequestMethod.POST)
    public ResponseEntity publishFact(){
        System.out.println(">> publishFact()");
        tweet.publishTweet();
        return ResponseEntity.ok(HttpStatus.OK);
    }

}
