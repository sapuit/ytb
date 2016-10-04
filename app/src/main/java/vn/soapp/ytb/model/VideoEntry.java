package vn.soapp.ytb.model;

public class VideoEntry {
    private String id;
    private String title;
    private String duration;
    private String viewCount;
    private String publish;

    public VideoEntry(String title, String id) {
        this.title = title;
        this.id = id;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }



}
