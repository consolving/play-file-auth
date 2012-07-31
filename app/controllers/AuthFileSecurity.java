/**
 * AuthFileSecurity
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.Map;
import play.modules.fileauth.FileAuthScanner;

public class AuthFileSecurity extends Secure.Security {

    public static boolean authentify(String username, String password) {
        Map<String,String> users = FileAuthScanner.getUsers();
        String userPassword = users.get(username.toLowerCase());
        return userPassword.equals(password);
    }

    public static boolean check(String profile) {
        // TODO add Group Foo here!
        return false;
    }
}
