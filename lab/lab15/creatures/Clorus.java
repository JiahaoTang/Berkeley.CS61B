package creatures;

import huglife.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

public class Clorus extends Creature{
    /** red color. */
    private int r = 34;
    /** green color. */
    private int g = 0;
    /** blue color. */
    private int b = 231;

    /** creates plip with energy equal to E. */
    public Clorus(double e) {
        super("clorus");
        energy = e;
    }

    public Clorus() {
        this(1);
    }

    @Override
    public Color color() {
        return new Color(r, g, b);
    }

    @Override
    public void move(){
        energy -= 0.03;
    }

    @Override
    public void attack(Creature c){
        energy += c.energy();
    }

    @Override
    public Clorus replicate() {
        Clorus baby =  new Clorus(energy * 0.5);
        energy *= 0.5;
        return baby;
    }

    @Override
    public void stay() {
        energy -= 0.01;
    }

    @Override
    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        List<Direction> empties = getNeighborsOfType(neighbors, "empty");
        List<Direction> plips = getNeighborsOfType(neighbors, "plip");
        if(empties.size() == 0) {
            return new Action(Action.ActionType.STAY);
        }else if(plips.size() > 0){
            int attackFlag = HugLifeUtils.randomInt(0, plips.size() - 1);
            return new Action(Action.ActionType.ATTACK, plips.get(attackFlag));
        }else if(energy() >= 1){
            int flag = HugLifeUtils.randomInt(0, empties.size() - 1);
            return new Action(Action.ActionType.REPLICATE, empties.get(flag));
        }else{
            int flag = HugLifeUtils.randomInt(0, empties.size() - 1);
            return new Action(Action.ActionType.MOVE, empties.get(flag));
        }

    }
}
