import java.util.ArrayList;
import java.util.Map;

public class HuffmanDecoder {
    public static void main(String[] args){
        ObjectReader or = new ObjectReader(args[0]);
        Object trie = or.readObject();
        Object size = or.readObject();
        Object bs = or.readObject();
        BitSequence bitSequence = (BitSequence)bs;
        char[] list = new char[(int)size];
        for(int i = 0; i < (int)size; i++){
            Match mc = ((BinaryTrie)trie).longestPrefixMatch(bitSequence);
            list[i] = mc.getSymbol();
            bitSequence = bitSequence.allButFirstNBits(mc.getSequence().length());
        }
        FileUtils.writeCharArray(args[1], list);
    }
}
