import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class BinaryTrie implements Serializable{
    public static final long serialVersionUID = 1L;
    PriorityQueue<TreeNode> pq;
    TreeNode root;

    private static class TreeNode implements Comparable, Serializable{
        public static final long serialUID = 1L;
        char ch;
        int freq;
        TreeNode left;
        TreeNode right;

        TreeNode(char ch, int freqency, TreeNode left, TreeNode right){
            this.ch = ch;
            this.freq = freqency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Object n){
            TreeNode n0 = (TreeNode)n;
            return this.freq - n0.freq;
        }
    }

    public BinaryTrie(Map<Character, Integer> frequencyTable){
        pq = new PriorityQueue<>();
        for(Character c: frequencyTable.keySet()){
            pq.add(new TreeNode(c, frequencyTable.get(c), null, null));
        }

        while(pq.size() > 1){
            root = new TreeNode('\0', 0, null, null);
            TreeNode left = pq.poll();
            TreeNode right = pq.poll();
            root.left = left;
            root.right = right;
            root.freq = left.freq + right.freq;
            pq.add(root);
        }
    }

    public Match longestPrefixMatch(BitSequence querySequence){
        Map<Character, BitSequence> map = buildLookupTable();
        BitSequence longestBS = new BitSequence("");
        Character symbol = '\0';
        for(Character c: map.keySet()){
            BitSequence cBS = map.get(c);
            int cLen = cBS.length();
            if(cLen > longestBS.length() && cLen <= querySequence.length() && querySequence.firstNBits(cLen).equals(cBS)){
                longestBS = cBS;
                symbol = c;
            }
        }
        return new Match(longestBS, symbol);
    }

    public Map<Character, BitSequence> buildLookupTable(){
        Map<Character, BitSequence> lookupTable = new HashMap<>();
        fillLoopupTable(lookupTable, root, "");
        return lookupTable;
    }

    private void fillLoopupTable(Map<Character, BitSequence> map, TreeNode tn, String str){
        if(tn.ch == '\0'){
            fillLoopupTable(map, tn.left, str + "0");
            fillLoopupTable(map, tn.right, str + "1");
        }else if(tn.ch != '\0'){
            map.put(tn.ch, new BitSequence(str));
        }
    }
}
