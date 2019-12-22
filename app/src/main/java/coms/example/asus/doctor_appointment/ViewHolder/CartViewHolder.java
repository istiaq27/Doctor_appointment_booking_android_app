package coms.example.asus.doctor_appointment.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import coms.example.asus.doctor_appointment.Interface.ItemClickListner;
import coms.example.asus.doctor_appointment.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtDoctorName, txtDoctorFees, txtDoctorAddress, txtDoctorNumber, txtAptDate, txtAptTimeSlot ;
    private ItemClickListner itemClickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtDoctorName = itemView.findViewById(R.id.cart_doctor_name);
        txtDoctorFees = itemView.findViewById(R.id.cart_appointment_fees);
        txtDoctorNumber = itemView.findViewById(R.id.cart_doctor_number);
        txtDoctorAddress = itemView.findViewById(R.id.cart_doctor_hospital);
        txtAptDate = itemView.findViewById(R.id.cart_apt_date);
        txtAptTimeSlot = itemView.findViewById(R.id.cart_appointment_timeslot);

    }
    @Override
    public void onClick(View view)
    {
        itemClickListner.onClick(view, getAdapterPosition(),false);
    }

    public void setItemClickListner(ItemClickListner itemClickListner)
    {
        this.itemClickListner = itemClickListner;
    }
}




