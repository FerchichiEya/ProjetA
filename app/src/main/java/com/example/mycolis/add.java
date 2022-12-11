package com.example.mycolis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class add extends AppCompatActivity {
    EditText name,id,adress,img,prix;
    Button submit,back;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add);

        name=(EditText)findViewById(R.id.txtName);
        adress=(EditText)findViewById(R.id.txtadresse);
        id=(EditText)findViewById(R.id.txtId);
        img=(EditText)findViewById(R.id.txtimage);
        prix=(EditText)findViewById(R.id.txtprix);

        back=findViewById(R.id.add_back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(i);
            }
        });

        submit=findViewById(R.id.add_submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                processinsert();
            }

            private void processinsert() {

                    Map<String, Object> map = new HashMap<>();
                map.put("id",Integer.parseInt(id.getText().toString()));
                map.put("name", name.getText().toString());
                    map.put("adresse", adress.getText().toString());
                    map.put("prix", prix.getText().toString());
                    map.put("image", img.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("Colis").push()
                        .setValue(map)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                id.setText("");
                                name.setText("");
                                adress.setText("");
                                prix.setText("");
                                img.setText("");
                                Toast.makeText(getApplicationContext(),"Inserted Successfully",Toast.LENGTH_LONG).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e)
                            {
                                Toast.makeText(getApplicationContext(),"Could not insert",Toast.LENGTH_LONG).show();
                            }
                        });



                }
            });
            }
        }


