package game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileManager {

  private File file;

  public FileManager(String fileName) {
    this.file = new File(fileName);
  }

  public int getFileNumberOfLines() {
    int numberOfLines = 0;
    try {
      Scanner fileReader = new Scanner(this.file);

      while (fileReader.hasNextLine()) {
        String line = fileReader.nextLine();
        numberOfLines++;
      }
      fileReader.close();

    } catch (FileNotFoundException exception) {

      System.out.println("Error: File not found! \n"
          + "Restart the game and make sure to pass the correct path of file.");
    }
    return numberOfLines;
  }

  public String getCityAtLineInFile(int fileLine) {
    String cityAtLine = null;

    try {
      cityAtLine = Files.readAllLines(Paths.get(this.file.getName())).get(fileLine);

    } catch (IOException exception) {
      System.out.println("Error: Fail to read file, try again.");
    }
    return cityAtLine;
  }

}
