/**
 * This class stores data of Tests
 */
class Test{
    private String issueKey = "";
    private String issueID = "";
    private String summary = "";
    private String description = "";
    private String manualTestSteps = "";
    private String assumptionsAndConstraints = "";
    private String testInputs = "";
    private String conditions = "";

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(String issueKey) {
        this.issueKey = issueKey;
    }

    public String getIssueID() {
        return issueID;
    }

    public void setIssueID(String issueID) {
        this.issueID = issueID;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getManualTestSteps() {
        return manualTestSteps;
    }

    public void setManualTestSteps(String manualTestSteps) {
        this.manualTestSteps = manualTestSteps;
    }

    public String getAssumptionsAndConstraints() {
        return assumptionsAndConstraints;
    }

    public void setAssumptionsAndConstraints(String assumptionsAndConstraints) {
        this.assumptionsAndConstraints = assumptionsAndConstraints;
    }

    public String getTestInputs() {
        return testInputs;
    }

    public void setTestInputs(String testInputs) {
        this.testInputs = testInputs;
    }

    public String getConditions() {
        return conditions;
    }

    public void setConditions(String conditions) {
        this.conditions = conditions;
    }
}