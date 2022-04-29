package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
      SQLiteDatabase Db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Button Log,CreateNew;
        EditText Username,Password;
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        //Permission
         DataBase ();


        Log=findViewById ( R.id.btn_Login );
        CreateNew=findViewById ( R.id.btn_NewD );
        Username=findViewById ( R.id.txt_UserName );
        Password=findViewById ( R.id.txt_Password );
         DataClass db=new DataClass ();
         MainPage ( db,Log,CreateNew );

        Log.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                String _Username = Username.getText ().toString ();
                String _Password = Password.getText ().toString ();
                db.setUsername ( _Username );
                db.setPassword ( _Password );

                Boolean datalog=db.login ( Db );
                if(datalog.equals ( true )){

                    Intent intent=new Intent (MainActivity.this,Diary_Menu.class);
                    startActivity (intent);


                }

                else if(datalog.equals ( false )){
                    Toast.makeText ( MainActivity.this, "Please check your username and password", Toast.LENGTH_SHORT ).show ();
                }
                else if(_Username.isEmpty ()||_Password.isEmpty ()){
                    Toast.makeText ( MainActivity.this, "Please check your username and password", Toast.LENGTH_SHORT ).show ();


                }







            }
        } );




        CreateNew.setOnClickListener ( new View.OnClickListener () {
             @Override
             public void onClick(View v) {



                 String _Username=Username.getText ().toString ();
                 String _Password=Password.getText ().toString ();



             if(_Username.isEmpty ()||_Password.isEmpty ()) {
                     Toast.makeText ( MainActivity.this, "Please check your user name and password", Toast.LENGTH_SHORT ).show ();

                 }else if(_Username.length ()<6||_Password.length ()<6){
                 Toast.makeText ( MainActivity.this, "Invalid user name or password length", Toast.LENGTH_SHORT ).show ();

             }


             else{
                 db.setUsername ( _Username );
                 db.setPassword ( _Password );
                 db.CreateAccount ( Db );
                 MainPage ( db, Log, CreateNew );



                 }


             }
         } );

    }

   public void MainPage(DataClass mydb,Button lg,Button createnewone){

       Cursor cursor=mydb.Selectadmin ( Db );
       if(cursor.getCount ()==0)
       {
           Toast.makeText ( MainActivity.this, "No data", Toast.LENGTH_SHORT ).show ();

           lg.setVisibility ( View.INVISIBLE );

       }
       else
       {


           while(cursor.moveToNext ()){

               //Toast.makeText ( MainActivity.this, "username:"+cursor.getString ( 0 ), Toast.LENGTH_SHORT ).show ();
               if(cursor.getString ( 0 )!=null){

                   lg.setVisibility ( View.VISIBLE );
                   createnewone.setVisibility ( View.INVISIBLE );

               }



           }



       }



   }
public void DataBase()
    {



        try {
            Db=openOrCreateDatabase ( "DiaryDataBase",MODE_PRIVATE,null );
            String CreateTable="CREATE TABLE IF NOT EXISTS  \"Account\" (\n" +
                    "\t\"username\"\tTEXT,\n" +
                    "\t\"password\"\tTEXT NOT NULL,\n" +
                    "\tPRIMARY KEY(\"username\")\n" +
                    ");";

            Db.execSQL ( CreateTable );

        }catch (Exception ex){
            Toast.makeText ( getApplicationContext (),
                    "DataBase Error",Toast.LENGTH_LONG ).show ();


        }




    }







    }


