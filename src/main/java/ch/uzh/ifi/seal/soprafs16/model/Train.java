package ch.uzh.ifi.seal.soprafs16.model;

/**
 * Created by Laurenz on 13/04/16.
 */

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Train implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<TrainWagon> wagons = new ArrayList<TrainWagon>();

    @Column(nullable = false)
    private long gameId;

    public Long getId() {
        return id;
    }

    public List<TrainWagon> getWagons() {
        return wagons;
    }

    public void setWagons(List<TrainWagon> wagons) {
        this.wagons = wagons;
    }

    public long getGameId() {
        return gameId;
    }

    public void setGameId(long gameId) {
        this.gameId = gameId;
    }


    /* Move a player to another place in the train
        @param character: the character that should be moved
        @param userPosition: the position where the player was before removal
        @param left: the direction the player wants to move
     */
    public void moveUser(Character character, int userPosition, boolean left)
    {
        if (left)
        {
            // Kann nicht mehr nach links
            if (userPosition <=1)
            {
                // do nothing
                wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
            }
            else
            {
                if (walkedIntoMarshal(userPosition, left))
                {
                    wagons.get(userPosition/2-1).getTrainLevels().get(1).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2-1).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
            }
        }
        else
        {
            if (userPosition>=8)
            {
                // do nothing
                wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
            }
            else
            {
                if (walkedIntoMarshal(userPosition, left))
                {
                    wagons.get(userPosition/2+1).getTrainLevels().get(1).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2+1).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
            }
        }
    }

    /*Move a user after a given move shema.
    @ param Move: Should be childclass DTOMove, will define where the player gets moved to
     */
    public void moveUser(Character character, int userPosition, Move move)
    {
        if (userPosition%2==0) {
            if (((DTOMove) move).isLeft()) {
                // Kann nicht mehr nach links

                if (userPosition <=1)
                {
                    //do nothing
                    wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2-1).getTrainLevels().get(0).addCharacter(character);
                }
            }
            else
            {

                if (userPosition>=8)
                {
                    //do nothing
                    wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2+1).getTrainLevels().get(0).addCharacter(character);
                }
            }
        }
        else {
            if (((DTOMove) move).isLeft()) {
                // Kann nicht mehr nach links

                if (1 >= userPosition)
                {
                    //do nothing
                    wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2-((DTOMove) move).getDistance()).getTrainLevels().get(1).addCharacter(character);
                }
            }
            else
            {

                if (userPosition >= 8)
                {
                    //do nothing
                    wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).addCharacter(character);
                }
                else
                {
                    wagons.get(userPosition/2+((DTOMove) move).getDistance()).getTrainLevels().get(1).addCharacter(character);
                }
            }

            }

    }

    /* Check if a player will walk into the marshal after his move
    *  @param userPosition: the position the user is right now
    *  @param left: direction the player will walk
    */
    public boolean walkedIntoMarshal(int userPosition, boolean left)
    {
        if (left)
        {
            if (wagons.get(userPosition/2-1).getTrainLevels().get(userPosition%2).isMarshal())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {

            if (wagons.get(userPosition/2+1).getTrainLevels().get(userPosition%2).isMarshal())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /* Locomotive base: 0
        Locomotive top: 1
        Wagon 1 base: 2
        Wagon 1 top: 3
        Wagon 2 base: 4
        Wagon 2 top: 5
        Wagon 3 base: 6
        Wagon 3 top: 7
        Wagon 4 base: 8
        Wagon 4 top: 9
        Find a user in a train, made as helping hand for several occasions.
        @return int:The list up there describes which value means which position.
     */
    public int findUserInTrain(long userId)
    {
        int counter = 0;
        for (TrainWagon tw : wagons)
        {
            for (TrainLevel tl: tw.getTrainLevels())
            {
                for (Character character : tl.getCharacters())
                {
                    if (character.getUserId() == userId){
                        return counter;
                    }
                }
                counter++;
            }
        }
        //not good
        return -1;
    }

    /*Get a certain character from the train. Similar to findUserInTrain but focused more one the characters.
    @return Character: returns the character of a user
     */
    public Character getCharacterInTrain(long userId)
    {
        int userPosition = findUserInTrain(userId);
        for (Character character : wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).getCharacters())
        {
            if (character.getUserId()==userId)
            {
                return character;
            }
        }
        return null;
    }
    /*Remove a character from a train.*/
    public void removeCharacterInTrain(long userId)
    {
        int userPosition = findUserInTrain(userId);
        wagons.get(userPosition/2).getTrainLevels().get(userPosition%2).removeCharacter(userId);
    }

    /* Moves a player to the other level.
    The Move dto is pretty much useless in this case, only one way possible. Still important for communication reasons.
    @param DTOClimb: Signals that the player has to get moved to top or opposite
     */
    
    public void climbCharacter(Character character, int userPosition)
    {
        if (userPosition%2==0)
        {
            wagons.get(userPosition/2).getTrainLevels().get(1).addCharacter(character);
        }
        else
        {
            wagons.get(userPosition/2).getTrainLevels().get(0).addCharacter(character);
        }
    }

/*    public void climbUser(Move move)
    {
        int userPosition = findUserInTrain(move.getUserId());
        if (userPosition%2==0)
        {
            Character character = getCharacterInTrain(move.getUserId());
            removeCharacterInTrain(move.getUserId());
            wagons.get(userPosition/2).getTrainLevels().get(1).addCharacter(character);
        }
        else
        {
            Character character = getCharacterInTrain(move.getUserId());
            removeCharacterInTrain(move.getUserId());

            wagons.get(userPosition/2).getTrainLevels().get(0).addCharacter(character);
        }
    }*/



    /*Finds the position of the marshal in a train.
    @return int: returns the position of the marshal, for more information check list in findUserInTrain
     */
    public int findMarshal()
    {
        int counter = 0;
        for (TrainWagon tw : wagons)
        {
            for (TrainLevel tl: tw.getTrainLevels())
            {
                if(tl.isMarshal())
                {
                    return counter;
                }
                counter++;
            }
        }
        //not good
        return -1;
    }
    /*Move the marshal to an other wagon, simple boolean is enough another possibility isn't there.
    @ param bool left: if not left then right
     */
    public void moveMarshal(boolean left)
    {
        int marshalPosition = findMarshal();
        if (left)
        {
            wagons.get((marshalPosition-2) / 2).getTrainLevels().get(0).setMarshal(true);
            wagons.get((marshalPosition) / 2).getTrainLevels().get(0).setMarshal(false);
        }
        else
        {
            wagons.get((marshalPosition+2) / 2).getTrainLevels().get(0).setMarshal(true);
            wagons.get((marshalPosition) / 2).getTrainLevels().get(0).setMarshal(false);
        }
    }

    /* If a marshal moves into a wagon where players are, they have to get moved to the top, this method does that.*/
    public void addFleeFromMarshal(List<Character> characters)
    {
        int marshalPosition = findMarshal();
        for (Character character:characters)
        {
            wagons.get(marshalPosition / 2).getTrainLevels().get(1).addCharacter(character);
        }
    }

    /* Remove a list of characters from the train postition of the marshal */
    public void removeFleeFromMarshal(List<Character> characters)
    {
        int marshalPosition = findMarshal();
        for (Character character: characters)
        {
            wagons.get(marshalPosition/2).getTrainLevels().get(0).removeCharacter(character.getUserId());
        }
    }

    /* Get all characters that are on a top level of the train */
    public List<Character> getCharactersOnTop()
    {
        List<Character> characters = new ArrayList<Character>();
        for (TrainWagon tW : wagons)
        {
            if (tW.getTrainLevels().get(1).getCharacters().size()>0)
            {
                for (Character character : tW.getTrainLevels().get(1).getCharacters())
                {
                    characters.add(character);
                }
            }
        }
        return characters;
    }

    /* Add a character to the train to the given position
    * @param character: the character that should be added to the train
    * @param position: the position the character should be added
    */
    public void addCharacter(Character character, int position)
    {
        wagons.get(position/2).getTrainLevels().get(position%2).addCharacter(character);
    }

    /* Get all characters that are on the base level, for special event BRAKE*/
    public List<Character> getCharactersOnBase()
    {
        List<Character> characters = new ArrayList<Character>();
        for (TrainWagon tW : wagons)
        {
            if (tW.getTrainLevels().get(1).getCharacters().size()>0)
            {
                for (Character character : tW.getTrainLevels().get(1).getCharacters())
                {
                    characters.add(character);
                }
            }
        }
        return characters;
    }
}
