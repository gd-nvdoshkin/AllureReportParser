package models;


import java.util.ArrayList;
import java.util.List;

public class Story {
    private String name;
    private List<Scenario> scenarios;
    private int passed;
    private int failed;
    public String defects;

    public Story() {
        this.scenarios = new ArrayList<>();
    }

    public Story(String[] story) {
        if (story.length == 5) {
            this.name = story[1];
            this.passed = Integer.parseInt(story[2]);
            this.failed = Integer.parseInt(story[3]);
            this.defects = story[4];
        } else if (story.length == 6) {
            this.name = story[2];
            this.passed = Integer.parseInt(story[3]);
            this.failed = Integer.parseInt(story[4]);
            this.defects = story[5];
        }
    }


    public Story(Scenario scenario) {
        this.name = scenario.getStoryName();
        this.scenarios = new ArrayList<>();
        this.defects = "";

        addScenario(scenario);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Scenario> getScenarios() {
        return scenarios;
    }

    public void setScenarios(List<Scenario> scenarios) {
        this.scenarios = scenarios;
    }

    public void addScenario(Scenario scenario) {
        this.scenarios.add(scenario);
        if (scenario.getStatus().equals("passed")) {
            this.passed++;
        } else {
            this.failed++;
        }
        String defect = scenario.getDefect();
        if (!defect.isEmpty() && !this.defects.contains(defect)) {
            this.defects += defect + " ";
        }
    }

    public int getFailed() {
        return failed;
    }

    public void addFailed(int failed) {
        this.failed += failed;
    }

    public int getPassed() {
        return passed;
    }

    public void addPassed(int passed) {
        this.passed += passed;
    }

    public String getDefects() {
        return defects.equals("") ? " " : defects;
    }

    @Override
    public String toString() {
        return "Story{" +
                "name='" + name + '\'' +
                ", scenarios=" + scenarios +
                ", passed=" + passed +
                ", failed=" + failed +
                ", defects='" + defects + '\'' +
                '}';
    }
}
