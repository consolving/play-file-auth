
import org.junit.Test;
import play.Logger;
import play.modules.fileauth.utils.MD5Crypt;
import play.test.UnitTest;

/**
 * EncryptionTest
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
public class EncryptionTest extends UnitTest {

    @Test
    public void testApacheCrypt() {
        Logger.info("testing APACHE_CRYPT");
        String clear = "pw_" + System.currentTimeMillis();
        String pass;
        String salt = "abcd1234";
        pass = MD5Crypt.apacheCrypt(clear);
        Logger.info(clear + " got me " + pass);
        assertTrue(MD5Crypt.verifyPassword(clear, pass));
        pass = MD5Crypt.apacheCrypt(clear, salt);
        Logger.info(clear + " & salt " + salt + " got me " + pass);
        assertTrue(MD5Crypt.verifyPassword(clear, pass));
    }

    @Test
    public void testCrypt() {
        Logger.info("testing CRYPT");
        String clear = "pw_" + System.currentTimeMillis();
        String pass;
        String salt = "abcd1234";
        pass = MD5Crypt.crypt(clear);
        Logger.info(clear + " got me " + pass);
        assertTrue(MD5Crypt.verifyPassword(clear, pass));
        pass = MD5Crypt.crypt(clear, salt);
        Logger.info(clear + " & salt " + salt + " got me " + pass);
        assertTrue(MD5Crypt.verifyPassword(clear, pass));
    }
}
