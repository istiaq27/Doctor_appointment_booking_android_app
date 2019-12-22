package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import coms.example.asus.doctor_appointment.Model.Cart;
import coms.example.asus.doctor_appointment.Model.Doctors;
import coms.example.asus.doctor_appointment.ViewHolder.CartViewHolder;

public class AdminPatientsDoctorActivity extends AppCompatActivity {
    private RecyclerView doctorsList;
    RecyclerView.LayoutManager layoutManager;
    private DatabaseReference cartListRef;
    private String userID= "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_patients_doctor);

        userID = getIntent().getStringExtra("uid");

        doctorsList = findViewById(R.id.patient_doctors_list);
        doctorsList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        doctorsList.setLayoutManager(layoutManager);

        cartListRef = FirebaseDatabase.getInstance().getReference()
                .child("Appointments Cart").child("Admin View").child(userID).child("Doctors");
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Cart> options =
                new FirebaseRecyclerOptions.Builder<Cart>()
                        .setQuery(cartListRef, Cart.class).build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull Cart model)
            {
                holder.txtDoctorAddress.setText(model.getAddress());
                holder.txtDoctorFees.setText(model.getFees());
                holder.txtDoctorName.setText(model.getDoctorName());
                holder.txtDoctorNumber.setText(model.getAddress());
                holder.txtAptTimeSlot.setText(model.getAptTimeSlot());
                holder.txtAptDate.setText(model.getAptDate());
            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent,false);
                CartViewHolder holder =new CartViewHolder(view);
                return  holder;
            }
        };
        doctorsList.setAdapter(adapter);
        adapter.startListening();
    }
}
