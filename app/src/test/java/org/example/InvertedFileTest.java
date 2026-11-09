package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvertedFileTest {
  @Test
  void itSaysHello() {
    InvertedFile file = new InvertedFile(10);
file.insert("yo", 0);
    assertEquals(file.isEmpty(), false);
  }
}
