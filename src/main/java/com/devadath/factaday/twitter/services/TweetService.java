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

/**
 * API KEY (consumer): quWkEHisMPsoTMJBEp51FrlL9
 * API Secret Key (consumer): FudyK9WyJdRnPeb95wFIxTYrhZvBppHYaCoSGPeG9aiKGr1FXY
 * Bearer token: AAAAAAAAAAAAAAAAAAAAAMAHRwEAAAAA1od8oFd%2BcZJdlV%2FPwFPVAsqUiGw%3D5hebCRPLJle99IZ8GFflUm4a8ufcMHbXdcUXtwa5laTMWVOQJL
 * Access token: 79719583-yWl1PcN2V23kk2MjzvztycGC5YHRX7Ir9AARnrmTk
 * Access token secret: 3PKjv8h0xvy1MBqNRThKFMxqkuyT1oIhEy606E7lB93Q0
 */

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
