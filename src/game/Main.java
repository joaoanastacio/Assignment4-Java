package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

  public static final int SHOULD_STILL_GAMING = 1;
  public static final int INITIAL_LINE_IN_FILE = 0;
  public static final int NO_GUESSES_AVAILABLE = 0;
  public static final int MAXIMUM_GUESSES_AVAILABLE = 10;
  public static final int FIRST_LETTER_FROM_WORD = 0;

  public static void main(String[] args) {

    int stillGaming = SHOULD_STILL_GAMING;

    File baseFile = new File("cities.txt");
    int fileNumberOfLines = getFileNumberOfLines(baseFile);

    int baseSearchRange = fileNumberOfLines - INITIAL_LINE_IN_FILE + 1;
    int targetLineInFile = (int) (Math.random() * baseSearchRange) + INITIAL_LINE_IN_FILE;

    String targetCity = getCityAtLineInFile(targetLineInFile, baseFile);
    System.out.println("Target City: " + targetCity); // TODO: remove comment
    displayIntroduction(targetCity.length());

    int playerGuesses = MAXIMUM_GUESSES_AVAILABLE;
    ArrayList<Character> wrongWordsGuessedByPlayer = new ArrayList<>();
    ArrayList<Character> rightWordsGuessedByPlayer = new ArrayList<>();

    Scanner userInputReader = new Scanner(System.in);

    while (stillGaming == SHOULD_STILL_GAMING) {

      if (playerGuesses == NO_GUESSES_AVAILABLE) {
        System.out.println("You lose!");
        System.out.println("The correct word was: " + targetCity);
        break;
      }

      System.out.print("Guess a letter: ");
      char userGuess = userInputReader.next().charAt(FIRST_LETTER_FROM_WORD);

      if(isValidGuess(userGuess, targetCity)) {
        rightWordsGuessedByPlayer.add(userGuess);
        displayGuessesPosition(targetCity, rightWordsGuessedByPlayer);

        if(playerWon(targetCity, rightWordsGuessedByPlayer)) {
          System.out.println("You win!");
          System.out.println("You have guessed " + targetCity + "correctly.");
        }

      } else {
        playerGuesses = playerGuesses - 1;
        wrongWordsGuessedByPlayer.add(userGuess);
      }
      System.out.println("You have guessed " + "(" + (MAXIMUM_GUESSES_AVAILABLE - playerGuesses)
          + ")" + " wrong letters: " + wrongWordsGuessedByPlayer);
    }
  }

  private static int getFileNumberOfLines(File baseFile) {
    int numberOfLines = 0;

    try {
      Scanner fileReader = new Scanner(baseFile);

      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();
        numberOfLines++;
      }
      fileReader.close();

    } catch (FileNotFoundException exception) {
      System.out.println("Error: File not found!");
    }
    return numberOfLines;
  }

  private static String getCityAtLineInFile(int fileLine, File baseFile) {
    String cityAtLine = null;

    try {
      cityAtLine = Files.readAllLines(Paths.get(baseFile.getName())).get(fileLine);

    } catch (IOException exception) {
      System.out.println("Error: Fail to read file, try again.");
    }
    return cityAtLine;
  }

  private static void displayIntroduction(int cityLength) {
    System.out.println("Here's the question.");

    for(int i = 1; i <= cityLength; i++) {
      System.out.print("_");
    }
    System.out.println();
  }

  private static boolean isValidGuess(char userGuess, String targetCity) {
    int notFoundTargetNumber = -1;

    if (targetCity.indexOf(userGuess) != notFoundTargetNumber) {
      return true;
    }
    return false;
  }

  private static void displayGuessesPosition(String targetCity, ArrayList<Character> userGuesses) {
    char[] targetCityInCharacters = targetCity.toCharArray();
    System.out.print("You are guessing: ");

    for(char character : targetCityInCharacters) {
      if(userGuesses.contains(character)) {
        System.out.print(character);
      } else {
        System.out.print("_");
      }
    }
    System.out.println();
  }

  private static boolean playerWon(String targetCity, ArrayList<Character> userGuesses) {
    boolean playerWon = false;

    // Check if player win
    // To player win, rightWordsGuessedByPlayer must have all characters from target City

    // Should I distinguish lower cases from upper case?
    // TODO: add logic to validate if player won
    return playerWon;
  }

}