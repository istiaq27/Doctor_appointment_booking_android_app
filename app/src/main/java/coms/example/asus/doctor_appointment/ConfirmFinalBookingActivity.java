package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import coms.example.asus.doctor_appointment.Prevalent.Prevalent;

public class ConfirmFinalBookingActivity extends AppCompatActivity {

    private EditText nameEditText, phoneEditText, addressEditText,diseaseEditText;
    private Button confirmBookingBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_final_booking);

        confirmBookingBtn = (Button) findViewById(R.id.confirm_final_booking_btn);
        nameEditText = (EditText)findViewById(R.id.pt_name);
        phoneEditText = (EditText)findViewById(R.id.pt_phone_number);
        addressEditText = (EditText) findViewById(R.id.pt_address);
        diseaseEditText = (EditText) findViewById(R.id.pt_disease);

        confirmBookingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Check();
            }
        });

    }

    private void Check()
    {
        if(TextUtils.isEmpty(nameEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Patient Full Name.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(phoneEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Patient Phone Number.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(addressEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Patient Address.", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(diseaseEditText.getText().toString()))
        {
            Toast.makeText(this, "Please Provide Disease Details.", Toast.LENGTH_SHORT).show();
        }

        else
        {
            ConfirmOrder();

        }


    }

    private void ConfirmOrder()
    {
        final String saveCurrentDate, saveCurrentTime;
        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH: mm:ss a");
        saveCurrentTime = currentDate.format(calForDate.getTime());
        final DatabaseReference ordersRef = FirebaseDatabase.getInstance().getReference()
                .child("Final Appointments List")
                .child(Prevalent.currentOnlineUser.getPhone());

        HashMap<String, Object> orderMap = new HashMap<>();

        orderMap.put("name",nameEditText.getText().toString());
        orderMap.put("phone",phoneEditText.getText().toString());
        orderMap.put("address",addressEditText.getText().toString());
        orderMap.put("disease",diseaseEditText.getText().toString());
        orderMap.put("date",saveCurrentDate);
        orderMap.put("time",saveCurrentTime);


        ordersRef.updateChildren(orderMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    FirebaseDatabase.getInstance().getReference()
                            .child("Appointments Cart")
                            .child("User View")
                            .child(Prevalent.currentOnlineUser.getPhone())
                            .removeValue()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task)
                                {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(ConfirmFinalBookingActivity.this, "Appointment successful", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(ConfirmFinalBookingActivity.this, HomeActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }

                                }
                            });
                }
            }
        });
    }
}