package com.github.infobarbosa.scyllalabs;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import twitter4j.Status;
import twitter4j.TwitterException;

@RunWith(JUnit4.class)
public class AppTest {
    App app = null;
    ColetorTwitter coletor = null;

    @Before
    public void init() {
        app = new App();
        app.createKeyspace();
        app.createTable();

        coletor = new ColetorTwitter();
    }

    @Test
    public void deveObterTweetsEPersistirNaTabela() {
        System.out.println("Obtendo tweets...");
        try {
            List<Status> tweets = coletor.getTweets();
            app.persisteTweets(tweets);
        } catch (TwitterException e) {
            e.printStackTrace();
            assertTrue(false);
        }
        assertTrue(true);
    }

    @After
    public void finalize(){
        System.out.println("fechando sessao...");
        app.dropKeyspace();
        System.out.println("sessao fechada! fechando conexao...");
        app.closeConnection();
        System.out.println("conexao fechada!");
    }
}
