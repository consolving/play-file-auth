/**
 * AuthFileSecurity
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package controllers;

import java.util.Map;
import play.modules.fileauth.FileAuth;

/**
 * This Class provides basice Security Functions for the Play! Security Plugin.
 * @author Philipp Haußleiter
 * <p>
 * Usage:
 * @With(Secure.class)
 * public class Application extends Controller {
 * ...
 * }
 *
 * </p>
 */
public class AuthFileSecurity extends Secure.Security {

    /**
     * Authentify an user against the given AuthFile.
     * @param username the given username.
     * @param password the given password.
     * @return true if username and password matches, otherwise false.
     */
    public static boolean authentify(String username, String password) {
        Map<String,String> users = FileAuth.getUsers();
        String userPassword = users.get(username.toLowerCase());
        return userPassword.equals(password);
    }

    /**
     * Checks if the current user (that is loged in) has the profile (e.g. is in the group).
     * @param profile
     * @return true if the current user has the profile, otherwise false.
     */
    public static boolean check(String profile) {
        String user = connected();
        return FileAuth.contains(profile, user);
    }
}
