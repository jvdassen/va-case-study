package ch.uzh.ifi.seal.soprafs16.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
public class ActionCard extends Card {

    @Column
    private long userId;

    @Column
    private boolean visible;

    @Enumerated(EnumType.STRING)
    private EAction eAction;

    @Column
    private int cardNum;

    @Enumerated(EnumType.STRING)
    private ECharacter character;

    public int getCardNum() {
        return cardNum;
    }

    public void setCardNum(int cardNum) {
        this.cardNum = cardNum;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public EAction geteAction() {
        return eAction;
    }

    public void seteAction(EAction eAction) {
        this.eAction = eAction;
    }

    // public void doAction(){};

    public ECharacter getCharacter()
    {
        return character;
    }

    public void setCharacter(ECharacter character)
    {
        this.character = character;
    }
}
