package controllers.baloot.ReposnsePackage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Response {
    public int status;
    public String statusMessage;
    public Object data;

//    public Response(int status, String statusMessage, Object content) {
//        this.status = status;
//        this.statusMessage = statusMessage;
//        this.content = content;
//    }
    @JsonCreator
    public Response(@JsonProperty("statusCode") Integer status,@JsonProperty("statusMsg") String statusMsg,
                  @JsonProperty("data") Object data) throws Exception {
        if (status == null) {
            throw new Exception();
        }
        this.status = status;
        this.data = data;
        this.statusMessage = statusMsg;
    }

    public int getStatusCode() {
        return status;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public Object getContent() {
        return data;
    }

    public void setStatusCode(int status) {
        this.status = status;
    }

    public void setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public void setContent(Object content) {
        this.data= content;
    }
}

