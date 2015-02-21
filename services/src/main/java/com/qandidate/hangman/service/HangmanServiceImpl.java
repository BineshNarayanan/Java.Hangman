package com.qandidate.hangman.service;

import java.util.HashMap;
import java.util.Random;

import javax.inject.Singleton;

import org.apache.log4j.Logger;

import com.qandidate.hangman.core.Hangman;
import com.qandidate.hangman.core.WordBank;
import com.qandidate.hangman.core.WordListFromFile;


public class HangmanServiceImpl implements HangmanService {
	
	private static final Logger logger = Logger.getLogger(HangmanServiceImpl.class);
	
	private static HangmanService instance = new HangmanServiceImpl();
	
	private HangmanServiceImpl() {
		logger.info("HangmanServiceImpl instantiated..");
	}
	
	public static synchronized HangmanService getInstance(){
		return instance;
	}

	private HashMap<Integer,Hangman> games = new HashMap<Integer, Hangman>();
	
	private WordListFromFile wordList = null;
	
	@Override
	public int startNewGame() {
		logger.info("HangmanServiceImpl startNewGame");
		int MAX_NUMBER = Integer.MAX_VALUE;
		int MIN_NUMBER = 1;
		int range = MAX_NUMBER - MIN_NUMBER + 1;
		Random rn = new Random();
		int randomNum =  rn.nextInt(range) + 1;
		if(null == wordList) {
			wordList = new WordListFromFile();
		}
		String word = wordList.getWord();
		Hangman hangman = new Hangman(11, word);
		games.put(randomNum, hangman);
		logger.info("HangmanServiceImpl game started with word "+word);
		return randomNum;
	}
	
	@Override
	public Hangman getGame(int id){
		return games.get(id);
	}
	
	@Override
	public HashMap<Integer,Hangman> getOverview() {
		return games;
	}
}
