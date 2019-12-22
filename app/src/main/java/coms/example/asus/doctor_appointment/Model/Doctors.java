package coms.example.asus.doctor_appointment.Model;

public class Doctors {
    private String doctorName, speciality, description, fees, image, department, doctorid, date, time, address, phone,mySlots,alreadyBooked,availableSlots, aptTimeSlot,aptDate;
    public Doctors()
    {

    }

    public Doctors(String doctorName, String speciality, String description, String fees, String image, String department, String doctorid, String date, String time, String address, String phone, String mySlots, String alreadyBooked, String availableSlots, String aptTimeSlot, String aptDate) {
        this.doctorName = doctorName;
        this.speciality = speciality;
        this.description = description;
        this.fees = fees;
        this.image = image;
        this.department = department;
        this.doctorid = doctorid;
        this.date = date;
        this.time = time;
        this.address = address;
        this.phone = phone;
        this.mySlots = mySlots;
        this.alreadyBooked = alreadyBooked;
        this.availableSlots = availableSlots;
        this.aptTimeSlot = aptTimeSlot;
        this.aptDate = aptDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMySlots() {
        return mySlots;
    }

    public void setMySlots(String mySlots) {
        this.mySlots = mySlots;
    }

    public String getAlreadyBooked() {
        return alreadyBooked;
    }

    public void setAlreadyBooked(String alreadyBooked) {
        this.alreadyBooked = alreadyBooked;
    }

    public String getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(String availableSlots) {
        this.availableSlots = availableSlots;
    }

    public String getAptTimeSlot() {
        return aptTimeSlot;
    }

    public void setAptTimeSlot(String aptTimeSlot) {
        this.aptTimeSlot = aptTimeSlot;
    }

    public String getAptDate() {
        return aptDate;
    }

    public void setAptDate(String aptDate) {
        this.aptDate = aptDate;
    }
}
