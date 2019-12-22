package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import coms.example.asus.doctor_appointment.Model.Cart;
import coms.example.asus.doctor_appointment.Prevalent.Prevalent;
import coms.example.asus.doctor_appointment.ViewHolder.CartViewHolder;

public class MyAppointmentsActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private  RecyclerView.LayoutManager layoutManager;
    private TextView myAppointmentsTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_appointments);

        recyclerView = findViewById(R.id.my_appointments_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myAppointmentsTxt = (TextView) findViewById(R.id.total_fees);
    }

    @Override
    protected void onStart() {
        super.onStart();
        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Appointments Cart");

        FirebaseRecyclerOptions<Cart> options = new FirebaseRecyclerOptions.Builder<Cart>()
                .setQuery(cartListRef.child("Admin View")
                        .child(Prevalent.currentOnlineUser.getPhone())
                        .child("Doctors"), Cart.class)
                .build();

        FirebaseRecyclerAdapter<Cart, CartViewHolder> adapter
                = new FirebaseRecyclerAdapter<Cart, CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull CartViewHolder holder, int position, @NonNull final Cart model)
            {

                holder.txtDoctorAddress.setText(model.getAddress());
                holder.txtDoctorFees.setText(model.getFees());
                holder.txtDoctorName.setText(model.getDoctorName());
                holder.txtDoctorNumber.setText(model.getPhone());
                holder.txtAptTimeSlot.setText(model.getAptTimeSlot());
                //holder.txtAptTimeSlot.setText(model.getPhone());
                holder.txtAptDate.setText(model.getAptDate());

            }

            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
            {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_items_layout, parent,false);
                CartViewHolder holder =new CartViewHolder(view);
                return  holder;
            }
        };
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }
}
