package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

public class DecryptText extends AppCompatActivity {

    public static Editable decryptText(String args, byte[] privateKeyBytes) throws Exception {

        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        PrivateKey privateKey = kf.generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));

        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA");

        //Initializing the same cipher for decryption
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        //Decrypting the text
        byte[] cipherText = args.getBytes();
        byte[] decipheredText = cipher.doFinal(cipherText);
        System.out.println(decipheredText);

        String plainText = new String(decipheredText);
        Editable finalMessage = new SpannableStringBuilder(plainText);
        System.out.println(finalMessage);

        return finalMessage;

    }
}
