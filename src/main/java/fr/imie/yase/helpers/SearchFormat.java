package fr.imie.yase.helpers;

import java.util.regex.Pattern;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class SearchFormat {

    public String boldKeywords(String input, String keywords) {
        String output = "";
        String[] keywordsArray = keywords.split(" ");

        for (int ii = 0; ii < keywordsArray.length; ii++) {
            keywordsArray[ii] = "(" + keywordsArray[ii] + ")";
            keywordsArray[ii] += "|"+ removeDiacriticalMarks(keywordsArray[ii]);
        }

        String regex = "(?ui)(" + my_implode("|", keywordsArray) + ")";
        output = input.replaceAll(regex, "<b>$1</b>");

        System.out.println("======");
        System.out.println(regex);
        System.out.println(output);
        System.out.println("======");
        return output;
    }

    private String my_implode(String spacer,String[] in_array){

        String res = "";

        for(int i = 0 ; i < in_array.length ; i++){

            if( !res.equals("") ){
                res += spacer;
            }
            res += in_array[i];
        }

        return res;
    }

    public static String removeDiacriticalMarks(String string) {
        return Normalizer.normalize(string, Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }
}
