package creatures;

import huglife.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotSame;

public class TestClorus {
    @Test
    public void testBasics() {
        Clorus p = new Clorus(2);
        assertEquals(2, p.energy(), 0.01);
        Plip plip = new Plip(1.5);
        assertEquals(new Color(34, 0, 231), p.color());
        p.move();
        assertEquals(1.97, p.energy(), 0.01);
        p.move();
        assertEquals(1.94, p.energy(), 0.01);
        p.stay();
        assertEquals(1.93, p.energy(), 0.01);
        p.stay();
        assertEquals(1.92, p.energy(), 0.01);
        p.attack(plip);
        assertEquals(3.42, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Clorus p = new Clorus(2);
        Clorus baby = p.replicate();
        assertEquals(p.energy(), 1.0, 0.01);
        assertEquals(baby.energy(), 1.0, 0.01);
        assertNotSame(p, baby);
    }

    @Test
    public void testChoose() {
        /*If these is no empty in neighbors, Clorus stay.*/
        Clorus p = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        /*If these is some plip exist in neighbors, Clorus attack.*/
        Clorus p1 = new Clorus(1.2);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Empty());
        surrounded1.put(Direction.BOTTOM, new Plip(1));
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());

        Action actual1 = p1.chooseAction(surrounded1);
        Action expected1 = new Action(Action.ActionType.ATTACK, Direction.BOTTOM);

        assertEquals(expected1, actual1);

        /*If clorus' energy is greater than or equal to 1, Clorus replicate.*/
        Clorus p2 = new Clorus(1);
        HashMap<Direction, Occupant> surrounded2 = new HashMap<Direction, Occupant>();
        surrounded2.put(Direction.TOP, new Empty());
        surrounded2.put(Direction.BOTTOM, new Impassible());
        surrounded2.put(Direction.LEFT, new Impassible());
        surrounded2.put(Direction.RIGHT, new Impassible());

        Action actual2 = p2.chooseAction(surrounded2);
        Action expected2 = new Action(Action.ActionType.REPLICATE, Direction.TOP);

        assertEquals(expected2, actual2);

        /*If clorus' energy is smaller than 1, Clorus stay.*/
        Clorus p3 = new Clorus(0.1);
        HashMap<Direction, Occupant> surrounded3 = new HashMap<Direction, Occupant>();
        surrounded3.put(Direction.TOP, new Empty());
        surrounded3.put(Direction.BOTTOM, new Impassible());
        surrounded3.put(Direction.LEFT, new Impassible());
        surrounded3.put(Direction.RIGHT, new Impassible());

        Action actual3 = p3.chooseAction(surrounded3);
        Action expected3 = new Action(Action.ActionType.MOVE, Direction.TOP);

        assertEquals(expected3, actual3);
    }

}
