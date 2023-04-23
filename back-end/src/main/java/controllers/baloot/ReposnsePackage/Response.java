package controllers.baloot.ReposnsePackage;

public class Response {
    public int status;
    public String statusMessage;
    public Object content;

    public Response(int status, String statusMessage, Object content) {
        this.status = status;
        this.statusMessage = statusMessage;
        this.content = content;
    }

    public int getStatusCode() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Object getContent() {
        return content;
    }

    public void setStatusCode(int status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setContent(Object content) {
        this.content = content;
    }
}

