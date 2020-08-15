/**
 * This class stores data of Manual Test Steps
 */
class InfoV8 {
    private int id;
    private int index;
    public static class Fields{
        private String Action;
        private String Data;
        private String Expected_$ThisPartWillBeRemoved$_Result;

        public String getAction() {
            return Action;
        }

        public void setAction(String action) {
            Action = action;
        }

        public String getData() {
            return Data;
        }

        public void setData(String data) {
            Data = data;
        }

        public String getExpectedResult() {
            return Expected_$ThisPartWillBeRemoved$_Result;
        }

        public void setExpectedResult(String expectedResult) {
            Expected_$ThisPartWillBeRemoved$_Result = expectedResult;
        }
    };

    private Fields fields;
    private String[] attachments;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public Fields getFields() {
        return fields;
    }

    public void setFields(Fields fields) {
        this.fields = fields;
    }

    public String[] getAttachments() {
        return attachments;
    }

    public void setAttachments(String[] attachments) {
        this.attachments = attachments;
    }


}