import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AllureParserRunner {

    public static void main(String[] args) throws IOException {
        MergeUtil unionUtils = new MergeUtil();

        Collection<File> files = FileUtils.listFiles(new File("tables"), new WildcardFileFilter("*.txt"), TrueFileFilter.TRUE);
        Collections.sort((List) files);
        unionUtils.readSuits(files);

        if (new File("tables_old").exists()) {
            Collection<File> oldFiles = FileUtils.listFiles(new File("tables_old"), new WildcardFileFilter("*.txt"), TrueFileFilter.TRUE);
            Collections.sort((List) oldFiles);
            unionUtils.readOldSuits(oldFiles);
        }

        unionUtils.printTable("table.txt");
    }
}
