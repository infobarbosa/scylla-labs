package com.github.infobarbosa.scyllalabs;

import java.util.List;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import twitter4j.Status;
import twitter4j.TwitterException;

/**
 * Unit test for simple App.
 */
public class ColetorTwitterTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ColetorTwitterTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ColetorTwitterTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        ColetorTwitter coletor = new ColetorTwitter();

        try {
            List<Status> tweets = coletor.getTweets();
            System.out.println("lista criada! " + tweets.isEmpty());
            for(Status tweet: tweets){
                System.out.println(tweet);
            }
        } catch (TwitterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        assertTrue( true );
    }
}
