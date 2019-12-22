package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.StringTokenizer;

import coms.example.asus.doctor_appointment.Model.Doctors;
import coms.example.asus.doctor_appointment.Prevalent.Prevalent;

public class DoctorDetailsActivity extends AppCompatActivity {
    private Button addToCartButton, getBtn, selectYourSlotsBtn;;
    private ImageView doctorImage;
    private TextView fees, doctorDescription, doctorName, doctorAddresss,doctorPhonee, showDate, alreadyBookedtxt,viewYourSelectedSlotsTxtView, mySlots,aptTimeSlot, aptDate;
    private String doctorID = "";
   /* private RadioGroup radioGroup;
    private RadioButton radioButton;*/
    private EditText dateEditText;
    DatePickerDialog datePicker;
    String[] listSlots;
    boolean[] checkedItems;
    ArrayList<Integer> mSlots  = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);
        doctorID = getIntent().getStringExtra("doctorid");
        //addToCartBtn = (FloatingActionButton) findViewById(R.id.add_product_to_cart_btn);
        //numberButton = (ElegantNumberButton) findViewById(R.id.number_btn);
        doctorImage = (ImageView) findViewById(R.id.doctor_image_details);
        doctorName = (TextView) findViewById(R.id.doctor_name_details);
        doctorDescription = (TextView) findViewById(R.id.doctor_description_details);
        fees = (TextView) findViewById(R.id.doctor_fees_details);
        doctorAddresss = (TextView) findViewById(R.id.doctor_address_details);
        mySlots = (TextView)findViewById(R.id.show_available_slots_txt);
        doctorPhonee = (TextView) findViewById(R.id.doctor_phone_details);
        addToCartButton = (Button) findViewById(R.id.dd_add_to_cart_button);
       // radioGroup = (RadioGroup) findViewById(R.id.radioGroupId);
        //datePicker =(DatePicker) findViewById(R.id.da);
        getBtn = (Button) findViewById(R.id.getDateBtn);
        dateEditText = (EditText) findViewById(R.id.select_date);
        showDate = (TextView) findViewById(R.id.showDate);
        aptDate =(TextView)findViewById(R.id.cart_apt_date);
        aptTimeSlot =(TextView)findViewById(R.id.cart_appointment_timeslot);
        alreadyBookedtxt = (TextView)findViewById(R.id.slotAlreadyBooked);
        selectYourSlotsBtn = (Button)findViewById(R.id.select_your_slot_btn);
        viewYourSelectedSlotsTxtView = (TextView) findViewById(R.id.slotSelected);
        listSlots = getResources().getStringArray(R.array.doctors_slots);
        checkedItems = new boolean[listSlots.length];




        dateEditText.setInputType(InputType.TYPE_NULL);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                datePicker= new DatePickerDialog(DoctorDetailsActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        dateEditText.setText(i2 + "-" + (i1 + 1) + "-" + i);
                    }
                },year, month, day);
                datePicker.show();


            }
        });
        getBtn = (Button) findViewById(R.id.getDateBtn);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                 showDate.setText("Selected Date: "+ dateEditText.getText());
            }
        });



        getDoctorDetails(doctorID);


       addToCartButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               addingToCartList();
           }
       });

      /*  final StringTokenizer st1 = new StringTokenizer("mySlots", ",");
        while(st1.hasMoreTokens()) {
            String availabaleSlots=st1.nextToken();

        }*/
        selectYourSlotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder mBuilder = new AlertDialog.Builder(DoctorDetailsActivity.this);
                mBuilder.setTitle("Choose your Time Slot");
                mBuilder.setSingleChoiceItems(listSlots, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewYourSelectedSlotsTxtView.setText(listSlots[i]);
                        dialogInterface.dismiss();

                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });
    }


    private void addingToCartList() {
        String saveCurrentDate,saveCurrentTime;


        Calendar calForDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calForDate.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calForDate.getTime());
       /* int selectedId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(selectedId);*/
       // String timeSlot = radioButton.getText().toString();

        final DatabaseReference cartListRef = FirebaseDatabase.getInstance().getReference().child("Appointments Cart");

        final HashMap<String, Object> cartMap = new HashMap<>();
        cartMap.put("doctorid", doctorID);
        cartMap.put("doctorName", doctorName.getText().toString());
        cartMap.put("fees", fees.getText().toString());
        cartMap.put("date", saveCurrentDate);
        cartMap.put("time", saveCurrentTime);
        cartMap.put("address", doctorAddresss.getText().toString());
        cartMap.put("phone", doctorPhonee.getText().toString());
       // cartMap.put("discount", " ");
        cartMap.put("aptTimeSlot", viewYourSelectedSlotsTxtView.getText().toString());
        cartMap.put("aptDate", dateEditText.getText().toString());
        cartMap.put("availableSlots",mySlots.getText().toString() );
        cartMap.put("alreadyBooked", alreadyBookedtxt.getText().toString());

        cartListRef.child("User View").child(Prevalent.currentOnlineUser.getPhone())
                .child("Doctors").child(doctorID)
                .updateChildren(cartMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {

                            cartListRef.child("Admin View").child(Prevalent.currentOnlineUser.getPhone())
                                    .child("Doctors").child(doctorID)
                                    .updateChildren(cartMap)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task)
                                        {
                                            if (task.isSuccessful())
                                            {
                                                Toast.makeText(DoctorDetailsActivity.this, "Added to Appointment Cart", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(DoctorDetailsActivity.this, HomeActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });

    }


 private void getDoctorDetails(final  String doctorID){
     DatabaseReference doctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");

     doctorRef.child(doctorID).addValueEventListener(new ValueEventListener() {
         @Override
         public void onDataChange(@NonNull DataSnapshot dataSnapshot)
         {
             if (dataSnapshot.exists()){

                 Doctors doctors = dataSnapshot.getValue(Doctors.class);
                 doctorName.setText((doctors.getDoctorName()));
                 fees.setText(doctors.getFees());
                 //aptTimeSlot.setText(doctors.getAptTimeSlot());
                 //aptTimeSlot.setText(doctors.getAptDate());


                 doctorPhonee.setText(doctors.getPhone());
                 doctorAddresss.setText(doctors.getAddress());
                 mySlots.setText(doctors.getMySlots());
                 doctorDescription.setText(doctors.getDepartment());
                 alreadyBookedtxt.setText(doctors.getAlreadyBooked());
                 Picasso.get().load(doctors.getImage()).into(doctorImage);
             }
         }

         @Override
         public void onCancelled(@NonNull DatabaseError databaseError) {

         }
     });
 }


}