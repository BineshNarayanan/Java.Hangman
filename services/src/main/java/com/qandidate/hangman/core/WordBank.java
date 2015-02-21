package com.qandidate.hangman.core;

import java.util.HashMap;
import java.util.Random;

import org.apache.log4j.Logger;

/**
 * Class which loads all the words for the game.
 * @author Binesh Narayanan
 *
 */
public abstract class WordBank {
	
	private static final Logger logger = Logger.getLogger(WordBank.class);
	
	private HashMap<Integer, String> words = new HashMap<Integer, String>();
	
	/**
	 * The implementer of the method will decide the strategy of loading words.
	 */
	public abstract boolean loadWords();
	
	public String getWord(){
		logger.info("Entered getWord of WordBank class");
		Random rn = new Random();
		int MAX_NUMBER = words.size();
		int MIN_NUMBER = 1;
		int range = MAX_NUMBER - MIN_NUMBER + 1;
		String word = null;
		if(range > 0){
			int randomNum =  rn.nextInt(range) + 1;
			
			if(randomNum > 0){
				word = words.get(randomNum);	
			}
			logger.info("Leaving getWord of WordBank with word" + word);
		}
		return (null == word || "".equals(word)) ? "." : word; 
	}

	public HashMap<Integer, String> getWords() {
		return words;
	}

	public void setWords(HashMap<Integer, String> words) {
		this.words = words;
	}

	

}
