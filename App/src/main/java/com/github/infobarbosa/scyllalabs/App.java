package com.github.infobarbosa.scyllalabs;

import java.util.Date;
import java.util.List;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import twitter4j.Status;
import twitter4j.TwitterException;

public class App {
    static Cluster cluster = Cluster.builder().addContactPoints("localhost").build();
    static Session session = cluster.connect();

    public static void main(String[] args) {
        App app = new App();
        app.createKeyspace();
        app.createTable();

        ColetorTwitter coletor = new ColetorTwitter();
        List<Status> tweets;
        while(true){
            try {
                tweets = coletor.getTweets();
                app.persisteTweets(tweets);

                for(Status tweet: tweets)
                    app.recuperaTweet(tweet.getId());

            } catch (TwitterException e) {
                e.printStackTrace();
            }

            //põe pra dormir um pouco
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                //Não precisa tratar
            }
        }
    }

    /**
     * Cria um keyspace para utilizarmos no nosso lab.
    */
    public void createKeyspace(){
        try {
            session.execute("CREATE KEYSPACE twitter WITH replication = {'class': 'SimpleStrategy', 'replication_factor': '1'}");            
        } catch (Exception e) {
            System.out.println("provavelmente o keyspace já existe.");
            e.printStackTrace();
        }
        System.out.println( "Keyspace criada!" );
    }

    /**
     * Elimina o keyspace quando não é mais necessário.
    */
    public void dropKeyspace(){
        session.execute("DROP KEYSPACE twitter");
        System.out.println("Keyspace eliminada");
    }

    /**
     * Fechamento da conexão com o Scylla
    */
    public void closeConnection(){
        session.close();
        cluster.close();
    }

    /**
     * Criação da tabela onde vamos persistir os tweets.
    */
    public void createTable(){
        try {
            session.execute("CREATE TABLE twitter.tweets(id BIGINT PRIMARY KEY, status_text TEXT, created_at TIMESTAMP)");            
        } catch (Exception e) {
            System.out.println("provavelmente a tabela já existe.");
            e.printStackTrace();
        }
        System.out.println("Tabela tweets criada!");
    }

    /**
     * Persistência efetiva dos tweets no Scylla.
    */
    public void persisteTweets(List<Status> tweets){
        PreparedStatement preparedStatement = session.prepare(
            "insert into twitter.tweets (id, status_text, created_at) values (?, ?, ?)");
        
        for(Status tweet: tweets){
            session.execute(preparedStatement.bind(tweet.getId(), tweet.getText(), tweet.getCreatedAt()));
            System.out.println("tweet " + tweet.getId() + " persistido com sucesso!");
        }
    }

    /**
     * Recupera um tweet baseado no seu ID
    */
    public void recuperaTweet(Long id){
        PreparedStatement preparedStatement = session.prepare("select status_text, created_at from twitter.tweets where id = ?");
        ResultSet rs = session.execute(preparedStatement.bind(id));
        Row row = rs.one();
        String statusText = row.getString(0);
        Date createdAt = row.getTimestamp(1);

        System.out.println("Tweet recuperado >> " +  id + " >>>> " + " - " + createdAt + ": " + statusText);
    }
}
