package creatures;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashMap;
import java.awt.Color;
import huglife.Direction;
import huglife.Action;
import huglife.Occupant;
import huglife.Impassible;
import huglife.Empty;

/** Tests the plip class   
 *  @authr FIXME
 */

public class TestPlip {

    /* Replace with the magic word given in lab.
     * If you are submitting early, just put in "early" */
    public static final String MAGIC_WORD = "";

    @Test
    public void testBasics() {
        Plip p = new Plip(2);
        assertEquals(2, p.energy(), 0.01);
        assertEquals(new Color(99, 255, 76), p.color());
        p.move();
        assertEquals(1.85, p.energy(), 0.01);
        p.move();
        assertEquals(1.70, p.energy(), 0.01);
        p.stay();
        assertEquals(1.90, p.energy(), 0.01);
        p.stay();
        assertEquals(2.00, p.energy(), 0.01);
    }

    @Test
    public void testReplicate() {
        Plip p = new Plip(2);
        Plip baby = p.replicate();
        assertEquals(p.energy(), 1.0, 0.01);
        assertEquals(baby.energy(), 1.0, 0.01);
        assertNotSame(p, baby);
    }

    @Test
    public void testChoose() {
        Plip p = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded = new HashMap<Direction, Occupant>();
        surrounded.put(Direction.TOP, new Impassible());
        surrounded.put(Direction.BOTTOM, new Impassible());
        surrounded.put(Direction.LEFT, new Impassible());
        surrounded.put(Direction.RIGHT, new Impassible());

        //You can create new empties with new Empty();
        //Despite what the spec says, you cannot test for Cloruses nearby yet.
        //Sorry!  

        Action actual = p.chooseAction(surrounded);
        Action expected = new Action(Action.ActionType.STAY);

        assertEquals(expected, actual);

        /*If the energy is bigger than 1.*/
        Plip p0 = new Plip(1.2);
        HashMap<Direction, Occupant> surrounded0 = new HashMap<Direction, Occupant>();
        surrounded0.put(Direction.TOP, new Impassible());
        surrounded0.put(Direction.BOTTOM, new Empty());
        surrounded0.put(Direction.LEFT, new Impassible());
        surrounded0.put(Direction.RIGHT, new Impassible());

        Action actual0 = p0.chooseAction(surrounded0);
        Action expected0 = new Action(Action.ActionType.STAY);

        assertNotEquals(expected0, actual0);

        /*If the energy is smaller than 1. It will stay.*/
        Plip p1 = new Plip(0.2);
        HashMap<Direction, Occupant> surrounded1 = new HashMap<Direction, Occupant>();
        surrounded1.put(Direction.TOP, new Impassible());
        surrounded1.put(Direction.BOTTOM, new Empty());
        surrounded1.put(Direction.LEFT, new Impassible());
        surrounded1.put(Direction.RIGHT, new Impassible());

        Action actual1 = p1.chooseAction(surrounded1);
        Action expected1 = new Action(Action.ActionType.STAY);

        assertEquals(expected1, actual1);
    }

    public static void main(String[] args) {
        System.exit(jh61b.junit.textui.runClasses(TestPlip.class));
    }
} 
