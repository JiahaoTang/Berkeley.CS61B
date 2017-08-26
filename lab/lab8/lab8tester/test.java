package lab8tester;

import lab8.BSTMap;

/**
 * Created by Administrator on 2017/8/25.
 */
public class test {
    public static void main(String[] args) {
        BSTMap<String, Integer> testTree = new BSTMap<String, Integer>();
        for(int i = 1; i <= 10; i++){
            testTree.put("hi" + i, i);
        }
    }
}
