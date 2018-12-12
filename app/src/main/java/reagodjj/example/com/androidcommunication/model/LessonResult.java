package reagodjj.example.com.androidcommunication.model;

import java.util.List;

public class LessonResult {
    private String message;
    private int status;
    private List<LessonInfo> lessonInfoList;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<LessonInfo> getLessonInfoList() {
        return lessonInfoList;
    }

    public void setLessonInfoList(List<LessonInfo> lessonInfoList) {
        this.lessonInfoList = lessonInfoList;
    }
}
