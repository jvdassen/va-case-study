package ch.uzh.ifi.seal.soprafs16.model;

/**
  @author Laurenz Shi
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.*;

@Entity
public class TrainLevel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private boolean marshal;

    @Column(nullable = false)
    private long gameId;

    @OneToMany
    private List<Character> characters = new ArrayList<Character>();

    @OneToMany
    private List<Loot> loot = new ArrayList<Loot>();

    @Column
    private boolean baseLevel = false;

    public Long getId() {
        return id;
    }

    public boolean isBaseLevel() {
        return baseLevel;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }

    public void setBaseLevel(boolean baseLevel) {
        this.baseLevel = baseLevel;
    }

    public List<Loot> getLoot() {
        return loot;
    }

    public List<Character> getCharacters() {
        return characters;
    }

    public void setCharacters(List<Character> characters) {
        this.characters = characters;
    }

    public void addCharacter(Character character) {
        this.characters.add(character);
    }

    public void setLoot(List<Loot> loot) {
        this.loot = loot;
    }

    public void addLoot(Loot loot) {
        this.loot.add(loot);
    }

    public void removeLoot(Loot loot)
    {
        this.loot.remove(loot);
    }

    public void removeCharacter(long userId)
    {
        for (Character character: characters)
        {
            if (character.getUserId()==userId)
            {
                characters.remove(character);
                break;
            }
        }
    }

    public boolean isMarshal() {
        return marshal;
    }

    public void setMarshal(boolean marshal) {
        this.marshal = marshal;
    }
}
