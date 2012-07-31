/**
 * FileAuthPlugin
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package play.modules.fileauth;

import play.PlayPlugin;

public class FileAuthPlugin extends PlayPlugin {

    @Override
    public void onApplicationStart() {
        ScanJob job = new ScanJob();
        job.now();
    }
}
