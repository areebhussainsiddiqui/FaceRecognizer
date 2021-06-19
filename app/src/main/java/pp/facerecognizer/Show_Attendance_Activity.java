package pp.facerecognizer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Show_Attendance_Activity extends AppCompatActivity {
    private RecyclerView RV;
    private Show_Attendance_Adapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ProgressDialog progressDialog;
    DatabaseReference reference;
    ArrayList<Model_Attendance> list;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show__attendance);
        progressDialog = new ProgressDialog (this);
        RV =  findViewById(R.id.show_RV);
        progressDialog.setMessage ("Fetching Data...");
        progressDialog.show ();
        RV.setLayoutManager( new LinearLayoutManager(this));


        reference = FirebaseDatabase.getInstance().getReference().child("Attendance");
        reference.addValueEventListener(new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren())
                {
                    Model_Attendance p = dataSnapshot1.getValue(Model_Attendance.class);
                    list.add(p);

                    progressDialog.dismiss ();
                }
                adapter = new Show_Attendance_Adapter (Show_Attendance_Activity.this,list);
                RV.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Show_Attendance_Activity.this, "Something is wrong", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss ();
            }
        });
    }
}