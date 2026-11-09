package org.example;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//You do NOT need to index so called common words a, and, the, they, to, for, they, you, your, etc.
//You should ignore punctuation like periods and commas when you index the words.

public class App {
  // declare global hash set of generic words
  static ArrayList<String> genericWords = new ArrayList<>(List.of("the","can", "and", "are", "but", "for", "into", "not", "such",
      "that", "the", "their", "then", "there", "these", "they", "this", "was", "will", "with"));

  public static void main(String[] filePath) { // accept file path as apart of args

    //String[] filePath = { ("app\\src\\main\\java\\org\\example\\Files_for_Testing\\Sample4.txt") };

    if (filePath.length == 0) { // if no file paths are call as arguements
      return;
    }

    String fileLine, splitValues = "[,\\.\\?\\!\\s\\n\\t]";
    String[] fileWords;
    int index;
    InvertedFile invertedFile;

    for (int i = 0; i < filePath.length; ++i) {
      // PROCESS INPUT ----------------------------------
      try (BufferedReader fileReader = Files.newBufferedReader(Paths.get(filePath[i]))) {
        invertedFile = new InvertedFile(Files.size(Paths.get(filePath[i])));

        index = 0;

        while ((fileLine = fileReader.readLine()) != null) { // iterates through the file line by line
          if (fileLine.isBlank())
            continue;

          fileWords = fileLine.trim().split(splitValues); // get rid of superfulous spaces and punctuation

          for (int n = 0; n < fileWords.length; ++n) { // go through each word in the line

            if (fileWords[n].isBlank())
              continue;
            else if (isGenericWord(fileWords[n])) {
              ++index;
              continue;
            }

            fileWords[n] = fileWords[n].toLowerCase();
            fileWords[n] = fileWords[n].substring(0, 1).toUpperCase() + fileWords[n].substring(1);

            invertedFile.insert(fileWords[n], index);
            ++index;
          }

        }

        fileReader.close();

        // PROCESS OUTPUT ----------------------------------
        try (FileWriter fileWriter = new FileWriter(filePath[i] + "InvertedIndex.txt")) {
          fileWriter.write(invertedFile.getAllOfThem());
          fileWriter.close();
        } catch (IOException e) {
          System.err.println("idk something went wrong with the writer " + e.getMessage());
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.err.println("idk something went wrong with the reader " + e.getMessage());
      }

    }
  }

  private static boolean isGenericWord(String word) {
    if (word.length() <= 2) // assumes any word less than or equal to 2 letters is negligable
      return true;
    else
      return genericWords.contains(word);
  }

}
