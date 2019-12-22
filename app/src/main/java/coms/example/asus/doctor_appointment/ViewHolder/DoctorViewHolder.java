package coms.example.asus.doctor_appointment.ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import coms.example.asus.doctor_appointment.Interface.ItemClickListner;
import coms.example.asus.doctor_appointment.R;

public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
{
    public TextView txtDoctorName, txtDoctorSpeciality, txtDoctorFees;
    public ImageView imageView;
    public ItemClickListner listner;

    public DoctorViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = (ImageView) itemView.findViewById(R.id.doctors_image);
        txtDoctorName = (TextView) itemView.findViewById(R.id.doctors_name);
        txtDoctorSpeciality = (TextView) itemView.findViewById(R.id.doctors_speciality_list);
        txtDoctorFees = (TextView) itemView.findViewById(R.id.doctors_fees);
    }

    public void setItemClickListner(ItemClickListner listner)
    {
        this.listner = listner;
    }

    @Override
    public void onClick(View view)
    {
        listner.onClick(view, getAdapterPosition(), false);
    }
}
