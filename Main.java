import java.util.Scanner;

// by Marc, Raul, Michael

public class Main {

  public static void main(String[] args) {

    try (Scanner scanner = new Scanner(System.in)) {

      Game rps = new RockPaperScissors(scanner);
      printInit();
      rps.start();
      while (!rps.isCompleted()) {
        rps.round();
      }
      System.out.println("Thank you for playing!");
    }
  }

  private static void printInit() {
    System.out.println("""
      *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
      *-*- Welcome To Rock Paper Scissors Ultimate v1.0 !!! *-*-
      *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
      """);
  }
}
