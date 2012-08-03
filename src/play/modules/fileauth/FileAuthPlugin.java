/**
 * FileAuthPlugin
 * 31.07.2012
 * @author Philipp Haussleiter
 *
 */
package play.modules.fileauth;

import play.PlayPlugin;

/**
 * Entry Class for first file scan.
 * @author Philipp Haußleiter
 */
public class FileAuthPlugin extends PlayPlugin {

    /**
     * Trigger to scan the files for the first Time.
     */
    @Override
    public void onApplicationStart() {
        ScanJob job = new ScanJob();
        job.now();
    }
}
