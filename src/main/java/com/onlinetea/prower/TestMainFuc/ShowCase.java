package com.onlinetea.prower.TestMainFuc;


import com.onlinetea.prower.View.SessionsVo;
import com.onlinetea.prower.View.UserInfoVo;
import org.apache.shiro.crypto.hash.SimpleHash;

public class ShowCase {

    public static void main(String[] args) {
        TestObject testObject = new TestObject();
        testObject.setNumInt(12);
        testObject.setNumObj(23);
        TestObject testObject1 = new TestObject();
        testObject1.setNumLong((long)testObject.getNumInt());
        testObject1.setNumObjToLong(Long.valueOf(String.valueOf(testObject.getNumObj())));
        System.out.println(testObject1);
    }
}
