package com.lify.elasor.io;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author Elasor
 */
@SuppressWarnings({"all"})
public class ElasorIO {

    public static void writeFile(File target, byte[] bs){
        FileOutputStream fos = null;

        try {
            fos = new FileOutputStream(target);
            fos.write(bs);
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeFile(File target, InputStream is, OnWritingListener onWritingListener){
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(target);
            byte[] bs = new byte[1024];
            int len;
            int available = is.available();
            int current = 0;
            while((len = is.read(bs)) != -1){
                fos.write(bs, 0, len);
                current += len;
                onWritingListener.onWriting(current, available);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != is){
                    is.close();
                }
                if(null != fos){
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public interface OnWritingListener{
        void onWriting(int currentLength, int totalLength);
    }
}
