/**
 * Created by Administrator on 2017/8/4.
 */
public class OffByN implements CharacterComparator {
    public int distance;
    public OffByN(int x){
        distance = x;
    }

    @Override
    public boolean equalChars(char x, char y){
        if((x - y) == distance || ( y - x) == distance){
            return true;
        }else{
            return false;
        }
    }
}
