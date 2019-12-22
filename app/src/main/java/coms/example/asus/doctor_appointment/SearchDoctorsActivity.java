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
import android.widget.EditText;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import coms.example.asus.doctor_appointment.Model.Doctors;
import coms.example.asus.doctor_appointment.ViewHolder.DoctorViewHolder;

public class SearchDoctorsActivity extends AppCompatActivity {
    private Button SearchBtn;
    private  EditText inputText;
    private RecyclerView searchList;
    private String SearchInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_doctors);

        inputText = findViewById(R.id.search_doctors_name);
        SearchBtn = findViewById(R.id.search_btn);
        searchList = findViewById(R.id.search_list);
        searchList.setLayoutManager(new LinearLayoutManager(SearchDoctorsActivity.this));

        SearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SearchInput = inputText.getText().toString();
                onStart();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("Doctors");
        FirebaseRecyclerOptions<Doctors> options =
                new FirebaseRecyclerOptions.Builder<Doctors>()
                .setQuery(reference.orderByChild("speciality").startAt(SearchInput), Doctors.class)
                .build();

        FirebaseRecyclerAdapter<Doctors, DoctorViewHolder> adapter =
                new FirebaseRecyclerAdapter<Doctors, DoctorViewHolder>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull DoctorViewHolder holder, int position, @NonNull final Doctors model)
                    {
                        Picasso.get().load(model.getImage()).into(holder.imageView);
                        holder.txtDoctorName.setText(model.getDoctorName());
                        holder.txtDoctorFees.setText("Fees = " + model.getFees()+ "$");
                        holder.txtDoctorSpeciality.setText(model.getSpeciality());
                        holder.itemView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(SearchDoctorsActivity.this, DoctorDetailsActivity.class);
                                intent.putExtra("doctorid",model.getDoctorid());
                                startActivity(intent);
                            }
                        });
                    }


                    @NonNull
                    @Override
                    public DoctorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
                    {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.doctors_list_layout, parent, false);
                        DoctorViewHolder holder = new DoctorViewHolder(view);
                        return holder;
                    }
                };
        searchList.setAdapter(adapter);
        adapter.startListening();
    }
}
