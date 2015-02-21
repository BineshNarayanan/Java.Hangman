
package com.qandidate.hangman.test;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import org.apache.log4j.Logger;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.servlet.GrizzlyWebContainerFactory;
import org.glassfish.jersey.server.ServerProperties;
import org.glassfish.jersey.servlet.ServletContainer;

import com.qandidate.HangmanResource;

public class App {
	private static final Logger logger = Logger.getLogger(App.class);
	
    private static final URI BASE_URI = URI.create("http://localhost:8080/services/");
    public static final String PATH_GAMES = "hangman/games/";

    public static void main(String[] args) {
        try {
            Map<String, String> initParams = new HashMap<String, String>();
            initParams.put(ServerProperties.PROVIDER_PACKAGES,HangmanResource.class.getPackage().getName());
            final HttpServer server = GrizzlyWebContainerFactory.create(BASE_URI, ServletContainer.class, initParams);

            System.out.println(String.format("Application started.%nTry out %s%s%nHit enter to stop it...",BASE_URI, PATH_GAMES));
            System.in.read();
            server.shutdownNow();
        } catch (IOException ex) {
        	logger.error("Exception in main method of App class:", ex);
        }
    }
}
