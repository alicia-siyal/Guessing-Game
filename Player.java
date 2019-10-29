/**
 * the Player class stores name, score and the last guesses of the player
 * @author Siya Li
 * @version 3.4 25 Mar 2018
 */
public class Player
{
    private String name;    //the name of the player
    private int score;  //the cumulative game score
    private int guesses;    //the last number guessed for the current round
    /**
     * default constructor
     */
    public Player()
    {
        name = " ";
        score = 0;
        guesses = 0;
    }

    /**
     * non-default constructor
     */
    public Player(String name, int score, int guesses)
    {
        this.name = name;
        this.score = score;
        this.guesses = guesses;
    }

    /**
     * accessor method for guesseses
     */
    public int getGuess()
    {
        return guesses;
    }
    
    /**
     * accessor method for player's name
     */
    public String getName()
    {
        return name;
    }

    /**
     * accessor method for score
     */
    public int getScore()
    {
        return score;
    }
    
    /**
     * return the players status of name, total score, and last guess
     */
    public String playerStatus()
    {
        return "player " + "'" + getName() + "'," + " total score is " + getScore() + ", last input is " + getGuess();
    }
    
    /**
     * last number guessed for the current round
     */
    public void setGuess(int currentGuess)
    {
        this.guesses = currentGuess;
    }
    
    /**
     * Mutalor method for player's name
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * set score
     */
    public void setScore(int score)
    {
        this.score = score;
    }
}