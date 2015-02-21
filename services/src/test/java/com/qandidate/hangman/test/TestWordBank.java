package com.qandidate.hangman.test;


import static org.junit.Assert.*;

import java.util.HashMap;

import org.junit.Test;

import com.qandidate.hangman.core.WordBank;

public class TestWordBank {

	@Test
	public void testGetWord() {
		WordBank w = new WordBank() {
			@Override
			public boolean loadWords() {
				return false;
			}
		};
		HashMap<Integer, String> words = new HashMap<Integer, String>();
		words.put(1, "aa");
		w.setWords(words);

		String word = w.getWord();
		assertFalse("Words havent been loaded.",".".equalsIgnoreCase(word));
	}

}
