package com.devadath.factaday.twitter.controller;

/*
 * @author Devadath Prabhu
 * @created Jul 16, 2021
 */

import com.devadath.factaday.twitter.services.TweetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TweetController {

    @Autowired
    private TweetService tweetService;

    @RequestMapping(path = "/tweet-a-fact", method = RequestMethod.POST)
    @Scheduled(cron = "0 45 10 * * ?", zone = "Asia/Kolkata")
    public ResponseEntity publishFact() {
        System.out.println(">> publishFact()");
        try {
            tweetService.publishFactOfTheDay();
            return ResponseEntity.ok(HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
