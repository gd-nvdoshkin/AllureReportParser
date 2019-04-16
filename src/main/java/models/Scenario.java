package models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Scenario {
    private String name;
    private List<Label> labels;
    private String status;

    public Scenario() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoryName() {
        for (Label label : labels) {
            if (label.getName().equals("story")) {
                return label.getValue();
            }
        }
        return "";
    }

    public String getDefect() {
        StringBuilder value = new StringBuilder();
        String meta = StringUtils.substringBetween(this.name, "[", "]");
        if (meta == null) {
            return "";
        }
        String[] metaTags = meta.split(",");
        StringBuilder defectValue = new StringBuilder();
        for (String metaTag : metaTags) {
            if (metaTag.contains("defect")) {
                String testCaseDefectValue;
                if (meta.contains("@defect")) {
                    testCaseDefectValue = StringUtils.substringAfter(metaTag, "@defect");
                    if (testCaseDefectValue.contains("@")) {
                        testCaseDefectValue = StringUtils.substringBefore(testCaseDefectValue, "@");
                    }
                } else {
                    testCaseDefectValue = StringUtils.substringAfter(metaTag, "defect=");
                }
                if (testCaseDefectValue != null) {
                    if (testCaseDefectValue.startsWith(" ") || testCaseDefectValue.startsWith("=")) {
                        testCaseDefectValue = testCaseDefectValue.substring(1);
                    }
                    if (testCaseDefectValue.endsWith(" ")) {
                        testCaseDefectValue = testCaseDefectValue.substring(0, testCaseDefectValue.length() - 1);
                    }
                    if (!defectValue.toString().contains(testCaseDefectValue)) {
                        if (this.status.equalsIgnoreCase("passed")) {
                            defectValue.append("*").append(testCaseDefectValue).append("*");
                        } else {
                            defectValue.append(testCaseDefectValue);
                        }
                    }

                }
            }
        }
        return defectValue.toString();
    }
}
