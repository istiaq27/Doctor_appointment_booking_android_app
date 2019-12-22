package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import coms.example.asus.doctor_appointment.Model.AdminAppointments;

public class AdminNewAppointmentsActivity extends AppCompatActivity {
    private RecyclerView aptList;
    private DatabaseReference aptRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_new_appointments);

        aptRef = FirebaseDatabase.getInstance().getReference().child("Final Appointments List");
        aptList =findViewById(R.id.appointments_list);
        aptList.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<AdminAppointments> options =
                new FirebaseRecyclerOptions.Builder<AdminAppointments>()
                        .setQuery(aptRef, AdminAppointments.class)
                        .build();
        FirebaseRecyclerAdapter<AdminAppointments,AdminAppointmentsViewHolder > adapter=
                new FirebaseRecyclerAdapter<AdminAppointments, AdminAppointmentsViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull AdminAppointmentsViewHolder holder, final int position, @NonNull final AdminAppointments model)
                    {

                        holder.ptName.setText("Patient Name: " + model.getName());
                        holder.ptAddress.setText("Patient Address: " + model.getAddress());
                        holder.ptDisease.setText("Disease: " + model.getDisease());
                        holder.ptPhoneNumber.setText("Contact Number: " + model.getPhone());
                        holder.showAptBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view)
                            {
                                String uID = getRef(position).getKey();

                                Intent intent = new Intent(AdminNewAppointmentsActivity.this, AdminPatientsDoctorActivity.class);
                                intent.putExtra("uid", uID);
                                startActivity(intent);
                            }
                        });

                    }

                    @NonNull
                    @Override
                    public AdminAppointmentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.appointments_layout, parent, false);
                        return new AdminAppointmentsViewHolder(view);
                    }
                };
        aptList.setAdapter(adapter);
        adapter.startListening();
    }

    public static class  AdminAppointmentsViewHolder extends RecyclerView.ViewHolder
    {
        public TextView ptName, ptPhoneNumber, ptDisease, ptAddress;
        public Button showAptBtn;

        public AdminAppointmentsViewHolder(@NonNull View itemView)
        {
            super(itemView);

            ptName = itemView.findViewById(R.id.pt_name);
            ptDisease = itemView.findViewById(R.id.pt_diseases);
            ptAddress = itemView.findViewById(R.id.pt_address);
            ptPhoneNumber =itemView.findViewById(R.id.pt_phone);
            showAptBtn = itemView.findViewById(R.id.show_all_doctors_btn);


        }
    }
}