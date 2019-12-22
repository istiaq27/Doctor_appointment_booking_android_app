package coms.example.asus.doctor_appointment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class AdminAddNewDoctorActivity extends AppCompatActivity {
    private String DepartmentName, Description, Fees, Doctorname, DoctorAddress, DoctorPhone,
            DoctorSpeciality, saveCurrentDate, saveCurrentTime;
    private Button AddNewDoctorButton, selectYourSlotsBtn;
    private ImageView InputDoctorImage;
    private EditText InputDoctorName, InputDoctorDescription, InputDoctorFees, InputDoctorAddress,
            InputDoctorPhone, InputDoctorSpeciality;
    private static final int GalleryPick = 1;
    private Uri ImageUri;
    private String DoctorRandomKey, downloadImageUrl;
    private StorageReference DoctorImagesRef;
    private DatabaseReference DoctorRef;
    private ProgressDialog loadingBar;
    private TextView viewYourSelectedSlotsTxtView;
    String[] listSlots;
    boolean[] checkedItems;
    ArrayList<Integer> mSlots  = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_new_doctor);

        DepartmentName = getIntent().getExtras().get("department").toString();
        DoctorImagesRef = FirebaseStorage.getInstance().getReference().child("Doctor Images");
        DoctorRef = FirebaseDatabase.getInstance().getReference().child("Doctors");


        selectYourSlotsBtn = (Button)findViewById(R.id.select_your_slots_btn);
        viewYourSelectedSlotsTxtView = (TextView) findViewById(R.id.slotSelected);
        AddNewDoctorButton = (Button) findViewById(R.id.add_new_doctor);
        InputDoctorImage = (ImageView) findViewById(R.id.select_doctor_image);
        InputDoctorName = (EditText) findViewById(R.id.doctor_name);
        InputDoctorDescription = (EditText) findViewById(R.id.doctor_description);
        InputDoctorFees = (EditText) findViewById(R.id.doctor_fees);
        InputDoctorAddress = (EditText) findViewById(R.id.doctor_address_add);
        InputDoctorPhone = (EditText) findViewById(R.id.doctor_phone_number);
        InputDoctorSpeciality = (EditText) findViewById(R.id. doctors_speciality);
        loadingBar = new ProgressDialog(this);

        listSlots = getResources().getStringArray(R.array.doctors_slots);
        checkedItems = new boolean[listSlots.length];

        InputDoctorImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                OpenGallery();
            }
        });


        AddNewDoctorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                ValidateDoctorData();
            }
        });
        selectYourSlotsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(AdminAddNewDoctorActivity.this);
                mBuilder.setTitle(R.string.dialog_title);
                mBuilder.setMultiChoiceItems(listSlots , checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked)
                    {
                        if(isChecked){
                            mSlots.add(position);
                        }else{
                            mSlots.remove((Integer.valueOf(position)));
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton(R.string.ok_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for (int i = 0; i < mSlots.size(); i++) {
                            item = item + listSlots [mSlots.get(i)];
                            if (i != mSlots.size() - 1) {
                                item = item + ", ";
                            }
                        }
                        viewYourSelectedSlotsTxtView.setText(item);
                    }
                });
                mBuilder.setNegativeButton(R.string.dismiss_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                mBuilder.setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        for (int i = 0; i < checkedItems.length; i++) {
                            checkedItems[i] = false;
                            mSlots.clear();
                            viewYourSelectedSlotsTxtView.setText("");
                        }
                    }
                });
                AlertDialog mDialog = mBuilder.create();
                mDialog.show();
            }
        });

    }


    private void OpenGallery()
    {
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GalleryPick);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==GalleryPick  &&  resultCode==RESULT_OK  &&  data!=null)
        {
            ImageUri = data.getData();
            InputDoctorImage.setImageURI(ImageUri);
        }
    }


    private void ValidateDoctorData()
    {
        Description = InputDoctorDescription.getText().toString();
        Fees = InputDoctorFees.getText().toString();
        Doctorname = InputDoctorName.getText().toString();
        DoctorAddress = InputDoctorAddress.getText().toString();
        DoctorPhone = InputDoctorPhone.getText().toString();
        DoctorSpeciality = InputDoctorSpeciality.getText().toString();


        if (ImageUri == null)
        {
            Toast.makeText(this, "Doctor image is mandatory...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Description))
        {
            Toast.makeText(this, "Please write Doctor description...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Fees))
        {
            Toast.makeText(this, "Please write Doctor Fees...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(Doctorname))
        {
            Toast.makeText(this, "Please write Doctor Name...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorPhone))
        {
            Toast.makeText(this, "Please write Doctor Contact Number...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorAddress))
        {
            Toast.makeText(this, "Please write Doctor Address...", Toast.LENGTH_SHORT).show();
        }
        else if (TextUtils.isEmpty(DoctorSpeciality))
        {
            Toast.makeText(this, "Please write Doctor Speciality...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            StoreDoctorInformation();
        }
    }



    private void StoreDoctorInformation()
    {
        loadingBar.setTitle("Add New Doctor");
        loadingBar.setMessage("Please wait while we are adding the new Doctor.");
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        //DoctorRandomKey = saveCurrentDate + saveCurrentTime;
        DoctorRandomKey = DoctorPhone;


        final StorageReference filePath = DoctorImagesRef.child(ImageUri.getLastPathSegment() + DoctorRandomKey + ".jpg");

        final UploadTask uploadTask = filePath.putFile(ImageUri);


        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e)
            {
                String message = e.toString();
                Toast.makeText(AdminAddNewDoctorActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                loadingBar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
            {
                Toast.makeText(AdminAddNewDoctorActivity.this, "Doctor Image uploaded Successfully...", Toast.LENGTH_SHORT).show();

                Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception
                    {
                        if (!task.isSuccessful())
                        {
                            throw task.getException();
                        }

                        downloadImageUrl = filePath.getDownloadUrl().toString();
                        return filePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task)
                    {
                        if (task.isSuccessful())
                        {
                            downloadImageUrl = task.getResult().toString();

                            Toast.makeText(AdminAddNewDoctorActivity.this, "got the Doctor image Url Successfully...", Toast.LENGTH_SHORT).show();

                            SaveDoctorInfoToDatabase();
                        }
                    }
                });
            }
        });
    }



    private void SaveDoctorInfoToDatabase()
    {
        String mySlots = viewYourSelectedSlotsTxtView.getText().toString();
        HashMap<String, Object> productMap = new HashMap<>();
        productMap.put("doctorid", DoctorRandomKey);
        productMap.put("date", saveCurrentDate);
        productMap.put("time", saveCurrentTime);
        productMap.put("speciality", DoctorSpeciality);
        productMap.put("image", downloadImageUrl);
        productMap.put("department", DepartmentName);
        productMap.put("description",Description);
        productMap.put("fees", Fees);
        productMap.put("doctorName", Doctorname);
        productMap.put("phone", DoctorPhone);
        productMap.put("address", DoctorAddress);
        productMap.put("mySlots", mySlots);

        DoctorRef.child(DoctorRandomKey).updateChildren(productMap)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        if (task.isSuccessful())
                        {
                            Intent intent = new Intent(AdminAddNewDoctorActivity.this, AdminCategoryActivity.class);
                            startActivity(intent);

                            loadingBar.dismiss();
                            Toast.makeText(AdminAddNewDoctorActivity.this, "Doctor is added successfully..", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            loadingBar.dismiss();
                            String message = task.getException().toString();
                            Toast.makeText(AdminAddNewDoctorActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
