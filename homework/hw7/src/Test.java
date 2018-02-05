import java.util.*;
import java.util.PriorityQueue;

public class Test{
    public static void main(String[] args){
        Map<Character, Integer> frequencyTable = new HashMap<Character, Integer>();
        frequencyTable.put('a', 1);
        frequencyTable.put('b', 2);
        frequencyTable.put('c', 4);
        frequencyTable.put('d', 5);
        frequencyTable.put('e', 6);
        BinaryTrie trie = new BinaryTrie(frequencyTable);
        Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
        BitSequence shouldBeA = new BitSequence("11");
        Match m = trie.longestPrefixMatch(shouldBeA);
        System.out.println((char) m.getSymbol());
        System.out.println(m.getSequence());
    }
}
