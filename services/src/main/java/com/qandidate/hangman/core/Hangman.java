package com.qandidate.hangman.core;


import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This is the core class of the application.
 * @author Binesh Narayanan
 *
 */
public class Hangman {
	
	private static final Logger logger = Logger.getLogger(Hangman.class);
	
	private int maxNoOfAttempts = 0;
	private String word = null;
	private final ArrayList<Integer> indicesLeft = new ArrayList<Integer>();
	private int status = -1;
	private StringBuilder toBeGuessedStringBuilder = null;
	private final StringBuilder usedLetters = new StringBuilder();
	
	/**
	 * Parameterized Constructor
	 * @param maxNoOfAttempts
	 * @param word
	 */
	public Hangman(int maxNoOfAttempts, String word) {
		this.maxNoOfAttempts = maxNoOfAttempts;
		this.word = word;
		int index = 0;
		if(null != word){
			while(index < word.length()){
				indicesLeft.add(index);
				index++;
			}	
		}
		toBeGuessedStringBuilder = new StringBuilder(getStringForBlankWord());
		logger.info(new StringBuilder("Hangman game with word(").append(word).append(") to be guessed instantiated successfully").toString());
	}
	
	/**
	 * Checks if the input is an alphabet(aA-zZ)
	 * @param letter
	 * @return boolean
	 */
	public boolean isInputAnAlphabet(String letter){
		logger.info("Entered isInputAnAlphabet with input : "+letter);
		boolean isInputAlpha = false;
		isInputAlpha = letter.matches("^[a-zA-Z]+$");
		logger.info("Leaving isInputAnAlphabet with value "+ isInputAlpha);
		return isInputAlpha;
	}
	
	/**
	 * Check if the input size is only of length = 1.
	 * @param letter
	 * @return boolean
	 */
	public boolean validLengthOfInput(String letter){
		logger.info("Entered validLengthOfInput with input "+ letter);
		boolean isInputValid = false;
		if(null != letter && letter.length() == 1){
			isInputValid = true;
		}
		logger.info("Leaving validLengthOfInput with value "+ isInputValid);
		return isInputValid;
	}
	
	/**
	 * Checks if the letter is present at the given index of the string.
	 * Returns true if present else false.
	 * @param letter
	 * @param index
	 * @return boolean
	 */
	public boolean matchletterInWord(String letter,int index){
		logger.info(new StringBuilder("Entered matchLetterInWord(").append(letter).append(",").append(index).append(")").toString());
		boolean isMatch = false;
		if(null != word) {
			isMatch = String.valueOf(word.charAt(index)).equalsIgnoreCase(letter);	
		}
		logger.info(new StringBuilder("Leaving matchLetterInWord(").append(letter).append(",").append(index).append(")").append(" with "+isMatch).toString());
		return isMatch;
	}
	
	/**
	 * Fill in the letters in the blank string if the letter is one from the word to be guessed.
	 * Returns true if the letter is present else false. This method has been optimized to run <br>
	 * only to match the spaces which are blank.
	 * 
	 * From a four letter if three is guessed, the loop will run only for (4-3)= 1 time instead of four times.
	 * 
	 * @param matchingLetter
	 * @return boolean
	 * @throws Exception
	 */
	public boolean fillInMatchingLetter(String matchingLetter) throws Exception {
		logger.info(new StringBuilder("Entered fillInMatchingLetter(").append(matchingLetter).append(")").toString());
		matchingLetter = matchingLetter.toLowerCase();
		boolean isMatch = false;
		ArrayList<Integer> copyOfIndices = (ArrayList<Integer>)indicesLeft.clone();
		for(int i : copyOfIndices){
			try {
				if(matchletterInWord(matchingLetter.toLowerCase(), i)){
					isMatch = true;
					indicesLeft.remove(new Integer(i));
					toBeGuessedStringBuilder.setCharAt(i, matchingLetter.charAt(0));	
				}
			} catch (Exception e) {
				isMatch = false;
				throw e;
			}	
		}
		logger.info(new StringBuilder("Leaving fillInMatchingLetter(").append(matchingLetter).append(") with values ").append(isMatch).toString());
		return isMatch;
	}
	
	/**
	 * Default status is busy. Used to set status on the basis of the word guessed.
	 * @param isWordGuessed
	 */
	public void setStatus(boolean isWordGuessed){
		logger.info(new StringBuilder("Entered setStatus(").append(isWordGuessed).append(")").toString());
		if(isWordGuessed){
			status = 1;
		} else if(!isWordGuessed && maxNoOfAttempts == 0) {
			status = 0;
		}
		logger.info(new StringBuilder("Leaving setStatus(").append(isWordGuessed).append(") with value : " + status).toString());
	}
	
	/**
	 * Checks if the input letter has already been used in the game or not.<br>
	 * Returns false if not used else true.
	 * @param letter
	 * @return boolean
	 */
	public boolean isLetterAlreadyUsed(String letter){
		logger.info(new StringBuilder("Entered isLetterAlreadyUsed(").append(letter).append(")").toString());
		boolean isUsed = true;
		isUsed = (usedLetters.indexOf(letter.toLowerCase()) == -1);
		if(isUsed){
			usedLetters.append(letter.toLowerCase()).append(",");
		}
		logger.info(new StringBuilder("Leaving isLetterAlreadyUsed(").append(letter).append(") with values : "+isUsed).toString());
		return isUsed;
	}
	
	/**
	 * Get number of attempts left on the basis of status of match.
	 * @param boolean isMatch
	 * @return int
	 */
	public int numberOfAttemptsLeft(boolean isMatch){
		logger.info(new StringBuilder("Entered numberOfAttemptsLeft(").append(isMatch).append(")").toString());
		logger.info(new StringBuilder("maxNoOfAttempts :: ").append(maxNoOfAttempts).toString());
		maxNoOfAttempts = (!isMatch) ? (maxNoOfAttempts - 1) : maxNoOfAttempts;
		logger.info(new StringBuilder("Leaving numberOfAttemptsLeft(").append(isMatch).append(") with values : "+maxNoOfAttempts).toString());
		return maxNoOfAttempts = (maxNoOfAttempts < 0) ? 0 : maxNoOfAttempts;
	}
	
	public String getStringForBlankWord(){
		StringBuilder blankStrBuilder = new StringBuilder();
		int i = 0;
		if(null != word){
			while(i < word.length()){
				blankStrBuilder.append(".");
				i++;
			}	
		}
		logger.info(new StringBuilder("Returned getStringForBlankWord() values : ").append(blankStrBuilder.toString()).toString());
		return blankStrBuilder.toString();
	}
	
	/**
	 * If word is equal returns true else returns false.
	 * @return boolean
	 */
	public boolean isWordGuessed(){
		if(null != toBeGuessedStringBuilder && null != word && maxNoOfAttempts >= 0){
			
			return toBeGuessedStringBuilder.toString().equalsIgnoreCase(word);
		}
		return false;
	}
	
	/**
	 * Returns the number of attempts left.
	 * @return int
	 */
	public int getNoOfAttemptsLeft() {
		return maxNoOfAttempts;
	}
	
	/**
	 * Returns the word which is to be guessed.
	 * @return String
	 */
	public String getWord() {
		return word;
	}
	
	/**
	 * Get JSON String of a game.
	 * @return String
	 */
	public String getJSONString() {
		JSONObject object = new JSONObject();
		try {
			object.put("word", getRepresentationOfWordWithGuesses());
			object.put("tries_left", getNoOfAttemptsLeft());
			object.put("status", getDescriptiveStatus());
		} catch (JSONException e) {
			logger.error("Exception occured during JSON to String conversion",e);
		}
		return object.toString();
	}
	
	/**
	 * Gets the status(-1:Busy,0:Fail,1:Success) of the game
	 * @return int
	 */
	public int getStatus() {
		return status;
	}
	
	/**
	 * Gets the descriptive status(-1:Busy,0:Fail,1:Success) of the game
	 * @return String
	 */
	public String getDescriptiveStatus() {
		if (status < 0) {
			return "Busy";
		} else if (status > 0) {
			return "Success";
		} else {
			return "Fail";
		}
	}
	
	public String getRepresentationOfWordWithGuesses(){
		return (null != toBeGuessedStringBuilder) ? toBeGuessedStringBuilder.toString() : "";
	}
	
	public String getUsedLetters(){
		if(usedLetters.length() > 0){
			usedLetters.setCharAt((usedLetters.length()-1),'.');	
		}
		return usedLetters.toString();
	}

}
