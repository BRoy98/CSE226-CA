package sh.broy.lpuhms.Models;

public class Doctor {

    private int id;
    private String name;
    private String email;
    private String password;
    private String specialization;

    public Doctor(final int id, final String name, final String email, final String password,
            final String specialization) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.specialization = specialization;
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setSpecialization(final String specialization) {
        this.specialization = specialization;
    }
}
