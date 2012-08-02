
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Play;
import play.cache.Cache;
import play.modules.fileauth.FileAuth;
import play.test.UnitTest;

/**
 * HtGroupsTest
 * 03.08.2012
 * @author Philipp Haussleiter
 *
 */
public class HtGroupsTest extends UnitTest {

    @BeforeClass
    public static void setup() {
        Play.configuration.setProperty("authfile.groups.path", Play.applicationPath + "/test/htgroups");
        Play.configuration.setProperty("authfile.users.delimeter", ":");
        Play.configuration.setProperty("authfile.groups.delimeter", " ");
    }

    @Test
    public void testUsersRead() {
        FileAuth.scanGroups();
        Map<String, Set<String>> groups = Cache.get(FileAuth.AUTH_FILE_GROUPS_CACHE_KEY, HashMap.class);
        assertNotNull(groups);
    }

    @Test
    public void testGroupContainsUser() {
        String user = "user";
        String group = "users";
        assertTrue(FileAuth.contains(group, user));
        user = "root";
        assertTrue(FileAuth.contains(group, user));
    }

    @Test
    public void testGroupNotContainsUser() {
        String user = "user";
        String group = "root";
        assertFalse(FileAuth.contains(group, user));
    }

    public void testGroupNotExists() {
        String user = "user";
        String group = "user";
        assertFalse(FileAuth.contains(group, user));
    }
}
