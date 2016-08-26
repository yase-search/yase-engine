package fr.imie.yase.helpers;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Cleaner;
import org.jsoup.safety.Whitelist;
import org.jsoup.*;

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

    public String pertinentExtract(String description, String content, String keywords) {

        String[] keywordsArray = keywords.split(" ");
        for (int ii = 0; ii < keywordsArray.length; ii++) {
            keywordsArray[ii] = "(" + keywordsArray[ii] + ")";
            keywordsArray[ii] += "|"+ removeDiacriticalMarks(keywordsArray[ii]);
        }
        String regex = "(?ui).*(" + my_implode("|", keywordsArray) + ").*";

        if (description.matches(regex)) {
            return description;
        }

        Document doc = Jsoup.parse(content);
        doc = new Cleaner(Whitelist.none()).clean(doc);
        content = doc.body().html();
        String shortContent = StringUtils.abbreviate(content, 255);

        if (!StringUtils.abbreviate(content, 255).matches(regex)) {

            int index = content.indexOf(keywords.split(" ")[0]);
            if (index > -1) {
                int beginning = Math.max(index - 100, 0);
                int ending = Math.min(index + 100, content.length());

                shortContent = "[...]" + StringUtils.abbreviate(content.substring(beginning, ending), 255);
            }
        }

        return shortContent;
    }
}
