package com.devadath.factaday.twitter.services;

/*
 * @author Devadath Prabhu
 * @created Jul 16, 2021
 */

import com.devadath.factaday.fact.FactFetcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

@Service
public class TweetService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FactFetcher factFetcher;

    public void publishFactOfTheDay() throws Exception{
        System.out.println(">> publishTweet()");
        try {
            String factOfTheDay = factFetcher.getFactOfTheDay();
            if(StringUtils.isNotBlank(factOfTheDay)){
                Twitter twitter = TwitterFactory.getSingleton();
                Status status = twitter.updateStatus(factOfTheDay);
                System.out.println("Successfully published the tweet [" + status.getText() + "].");
            } else {
                throw new Exception("Could not fetch the fact");
            }
        } catch (Exception e){
            String errMsg = "Failed to publish tweet: " + e.getMessage();
            System.out.println(errMsg);
            throw new Exception(errMsg);
        }
    }
}
