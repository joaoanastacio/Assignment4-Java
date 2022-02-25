package game;

import java.util.ArrayList;
import java.util.Scanner;

public class Driver {

  public static final int INITIAL_LINE_IN_FILE = 0;
  public static final int NO_GUESSES_AVAILABLE = 0;
  public static final int FIRST_LETTER_FROM_WORD = 0;
  public static final int MAXIMUM_GUESSES_AVAILABLE = 10;

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

      System.out.print("\nGuess a letter: ");
      char userGuess = userInputReader.next().charAt(FIRST_LETTER_FROM_WORD);

      if (!Character.isLetter(userGuess) || player.playedWrongGuessBefore(userGuess)) {
        player.decreasePlayerGuesses();
        player.addToPlayerWrongGuesses(userGuess);
        displayGuessesPosition(targetCity, player.getRightWordsGuessedByPlayer());
        displayWrongGuesses(player.getAvailablePlayerGuesses(), player.getWrongWordsGuessedByPlayer());
        continue;
      }

      if(isValidGuess(userGuess, targetCity)) {
        if(!player.playedRightGuessBefore(userGuess)) {
          player.addToPlayerRightGuesses(userGuess);
        } else {
          System.out.println("Letter already played. try again using another one.");
          continue;
        }

        if(player.playerWon(targetCity, player.getRightWordsGuessedByPlayer())) {
          System.out.println("You win!\nYou have guessed " + targetCity + " correctly.");
          break;
        }
      } else {
        player.decreasePlayerGuesses();
        player.addToPlayerWrongGuesses(userGuess);
      }

      displayGuessesPosition(targetCity, player.getRightWordsGuessedByPlayer());
      displayWrongGuesses(player.getAvailablePlayerGuesses(), player.getWrongWordsGuessedByPlayer());
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

  private static void displayWrongGuesses(int availableGuesses, ArrayList<Character> wrongGuesses) {
    System.out.println("You have guessed " + "(" +
        (MAXIMUM_GUESSES_AVAILABLE - availableGuesses) + ")" + " wrong letters: " + wrongGuesses);
  }

  private static boolean isValidGuess(char userGuess, String targetCity) {
    int notFoundTargetNumber = -1;

    if (targetCity.toLowerCase().indexOf(userGuess) != notFoundTargetNumber) {
      return true;
    }
    return false;
  }

}
