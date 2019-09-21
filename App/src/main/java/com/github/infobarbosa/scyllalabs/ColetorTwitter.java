package com.github.infobarbosa.scyllalabs;

import java.util.List;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

class ColetorTwitter{
    private static String CONSUMER_KEY = System.getenv("TWITTER_CONSUMER_KEY");
    private static String CONSUMER_SECRET = System.getenv("TWITTER_CONSUMER_SECRET");
    private static String ACCESS_TOKEN = System.getenv("TWITTER_ACCESS_TOKEN");
    private static String ACCESS_TOKEN_SECRET = System.getenv("TWITTER_ACCESS_TOKEN_SECRET");

    private Twitter getTwitterinstance(){
        System.out.println("consumer key: " + CONSUMER_KEY);
        System.out.println("consumer secret: " + CONSUMER_SECRET);
        System.out.println("access token: " + ACCESS_TOKEN);
        System.out.println("access token secret: " + ACCESS_TOKEN_SECRET);

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
        .setOAuthConsumerKey( CONSUMER_KEY )
        .setOAuthConsumerSecret( CONSUMER_SECRET )
        .setOAuthAccessToken( ACCESS_TOKEN )
        .setOAuthAccessTokenSecret( ACCESS_TOKEN_SECRET );
        TwitterFactory tf = new TwitterFactory(cb.build());
        Twitter twitter = tf.getInstance();

        System.out.println("Instancia Twitter criada!");
        return twitter;
    }

    public List<Status> getTweets() throws TwitterException {
  
        Twitter twitter = getTwitterinstance();
        Query query = new Query("bolsonaro");
        QueryResult result = twitter.search(query);
         
        List<Status> statusList = result.getTweets();
        for(Status status: statusList)
            System.out.println(">>>>>>>>" + status.getId() + " - " + status.getCreatedAt());
        
        return statusList;
    }

    public static void main(String args[]){

        ColetorTwitter coletor = new ColetorTwitter();

        try {
            List<Status> tweets = coletor.getTweets();
            System.out.println("lista criada! " + tweets.isEmpty());
            for(Status tweet: tweets){
                System.out.println(tweet.getText());
            }
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}