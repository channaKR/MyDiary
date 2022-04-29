package com.example.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Diary_Menu extends AppCompatActivity {
    Button Add_Record,_ViewDiary,logout,setmypin;
    SQLiteDatabase Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_diary_menu );


        ActivityCompat.requestPermissions ( this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE},
                PackageManager.PERMISSION_GRANTED );
        _ViewDiary=findViewById ( R.id.ViewDiary );
        Add_Record=findViewById ( R.id.AddDiary );
        logout=findViewById ( R.id.lgout );
        setmypin=findViewById ( R.id.set_pin );

        Add_Record.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent (Diary_Menu.this,Add_Diary.class);
                startActivity ( intent );


            }
        } );

        _ViewDiary.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent viewdiary=new Intent (getApplicationContext (),pinlog.class);
                startActivity ( viewdiary );
            }
        } );

        setmypin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                Intent pin=new Intent (getApplicationContext (),SetPin.class);
                startActivity ( pin );
            }
        } );
        logout.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder (logout.getContext ()  );
                builder.setMessage("Are you sure, you want to Logout?").setTitle ( "Logout" ).setPositiveButton ( "YES", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Diary_Menu.this.finish();
                        System.exit(0);
                    }
                } ).setNegativeButton ( "NO", new DialogInterface.OnClickListener () {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                } );
                AlertDialog alertDialog=builder.create ();
                builder.show();
            }
        } );

    }




}