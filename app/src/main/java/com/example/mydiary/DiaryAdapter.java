package com.example.mydiary;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class DiaryAdapter extends RecyclerView.Adapter<DiaryAdapter.ViewHolder> {
    List<DataClass> dataClassList;
    SQLiteDatabase Db;


    public DiaryAdapter(List<DataClass>dataClasses,SQLiteDatabase _Db){
        dataClassList=dataClasses;
        Db=_Db;
    }
    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater=LayoutInflater.from ( parent.getContext () );
        View DiaryRecords=layoutInflater.inflate ( R.layout.datarow,parent,false );
        ViewHolder viewHolder=new ViewHolder ( DiaryRecords );
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull DiaryAdapter.ViewHolder holder, int position) {
          DataClass dataClass=dataClassList.get ( position );
          holder.lblTitle.setText ( dataClass.getText_Title () );
          holder.lblEDate.setText ( dataClass.getText_Date () );

          holder.btnDelete.setOnClickListener ( new View.OnClickListener () {
              @Override
              public void onClick(View v) {
                  AlertDialog.Builder builder=new AlertDialog.Builder(holder.btnDelete.getContext());
                  builder.setMessage("Are you sure, you want to delete?")
                          .setTitle("Confirm Delete")
                          .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {
                                  dataClass.Delete(Db);
                                  dataClassList.remove(position);
                                  notifyItemRemoved(position);
                              }
                          })
                          .setNegativeButton("No", new DialogInterface.OnClickListener() {
                              @Override
                              public void onClick(DialogInterface dialog, int which) {

                              }
                          });
                  AlertDialog dialog=  builder.create();
                  builder.show();



                  //Toast.makeText ( v.getContext (), dataClass.getID (), Toast.LENGTH_SHORT ).show ();
              }
          } );


          holder.btnEdit.setOnClickListener ( new View.OnClickListener () {
              @Override
              public void onClick(View v) {
                  int id=dataClass.getID ();
                  byte[] imgreturn= dataClass.getImage ();

                  Intent intent=new Intent (v.getContext (),Add_Diary.class);
                  intent.putExtra ( "ID",String.valueOf (  id) );
                  intent.putExtra ( "Title",dataClass.getText_Title () );
                  intent.putExtra ( "TextRecord",dataClass.getText_Record () );
                  intent.putExtra ( "Date",dataClass.getText_Date () );
                  intent.putExtra ( "Time",dataClass.getText_Time () );
                  intent.putExtra ( "Location",dataClass.getText_Location () );
                  if(dataClass.getImage ()!=null){
                  intent.putExtra ( "Image",dataClass.getImage ());
                  }
                  v.getContext().startActivity(intent);
                   //Toast.makeText ( v.getContext (),String.valueOf ( dataClass.getImage () ).toString () , Toast.LENGTH_SHORT ).show ();



              }
          } );

    }

    @Override
    public int getItemCount() {
        return dataClassList.size ();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView lblTitle, lblEDate;
        Button btnEdit, btnDelete;
        LinearLayout linearLayout;
        ConstraintLayout constraintLayout;
        ImageView small;

        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super ( itemView );
            lblTitle = itemView.findViewById(R.id.lblEname);
            lblEDate = itemView.findViewById(R.id.lblDate);
            linearLayout=  itemView.findViewById(R.id.linearLayEvent);
            constraintLayout =  itemView.findViewById(R.id.conLayoutEData);
            btnEdit =  itemView.findViewById(R.id.btnEdit);
            btnDelete =  itemView.findViewById(R.id.btnDelete);
            //small=itemView.findViewById ( R.id.smallview );



        }
    }
}
