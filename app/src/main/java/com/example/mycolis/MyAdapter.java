package com.example.mycolis;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyAdapter extends FirebaseRecyclerAdapter<MainModel,MyAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MyAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyAdapter.myViewHolder holder,  int position, @NonNull MainModel model) {
        holder.name.setText(model.getName());
        holder.id.setText(Integer.toString(model.getId()));
        holder.adresse.setText(model.getAdresse());
        holder.prix.setText(model.getPrix());

        Glide.with(holder.img.getContext())
                .load(model.getTurl())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .centerCrop()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);
        holder.btnEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                final DialogPlus dialogPlus = DialogPlus.newDialog(
                                holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update))
                        .setExpanded(true, 1200)
                        .create();

                //dialogPlus.show();

                View view=dialogPlus.getHolderView();

                EditText img=view.findViewById(R.id.txtimage) ;
                EditText name=view.findViewById(R.id.txtName) ;
                EditText adresse=view.findViewById(R.id.txtadresse) ;
                EditText prix=view.findViewById(R.id.txtprix) ;

                Button btnUpdate=view.findViewById((R.id.btnUpdate));
                name.setText(model.getName());
                adresse.setText(model.getAdresse());
                prix.setText(model.getPrix());
                img.setText(model.getTurl());

                dialogPlus.show();

                btnUpdate.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name.getText().toString());
                    map.put("adresse", adresse.getText().toString());
                    map.put("prix", prix.getText().toString());
                    map.put("image", img.getText().toString());

                    FirebaseDatabase.getInstance().getReference().child("Colis")
                            .child(getRef(position).getKey()).updateChildren(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(holder.name.getContext(), "Data Update Successfully", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();


                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull @NotNull Exception e) {
                                    Toast.makeText(holder.name.getContext(), "Error while Updating ", Toast.LENGTH_SHORT).show();
                                    dialogPlus.dismiss();
                                }
                            });


                }
                });

            }
        });

         holder.btnSupp.setOnClickListener(new View.OnClickListener(){
             @Override
             public void onClick(View v){
                 AlertDialog.Builder builder=new AlertDialog.Builder(holder.name.getContext());
                 builder.setTitle("Are You Sure ?");
                 builder.setMessage("Deleted data can't be Undo.");

                 builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         FirebaseDatabase.getInstance().getReference().child("Colis")
                                 .child(getRef(position).getKey()).removeValue();

                     }
                 });
                 builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                     @Override
                     public void onClick(DialogInterface dialog, int which) {
                         Toast.makeText(holder.name.getContext(),"Cancelled.",Toast.LENGTH_SHORT).show();

                     }
                 });
                 builder.show();
             }
         });
    }

    @NonNull
    @Override
    public MyAdapter.myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_main,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
      CircleImageView img;
      TextView name,adresse,prix,id;
      Button btnEdit,btnSupp;
      public myViewHolder(@NotNull View itemView){
          super(itemView);
          img=(CircleImageView)itemView.findViewById(R.id.img1) ;
          name=itemView.findViewById(R.id.name) ;
          id=itemView.findViewById(R.id.id) ;
          adresse=itemView.findViewById(R.id.adresse) ;
          prix=itemView.findViewById(R.id.prix) ;
          btnEdit=itemView.findViewById(R.id.btnEdit) ;
          btnSupp=itemView.findViewById(R.id.btnSupp) ;

      }
  }
}
