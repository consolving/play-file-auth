
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import play.Play;
import play.cache.Cache;
import play.modules.fileauth.FileAuth;
import play.modules.fileauth.utils.MD5Crypt;
import play.test.UnitTest;

/**
 * ApacheUsersTest
 * 03.08.2012
 * @author Philipp Haussleiter
 *
 */
public class ApacheUsersTest extends UnitTest {

    @BeforeClass
    public static void setup() {
        Play.configuration.setProperty("authfile.users.path", Play.applicationPath + "/test/htusers");
        Play.configuration.setProperty("authfile.users.delimeter", ":");
        Cache.safeDelete(FileAuth.AUTH_FILE_USERS_CACHE_KEY);
    }

    @Test
    public void testUsersRead() {
        FileAuth.scanUsers();
        Map<String, String> users = Cache.get(FileAuth.AUTH_FILE_USERS_CACHE_KEY, HashMap.class);
        assertNotNull(users);
    }

    @Test
    public void testUsersValidate() {
        String clear = "user";
        String pass = MD5Crypt.crypt("user");
        assertTrue(MD5Crypt.verifyPassword(clear, pass));
    }

    @Test
    public void testValidatePassword() {
        String pass = "user";
        String user = "user";
        assertTrue(FileAuth.validate(user, pass));
        user = "apacheUser";
        assertTrue(FileAuth.validate(user, pass));
        user = "user";
        pass = "user123";
        assertFalse(FileAuth.validate(user, pass));
    }

    @Test
    public void testValidateNotExistingUser() {
        String pass = "user123";
        String user = "user123";
        assertFalse(FileAuth.validate(user, pass));
    }
}
