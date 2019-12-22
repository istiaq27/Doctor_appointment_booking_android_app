package coms.example.asus.doctor_appointment.Model;

public class Cart {
    private  String  doctorName, fees, doctorid, quantity, discount, address, phone, aptTimeSlot, aptDate;

    public Cart()
    {

    }

    public Cart(String doctorName, String fees, String doctorid, String quantity, String discount, String address, String phone, String aptTimeSlot, String aptDate) {
        this.doctorName = doctorName;
        this.fees = fees;
        this.doctorid = doctorid;
        this.quantity = quantity;
        this.discount = discount;
        this.address = address;
        this.phone = phone;
        this.aptTimeSlot = aptTimeSlot;
        this.aptDate = aptDate;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getFees() {
        return fees;
    }

    public void setFees(String fees) {
        this.fees = fees;
    }

    public String getDoctorid() {
        return doctorid;
    }

    public void setDoctorid(String doctorid) {
        this.doctorid = doctorid;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
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
