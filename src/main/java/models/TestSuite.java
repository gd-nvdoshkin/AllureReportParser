package models;

import java.util.*;

public class TestSuite {
    private String name;
    private Map<String, Story> stories;

    public TestSuite() {
        stories = new TreeMap<>();
    }

    public String getName() {
        return name;
    }

    public Story getStoryByName(String name) {
        return stories.containsKey(name) ? stories.get(name) : null;
    }

    public void setName(String name) {
        this.name = name;
    }


    public void setStories(Map<String, Story> stories) {
        this.stories = stories;
    }

    public void addStory(Story story) {
        if (this.stories.containsKey(story.getName())) {
            this.stories.get(story.getName()).addPassed(story.getPassed());
            this.stories.get(story.getName()).addFailed(story.getFailed());
        } else {
            this.stories.put(story.getName(), story);
        }
    }

    public Map<String, Story> getStories() {
        return stories;
    }


    public int getTotalPassed() {
       return stories.values().stream().mapToInt(Story::getPassed).sum();
    }

    public int getTotalFailed() {
        return stories.values().stream().mapToInt(Story::getFailed).sum();
    }

    @Override
    public String toString() {
        return "TestSuite{" +
                "name='" + name + '\'' +
                ", stories=" + stories +
                '}';
    }
}
