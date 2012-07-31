/**
 * FileAuthScanner
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package play.modules.fileauth;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import play.Logger;
import play.Play;
import play.cache.Cache;

public class FileAuthScanner {

    private static String AUTH_FILE = Play.configuration.getProperty("authfile.path");
    private static String AUTH_DELIMETER = Play.configuration.getProperty("authfile.delimeter", ":");
    private final static String AUTH_FILE_CACHE_KEY = "AUTH_FILE_USERS";

    public static Map<String, String> getUsers() {
        Map<String, String> users = Cache.get(AUTH_FILE_CACHE_KEY, HashMap.class);
        if (users == null) {
            users = scanUsers();
        }
        return users;
    }

    public static Map<String, String> scanUsers() {
        Logger.info("Scanning " + AUTH_FILE + " @" + System.currentTimeMillis());
        Map<String, String> users = new HashMap<String, String>();
        File file = new File(AUTH_FILE);
        if (!file.exists() && !file.isFile()) {
            Logger.warn(AUTH_FILE + " is not a valid Auth-File!");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String parts[];
            while ((line = br.readLine()) != null) {
                parts = line.split(AUTH_DELIMETER);
                if (parts.length == 2) {
                    users.put(parts[0].toLowerCase().trim(), parts[1].trim());
                }
            }
            Cache.set(AUTH_FILE_CACHE_KEY, users);
        } catch (FileNotFoundException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
        Logger.info("found " + users.size() + " mappings");
        return users;
    }
}
