import java.util.Scanner;
import java.lang.Math;
import java.lang.String;
/**
 * the Game class is designed to run the whole process of the Guessing Game
 * including check the valid of player's name, decide order that who goes first, 
 * the guessing process for human player and simulation for computer player, 
 * as well as calculate the score for each player
 * @author Siya Li
 * @version 2.6 13 Apr 2018
 */
public class Game
{
    private Player player1;     //player1, the human player
    private Player player2;     //player2, the computer player
    private RandomNumber range;     //this object is used to store upperLimit and lowerlimit of each round, and the correct number which is called hiddenNumber
    /**
     * default Constructor for Game class
     */
    public Game()
    {
        player1 = new Player();     //initialise the "player1" object which is for player
        player2 = new Player();     //initialise the "player2" object which is for computer
        range = new RandomNumber();     //initialise the "range" object
    }
    
    /**
     * non-default constructor for Game class
     */
    public Game(Player player1, 
                    Player player2, 
                        RandomNumber range)
    {
        this.player1 = player1;
        this.player2 = player2;
        this.range = range;
    }

    /**
     * request the player to enter their name and
     * check whether player's name is valid
     */
    public void checkName()
    {
        String name;
        System.out.println("Please insert your name:  ");
        Scanner console = new Scanner(System.in);
        name = console.nextLine().trim();
        while ((name.length() == 0) || (name.length() > 8)) //if the player input nothing or the length of input is more than 8 characters
        {
            System.out.println("Your name must be within 1-8 characters or not empty");
            name = console.nextLine().trim();
        }
        System.out.println("Hello, " + name);
        player1.setName(name);
        player2.setName("computer");
    }

    /**
     * compare the total score for each player and
     * print out who is the winner
     */
    public void compareScore()
    {
        if (player1.getScore() > player2.getScore())
            System.out.println("You win!");
        else if (player1.getScore() < player2.getScore())
            System.out.println("Computer wins!");
        else
            System.out.println("Dogfall! Both wins!");
    }

    /**
     * generate an abandon number for computer 
     * to simulate the 'abandon' process
     */
    public boolean computerCheckAbandon()
    {
        RandomNumber random = new RandomNumber();
        boolean valid = false;
        int abandonNumber = range.generateRandomNumber(1,20);
        int guessAbandonNumber = random.generateRandomNumber(1,20);
        if (abandonNumber == guessAbandonNumber)    //if the computer has 'guessed' the abandonNumber, it will abandon this round
            valid = true;
        return valid;
        /*
         * if (random.generateAbandonNumber(1,20) == range.getRandomNumber)
         */
    }

    /**
     * generate an random number for computer
     * to simulate the guessing process
     */
    public int computerGuess()
    {
        RandomNumber random = new RandomNumber();
        int lowerLimit = range.getLowerLimit();
        int upperLimit = range.getUpperLimit();
        int valid = 0;
        boolean computerAbandon = computerCheckAbandon();
        System.out.println("Now it's computer's turn");
        System.out.println("The computer will choose a number between " + lowerLimit + " and " + upperLimit);
        int computerGuessNumber = random.generateRandomNumber(lowerLimit, upperLimit);
        player2.setGuess(computerGuessNumber);
        if (computerAbandon == true)
        {
            System.out.println("Computer abandoned this round");
            valid = 1;
        }
        else if (player2.getGuess() == range.getHiddenNumber())
        {
            System.out.println("Computer guesses " + player2.getGuess());
            System.out.println("Computer wins, the number is " +  range.getHiddenNumber() + "\n" + "This is end of this round");
            valid = 2;
        }
        else if (player2.getGuess() > range.getHiddenNumber())
        {
            upperLimit = player2.getGuess() - 1;
            System.out.println("The computer guesses " + player2.getGuess() + "\n" + "The real number is lower");
            range.setUpperLimit(upperLimit);
        }
        else if (player2.getGuess() < range.getHiddenNumber())
        {
            lowerLimit = player2.getGuess() + 1;            
            System.out.println("The computer guesses " + player2.getGuess() + "\n" + "The real number is higher");
            range.setLowerLimit(lowerLimit);
        }
        return valid;
    }

    /**
     * to randomly choose which player goes first
     */
    public int decideOrder()
    {
        RandomNumber random = new RandomNumber();
        int order = random.generateRandomNumber(0,1);
        System.out.println("=============================");
        if (order == 0)     //"0" means player goes first
            System.out.println("You will go first");
        else    //another situation "1" means computer goes first
            System.out.println("The computer goes first");
        return order;
    }

    /**
     * the whole game process for 4 rounds
     *
     */
    public void gameProcess()
    {
        Scanner console = new Scanner(System.in);
        for (int round = 1; round < 5; round++)
        {
            updateRange();
            generateHiddenNumber();
            int order = decideOrder();
            int attempt = 0;
            int i = 0;
            int playerAttempt = 0;
            int computerAttempt = 0;
            System.out.println( "This is round " + round + " of 4 rounds" + "\n" + "You will have 3 attempts for each round");
            System.out.println("Press ENTER to continue...");
            console.nextLine();
            for (i = 0; i < 6; i ++)
            {
                if (order == 0)  //0 indicates player goes first this round
                {
                    System.out.println("=============================");
                    System.out.println("This is your attempt " + (i / 2 + 1));
                    int playerOut = playerGuess();  //playerGuess() return a value that indicates player guessed the correct number or abandoned
                                                    //this round, so that this round can be finished
                    order = 1;  //turn to computer player
                    attempt ++;
                    if (playerOut == 1)  //1 indicates player abandoned
                    {
                        i = 10; //to drop the loop
                        playerAttempt = 10;
                        computerAttempt = 10;
                    }
                    else if (playerOut == 2)    //2 indicates player guessed the correct number and wins
                    {
                        i = 20;
                        playerAttempt = attempt;
                        computerAttempt = 10;
                    }
                    else if (attempt == 6 &&   //6 indicates neither of the two players has guessed the correct number
                             playerOut != 1 &&  
                             playerOut != 2)  
                        i = 30;
                }
                else
                {
                    System.out.println("=============================");
                    int computerOut = computerGuess();
                    order = 0;  //turn to human player
                    attempt ++;
                    if (computerOut == 1)//i indicates computer abandoned
                    {
                        i = 10;
                        playerAttempt = 10;
                        computerAttempt = 10;
                    }
                    else if (computerOut == 2)//2 indicates computer wins
                    {
                        i = 20;
                        computerAttempt = attempt;
                        playerAttempt = 10;
                    }
                    else if (attempt == 6 &&  //6 indicates neither of the two players has guessed the correct number
                             computerOut != 1 && 
                             computerOut != 2)
                        i = 30;
                }
            }
            System.out.println("=============================");
            System.out.println("This is end of round " + round);
            System.out.println("The real number is " + range.getHiddenNumber());
            if (i == 11)    //one of two players abandon, no one add score
                System.out.println("No score will be added for this round");
            else if (i == 21)   //one of two players wins to add score, another one doesn't add score
            {
                player1.setScore(player1.getScore() + score(player1, playerAttempt));
                player2.setScore(player2.getScore() + score(player2, computerAttempt));
                System.out.println("You scored " + score(player1, playerAttempt) + ", Computer scored " + score(player2, computerAttempt));
            }
            else if (i == 31)   //neither of two players win, add score according to how far they are from the hiddenNumber
            {
                System.out.println("No one wins this round");
                System.out.println("Score depends on the proximity of the guess to the real number");
                player1.setScore(player1.getScore() + noWinScore(player1, attempt));
                player2.setScore(player2.getScore() + noWinScore(player2, attempt));
                System.out.println("You scored " + noWinScore(player1, attempt) + ", Computer scored " + noWinScore(player2, attempt));
            }
            System.out.println("=============================");
            System.out.println("Press ENTER to continue...");
            console.nextLine();
        }
        System.out.println(player1.playerStatus() + "\n" + player2.playerStatus());
        compareScore();
    }

    /**
     * generate a hidden number for two players to guess
     */
    public void generateHiddenNumber()
    {
        int hiddenNumber = range.generateRandomNumber(1,100);
        range.setHiddenNumber(hiddenNumber);
    }

    /**
     * check whether the input is numeric or not
     */
    public boolean isNumeric(String input)
    {
        boolean valid = false;
        for (int i = 0; i < input.length(); i++)
        {
            if (!Character.isDigit(input.charAt(i)))    //only if there is one character in 'input' is not a digit, return false
            {
                valid = false;
                return valid;
            }
        }
        valid = true;
        return valid;
    }

    /**
     * calculate score for each player if none of them get the correct number
     */
    public int noWinScore(Player player, 
                              int attempts)
    {
        int score = 0;
        if ((attempts == 6) && 
            (player.getGuess() != range.getHiddenNumber()))
        {
            if (Math.abs(player.getGuess() - range.getHiddenNumber()) > 10)
                score = 0;
            else if (Math.abs(player.getGuess() - range.getHiddenNumber()) < 10)
                score =  10 - Math.abs(player.getGuess() - range.getHiddenNumber());
        }
        return score;
    }

    /**
     * check whether the number player guessed is correct or not
     */
    public int playerGuess()
    {
        Scanner console = new Scanner(System.in);
        int valid = 0;
        System.out.println("Now it's your turn");
        System.out.println("Please choose a number between " + range.getLowerLimit() + " and " + range.getUpperLimit());
        String input = console.nextLine();
        while (isNumeric(input) == false || input.trim().length() == 0) //to check whether the player's input is null or not a number
        {
            System.out.println("You should enter a number");
            input = console.nextLine();
        }
        int guess = Integer.parseInt(input);    //to transfer String type of number into int so that it can make comparation with other numbers
        player1.setGuess(guess);
        if (playerInputValid() == true)  //if the player's input is valid
        {
            if (player1.getGuess() == 999)
            {
                System.out.println("You abandoned this round");
                valid = 1;
            }
            else if (player1.getGuess() == range.getHiddenNumber())
            {
                System.out.println("You win, the number is " + range.getHiddenNumber());
                valid = 2;
            }
            else if (player1.getGuess() > range.getHiddenNumber())
            {
                range.setUpperLimit(player1.getGuess() - 1);
                System.out.println("You guess " + player1.getGuess() + "\n" + "The real number is lower");
            }
            else if (player1.getGuess() < range.getHiddenNumber())
            {
                range.setLowerLimit(player1.getGuess() + 1);            
                System.out.println("You guess " + player1.getGuess() + "\n" + "The real number is higher");
            }
        }
        return valid;
    }

    /**
     * check thether the number player input is valid
     */
    public boolean playerInputValid()
    {
        Scanner console = new Scanner(System.in);
        boolean valid = false;
        int lowerLimit = range.getLowerLimit();
        int upperLimit = range.getUpperLimit();
        int guess = 0;
        if (player1.getGuess() == 999)
            valid = true;
        else if ((player1.getGuess() >= 1 && player1.getGuess() <= 100) && 
                 (player1.getGuess() < range.getLowerLimit() || player1.getGuess() > range.getUpperLimit()))
        {
            System.out.println("You must enter a number within " + lowerLimit + " and " + upperLimit);
            System.out.println("No extra chance to enter a number");
            //player1.setGuess(-999);
        }
        else if ((player1.getGuess() >= range.getLowerLimit()) && 
                 (player1.getGuess() <= range.getUpperLimit()))
        {
            valid = true;
            System.out.println("Your number is " + player1.getGuess());
        }
        else    //if the player input a number which is out out 1-100 and not '999', then ask they to renter and re-check the valid 
            while ((player1.getGuess() < 1 || player1.getGuess() > 100) && player1.getGuess() != 999 )
            {
                System.out.println("You cannot enter a number out of 1-100");
                System.out.println("Please enter a number");
                String input = console.nextLine();
                while (isNumeric(input) == false || input.trim().length() == 0)
                {
                    System.out.println("You should enter a number");
                    input = console.nextLine();
                }
                valid = true;
                guess = Integer.parseInt(input);
                if ((guess >= 1 && guess <= 100) &&     
                    (guess < range.getLowerLimit() || guess > range.getUpperLimit()))   //re-check whether the new input is in the correct range
                {
                    System.out.println("You must enter a number within " + lowerLimit + " and " + upperLimit);
                    System.out.println("No extra chance to enter a number");
                    valid = false;  //false means no comparation will be made in playGuess() 
                    //player1.setGuess(-999);
                }
                player1.setGuess(guess);
            }
        return valid;
    }

    /**
     * calculate score to one player whose guess meet the hiddenNumber 
     * according to the total attempts both players have done
     */
    public int score(Player player, 
                         int attempts)
    {
        int score = 0;
        switch (attempts)
        {
            case 1: 
            score = 20; break;
            case 2: 
            score = 15; break;
            case 3: 
            score = 11; break;
            case 4: 
            score = 8; break;
            case 5: 
            score = 6; break;
            case 6: 
            score = 5; break;
            case 10:
            score = 0; break;
        }
        return score;
    }

    /**
     * a welcome message for the game and
     * start game
     */
    public void startGame()
    {
        Scanner console = new Scanner(System.in);
        System.out.println("Welcome to Guessing Game!");
        System.out.println("*****************************");
        System.out.println("This game has four rounds,");
        System.out.println("A number between 1 and 100 (inclusive) is randomly generated per round");
        System.out.println("You and computer take turns to guess number");
        System.out.println("You have 3 guesses each round");
        System.out.println("You can press '999' to abandon each round");
        System.out.println("*****************************");
        System.out.println("Press ENTER to start game");
        console.nextLine();
        checkName();
        gameProcess();
    }

    /**
     * update number range of each round 
     */
    public void updateRange()
    {
        int lowerLimit = 1;
        int upperLimit = 100;
        range.setLowerLimit(lowerLimit);
        range.setUpperLimit(upperLimit);
    }
}
