/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.io.*;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import twitter4j.GeoLocation;
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
        
        ConfigurationBuilder configurtacion = new ConfigurationBuilder();
        configurtacion.setDebugEnabled(true)
                .setOAuthConsumerKey("KdVOb7h8mqcjWIfkXXED2G6sJ")
                .setOAuthConsumerSecret("EXImfgfGCYIbWZIOSEeYVvaDE5oxwJZY7UIuwUusbyRWf2ds7l")
                .setOAuthAccessToken("785481406654722049-aRARaHgZujPZIdpYla4mVZIMMlvzJRb")
                .setOAuthAccessTokenSecret("YWWQwbFw8K7rxsnivwpBRZVoQlUhMYy7gOs7KUWOb8Rvy");
        Twitter twitter = new TwitterFactory(configurtacion.build()).getInstance();
        twitter.getUserTimeline();

        String hashtag = "#ElectionNight";
        Query busqueda = new Query(hashtag);
        int numeroTweets = 1500;
        long ultimo = Long.MAX_VALUE;
        ArrayList<Status> tweets = new ArrayList<Status>();

        while (tweets.size() < numeroTweets) {
            if (numeroTweets - tweets.size() > 255) {
                busqueda.setCount(255);
            } else {
                busqueda.setCount(numeroTweets - tweets.size());
            }
            try {
                QueryResult result = twitter.search(busqueda);
                tweets.addAll(result.getTweets());
                System.out.println("Generados " + tweets.size() + " tweets" + "\n");
                for (Status t : tweets) {
                    if (t.getId() < ultimo) {
                        ultimo = t.getId();
                    }
                    System.out.println("Generados " + tweets.size() + " tweets" + "\n");

                }

            } catch (TwitterException excepcion) {
                System.out.println("Sin conexión " + excepcion);
            };
            busqueda.setMaxId(ultimo - 1);
        }

        // guardamos los datos
        java.util.Date fecha = new Date();
        int hora = fecha.getHours();
        int minuto = fecha.getMinutes();
        System.out.println();
        String nombre = "Datos_" + hashtag + " " + hora + " " + minuto + ".txt";
        File f = new File(nombre);
        FileWriter fw = new FileWriter(f);
        BufferedWriter bw = new BufferedWriter(fw);

        System.out.println("identificador\tUsuario\ttweet\n");
        for (int i = 0; i < tweets.size(); i++) {
            Status estadoTweet = (Status) tweets.get(i);

            GeoLocation loc = estadoTweet.getGeoLocation();
            String user = estadoTweet.getUser().getScreenName();
            String msg = estadoTweet.getText();
            Boolean sensitive = estadoTweet.isPossiblySensitive();
            int fav = estadoTweet.getFavoriteCount();
 
            System.out.println("Id: " + i + "| User: " + user +  "| Texto: " + msg + "| SentimientoPositivo: " + sensitive+"$\n");
            int id = i + 1;
            bw.append(msg + " | " + sensitive+";");
            bw.newLine();

        }
        bw.close();
    }
}