
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

        Collection<File> files = FileUtils.listFiles(new File("tables"), new WildcardFileFilter("*.txt"), TrueFileFilter.TRUE);

        //Collection<File> oldFiles = FileUtils.listFiles(new File("tables_old"), new WildcardFileFilter("*.txt"), TrueFileFilter.TRUE);

        Collections.sort((List) files);
       // Collections.sort((List) oldFiles);
        MergeUtil unionUtils = new MergeUtil();
        unionUtils.readSuits(files);
       // unionUtils.readOldSuits(oldFiles);
        unionUtils.printTable("table.txt");
    }
}
