package com.qandidate.hangman.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.json.JSONObject;
import org.junit.Test;

import com.qandidate.hangman.core.Hangman;

public class TestHangman {
	
	Hangman hangman = new Hangman(11, "testing");

	@Test
	public void checkIfInputIsAlphabetBetweenAtoZ(){
		String letter = "A";
		boolean isAlpha = hangman.isInputAnAlphabet(letter);
		assertTrue("Input is not an alphabet", isAlpha);
	}
	
	@Test
	public void checkIfLengthOfInputIsOne(){
		String letter = "A";
		boolean isValid = hangman.validLengthOfInput(letter);
		assertTrue("Input is more than one character", isValid);
		
	}
	
	@Test
	public void testMatchletterInWord(){
		boolean isMatch = hangman.matchletterInWord("t", 0);
		assertTrue("Input letter is not present at index specified",isMatch);
	}

	@Test
	public void testFillInMatchingLetter() {
		Hangman hangman = new Hangman(11, null);
		try {
			hangman.fillInMatchingLetter("a");
		} catch (Exception e) {
			fail("Error occured fillInMatchingLetter");
		}
	}
	
	@Test
	public void testMaxNoOfAttemptsDoesNotGoNegative(){
		Hangman hangman = new Hangman(1, null);
		int noOfAttempts = hangman.numberOfAttemptsLeft(false);
		noOfAttempts = hangman.numberOfAttemptsLeft(false);
		assertTrue("No Of Attempts went Negative", noOfAttempts >= 0);
	}
	
	@Test
	public void testGetStringForBlankWord(){
		Hangman hangman = new Hangman(1, null);
		String blankWord = hangman.getStringForBlankWord();
		assertFalse("Blank String is null",blankWord == null);
	}
	
	@Test
	public void testisLetterAlreadyUsed(){
		try {
			hangman.fillInMatchingLetter("t");
			boolean isUsed = hangman.isLetterAlreadyUsed("t");
			StringBuilder strBuilder = new StringBuilder(hangman.getUsedLetters());
			boolean isPresent = (strBuilder.indexOf("t") != -1);
			assertTrue("The letter AlreadyUsed method is not working correctly.",isUsed == isPresent);
		} catch (Exception e) {
			fail("Exception in testisLetterAlreadyUsed");
		}
		
	}
	
	@Test
	public void testisLetterAlreadyUsedCaseInsensitive(){
		try {
			hangman.fillInMatchingLetter("t");
			boolean isUsed = hangman.isLetterAlreadyUsed("T");
			StringBuilder strBuilder = new StringBuilder(hangman.getUsedLetters());
			boolean isPresent = (strBuilder.indexOf("t") != -1);
			assertTrue("The letter AlreadyUsed method is case sensitive.",isUsed == isPresent);
		} catch (Exception e) {
			fail("Exception in testisLetterAlreadyUsedCaseInsensitive");
		}
		
	}
	
	@Test
	public void testIsWordGuessed(){
		Hangman hangman = new Hangman(11,"aa");
		try {
			hangman.fillInMatchingLetter("a");
			String wordGuessed = hangman.getRepresentationOfWordWithGuesses();
			boolean isWordGuessed = hangman.isWordGuessed();
			assertTrue("IsWordGuessed Method testing fails.","aa".equalsIgnoreCase(wordGuessed) == isWordGuessed);
		} catch (Exception e) {
			
		}
	}
	
	@Test
	public void testGetJSONString(){
		String jsonString = hangman.getJSONString();
		try {
			new JSONObject(jsonString);
		} catch (Exception e) {
			fail("JSON String creation is not working");
		}
	}
	
	@Test
	public void testGetRepresentationOfWordWithGuesses(){
		String guessedWord = hangman.getRepresentationOfWordWithGuesses();
		assertTrue("Representation of Word with Guesses Cannot Be Null",guessedWord != null);
	}
	
	@Test
	public void testGetUsedLetters(){
		String usedLetters = hangman.getUsedLetters();
		assertTrue("Used Letters cannot be NULL at any stage", usedLetters != null);
	}

}
