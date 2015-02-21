package com.qandidate.hangman.test;

import static org.junit.Assert.assertTrue;

import java.util.HashMap;

import org.junit.Test;

import com.qandidate.hangman.core.Hangman;
import com.qandidate.hangman.service.HangmanService;
import com.qandidate.hangman.service.HangmanServiceImpl;

public class TestHangmanService {
	
	HangmanService service = HangmanServiceImpl.getInstance();
	
	@Test
	public void testStartNewGame() {
		int id = service.startNewGame();
		assertTrue("Id cannot be negative or equal to zero", id > 0);
	}
	
	@Test
	public void testGetGame(){
		int id = service.startNewGame();
		Hangman game = service.getGame(id);
		assertTrue("Game can never be null", game != null);
	}
	
	@Test
	public void testGetOverview(){
		HashMap<Integer,Hangman> games = service.getOverview();
		assertTrue("Games can never be null", games != null);
	}

}
