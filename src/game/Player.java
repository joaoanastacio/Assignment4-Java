package game;

import java.util.ArrayList;

public class Player {

  private static final int MAXIMUM_GUESSES_AVAILABLE = 10;

  private ArrayList<Character> wrongWordsGuessedByPlayer = new ArrayList<>();
  private ArrayList<Character> rightWordsGuessedByPlayer = new ArrayList<>();
  private int playerGuesses = MAXIMUM_GUESSES_AVAILABLE;

  public Player() {

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
}
