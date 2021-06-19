package pp.facerecognizer;

import android.content.SharedPreferences;

public class Model_Attendance {

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Model_Attendance(String date, String status) {
        Date = date;
        Status = status;
    }

    private String Date;
    private String Status;

    public String getStudent() {
        return Username;
    }

    public void setStudent(String username) {
        Username = username;
    }

    private String Username;

    public Model_Attendance() {
    }
}
