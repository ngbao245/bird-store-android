package com.example.birdstoreandroid;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class TextUtils {
    public static String normalize(String text) {
        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("").replaceAll("đ", "d").replaceAll("Đ", "D");
    }
}
