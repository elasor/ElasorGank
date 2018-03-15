package com.lify.elasor;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.lify.elasor.msg.ElasorLog;
import com.lify.elasor.secret.ElasorSecret;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.lify.elasor.test", appContext.getPackageName());
    }

    @Test
    public void test1(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        int dp2px = ElasorUtil.dp2px(appContext, 20);
        int px2dp = ElasorUtil.px2dp(appContext, dp2px);

        ElasorLog.e("dp2px / px2dp / px22dp = "+ dp2px + "/" + px2dp + "/" );
    }

    @Test
    public void test2(){
        String haha = ElasorSecret.encodeBase64("haha");
        String haha1 = ElasorSecret.decodeBase64(haha);
        ElasorLog.e(haha+"/"+haha1);
    }

    @Test
    public void test3(){
        String haha = ElasorSecret.encodeDES("haha", "123");
        String haha1 = ElasorSecret.decodeDES(haha, "123");
        ElasorLog.e(haha+"/"+haha1);
    }
}
