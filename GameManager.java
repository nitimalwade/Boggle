package assignment;

import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class GameManager implements BoggleGame {
	
	ArrayList<String> cubes = new ArrayList<String>();
	char[][] board;
	ArrayList<ArrayList<String>> playerLists;
	GameDictionary dictionary = new GameDictionary();
	//Iterator<String> itr = GameDictionary.iterator();
	SearchTactic currentSearchTactic = BoggleGame.SEARCH_DEFAULT;
	int [] scores;
	
	@Override
	public void newGame(int size, int numPlayers, String cubeFile, BoggleDictionary dict) throws IOException {
		
		board = new char[size][size];
		
		//read from cubes.txt into a 2D array
		Scanner scan = new Scanner(new File (cubeFile));
		while (scan.hasNextLine()) {
			cubes.add(scan.nextLine());
		}
		
		//put random pieces on the board - cubes should be empty after this
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {
				int pos = (int) (Math.random() * cubes.size());
				board[i][j] = cubes.get(pos).charAt((int) (Math.random() * 6));
				cubes.remove(pos);
			}
		}	
		
		scores = new int[numPlayers];
		playerLists = new ArrayList<ArrayList<String>>(numPlayers);
		dictionary = (GameDictionary) dict;
	}

	@Override
	public char[][] getBoard() {
		return board;
	}

	@Override
	public int addWord(String word, int player) {
		//1. check if word is on the board - using getAllWords
		//2. if word is valid, need to check if been used before
		//3. if still good, then score it - 1 point for 4 letters, 2 points for 5 etc.
		//add word to player list if valid
		
		boolean valid = false;
		Collection<String> allWords = getAllWords();
		for (int i = 0; i < allWords.size(); i++) {
			 if (allWords.contains(word)) {
				 valid = true;
			 }
			 
		}
		
//		if (valid) {
//			playerList
//		}
		return 0;
	}

	@Override
	public List<Point> getLastAddedWord() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGame(char[][] board) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Collection<String> getAllWords() {
		// search through board using currentSearchTactic 
		
		ArrayList<String> allWords = new ArrayList<String>();
		Trie words = dictionary.getDictionary();
		boolean [][]visited = new boolean[board.length][board[0].length];
		int r = 0; 
		int c = 0;
		String boardWord = "";
		
		if (currentSearchTactic == SearchTactic.SEARCH_BOARD) {
			String word = words.next();
			allWords = searchBoard(words, board, visited, r, c, boardWord, word, allWords, word.charAt(0));
			System.out.println(allWords.size());
			for (int i = 0; i < allWords.size(); i++) {
				System.out.println("j" + allWords.get(i));
			}
		}
		else if (currentSearchTactic == SearchTactic.SEARCH_DICT) {
			allWords = searchDictionary(words, board, visited, r, c, boardWord, allWords);
		}
		
		//return list
		return allWords;
	}

	@Override
	public void setSearchTactic(SearchTactic tactic) {
		currentSearchTactic = tactic;
		
	}

	@Override
	public int[] getScores() {
		return scores;
	}
	
	//gets all words from dictionary and checks if in board
	public ArrayList<String> searchBoard(Trie words, char [][] board, boolean [][] visited, int r, int c, String boardWord, String currentWord, ArrayList<String> allWords, char letter) {
		
		//visited[r][c] = true;
		
		
		if (currentWord.equals(boardWord) && words.hasNext()) {
			System.out.println("sfdhtzs");
			allWords.add(currentWord);
			currentWord = words.next();
			letter = currentWord.charAt(0);
		}
		
		for(int i = r - 1; i < r + 1; i++) {
			for(int j = c - 1; j < c + 1; j++) {
				if(i >= 0 && j >= 0 && i < board.length && j < board[0].length)
					System.out.println(visited[i][j]);
				if(i >= 0 && j >= 0 && i < board.length && j < board[0].length && !visited[i][j] && letter == board[i][j]) {
					System.out.println("fgxn");
					boardWord+=letter;
					r = i; 
					c = j;
					searchBoard(words, board, visited, r, c, boardWord, currentWord, allWords, letter);
				}
			}
		}
		
		visited[r][c] = false;
		
		
		return allWords;
		
	}
	
	//gets all words from board and checks if in dictionary
	public ArrayList<String> searchDictionary(Trie words, char [][] board, boolean [][] visited, int r, int c, String word, ArrayList<String> allWords) {
		//traverse through dictionary, check if each word is on board
		//if on board, add to list
		
		visited[r][c] = true;
		word = word + board[r][c];
		
		if(words.search(word))
			allWords.add(word);
		
		for(int i = r - 1; i < r + 1; i++) {
			for(int j = c - 1; j < c + 1; j++) {
				if(i >= 0 && j >= 0 && i < board.length && j < board[0].length && !visited[i][j])
					searchDictionary(words, board, visited, r, c, word, allWords);
			}
		}
		
		word = word.substring(0, word.length()-2);
		visited[r][c] = false;
		return allWords;
	}
	
	public void print() {
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board.length; j++) {
				System.out.print(board[i][j] + "| ");
			}
			System.out.println();
		}
	}
	
	//errors: size of allWords is 0

}
