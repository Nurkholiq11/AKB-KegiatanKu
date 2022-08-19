package com.nurkholiq.kegiatanku;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private DatabaseReference reference;

    private ProgressDialog loader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.homeToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("KegiatanKu App");

        RecyclerView recyclerView = findViewById(R.id.recylerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        loader = new ProgressDialog(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        assert mUser != null;
        String onlineUserID = mUser.getUid();
        reference = FirebaseDatabase.getInstance().getReference().child("task").child(onlineUserID);

        FloatingActionButton floatingActionButton = findViewById(R.id.btn_float);
        floatingActionButton.setOnClickListener(view -> addTask());
    }

    private void addTask(){
        AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);

        View view = inflater.inflate(R.layout.input_task, null);
        myDialog.setView(view);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText task = view.findViewById(R.id.nameTask);
        final EditText description = view.findViewById(R.id.descriptionTask);
        Button save = view.findViewById(R.id.btn_save);
        Button cancel = view.findViewById(R.id.btn_cancel);

        cancel.setOnClickListener((v) -> dialog.dismiss());

        save.setOnClickListener((v) -> {
            String mTask = task.getText().toString().trim();
            String mDescription = description.getText().toString().trim();
            String id = reference.push().getKey();
            String date = DateFormat.getDateInstance().format(new Date());

            if(TextUtils.isEmpty(mTask)) {
                task.setError("Isi Data Kegiatan");
            } else if(TextUtils.isEmpty(mDescription)) {
                task.setError("Isi Data Kegiatan");
            } else {
                loader.setMessage("Tambah Data Kegiatan");
                loader.setCanceledOnTouchOutside(false);
                loader.show();

                Model model = new Model(mTask, mDescription, id, date);
                assert id != null;
                reference.child(id).setValue(model).addOnCompleteListener(task1 -> {
                    if(task1.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Data Kegiatan Berhasil Ditambah", Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                    } else {
                        String error = Objects.requireNonNull(task1.getException()).toString();
                        Toast.makeText(MainActivity.this, "Keterangan Gagal: " + error, Toast.LENGTH_SHORT).show();
                        loader.dismiss();
                    }
                });
            }
            dialog.dismiss();
        });
        dialog.show();
    }
}