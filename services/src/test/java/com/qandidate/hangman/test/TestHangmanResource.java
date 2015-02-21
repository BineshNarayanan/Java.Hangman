package com.qandidate.hangman.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertEquals;
import java.net.URI;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.test.JerseyTest;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.qandidate.hangman.service.HangmanService;
import com.qandidate.hangman.service.HangmanServiceImpl;

public class TestHangmanResource extends JerseyTest {
	
	@Override
    protected Application configure() {
        return new MyApplication();
    }

    @Override
    protected URI getBaseUri() {
        return UriBuilder.fromUri(super.getBaseUri()).path("services").build();
    }

    @Test
    public void testGetOverview() {
        String responseString = target().path(App.PATH_GAMES).request().get(String.class);
		try {
			JSONObject json = null;
			json = new JSONObject(responseString);
			assertTrue("Games not present in the response!!", json.has("games"));
		} catch (JSONException e) {
			fail("Invalid JSON Response");
		}
    }
    
    @Test
    public void testGetProgress() {
    	HangmanService service = HangmanServiceImpl.getInstance();
    	int id = service.startNewGame();
        String s = target().path(App.PATH_GAMES+id).request().get(String.class);
		try {
			JSONObject json = null;
			json = new JSONObject(s);
			assertTrue("Game Status not present in the response!!", json.has("status"));
			assertTrue("Word representation not present in the response!!", json.has("word"));
			assertTrue("Number Of Tries not present in the response!!", json.has("nooftries"));
			assertTrue("Descriptive Status not present in the response!!", json.has("statusdescription"));
		} catch (JSONException e) {
			fail("JSON Exception occured in testGetProgress");
		}
    }
    
    @Test
    public void testStartNewGame() {
        Response response = target().path(App.PATH_GAMES).request().post(Entity.json("{}"));
        assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testPostGuessLetter() {
    	HangmanService service = HangmanServiceImpl.getInstance();
    	int id = service.startNewGame();
        Response response = target().path(App.PATH_GAMES+id).request().post(Entity.json("a"));
        assertEquals(200, response.getStatus());
    }

}
