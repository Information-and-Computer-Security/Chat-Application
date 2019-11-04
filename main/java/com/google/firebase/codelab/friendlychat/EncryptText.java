package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.SpannableStringBuilder;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

//import static com.google.firebase.codelab.friendlychat.MainActivity.pair;

public class EncryptText extends AppCompatActivity {

    protected static Editable encryptText(Editable args, byte[] publicKeyBytes) throws Exception {

        KeyFactory kf = KeyFactory.getInstance("RSA"); // or "EC" or whatever
        PublicKey publicKey = kf.generatePublic(new X509EncodedKeySpec(publicKeyBytes));

        //Getting the public key from the key pair
//        PublicKey publicKey = pair.getPublic();
        System.out.println(publicKey);

        //Creating a Cipher object
        Cipher cipher = Cipher.getInstance("RSA");

        //Initializing a Cipher object
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        //Adding data to the cipher
        String message = args.toString();
        byte[] input = message.getBytes();
        cipher.update(input);

        //encrypting the data
        byte[] cipherText = cipher.doFinal();
        System.out.println(new String(cipherText, "UTF8"));
        String textMessage = new String(cipherText);
        System.out.println(textMessage);

        Editable finalMessage = new SpannableStringBuilder(textMessage);
        System.out.println(finalMessage);


        System.out.println("\n****The Cipher Class Executed\n");
        return finalMessage;

    }
}
