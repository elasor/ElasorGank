package com.lify.elasor.secret;

import android.util.Base64;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.KeyFactory;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author Elasor
 */
@SuppressWarnings("unused")
public class ElasorSecret {

    public static final String MODULUS = "100631058000714094813874361191853577129731636346684218206605779824931626830750623070803100189781211343851763275329364056640619755337779928985272486091431384128027213365372009648233171894708338213168824861061809490615593530405056055952622249066180336803996949444124622212096805545953751253607916170340397933039";
    public static final String EXPONENT = "26900155715313643087786516528374548998821559381075740707715132776187148793016466508650068087107695523642202737697714709374658856733792614490943874205956727606674634563665154616758939576547663715234643273055658829482813503959459653708062875625210008961239643775661357655599312857249418610810177817213648575161";
    public static final String PUBLIC_EXPONENT = "65537";

    public static String md5(String encodeStr) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(encodeStr.getBytes(Charset.forName("UTF-8")));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("there is no such algorithm which named MD5!", e);
        }

        StringBuilder hex = new StringBuilder();
        for (byte b : hash) {
            String s = String.format("%02x", b);
            hex.append(s);
        }
        return hex.toString();
    }

    //未测试
    public static String encodeBase64(String msg) {
        byte[] encode = Base64.encode(msg.getBytes(Charset.forName("utf-8")), Base64.NO_WRAP);
        return new String(encode, Charset.forName("utf-8"));
    }

    //未测试
    public static String decodeBase64(String secret) {
        return new String(Base64.decode(secret, Base64.DEFAULT), Charset.forName("utf-8"));
    }

    //未测试
    public static String encodeDES(String src, String keyStr) {
        String result = null;
        try {
            //根据算法和秘钥字符串，创建秘钥
            SecretKeySpec key = new SecretKeySpec(keyStr.getBytes(), "DES");
            //根据算法创建密码对象
            Cipher cipher = Cipher.getInstance("DES");
            //指定为编码模式，指定秘钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            //执行得到密文的字节数组
            byte[] bytes = cipher.doFinal(src.getBytes(Charset.forName("utf-8")));
            //密码也采用64种基本字符，否则会乱码
            result = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    //未测试
    public static String decodeDES(String secret, String keyStr) {
        String result = null;
        try {
            SecretKeySpec key = new SecretKeySpec(keyStr.getBytes(), "DES");
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] bytes = cipher.doFinal(Base64.decode(secret, Base64.DEFAULT));
            result = new String(bytes, Charset.forName("utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //未测试
    @Deprecated
    public static String encodeRSA(String src, String charset, String publicKeyStr) {
        String result = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(new RSAPublicKeySpec(new BigInteger(MODULUS), new BigInteger(PUBLIC_EXPONENT)));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = cipher.doFinal(src.getBytes(charset));
            result = Base64.encodeToString(bytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //未测试
    @Deprecated
    public static String decodeRSA(String secret, String charset, String privateKeyStr) {
        String result = null;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(new RSAPrivateKeySpec(new BigInteger(MODULUS), new BigInteger(EXPONENT)));

            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = cipher.doFinal(Base64.decode(secret, Base64.DEFAULT));
            result = new String(bytes, charset);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
