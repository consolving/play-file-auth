/**
 * FileAuth
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.modules.fileauth.utils.MD5Crypt;

public class FileAuth {

    public final static String AUTH_FILE_USERS_CACHE_KEY = "AUTH_FILE_USERS";
    public final static String AUTH_FILE_GROUPS_CACHE_KEY = "AUTH_FILE_USERS";

    public static Map<String, String> getUsers() {
        Map<String, String> users = Cache.get(AUTH_FILE_USERS_CACHE_KEY, HashMap.class);
        if (users == null) {
            users = scanUsers();
        }
        return users;
    }

    public static Map<String, Set<String>> getGroups() {
        Map<String, Set<String>> groups = Cache.get(AUTH_FILE_GROUPS_CACHE_KEY, HashMap.class);
        if (groups == null) {
            groups = scanGroups();
        }
        return groups;
    }

    public static boolean contains(String group, String user) {
        if (group == null || user == null) {
            return false;
        }
        Map<String, Set<String>> groups = getGroups();
        Set<String> groupUsers = groups.get(group);
        if(groupUsers == null){
            return false;
        }
        return groupUsers.contains(user);
    }

    public static boolean validate(String user, String password) {
        if (user == null || password == null) {
            return false;
        }
        Map<String, String> users = getUsers();
        String encryptedPass = users.get(user);
        if(encryptedPass == null){
            return false;
        }
        return MD5Crypt.verifyPassword(password, encryptedPass);
    }

    public static Map<String, String> scanUsers() {
        String fileName = Play.configuration.getProperty("authfile.users.path");
        String delimeter1 = Play.configuration.getProperty("authfile.users.delimeter", ":");
        Logger.info("Scanning " + fileName + " @" + System.currentTimeMillis());
        Map<String, String> users = new HashMap<String, String>();
        File file = new File(fileName);
        if (!file.exists() && !file.isFile()) {
            Logger.warn(fileName + " is not a valid Auth-File!");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String parts[];
            while ((line = br.readLine()) != null) {
                parts = line.split(delimeter1);
                if (parts.length == 2) {
                    users.put(parts[0].trim(), parts[1].trim());
                }
            }
            Cache.set(AUTH_FILE_USERS_CACHE_KEY, users);
        } catch (FileNotFoundException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
        Logger.info("found " + users.size() + " mappings");
        return users;
    }

    public static Map<String, Set<String>> scanGroups() {
        String fileName = Play.configuration.getProperty("authfile.groups.path");
        String delimeter1 = Play.configuration.getProperty("authfile.users.delimeter", ":");
        String delimeter2 = Play.configuration.getProperty("authfile.groups.delimeter", " ");
        Logger.info("Scanning " + fileName + " @" + System.currentTimeMillis());
        Map<String, Set<String>> groups = new HashMap<String, Set<String>>();
        File file = new File(fileName);
        if (!file.exists() && !file.isFile()) {
            Logger.warn(fileName + " is not a valid Auth-File!");
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            String parts[];
            String userParts[];
            Set<String> users;
            while ((line = br.readLine()) != null) {
                parts = line.split(delimeter1);
                if (parts.length == 2) {
                    userParts = parts[1].split(delimeter2);
                    if (userParts.length > 0) {
                        users = new HashSet<String>();
                        for (String user : userParts) {
                            users.add(user.trim());
                        }
                        groups.put(parts[0].trim(), users);
                    }
                }
            }
            Cache.set(AUTH_FILE_GROUPS_CACHE_KEY, groups);
        } catch (FileNotFoundException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        } catch (IOException ex) {
            Logger.error(ex.getLocalizedMessage(), ex);
        }
        Logger.info("found " + groups.size() + " mappings");
        return groups;
    }
}
