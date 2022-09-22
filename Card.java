
/**
 * The Card class creates Card objects that imitate playing cards. 
 *
 * @author Caden Lin
 * @version Dec 06 2020 
 */
public class Card
{
    private int rank;
    private String suit;
    private boolean isFaceUp;

    /**
     * Constructor for objects of class Card
     * @param rank the rank of the card 
     * @param suit the suit of the card 
     */
    public Card(int rank, String suit)
    {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Determines the rank of the card 
     *
     * @return   the rank of the card
     */
    public int getRank()
    {
        return rank;
    }

    /** determines the suit of the card
     * @return the suit of the card 
     */
    public String getSuit()
    {
        return suit;
    }

    /** determines whether the card is red
     * 
     * @return true if the card is red (if it a diamond or heart) 
     *          false if the card is not red (a spade or clover) 
     * 
     */
    public boolean isRed()
    {
        if(suit.equals("d") || suit.equals("h"))
            return true;
        return false;
    } 

    /** determines whether the card is facing up 
     * 
     * @return true if the card is facing up 
     *             false otherwise 
     * 
     */
    public boolean isFaceUp()
    {
        return isFaceUp;
    }

    /** turns the card up 
     * @postcondition the card has been turned up, isFaceUp is true 
     * 
     */
    public void turnUp()
    {
        isFaceUp = true;
    }

    /** turns the card down
     * @postcondition the card has been turned down, isFaceUp is false
     * 
     */ 
    public void turnDown()
    {
        isFaceUp=false;
    } 

    /** determines the file name of the appropriate image for the card 
     * 
     * @return the name of the file for the image of the card 
     * 
     */
    public String getFileName()
    {
        String front = "cards/";
        String end = ".gif";
        if(!isFaceUp)
        {
            return "cards/back.gif";
        }
        if(rank <= 9 && rank != 1)
        {
            return front + rank + suit + end;
        }
        if(rank == 10)
        {
            return front + "t" + suit + end;
        }
        if(rank == 11)
        {
            return front + "j" + suit + end;
        }
        if(rank == 12)
        {
            return front + "q" + suit + end;
        }
        if(rank == 13)
        {
            return front + "k" + suit + end;
        }
        return front + "a" + suit + end;
    }
}
