import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HuffmanEncoder {
    public static Map<Character, Integer> buildFrequencyTable(char[] inputSymbols){
        Map<Character, Integer> map = new HashMap<>();
        for(Character c : inputSymbols){
            if(!map.containsKey(c)){
                map.put(c, 1);
            }else{
                map.put(c, map.get(c) + 1);
            }
        }
        return map;
    }

    public static void main(String[] args){
        char[] chars = FileUtils.readFile(args[0]);
        Map<Character, Integer> map = buildFrequencyTable(chars);
        BinaryTrie trie = new BinaryTrie(map);
        ObjectWriter ow = new ObjectWriter(args[0] + ".huf");
        ow.writeObject(trie);
        ow.writeObject(chars.length);
        Map<Character, BitSequence> lookupTable = trie.buildLookupTable();
        List<BitSequence> list = new ArrayList<>();
        for(Character c : chars){
            list.add(lookupTable.get(c));
        }
        BitSequence allBS = BitSequence.assemble(list);
        ow.writeObject(allBS);
    }
}
