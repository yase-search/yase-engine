package fr.imie.yase.helpers;

public class FormatUtil {

    /**
     * Permet de savoir si il s'agit d'un Integer
     * @param s String
     * @return true si Integer
     */
    public static boolean isInteger(String s) {
        boolean isValidInteger = false;
        try
        {
           Integer.parseInt(s);
           isValidInteger = true;
        }
        catch (NumberFormatException ex)
        {
           // s is not an integer
        }
   
        return isValidInteger;
     }
    
}
