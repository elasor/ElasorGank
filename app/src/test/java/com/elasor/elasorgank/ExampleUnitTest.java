package com.elasor.elasorgank;

import com.elasor.elasorgank.util.TimeUtil;

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
        for (int i = 2000; i < 2101; i++) {
            boolean leapYear = TimeUtil.isLeapYear(i);
            System.out.println(i+" is "+leapYear);
        }
    }

    @Test
    public void test2(){
        int i = TimeUtil.daysAgo("2018-3-1");
        System.out.println(i);
    }
}