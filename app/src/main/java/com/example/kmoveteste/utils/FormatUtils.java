package com.example.kmoveteste.utils;

import java.text.NumberFormat;
import java.util.Locale;

public class FormatUtils {
    public static String moeda(double v){
        NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt","BR"));
        return nf.format(v);
    }
}
