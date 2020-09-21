import java.util.regex.Matcher;
import java.util.regex.Pattern;




public class RegexValidateCode {
    /**
     * The valid hexadecimal color code must satisfy the following conditions.
     * It should start from ‘#’ symbol.
     * It should be followed by the letters from a-f, A-F and/or digits from 0-9.
     * The length of the hexadecimal color code should be either 6 or 3, excluding ‘#’ symbol.
     */

    public boolean validateHexaDecimal(String str) {
        String regex = "^#([a-zA-Z0-9]{3}|[a-zA-Z0-9]{6})$" ;
        return matchString(str, regex);
    }

    /**
     * The valid domain name must satisfy the following conditions:
     *
     * The domain name should be a-z or A-Z or 0-9 and hyphen (-).
     * The domain name should be between 1 and 63 characters long.
     * The domain name should not start or end with a hyphen(-) (e.g. -geeksforgeeks.org or geeksforgeeks.org-).
     * The last TLD (Top level domain) must be at least two characters, and a maximum of 6 characters.
     * The domain name can be a subdomain (e.g. contribute.geeksforgeeks.org).
     * @param str
     * @return
     */
    public boolean validateDomainName(String str) {
        String regex = "^((?!-)[a-zA-Z0-9-]{1,63}(?<!-)\\.)+[a-zA-Z]{2,6}$";
        // explain
        String notFollowedByHyphon = "(?!-)";  // lookahead  - (?!=-) followedByHyphon
        String notPreceedByHyphon = "(?<!-)"; // lookbehind  - (?<=-) proceeded by hyphon
       return matchString(str,regex);
    }

    /**
     * The valid HTML tag must satisfy the following conditions:
     *
     * It should start with an opening tag (<).
     * It should be followed by double quotes string, or single quotes string.
     * It should not allow one double quotes string, one single quotes string or a closing tag (>) without single or double quotes enclosed.
     * It should end with a closing tag (>).
     * @param str
     * @return
     */
    public boolean validateHTML(String str) {
        //https://www.geeksforgeeks.org/how-to-validate-html-tag-using-regular-expression/?ref=leftbar-rightbar
        //String regex = "\"<(\\\"[^\\\"]*\\\"|'[^']*'|[^'\\\">])*>\"1";
        String regex = "<!?[a-zA-Z/0-9 =\"-./]*>";   // I wrote this.
        return matchString(str, regex);
    }

    /**
     * The valid image file extension must specify the following conditions:
     *
     * It should start with a string of atleast one character.
     * It should not have any white space.
     * It should be followed by a dot(.).
     * It should be end with any one of the following extensions: jpg, jpeg, png, gif, bmp.
     * @param str
     * @return
     */
    public boolean validateImageFile(String str) {
        String regex = "[^\\s]+\\.(jpe?g|png|gif|bmp)";
        return matchString(str,regex);
    }

    public boolean matchString(String str, String regex) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();

    }
    public static void main(String[] args) {
        RegexValidateCode test = new RegexValidateCode();
        System.out.println(test.validateHexaDecimal("#7732d5"));
        System.out.println(test.validateDomainName("goole.co"));
    }
}
