package com.unitn.francesco.rendezvous;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;


public class TagToken {

    public static List<String> getListTags(String string){
        List<String> tokens = new ArrayList<String>();
        StringTokenizer tokenizer = new StringTokenizer(string, ",");
        while (tokenizer.hasMoreTokens()){
            String token = tokenizer.nextToken();
            token = token.replaceAll("\\s+","");
            token = token.toLowerCase();
            tokens.add(token);
        }
        return tokens;
    }

}
