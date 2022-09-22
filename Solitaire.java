import java.util.*;

/**
 * This class simulates the card game Solitaire.
 * 
 * @author Caden Lin
 * @version Dec 5 2020 
 */
public class Solitaire
{
    /** the main method 
     * 
     * @param args arguments from the command line 
     * 
     */
    public static void main(String[] args)
    {
        new Solitaire();
    }

    private Stack<Card> stock;
    private Stack<Card> waste;
    private Stack<Card>[] foundations;
    private Stack<Card>[] piles;
    private SolitaireDisplay display;

    /** Constructor for Solitaire objects
     * 
     */
    public Solitaire()
    {
        foundations = new Stack[4];
        for (int i = 0; i <foundations.length; i++)
        {
            foundations[i] = new Stack<Card>();
        }
        piles = new Stack[7];
        for (int i =0; i < piles.length; i++)
        {
            piles[i] = new Stack<Card>();
        }
        stock = new Stack<Card>();
        waste = new Stack<Card>();
        createStock();
        deal();
        display = new SolitaireDisplay(this);
    }

    /** returns the card on top of the stock
     * 
     * @return the card on top of the stock, or null if the stock is empty
     */
    public Card getStockCard()
    {
        if(!stock.isEmpty())
            return stock.peek();
        return null;
    }

    /**
     * returns the card on top of the waste
     * 
     * @return the card on top of the waste, or null if the waste is empty
     * 
     */
    public Card getWasteCard()
    {
        if(!waste.isEmpty())
            return waste.peek();
        return null;
    }

    /** returns the card on top of the given foundation
     * precondition:  0 <= index < 4
     * postcondition: returns the card on top of the given
     * foundation, or null if the foundation is empty 
     * 
     * @param index the number of the foundation that you want the top card of 
     * @return the card on top of the given foundation, or null if the foundation is empty
     * 
     */
    public Card getFoundationCard(int index)
    {
        if (!foundations[index].isEmpty())
        {
            return foundations[index].peek();
        }
        return null;
    }

    /** returns the reference to a specific pile
     * @precondition:  0 <= index <7
     * @param index the number of the pile that you want a reference to 
     * @return a reference to the given pile
     */
    public Stack<Card> getPile(int index)
    {
        return piles[index];
    }

    /** creates the stock of 52 cards in random order 
     * @postcondition the stock has been created and randomized 
     */
    private void createStock()
    {
        ArrayList<Card> fullStack = new ArrayList<Card>();
        for (int i = 1; i <= 13; i++)
        {
            fullStack.add(new Card(i, "h"));
            fullStack.add(new Card(i, "c"));
            fullStack.add(new Card(i, "d"));
            fullStack.add(new Card(i, "s"));
        }
        while(fullStack.size() > 0)
        {
            int x = (int) (Math.random() * fullStack.size()); 
            Card card = fullStack.remove(x);
            stock.push(card); 
        }
    }

    /** deals the stock to the piles 
     * @postcondition the stock has been dealt to the piles appropriately, 
     * with the remaining cards still in the stock 
     * 
     */
    private void deal()
    {
        for (int i = 1; i <=7; i++)
        {
            for (int j = 1; j <= i; j++)
            {
                piles[i-1].push(stock.pop());
            }
            piles[i-1].peek().turnUp();
        }              
    }

    /** if there are at least three cards in the stock, 
     * moves three cards from the stock to the waste and turns them up
     * otherwise, moves all cards from the stock to the waste and turns them up 
     * @postcondition three (or fewer) cards have been moved from the stock to the waste 
     * 
     */
    private void dealThreeCards()
    {
        for(int i = 0 ; i < 3 ; i++)
        {
            if(!stock.isEmpty())
            {
                waste.push(stock.pop());
                waste.peek().turnUp();
            }
        }
    }

    /** moves all cards from the waste to the stock and turns them down 
     * @postcondition all cards have been moved from the waste to the stock 
     * and turned face down 
     * 
     */
    private void resetStock()
    {
        while(!waste.isEmpty())
        {
            stock.push(waste.pop());
            stock.peek().turnDown(); 
        }
    }

    /** called when the stock is clicked
     *  deals three cards if a pile is selected and the waste is not selected and 
     *  the stock is not empty 
     *  otherwise, reset the stock 
     * @postcondition three cards have been dealt or the stock has been reset 
     */
    public void stockClicked()
    {

        if (!(display.isWasteSelected() || display.isPileSelected()))
        {
            if (! stock.isEmpty())
            {
                dealThreeCards();
            }
            else
            {
                resetStock();
            }
        }
        System.out.println("stock clicked");
    }

    /** called when the waste is clicked 
     * selects the waste if the waste is not selected or empty and a pile is not selected 
     * unselects the waste if it is selected already 
     * @postcondition the waste has been selected or unselected 
     */
    public void wasteClicked()
    {
        if(!display.isWasteSelected() && !waste.isEmpty() && !display.isPileSelected() )
            display.selectWaste();
        else if(display.isWasteSelected())
            display.unselect();

        System.out.println("waste clicked");
    }

    /** called when the given foundation is clicked 
     * the foundation is either selected or unselected if nothing else is selected, or 
     * a card is moved from the piles or the waste to the given foundation, 
     * depending on which is selected, or 
     * the player is given a victory message (if they have won) 
     * @precondition: 0<=index<4
     * @postcondition the foundation is selected or unselected, or a card is moved from 
     * the piles or the waste to the given foundation
     * @param index the index of the foundation that is clicked 
     */
    public void foundationClicked(int index)
    {
        if (!display.isPileSelected() && !display.isWasteSelected() && 
            !display.isFoundationSelected() && !foundations[index].isEmpty())
        {
            display.selectFoundation(index);
        }
        else if (!display.isPileSelected() && !display.isWasteSelected() 
            && display.isFoundationSelected())
        {
            display.unselect();
        }
        else if(display.isPileSelected() && !piles[display.selectedPile()].isEmpty() && 
            canAddToFoundation (piles[display.selectedPile()].peek(),index))
        {
            foundations[index].push(piles[display.selectedPile()].pop());
            display.unselect();
        }
        else if (display.isWasteSelected() && !waste.isEmpty() 
            && canAddToFoundation(waste.peek(), index))
        {
            foundations[index].push(waste.pop());
            display.unselect();
        }
        if (!foundations[0].isEmpty() && !foundations[1].isEmpty() 
            && !foundations[2].isEmpty() && !foundations[3].isEmpty())
        {
            if (foundations[0].peek().getRank() == 13 && foundations[1].peek().getRank() == 13 
                && foundations[2].peek().getRank() == 13 && foundations[3].peek().getRank() == 13)
            {
                System.out.println("You win! :) ");
            }
        }
        System.out.println("foundation #" + index + " clicked");
    }

    /** called when the given pile is clicked 
     * a card is moved from the waste or the foundations to the given pile 
     * if either is selected, or 
     * all face up cards from another pile are moved to the given pile 
     * if another pile is selected, or 
     * the pile is selected 
     * 
     * @precondition:  0 <= index < 7
     * @postcondition card(s) is(are) moved to the given pile, or the pile is selected
     * @param index the index of the pile that is clicked 
     */
    public void pileClicked(int index)
    {
        if(display.isWasteSelected())
        {
            if(canAddToPile(waste.peek(),index))
                piles[index].push(waste.pop());
            display.unselect();

        }
        else if (!display.isWasteSelected() && !display.isPileSelected() && 
                display.isFoundationSelected() && 
                   !foundations[display.selectedFoundation()].isEmpty())
        {
            if (canAddToPile(foundations[display.selectedFoundation()].peek(), index))
            {
                piles[index].push(foundations[display.selectedFoundation()].pop());
            }
            display.unselect();
        } 
        else 
        {
            if(!display.isPileSelected() && !piles[index].isEmpty())
            {
                if(piles[index].peek().isFaceUp())
                    display.selectPile(index);

                piles[index].peek().turnUp();

            }
            else if(display.selectedPile()==index)
                display.unselect();
            else
            {
                int otherPile = display.selectedPile();
                Stack<Card> otherStack = removeFaceUpCards(otherPile);
                if(!piles[index].isEmpty() && !otherStack.isEmpty() && 
                    canAddToPile(otherStack.peek(), index))
                    addToPile(otherStack, index);
                else if(piles[index].isEmpty())
                {
                    if(otherStack.peek().getRank()==13)
                        addToPile(otherStack, index);

                    addToPile(otherStack, otherPile);
                }
                else
                    addToPile(otherStack, otherPile);
                display.unselect();
            }
        }
        System.out.println("pile #" + index + " clicked");
    }

    /** determines if the card can be added to the given pile 
     * @precondition 0<=index<7 
     *  @param card the card to be added to the pile 
     *  @param index the index of the given pile 
     *  @return true if the card can be added to the pile; the card is of the opposite card and 
     * one rank lower than the top card on the pile 
     *          false otherwise 
     */
    private boolean canAddToPile(Card card, int index)
    {

        if(piles[index].isEmpty())
        {
            if(card.getRank()==13)
                return true;
        }
        int rank = piles[index].peek().getRank();
        if(card.getRank() == rank-1 && card.isRed()==!piles[index].peek().isRed())
            return true;
        return false;
    }

    /** removes the cards that are face up from a given pile 
     * @precondition 0<=index<7 
     * @param index the index of the pile to remove the face up cards from 
     * @return a stack with the face up cards of the given pile 
     */
    private Stack<Card> removeFaceUpCards(int index)
    {
        Stack<Card> upCards = new Stack<Card>();
        while(!piles[index].isEmpty() && piles[index].peek().isFaceUp())
        {
            upCards.push(piles[index].pop());
        }
        return upCards; 
    }

    /** adds a stack of cards to the given pile 
     * @precondition 0<=index<7
     * @postcondition the stack of cards is added to the given pile 
     * @param cards the stack of cards to add 
     * @param index the index of the given pile to add the cards to 
     * 
     */
    private void addToPile(Stack<Card> cards, int index)
    {
        while(!cards.isEmpty())
        {
            piles[index].push(cards.pop());
        }
    }

    /** determines whether a card can be added to a given foundation
     * @precondition 0<=index<4
     * @param card the card to be added 
     * @param index the index of the given foundation 
     * @return true if the card can be added to the foundation; the card is 
     * of the same suit as the top card of the foundation 
     * and is one rank higher than it 
     *              false otherwise 
     * 
     */
    private boolean canAddToFoundation(Card card, int index)
    {
        if(foundations[index].isEmpty() && card.getRank()==1)
            return true;
        else if(foundations[index].peek().getRank() == card.getRank()-1 && 
                foundations[index].peek().getSuit().equals(card.getSuit()))
            return true;
        return false;

    }
    
    /** resets the game by making a new Solitaire game 
     * 
     * @postcondition a new window of Solitaire is created that begins from the start 
     * 
     */
    public void reset()
    {
        new Solitaire();
    }

}