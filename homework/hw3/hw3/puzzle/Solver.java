package hw3.puzzle;

import java.util.Iterator;
import edu.princeton.cs.algs4.MinPQ;

/**
 * Created by JiahaoTang on 2017/9/17.
 */
public class Solver {
    int steps;
    Iterable<WorldState> solution;

    private class SearchNode implements WorldState{
        String word;
        String startWord;
        public SearchNode pre;

        public SearchNode(String startWord, String word){
            this.word = word;
            this.startWord = startWord;
            pre = null;
        }

        @Override
        public int estimatedDistanceToGoal(){
            Word newWord = new Word(startWord, word);
            return newWord.estimatedDistanceToGoal();
        }

        @Override
        public Iterable<WorldState> neighbors(){
            Word newWord = new Word(startWord, word);
            return newWord.neighbors();
        }
    }
    /*Constructor which solves the puzzle, computing everything necessary for moves() and solution() to
    * not have to solve the problem again. Solves the puzzle using the A* algorithm. Assumes a solution
    * exists.*/
    public Solver(WorldState initial){
        solution = new MinPQ<>();
        steps = 0;

    }

    /*Returns the minimum number of moves to solve the puzzle starting at the initial WorldState.*/
    public int moves(){
        return steps;
    }

    /*Returns a sequence of WorldStates from the initial WorldState to the solution.*/
    public Iterable<WorldState> solution(){
        return solution;
    }
}
