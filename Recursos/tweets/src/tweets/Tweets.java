/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.io.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 *
 * @author Aaron
 */
public class Tweets {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {

        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("KdVOb7h8mqcjWIfkXXED2G6sJ")
                .setOAuthConsumerSecret("EXImfgfGCYIbWZIOSEeYVvaDE5oxwJZY7UIuwUusbyRWf2ds7l")
                .setOAuthAccessToken("785481406654722049-aRARaHgZujPZIdpYla4mVZIMMlvzJRb")
                .setOAuthAccessTokenSecret("YWWQwbFw8K7rxsnivwpBRZVoQlUhMYy7gOs7KUWOb8Rvy");
        Twitter twitter = new TwitterFactory(cb.build()).getInstance();
        twitter.getUserTimeline();
        List<Status> statuses = twitter.getHomeTimeline();
        Query query = new Query(" #3de3");
        int numberOfTweets = 10;
        long lastID = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();
        while (tweets.size() < numberOfTweets) {
            if (numberOfTweets - tweets.size() > 255) {
                query.setCount(255);
            } else {
                query.setCount(numberOfTweets - tweets.size());
            }
            try {
                QueryResult result = twitter.search(query);
                tweets.addAll(result.getTweets());
                System.out.println("Gathered " + tweets.size() + " tweets" + "\n");
                for (Status t : tweets) {
                    if (t.getId() < lastID) {
                        lastID = t.getId();
                    }
                    System.out.println("Gathered " + tweets.size() + " tweets" + "\n");

                }

            } catch (TwitterException te) {
                System.out.println("Couldn't connect: " + te);
            };
            query.setMaxId(lastID - 1);
        }

        // guardamos los datos
        String nombre = "Datos_3de3_prueba.txt";
        File f = new File(nombre);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);
  
        System.out.println("identificador\tUsuario\ttweet\n");
        for (int i = 0; i < tweets.size(); i++) {
            Status t = (Status) tweets.get(i);

            GeoLocation loc = t.getGeoLocation();
            String user = t.getUser().getScreenName();
            String msg = t.getText();
            //if (loc!=null) {
            //Double lat = t.getGeoLocation().getLatitude();
            //Double lon = t.getGeoLocation().getLongitude();*/
            System.out.println("id: " + i + " | " + user + "  | " + msg + "\n");
            int id=i+1;
            bw.append(id + "|" + user + " |" + msg);
            bw.newLine();

        }
        bw.close();
        //else 
        //System.out.println(i + " USER: " + user + " wrote: " + msg+"\n");
    }
}
