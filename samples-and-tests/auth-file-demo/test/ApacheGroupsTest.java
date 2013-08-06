
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Logger;
import play.Play;
import play.cache.Cache;
import play.modules.fileauth.FileAuth;
import play.test.UnitTest;

/**
 * ApacheGroupsTest 03.08.2012
 *
 * @author Philipp Haussleiter
 *
 */
public class ApacheGroupsTest extends UnitTest {

    @BeforeClass
    public static void setup() {
        Play.configuration.setProperty("authfile.groups.path", Play.applicationPath + "/test/htgroups");
        Play.configuration.setProperty("authfile.users.path", Play.applicationPath + "/test/htusers");
        Play.configuration.setProperty("authfile.users.delimeter", ":");
        Play.configuration.setProperty("authfile.groups.delimeter", " ");
        Cache.safeDelete(FileAuth.AUTH_FILE_GROUPS_CACHE_KEY);
        Cache.safeDelete(FileAuth.AUTH_FILE_USERS_CACHE_KEY);
        FileAuth.scanGroups();
        FileAuth.scanUsers();
    }

    @Test
    public void testGroupsRead() {
        Logger.info("testGroupsRead");
        FileAuth.scanGroups();
        Map<String, Set<String>> groups = Cache.get(FileAuth.AUTH_FILE_GROUPS_CACHE_KEY, HashMap.class);
        assertNotNull(groups);
        int groupSize = groups.size();
        assertTrue(String.format("but was %d ", groupSize), groupSize > 0);
    }

    @Test
    public void testGroupContainsUser() {
        FileAuth.scanGroups();
        FileAuth.scanUsers();
        String user = "user";
        String group = "users";
        assertTrue(String.format("u: %s, g: %s", user, group), FileAuth.contains(group, user));
        user = "root";
        assertTrue(String.format("u: %s, g: %s", user, group), FileAuth.contains(group, user));
    }

    @Test
    public void testGroupNotContainsUser() {
        FileAuth.scanGroups();
        FileAuth.scanUsers();
        String user = "user";
        String group = "root";
        assertFalse(String.format("u: %s, g: %s", user, group), FileAuth.contains(group, user));
    }

    public void testGroupNotExists() {
        FileAuth.scanGroups();
        FileAuth.scanUsers();
        String user = "user";
        String group = "user";
        assertFalse(String.format("u: %s, g: %s", user, group), FileAuth.contains(group, user));
    }
}
