import java.util.Random;
/**
 * This class can generate random number and 
 * store lowerLimit and upperLimit
 * @author Siya Li
 * @version  2.0 1 Apr 2018
 */public class RandomNumber
{
    private int randomNumber;   //random number for computer to simulate the real process of guessing
    private int lowerLimit;     //the lower limit of number range when guessing
    private int upperLimit;     //the upper limit of number range when guessing
    private int hiddenNumber;   //the real number for both players to guess
    /**
     * default constructor
     */
    public RandomNumber()
    {
        randomNumber = 0;
        lowerLimit = 1;
        upperLimit = 100;
        hiddenNumber = 0;
    }
    
    /**
     * non-default constructor
     */
    public RandomNumber(int randomNumber,int minNumber, int maxNumber, int hiddenNumber)
    {
        this.randomNumber = randomNumber;
        lowerLimit = minNumber;
        upperLimit = maxNumber;
        this.hiddenNumber = hiddenNumber;
    }
    
    /**
     * Retrieved from week 5 exercise
     * Generate and return a random number between minimun number and the
     * maximum number, inclusive, entered as a parameter
     */
    
    public int generateRandomNumber(int minNumber, int maxNumber)
    {
        return minNumber + (int)(Math.random() * (maxNumber - minNumber));
    } 
    
    /**
     * accessor for hiddenNumber
     */
    public int getHiddenNumber()
    {
        return hiddenNumber;
    }
    
    /**
     * mutator for lowerLimit
     */
    public int getLowerLimit()
    {
        return lowerLimit;
    }
    
    /**
     * mutator for randomNumber
     */
    public int getRandomNumber()
    {
        return randomNumber;
    }
    
    /**
     * mutator for upperLimit
     */
    public int getUpperLimit()
    {
        return upperLimit;
    }
    
    /**
     * mutator for hiddenNumber
     */
    public void setHiddenNumber(int hiddenNumber)
    {
        this.hiddenNumber = hiddenNumber;
    }
    
    /**
     * accessor for lowerLimit
     */
    public void setLowerLimit(int lowerLimit)
    {
        this.lowerLimit = lowerLimit;
    }
    
    /**
     * accessor for randomNumber
     */
    public void setRandomNumber(int randomNumber)
    {
        this.randomNumber = randomNumber;
    }
    
    /**
     * accessor for upperLimit
     */
    public void setUpperLimit(int upperLimit)
    {
        this.upperLimit = upperLimit;
    }
}