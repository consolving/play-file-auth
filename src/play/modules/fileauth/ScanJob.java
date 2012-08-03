/**
 * ScanJob
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package play.modules.fileauth;

import play.jobs.Every;
import play.jobs.Job;

/**
 * Periodically Scan of user/group files. Scan runs every 5 mins.
 * @author Philipp Haußleiter
 */
@Every("5min")
public class ScanJob extends Job {
    @Override
    public void doJob(){
        FileAuth.scanUsers();
        FileAuth.scanGroups();
    }
}
