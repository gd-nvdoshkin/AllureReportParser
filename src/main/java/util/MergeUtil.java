package util;

import models.Story;
import models.TestSuite;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MergeUtil {
    private static final String delimiter = "|";
    ArrayList<TestSuite> testSuites = new ArrayList<>();
    ArrayList<TestSuite> testSuitesOld = new ArrayList<>();

    public void readSuits(Collection<File> files) throws FileNotFoundException {
        for (File file : files) {
            TestSuite testSuite = readFromTable(file);
            System.out.println(testSuite);
            testSuites.add(testSuite);
        }
    }

    private TestSuite readFromTable(File file) throws FileNotFoundException {
        TestSuite result = new TestSuite();
        result.setName(file.getName().replace(".txt", ""));
        Scanner scanner = new Scanner(file);
        if (scanner.hasNextLine()) {
            scanner.nextLine();
        }
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (!(line.equals("||Story||Passed||Failed||RCA||") || line.trim().isEmpty())) {
                String[] split = line.split("\\|");
                result.addStory(new Story(split));
            }
        }
        return result;
    }

    public void printTable(String path) throws IOException {
        StringBuilder table = new StringBuilder("||Story||Passed||Failed||");
        for (int i = 0; i < testSuites.size() - 1; i++) {
            table.append("Passed||Failed||");
        }
        table.append("\n");
        table.append("|Total|");
        testSuites.forEach(testSuite -> table.append(testSuite.getTotalPassed()).append(delimiter)
                .append(testSuite.getTotalFailed()).append(delimiter));
        table.append("\n");

        TreeSet<String> stories = new TreeSet<>();
        testSuites.forEach(testSuite -> stories.addAll(testSuite.getStories().keySet()));

        for (String story : stories) {
            table.append(delimiter).append(story).append(delimiter);
            AtomicInteger index = new AtomicInteger();
            testSuites.forEach(testSuite -> {
                Story storyByName = testSuite.getStoryByName(story);
                Story oldStoryByName = null;
                if (testSuitesOld.size() > index.get()) {
                    oldStoryByName = testSuitesOld.get(index.get()).getStoryByName(story);
                }
                if (storyByName != null) {
                    if (oldStoryByName != null) {
                        if (storyByName.getFailed() > oldStoryByName.getFailed()) {
                            table.append("*").append(storyByName.getPassed()).append("*").append(delimiter).append("*")
                                    .append(storyByName.getFailed()).append("*").append(delimiter);
                        } else {
                            table.append(storyByName.getPassed()).append(delimiter)
                                    .append(storyByName.getFailed()).append(delimiter);
                        }
                    } else {
                        table.append(storyByName.getPassed()).append(delimiter).append(storyByName.getFailed()).append(delimiter);
                    }
                } else {
                    table.append(" ").append(delimiter).append(" ").append(delimiter);
                }
                index.getAndIncrement();
            });
            table.append("\n");
        }

        FileWriter fileWriter = new FileWriter(path);
        fileWriter.write(table.toString());
        fileWriter.flush();
    }

    public void readOldSuits(Collection<File> files) throws FileNotFoundException {
        for (File file : files) {
            TestSuite testSuite = readFromTable(file);
            System.out.println(testSuite);
            testSuitesOld.add(testSuite);
        }
    }
}
