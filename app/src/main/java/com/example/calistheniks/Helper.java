package com.example.calistheniks;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
Clase final, esto hace que la clase no se pueda heredar
 */
final public class Helper {

    /*
    Constructor privado, esto impide que la clase se instancie
     */
    private Helper() {}

    /**
     * Metodo que encripta una cadena de texto en md5
     * @params
     * @return
     */
    public static String MD5Hash(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    public static String baseUrl() {
        //return "https://zavaletazea.dev/awos/carrito/";
        return "http://dtai.uteq.edu.mx/~abesan201/Calisthenix/back/";
    }
}
