/**
 * ScanJob
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package play.modules.fileauth;

import play.jobs.Every;
import play.jobs.Job;

@Every("1h")
public class ScanJob extends Job {
    @Override
    public void doJob(){
        FileAuth.scanUsers();
        FileAuth.scanGroups();
    }
}
