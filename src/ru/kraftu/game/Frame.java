package ru.kraftu.game;

// Наша кардр для рисования
 public class Frame {
  public char[][] arr = new char[World.PAGE_SIZE][World.PAGE_SIZE];

  public void clearFrame() {
   for (int i = 0; i < World.PAGE_SIZE; i++) {
    for (int j = 0; j < World.PAGE_SIZE; j++) {
     arr[i][j] = '0';
    }
   }
  }

  public void setChar(char ch, int i, int j) {
   arr[i][j] = ch;
  }

  public void clearConsol() {
   for (int i = 0; i < World.PAGE_SIZE; i++) {
    System.out.println();
   }
  }

  public void printFrame() {
   for (int i = 0; i < World.PAGE_SIZE; i++) {
    for (int j = 0; j < World.PAGE_SIZE; j++) {
     System.out.print(arr[i][j] + " ");
    }
    System.out.println();
   }
  }
 }
