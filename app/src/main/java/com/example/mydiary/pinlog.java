package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class pinlog extends AppCompatActivity {
Button bckHome,Lgpin;
TextView tpin;
SQLiteDatabase Db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_pinlog );
        bckHome=findViewById ( R.id.back_menupin2 );
        Lgpin=findViewById ( R.id.lg_pin );
        tpin=findViewById ( R.id.pin );
        DataClass pinlg=new DataClass ();
        datab ();

        MainPage ( pinlg );
        bckHome.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                DiaryBack();
            }
        } );

        Lgpin.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                pinlg.setPin ( tpin.getText ().toString () );
                Boolean pinl=pinlg.Pinlogin ( Db );
                if(pinl.equals ( true )){
                    Intent intent=new Intent (getApplicationContext (),ViewDiary.class);

                    startActivity ( intent );


                }
                else if(pinl.equals ( false )){
                    Toast.makeText ( pinlog.this, "Wrong pin", Toast.LENGTH_SHORT ).show ();


                }
            }
        } );

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed ();

        DiaryBack();


    }
    public  void  DiaryBack()
    {
        Intent intent=new Intent (getApplicationContext (),Diary_Menu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();



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

    public void MainPage(DataClass mydb){

        Cursor cursor=mydb.Pin ( Db );

        if(cursor.getCount ()==0)
        {
           Intent intent=new Intent (getApplicationContext (),ViewDiary.class);
           startActivity ( intent );

        }
        else
        {


            while(cursor.moveToNext ()){

                //Toast.makeText ( MainActivity.this, "username:"+cursor.getString ( 0 ), Toast.LENGTH_SHORT ).show ();
                if(cursor.getString ( 0 )!=null){



                }



            }



        }




    }

}