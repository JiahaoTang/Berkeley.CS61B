/**
 * Created by Administrator on 2017/8/3.
 */
public class Palindrome {
    public static Deque<Character> wordToDeque(String word){
        LinkedListDeque words = new LinkedListDeque();

        for(int i = 0; i < word.length(); i++){
            Character character = word.charAt(i);
            words.addLast(character);
        }
        return words;
    }


    public static boolean isPalindrome(String word){
        Deque<Character> wordsToJudge = new Palindrome().wordToDeque(word);
        if(wordsToJudge.size() == 0 || wordsToJudge.size() == 1) {
            return true;
        }else if(wordsToJudge.get(wordsToJudge.size()) != wordsToJudge.get(1)) {
            return false;
        }else{
            wordsToJudge.removeFirst();
            wordsToJudge.removeLast();
            String wordString = "";
            for(int i = 1; i <= wordsToJudge.size(); i++){
                wordString += wordsToJudge.get(i);
            }
            return isPalindrome(wordString);
        }
    }

    public static boolean isPalindrome(String word, CharacterComparator cc){
        Deque<Character> wordsToJudge = new Palindrome().wordToDeque(word);
        if(wordsToJudge.size() == 0 || wordsToJudge.size() == 1) {
            return true;
        }else if(!cc.equalChars(wordsToJudge.get(wordsToJudge.size()), wordsToJudge.get(1))) {
            return false;
        }else{
            wordsToJudge.removeFirst();
            wordsToJudge.removeLast();
            String wordString = "";
            for(int i = 1; i <= wordsToJudge.size(); i++){
                wordString += wordsToJudge.get(i);
            }
            return isPalindrome(wordString, cc);
        }
    }


    public static void main(String[] args){
        Deque<Character> tangjiahao = new Palindrome().wordToDeque("abcdefedcba");
        OffByN obo = new OffByN(5);
        boolean result = isPalindrome("af4ax", obo);//return false.
        if(result == true){
            System.out.println("True");
        }else{
            System.out.println("False");
        }

    }

}
