package assignment;

import java.io.IOException;

import assignment.BoggleGame.SearchTactic;

public class Boggle {

	public static void main(String[] args) throws IOException {
		GameManager manager = new GameManager ();
		GameDictionary dictionary = new GameDictionary();
		dictionary.loadDictionary("words.txt");
		manager.newGame(3, 1, "cubes.txt", dictionary);
		manager.print();
		manager.newGame(4, 1, "cubes.txt", dictionary);
		//manager.newGame(5, 1, "cubes.txt", dictionary);
		manager.setSearchTactic(SearchTactic.SEARCH_BOARD);
		manager.print();
		manager.getAllWords();
		
		
		
	}

}
