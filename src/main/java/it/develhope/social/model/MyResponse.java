package it.develhope.social.model;

public class MyResponse {

    private String status;

    private String value;

    private String error;



    public MyResponse(String status) {
        this.status = status;
    }

    public MyResponse(String status, String error) {
        this.status = status;
        this.error = error;
    }

    public MyResponse(String status, String value, String error) {
        this.status = status;
        this.value = value;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "MyResponse{" +
                "status='" + status + '\'' +
                ", value='" + value + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
