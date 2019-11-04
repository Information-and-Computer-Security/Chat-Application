package com.google.firebase.codelab.friendlychat;

import android.support.v7.app.AppCompatActivity;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.Signature;


public class GenerateCipher extends AppCompatActivity {

    public static KeyPairGenerator getKeypair() throws Exception{
        //Creating a Signature object
        Signature sign = Signature.getInstance("SHA256withRSA");

        //Creating KeyPair generator object
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        //Initializing the key pair generator
        keyPairGen.initialize(2048);
//
//        //Generating the pair of keys
//        KeyPair pair = keyPairGen.generateKeyPair();

        return keyPairGen;

    }
}
