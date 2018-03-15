package com.lify.elasor;

import com.lify.elasor.secret.ElasorSecret;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test1(){
        String haha = ElasorSecret.encodeBase64("haha");
//        String haha1 = ElasorSecret.decodeBase64("haha");

        System.out.println(haha+"\n");
    }

    @Test
    public void test2(){
//        ElasorIO.writeFile(new File("C:\\Users\\Administrator\\Desktop\\Java Web\\test\\test1"));
    }
}