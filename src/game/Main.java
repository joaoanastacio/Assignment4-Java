package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {

  public static final int SHOULD_STILL_GAMING = 1;
  public static final int INITIAL_LINE_IN_FILE = 0;
  public static final int NO_GUESSES_AVAILABLE = 0;
  public static final int MAXIMUM_GUESSES_AVAILABLE = 10;
  public static final int FIRST_LETTER_FROM_WORD = 0;
  public static final int HASHMAP_BASE_VALUE_TO_INCREASE = 1;

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

      if (!Character.isLetter(userGuess) || wrongWordsGuessedByPlayer.contains(userGuess)) {
        System.out.println("You have guessed " + "(" + (MAXIMUM_GUESSES_AVAILABLE - playerGuesses)
            + ")" + " wrong letters: " + wrongWordsGuessedByPlayer);
        playerGuesses = playerGuesses - 1;
        wrongWordsGuessedByPlayer.add(userGuess);
        continue;
      }

      if(isValidGuess(userGuess, targetCity)) {
        if(!rightWordsGuessedByPlayer.contains(userGuess)) {
          rightWordsGuessedByPlayer.add(userGuess);
          displayGuessesPosition(targetCity, rightWordsGuessedByPlayer);
        } else {
          System.out.println("Letter already played. try again with another one.");
          continue;
        }

        if(playerWon(targetCity, rightWordsGuessedByPlayer)) {
          System.out.println("You win!");
          System.out.println("You have guessed " + targetCity + " correctly.");
          break;
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
    HashMap<Character, Integer> countByWord = new HashMap<>();
    boolean playerWon = false;

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
      if (countCharacterInArray(wordMap.getKey(), userGuesses) == wordMap.getValue()) {
        playerWon = true;
      } else {
        playerWon = false;
      }
    }
    return playerWon;
  }

  private static int countCharacterInArray(char targetCharacter, ArrayList<Character> userGuesses) {
    int characterQuantity = 0;

    for (char character : userGuesses) {
      if (character == targetCharacter) {
        characterQuantity += 1;
      }
    }
    return characterQuantity;
  }

}
