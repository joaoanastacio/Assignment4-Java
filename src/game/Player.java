package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {

  private static final int MAXIMUM_GUESSES_AVAILABLE = 10;
  private static final int HASHMAP_BASE_VALUE_TO_INCREASE = 1;

  private ArrayList<Character> wrongWordsGuessedByPlayer = new ArrayList<>();
  private ArrayList<Character> rightWordsGuessedByPlayer = new ArrayList<>();
  private int playerGuesses;

  public Player() {
    this.playerGuesses = MAXIMUM_GUESSES_AVAILABLE;
  }

  public void addToPlayerWrongGuesses(char userGuess) {
    this.wrongWordsGuessedByPlayer.add(userGuess);
  }

  public void addToPlayerRightGuesses(char userGuess) {
    this.rightWordsGuessedByPlayer.add(userGuess);
  }

  public ArrayList<Character> getWrongWordsGuessedByPlayer() {
    return wrongWordsGuessedByPlayer;
  }

  public ArrayList<Character> getRightWordsGuessedByPlayer() {
    return this.rightWordsGuessedByPlayer;
  }

  public boolean playedWrongGuessBefore(char userGuess) {
    return this.wrongWordsGuessedByPlayer.contains(userGuess);
  }

  public boolean playedRightGuessBefore(char userGuess) {
    return this.rightWordsGuessedByPlayer.contains(userGuess);
  }

  public int getAvailablePlayerGuesses() {
    return this.playerGuesses;
  }

  public void decreasePlayerGuesses() {
    this.playerGuesses = this.playerGuesses - 1;
  }

  public boolean playerWon(String targetCity, ArrayList<Character> userGuesses) {
    HashMap<Character, Integer> countByWord = new HashMap<>();
    int countOfCorrectLetter = 0;

    for (int index = 0; index < targetCity.length(); index++) {
      Character letterInIndex = targetCity.charAt(index);

      if (!countByWord.containsKey(letterInIndex)) {
        countByWord.put(letterInIndex, HASHMAP_BASE_VALUE_TO_INCREASE);
      } else {
        int currentValueInKey = countByWord.get(letterInIndex);
        countByWord.put(letterInIndex, (currentValueInKey + HASHMAP_BASE_VALUE_TO_INCREASE));
      }
    }

    for (Map.Entry<Character, Integer> wordMap : countByWord.entrySet()) {
      if(userGuesses.contains(Character.toLowerCase(wordMap.getKey())) ||
          userGuesses.contains(Character.toUpperCase(wordMap.getKey()))) {
        countOfCorrectLetter += 1;
      }
    }
    return countOfCorrectLetter == countByWord.size();
  }

}
