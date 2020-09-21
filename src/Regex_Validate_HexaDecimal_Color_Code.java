import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The valid hexadecimal color code must satisfy the following conditions. 
 * It should start from ‘#’ symbol.
 * It should be followed by the letters from a-f, A-F and/or digits from 0-9.
 * The length of the hexadecimal color code should be either 6 or 3, excluding ‘#’ symbol.
 */


public class Regex_Validate_HexaDecimal_Color_Code {

    public boolean validateHexaDecimal(String str) {
        String regex = "^#([a-zA-Z0-9]{3}|[a-zA-Z0-9]{6})$" ;
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();

    }

    public static void main(String[] args) {
        Regex_Validate_HexaDecimal_Color_Code test = new Regex_Validate_HexaDecimal_Color_Code();
        System.out.println(test.validateHexaDecimal("#7732d5"));
        System.out.println(test.validateHexaDecimal("#099"));
    }
}
