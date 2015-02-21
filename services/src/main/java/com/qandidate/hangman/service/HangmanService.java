package com.qandidate.hangman.service;

import java.util.HashMap;

import com.qandidate.hangman.core.Hangman;

public interface HangmanService {
	
	/**
	 * Starts a new game.
	 * @return
	 */
	public int startNewGame();
	
	/**
	 * Returns a game with the id passed.
	 * @param id
	 * @return
	 */
	public Hangman getGame(int id);
	
	/**
	 * Gets an overview of the entire games.
	 * @return
	 */
	public HashMap<Integer, Hangman> getOverview();
	
}
