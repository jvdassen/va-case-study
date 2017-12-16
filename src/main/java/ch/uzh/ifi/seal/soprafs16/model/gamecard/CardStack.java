package ch.uzh.ifi.seal.soprafs16.model.gamecard;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import ch.uzh.ifi.seal.soprafs16.model.dto.Move;
import ch.uzh.ifi.seal.soprafs16.model.game.Round;
import ch.uzh.ifi.seal.soprafs16.model.lootobject.Loot;

@Entity
public class CardStack implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private long gameId;

    @OneToMany
    private List<ActionCard> acStack = new ArrayList<ActionCard>();

    @OneToMany
    private List<RoundCard> rcStack = new ArrayList<RoundCard>();

    @OneToMany
    private List<AmmoCard> amcStack = new ArrayList<AmmoCard>();

    @OneToOne
    private Loot lockbox;

    @OneToMany
    private List<Move> moveStack = new ArrayList<Move>();

    // Only for that one special Event
    @ElementCollection
    private List<Long> ransom = new ArrayList<Long>();

    public List<Long> getRansom()
    {
        return ransom;
    }

    public void addRansom(long userId)
    {
        ransom.add(userId);
    }

    public Long getId() {
        return id;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    /* Add an action card to stack.*/
    public void addActionCardToStack(ActionCard actionCard) {
        acStack.add(actionCard);
    }

    /*clear the whole action card stack*/
    public void clearActionCardStack() {
        acStack.clear();
    }

    public List<ActionCard> getAcStack() {
        return acStack;
    }

    public List<RoundCard> getRcStack() {
        return rcStack;
    }

    public List<AmmoCard> getAmcStack() {
        return amcStack;
    }

    public Loot getLockbox() {
        return lockbox;
    }

    public void setLockbox(Loot lockbox) {
        this.lockbox = lockbox;
    }

    /* @return ActionCard: gets the last action card from the stack*/
    public ActionCard getLastActionCardFromStack() {
        if (acStack.size() > 0) {
            return acStack.get(acStack.size() - 1);
        } else {
            return null;
        }
    }

    /*Adds a roundcard to the stack.*/
    public void addRoundCardToStack(RoundCard roundCard) {
        rcStack.add(roundCard);
    }

    /*Get the roundcard of a certain round.
    @param round: starts with 1 not with 0
     */
    public RoundCard getRoundCardOfRound(int round) {
        if (acStack.size() > 0 && round > 0) {
            return rcStack.get(round - 1);
        } else {
            return null;
        }
    }

    // That method could be wrong because of the references
    public AmmoCard getAmmoCard() {
        if (amcStack.size() > 0) {
            return amcStack.get(amcStack.size()-1);
        }
        // All AmmoCards played
        else {
            return null;
        }
    }

    public void removeLastAmmoCard()
    {
        amcStack.remove(amcStack.size()-1);
    }

    public void addAmmoCard(AmmoCard ammoCard) {
        amcStack.add(ammoCard);
    }

    public List<Move> getMoveStack() {
        return moveStack;
    }

    // probably not needed anymore
    public Move getLastMove() {
        if (moveStack.size() > 0) {
            return moveStack.get(moveStack.size() - 1);
        } else {
            return null;
        }

    }

    public void addMove(Move move) {
        moveStack.add(move);
    }

    /* get the current roundcard.
    @param round: round object from gamestate
     */
    public RoundCard getCurrentRoundCard(Round round) {
        if (rcStack.size() > 1)
        {
            switch (round.getCurrentRound())
            {
                case ONE:
                {
                    return rcStack.get(4);
                }
                case TWO:
                {
                    return rcStack.get(3);
                }
                case THREE:
                {
                    return rcStack.get(2);
                }
                case FOUR:
                {
                    return rcStack.get(1);
                }
                case FIVE:
                {
                    return rcStack.get(0);
                }
			default:
				break;
            }
        }
        //short game
        else
        {
            return rcStack.get(0);
        }
        return null;
    }

    /*Check if an actioncard is in the stack
    * @param Actioncard: the action card that one would like to know if it is in the stack
   */
    public boolean checkACIfInStack(ActionCard actionCard) {
        for (ActionCard ac : acStack) {
            if (actionCard.equals(ac)) {
                return true;
            }
        }
        return false;
    }

    /*For ACTION phase, removes the first card of the stack
     */
    public void removePlayedACCard() {
        acStack.remove(0);
    }

    /*Clears the last move in the move stack*/
    public void clearPlayedMove() {
        moveStack.remove(moveStack.size() - 1);
    }

    public void clearMoveStack() {
        moveStack.clear();
    }

    /*For ACTION phase, returns the first card of the stack*/
    public ActionCard getFirstActionCard() {
        if (acStack.size() > 0) {
            return acStack.get(0);
        } else {
            return null;
        }
    }

    /* Remove a move from the move stack.
    @param Move: the move that should get removed, important is id
     */
    public void removeMove(Move move)
    {
        moveStack.remove(move);
    }
}
