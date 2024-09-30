import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors implements Game {

  private int playerScore;
  private int computerScore;
  private int roundsLeft;
  private int roundsTotal;
  private boolean isCompleted;
  private final Random random = new Random();
  private final Scanner scanner;

  public RockPaperScissors(Scanner scanner) {
    this.scanner = scanner;
  }

  public boolean isCompleted() {
    return isCompleted;
  }

  public int getRoundsLeft() {
    System.out.print("How many rounds do you want to play? \nEnter an integer number: ");
    while (true) {
      try {
        int result = scanner.nextInt();
        if (result < 1) {
          throw new IllegalArgumentException("Number must be positive and larger than 0.");
        }
        scanner.nextLine();
        return result;
      } catch (InputMismatchException e) {
        System.out.println("Illegal number format, try again:");
        scanner.nextLine();
      } catch (IllegalArgumentException e) {
        System.out.println(e.getMessage() + " Try again:");
        scanner.nextLine();
      }
    }
  }

  public RPS getComputerChoice() {
    return RPS.values()[random.nextInt(0,RPS.values().length)];
  }

  public RPS getPlayerChoice() {
    RPS playerChoice = null;
    while (playerChoice == null) {
      System.out.print("""
        Enter (r)ock, (p)aper or (s)cissors:
        """);

      String input = scanner.nextLine().trim().toLowerCase();
      switch (input.charAt(0)) {
        case 'r' -> playerChoice = RPS.ROCK;
        case 'p' -> playerChoice = RPS.PAPER;
        case 's' -> playerChoice = RPS.SCISSORS;
        default -> System.out.println("Invalid input, try again.");
      }
    }
    return playerChoice;
  }

  public Round decideRound(RPS player, RPS npc) {
    return player == npc ? Round.DRAW :
      player.ordinal() + 1 == (npc.ordinal() + 2) % 3 ? Round.WON :
        Round.LOSS;
  }

  @Override
  public void start() {
    roundsTotal = roundsLeft = getRoundsLeft();
  }

  @Override
  public void round() {

    if (roundsLeft < 1) {
      end();
      return;
    }

    printRoundState();

    RPS npc = getComputerChoice();
    RPS player = getPlayerChoice();

    System.out.println("Player choses: " + player);
    System.out.println("Computer choses: " + npc);
    Round result = decideRound(player, npc);
    System.out.printf("Round (%d/%d): %s %n", roundsTotal - roundsLeft + 1, roundsTotal, result);

    roundsLeft--;

    if (result == Round.WON) {
      playerScore++;
    } else if (result == Round.LOSS) {
      computerScore++;
    }

    System.out.println("\nPress enter to continue:");
    scanner.nextLine();
  }

  private void printRoundState() {
    System.out.println();
    System.out.println("-----------------------------------------");
    System.out.printf("Round (%d/%d) START %n", roundsTotal - roundsLeft + 1, roundsTotal);
    System.out.println("-----------------------------------------");
    System.out.println("Player Score: " + playerScore);
    System.out.println("Computer Score: " + computerScore);
    System.out.println();
  }

  @Override
  public void end() {
    String result = "GAME ";
    result += playerScore > computerScore ? "WON" :
      playerScore < computerScore ? "LOSS" :
        "DRAW";

    printGameEnd(result);
    isCompleted = true;
  }

  private void printGameEnd(String result) {
    System.out.println();
    System.out.println("******************");
    System.out.println(result + "!");
    System.out.println("******************");
    System.out.println();
    System.out.println("FINAL SCORES");
    System.out.println("-------------------");
    System.out.println("Player: " + playerScore);
    System.out.println("Computer Score: " + computerScore);
    System.out.println();
  }
}
