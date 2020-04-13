package sh.broy.lpuhms.Models;

public class Appointment {

    private int id, active;
    private String pName, dName, issue, time;

    public int getActive() {
        return active;
    }

    public int getId() {
        return id;
    }

    public void setActive(final int active) {
        this.active = active;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(final String pName) {
        this.pName = pName;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(final String dName) {
        this.dName = dName;
    }

    public String getIssue() {
        return issue;
    }

    public void setIssue(final String issue) {
        this.issue = issue;
    }

    public String getTime() {
        return time;
    }

    public void setTime(final String time) {
        this.time = time;
    }

    public Appointment(final int id, final String pName, final String dName, final String issue,
            final String time, final int active) {
        this.id = id;
        this.pName = pName;
        this.dName = dName;
        this.issue = issue;
        this.time = time;
        this.active = active;
    }
}
