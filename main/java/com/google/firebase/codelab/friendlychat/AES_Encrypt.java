package com.google.firebase.codelab.friendlychat;

import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.widget.EditText;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AES_Encrypt {
    private static SecretKeySpec secretKey;
    private static byte[] key;

    public static void setKey(String myKey)
    {
        MessageDigest sha = null;
        try {
            key = myKey.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            secretKey = new SecretKeySpec(key, "AES");
        }
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static Editable encrypt(Editable strToEncrypt, String secret)
    {
        try
        {
            String strEncrypt = strToEncrypt.toString();
            byte[] message = strEncrypt.getBytes("UTF-8");
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            String encrypted = Base64.getEncoder().encodeToString(cipher.doFinal(message));
            Editable edtEncrypted = new SpannableStringBuilder(encrypted);
            return edtEncrypted;
        }
        catch (Exception e)
        {
            System.out.println("Error while encrypting: " + e.toString());
        }
        return null;
    }

    public static Editable decrypt(String strToDecrypt, String secret)
    {
        try
        {
            setKey(secret);
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            String decrypt = new String(cipher.doFinal(Base64.getDecoder().decode(strToDecrypt)));
            Editable finalMessage = new SpannableStringBuilder(decrypt);
            return finalMessage;
        }
        catch (Exception e)
        {
            System.out.println("Error while decrypting: " + e.toString());
        }
        return null;
    }
}


