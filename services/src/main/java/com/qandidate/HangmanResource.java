package com.qandidate;

import java.util.HashMap;
import java.util.logging.Level;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.qandidate.hangman.core.Hangman;
import com.qandidate.hangman.service.HangmanService;
import com.qandidate.hangman.service.HangmanServiceImpl;

@Path("/hangman")
public class HangmanResource {
	
	private static final Logger logger = org.apache.log4j.Logger.getLogger(HangmanResource.class);
	private HangmanService service = HangmanServiceImpl.getInstance();
	
    @GET
    @Path("/games")
    public String getOverview() {
    	JSONObject json = new JSONObject();
    	JSONArray array =  new JSONArray();
    	HashMap<Integer, Hangman> games = service.getOverview();
    	for (Integer key : games.keySet()){
    		Hangman hangman = games.get(key);
    		try {
    			JSONObject arrayObject = new JSONObject();
    			arrayObject.put("id", key);
    			arrayObject.put("word", (hangman.getStatus() == -1) ? hangman.getRepresentationOfWordWithGuesses() : hangman.getWord());
    			arrayObject.put("status", hangman.getDescriptiveStatus());
    			array.put(arrayObject);
			} catch (Exception e) {
				logger.error("Exception in getOverview of HangmanResource", e);
			}
    	}
    	try {
			json.put("games", array);
		} catch (JSONException e) {
			logger.error( "Exception in getOverview of HangmanResource", e);
			
		}
        return json.toString();
    }
    
    @GET
    @Path("/games/{id}")
    public String getProgress(@PathParam("id") String id) {
    	Hangman hangman = service.getGame(Integer.parseInt(id));
    	JSONObject json = new JSONObject();
    	try {
			json.put("word", hangman.getRepresentationOfWordWithGuesses());
			json.put("nooftries", hangman.getNoOfAttemptsLeft());
			json.put("status", hangman.getStatus());
			json.put("statusdescription", hangman.getDescriptiveStatus());
			if(hangman.getNoOfAttemptsLeft() == 0 && hangman.getStatus() == 0){
				json.put("originalword", hangman.getWord());
			}
		} catch (JSONException e) {
			logger.error( "Exception in getProgress of HangmanResource", e);
		}
        return json.toString();
    }
    
    
    @POST
    @Path("/games/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public Response postGuessLetter(@PathParam("id")String id, String inputLetter) {
    	Hangman hangman = service.getGame(Integer.parseInt(id));
    	JSONObject json = new JSONObject();
    	try {
			if(hangman.validLengthOfInput(inputLetter)){
				if(hangman.isInputAnAlphabet(inputLetter)){
					if(hangman.isLetterAlreadyUsed(inputLetter)){
						boolean isMatch = hangman.fillInMatchingLetter(inputLetter);
						hangman.numberOfAttemptsLeft(isMatch);
						hangman.setStatus(hangman.isWordGuessed());
					} else {
						json.put("errorMessage",new StringBuilder("You have already used the letter : ").append(inputLetter).toString());
					}	
				} else {
					json.put("errorMessage",new StringBuilder("The entered letter ").append(inputLetter).append(" is not an alphabet!!").toString());
				}	
			} else {
				json.put("errorMessage",new StringBuilder("The input should only be a single character").toString());
			}
		} catch (Exception e) {
			try {
				json.put("errorMessage",new StringBuilder("Oops!!! Something wrong happened during the game!!").toString());
			} catch (JSONException e1) {
				logger.error( "Exception in postGuessLetter of HangmanResource", e);
			}
		}
    	if(!json.has("errorMessage")){
    		try {
				json.put("errorMessage","");
			} catch (JSONException e) {
				logger.error( "Exception in postGuessLetter of HangmanResource", e);
				return Response.status(404).entity(json.toString()).build();
			}
    	}
    	return Response.status(200).entity(json.toString()).build();
	}
	
	@POST
    @Path("/games")
    public Response startNewGame() {
		try {
			int id = service.startNewGame();
			return Response.status(200).entity(id).build();
		} catch (Exception e) {
			logger.error("Exception in startNewGame of HangmanResource", e);
			return Response.status(404).entity(0).build();
		}
    }
    
    
}
