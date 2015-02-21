package com.qandidate.hangman.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;

import org.apache.log4j.Logger;

/**
 * Extends WordBank.
 * This implementation loads words from file words.english. 
 * @author BNaraya
 *
 */
public class WordListFromFile extends WordBank {
	
	private static final Logger logger = Logger.getLogger(WordListFromFile.class);
	
	public WordListFromFile() {
		loadWords();
	}
	
	@Override
	public boolean loadWords() {
		if(checkIfFileExists()){
			if(getWords().size() == 0) {
				return readFile();
			}
		} else {
			logger.info("File with words is not PRESENT!!");
		}
		return (getWords().size() > 0);
	}
	
	/**
	 * Checks if the dictionary file is present or not.
	 * @return boolean
	 */
	public boolean checkIfFileExists(){
		InputStream inputFile = null;
		try {
			inputFile = this.getClass().getClassLoader().getResourceAsStream("words.english");
			return (null == inputFile) ? false : true;
		} catch (Exception e) {
			logger.error("Exception in reading the file", e);
			return false;
		}
	}
	
	/**
	 * Reads the dictionary file.
	 * Returns true if the read is success.
	 * @return boolean
	 */
	public boolean readFile(){
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("words.english");
		BufferedReader br = null;
		boolean isReadSuccess = false;
		if(null != in) {
			try {
				String word = null;
				br = new BufferedReader(new InputStreamReader(in));
				int number_of_word = 1;
				while ((word = br.readLine()) != null) {
					if(!"".equalsIgnoreCase(word)){
						getWords().put(number_of_word, word);
						number_of_word++;	
					}
				}
				isReadSuccess = true;
	 		} catch (IOException e) {
				logger.error("IO Exception in readFile", e);
				setWords(new HashMap<Integer, String>());
			} finally {
				try {
					if (br != null)br.close();
				} catch (IOException ex) {
					logger.error("IO Exception in readFile while closing the file reference", ex);
					isReadSuccess = false;
					setWords(new HashMap<Integer, String>());
				}
			}
		}
		return isReadSuccess;
	}
}
