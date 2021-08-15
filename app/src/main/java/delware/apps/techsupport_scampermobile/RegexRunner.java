package delware.apps.techsupport_scampermobile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexRunner {
    final String regexUserName = "^([\\w_]{4,12})$";
    final String regexPassword = "^((?=(?:.*[A-Z]){1,})(?=(?:.*[a-z]){3,})(?=(?:.*[0-9]){1,})(?=(?:.*[@#$%!^&+=()}-]){1,}).{1,})$";
    private  static Matcher matcher(String strLine, String strRegex) {
        Pattern p = null;
        Matcher m = null;

        p = Pattern.compile(strRegex);
        m = p.matcher(strLine);
        return m;
    }
    public boolean isValidUserName(String username){
      Matcher m = matcher(username, regexUserName);
      try {
          if (m.find() & m.group().equals(username)){
              return true;
          }else {
              System.out.println("Username must be at least 4 characters long, contain no white-space, and does not exceed 12 characters");
              return false;
          }

      }catch(Exception e){
          System.out.println("Username must be at least 4 characters long, contain no white-space, and does not exceed 12 characters");
          return false;
      }
    }
    public boolean isValidPassword(String password){
        Matcher m = matcher(password, regexPassword);
        try {
            if (m.find() & m.group().equals(password)){
                return true;
            }else {
                System.out.println("Password must have at least 1 capital, lowercase, number, and special character");
                return false;
            }

        }catch(Exception e){
            System.out.println("Password must have at least 1 capital, lowercase, number, and special character");
            return false;
        }
    }
}
