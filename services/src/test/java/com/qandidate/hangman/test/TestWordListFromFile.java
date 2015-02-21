package com.qandidate.hangman.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.qandidate.hangman.core.WordListFromFile;


public class TestWordListFromFile {
	
	private WordListFromFile words = new WordListFromFile();
	
	/**
	 * Expected True - if File exists or False - if it doesnt.
	 */
	@Test
	public void testCheckIfFileExists() {
		assertTrue("File Not Exists.",words.checkIfFileExists());
	}
	
	@Test
	public void checkReadFile(){
		boolean isReadSuccess = words.readFile();
		assertTrue("Read File Failed", isReadSuccess);
	}
	
	@Test
	public void checkLoadWords(){
		boolean isLoadSuccess = words.loadWords();
		assertTrue("Load Word Failed", isLoadSuccess);
	}

}
