package game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static final int INITIAL_LINE_IN_FILE = 0;
  public static final int NO_GUESSES_AVAILABLE = 0;
  public static final int MAXIMUM_GUESSES_AVAILABLE = 10;
  public static final int FIRST_LETTER_FROM_WORD = 0;
  public static final int HASHMAP_BASE_VALUE_TO_INCREASE = 1;

  public static void main(String[] args) {

    FileManager fileManager = new FileManager("cities.txt");
    int fileNumberOfLines = fileManager.getFileNumberOfLines();

    int baseSearchRange = fileNumberOfLines - INITIAL_LINE_IN_FILE + 1;
    int targetLineInFile = (int) (Math.random() * baseSearchRange) + INITIAL_LINE_IN_FILE;

    String targetCity = fileManager.getCityAtLineInFile(targetLineInFile);
    displayIntroduction(targetCity);

    Player player = new Player();

    Scanner userInputReader = new Scanner(System.in);

    while (true) {

      if (player.getAvailablePlayerGuesses() == NO_GUESSES_AVAILABLE) {
        System.out.println("You lose!");
        System.out.println("The correct word was: " + targetCity);
        break;
      }

      System.out.print("Guess a letter: ");
      char userGuess = userInputReader.next().charAt(FIRST_LETTER_FROM_WORD);

      if (!Character.isLetter(userGuess) || player.playedWrongGuessBefore(userGuess)) {
        player.decreasePlayerGuesses();
        player.addToPlayerWrongGuesses(userGuess);
        displayGuessesPosition(targetCity, player.getRightWordsGuessedByPlayer());

        System.out.println("You have guessed " + "(" +
            (MAXIMUM_GUESSES_AVAILABLE - player.getAvailablePlayerGuesses())+
            ")" + " wrong letters: " + player.getWrongWordsGuessedByPlayer());
        continue;
      }

      if(isValidGuess(userGuess, targetCity)) {
        if(!player.playedRightGuessBefore(userGuess)) {
          player.addToPlayerRightGuesses(userGuess);
          displayGuessesPosition(targetCity, player.getRightWordsGuessedByPlayer());
        } else {
          System.out.println("Letter already played. try again with another one.");
          continue;
        }

        if(playerWon(targetCity, player.getRightWordsGuessedByPlayer())) {
          System.out.println("You win!");
          System.out.println("You have guessed " + targetCity + " correctly.");
          break;
        }
      } else {
        player.decreasePlayerGuesses();
        player.addToPlayerWrongGuesses(userGuess);
        displayGuessesPosition(targetCity, player.getRightWordsGuessedByPlayer());
      }
      System.out.println("You have guessed " + "(" +
          (MAXIMUM_GUESSES_AVAILABLE - player.getAvailablePlayerGuesses())
          + ")" + " wrong letters: " + player.getWrongWordsGuessedByPlayer());
    }
  }

  private static void displayIntroduction(String targetCity) {
    System.out.println("Here's the question.");

    for(int i = 0; i < targetCity.length(); i++) {
      if(!Character.isWhitespace(targetCity.charAt(i))) {
        System.out.print("_");
      } else {
        System.out.print(" ");
      }
    }
    System.out.println();
  }

  private static boolean isValidGuess(char userGuess, String targetCity) {
    int notFoundTargetNumber = -1;

    if (targetCity.toLowerCase().indexOf(userGuess) != notFoundTargetNumber) {
      return true;
    }
    return false;
  }

  private static void displayGuessesPosition(String targetCity, ArrayList<Character> userGuesses) {
    char[] targetCityInCharacters = targetCity.toCharArray();
    System.out.print("You are guessing: ");

    for(char character : targetCityInCharacters) {
      if(userGuesses.contains(Character.toLowerCase(character)) ||
          userGuesses.contains(Character.toUpperCase(character))) {
        System.out.print(character);
      } else {
        if (Character.isWhitespace(character)) {
          System.out.print(" ");
          continue;
        }
        System.out.print("_");
      }
    }
    System.out.println();
  }

  private static boolean playerWon(String targetCity, ArrayList<Character> userGuesses) {
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
