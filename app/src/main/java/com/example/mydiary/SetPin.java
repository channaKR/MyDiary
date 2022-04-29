package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPin extends AppCompatActivity {
    Button Pinset,Pinreset,back_home;
    EditText pintxt;
    SQLiteDatabase Db;
    DataClass datcls=new DataClass ();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_set_pin );
        datab();

        Pinset=findViewById ( R.id.lg_pin );
        Pinreset=findViewById ( R.id.reset_pin );
        pintxt=findViewById ( R.id.pin );
        back_home=findViewById ( R.id.back_menupin2 );


        MainPage(datcls,Pinset,Pinreset);


        Pinset.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if (pintxt.getText ().toString ().length () < 6) {
                    Toast.makeText ( SetPin.this, "Please Enter pin number more than 6 numbers", Toast.LENGTH_SHORT ).show ();
                } else {

                    datcls.setPin ( pintxt.getText ().toString () );
                    datcls.setpin ( Db );
                    Toast.makeText ( SetPin.this, "Pin number set", Toast.LENGTH_SHORT ).show ();
                    MainPage ( datcls, Pinset, Pinreset );
                }

            }
        } );

        back_home.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DiaryBack();
            }
        } );


        Pinreset.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {




                    datcls.setPin ( pintxt.getText ().toString () );
                    Boolean pinlg = datcls.Pinlogin ( Db );

                    if (pinlg.equals ( true )) {
                        datcls.restPin ( Db );
                        Toast.makeText ( SetPin.this, "Reset pin", Toast.LENGTH_SHORT ).show ();
                        DiaryBack ();
                    } else {
                        Toast.makeText ( SetPin.this, "Invalid pin", Toast.LENGTH_SHORT ).show ();
                    }

                }

        } );


    }
    public void MainPage(DataClass mydb,Button setpin,Button restpin){

        Cursor cursor=mydb.Pin ( Db );
        if(cursor.getCount ()==0)
        {
            Toast.makeText ( SetPin.this, "No data", Toast.LENGTH_SHORT ).show ();



        }
        else
        {


            while(cursor.moveToNext ()){

                //Toast.makeText ( MainActivity.this, "username:"+cursor.getString ( 0 ), Toast.LENGTH_SHORT ).show ();
                if(cursor.getString ( 0 )!=null){

                    setpin.setVisibility ( View.INVISIBLE );
                    restpin.setVisibility ( View.VISIBLE );
                    pintxt.setHint ( "Enter old pin number" );

                }



            }



        }



    }

    private  void  datab(){


        try {
            Db=openOrCreateDatabase ( "DiaryDataBase",MODE_PRIVATE,null );
            String CreateTable="CREATE TABLE IF NOT EXISTS \"pin\" (\n" +
                    "\t\"pin\"\tTEXT NOT NULL\n" +
                    ");";

            Db.execSQL ( CreateTable );

        }catch (Exception ex){
            Toast.makeText ( getApplicationContext (),
                    "DataBase Error",Toast.LENGTH_LONG ).show ();


        }

    }
    public  void  DiaryBack()
    {
        Intent intent=new Intent (getApplicationContext (),Diary_Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();



    }

}