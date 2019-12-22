package coms.example.asus.doctor_appointment.Model;

public class AdminAppointments {
    private String address, date, name, disease, phone, time;

    public AdminAppointments()
    {
    }

    public AdminAppointments(String address, String date, String name, String disease, String phone, String time) {
        this.address = address;
        this.date = date;
        this.name = name;
        this.disease = disease;
        this.phone = phone;
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
