package mish.mish.assefa.com.fixmycity.data.report;

public class ReportReq {
    private String description;
    private String name;
    private String municipal;
    private String image;
    private String reported_by;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipal() {
        return municipal;
    }

    public void setMunicipal(String municipal) {
        this.municipal = municipal;
    }

    public String getReported_by() {
        return reported_by;
    }

    public void setReported_by(String reported_by) {
        this.reported_by = reported_by;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
