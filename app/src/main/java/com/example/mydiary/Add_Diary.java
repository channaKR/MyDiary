package com.example.mydiary;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.ByteArrayOutputStream;
import java.util.Calendar;


public class Add_Diary extends AppCompatActivity {

    ImageView Image_add,imadd2;
    SQLiteDatabase Db;
   TextView _lbl_date,_lbl_Time,lblid;
   EditText _location_Select,_Tile,_Record;
   Button save_button,_update;
   byte[] ret;
    SQLiteDatabase datab;

    private int Calender_month,Calender_Date,Calender_Year;
    private int Time_Hour,Time_Min,Time_Second;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_add_diary );

        DataBase ();
        Button back_menu;
        back_menu=findViewById ( R.id.btn_backmenu );
        Image_add= findViewById ( R.id.Addimg );
        _lbl_date=findViewById ( R.id.lbl_dateDiary );
        _lbl_Time=findViewById ( R.id.lbl_timeDiary );
        _location_Select=findViewById ( R.id.location_addDiary);
        _Record=findViewById ( R.id.txt_Record );
        save_button=findViewById ( R.id.btn_save );
        _Tile=findViewById ( R.id.txt_Title );
        _update=findViewById ( R.id.btn_update );
         lblid=findViewById ( R.id.lblID );


        DataClass datareturn=new DataClass();
        Intent myintent=getIntent ();
        if(myintent.hasExtra ( "Title" )){
            _Tile.setText ( getIntent().getExtras().getString("Title") );
            _Record.setText ( getIntent ().getExtras ().getString ( "TextRecord" ) );
            _lbl_date.setText (  getIntent ().getExtras ().getString ( "Date" ) );
            _lbl_Time.setText (  getIntent ().getExtras ().getString ( "Time" ) );
            _location_Select.setText ( getIntent ().getExtras ().getString ( "Location" ) );

            save_button.setVisibility ( View.INVISIBLE );
            _update.setVisibility ( View.VISIBLE );
            String id=getIntent().getExtras().getString("ID");
            lblid.setText(getIntent().getExtras().getString("ID"));
            datareturn.setID ( Integer.valueOf (getIntent().getExtras().getString("ID")  ) );
            byte[]imageget=getIntent ().getExtras ().getByteArray ( "Image" );
            if(imageget!=null){
                Bitmap bitmap=BitmapConvert ( imageget );
                Image_add.setImageBitmap ( bitmap );

            }

            if(_Tile.toString ().isEmpty ()||_Record.toString ().isEmpty ()){
                Toast.makeText ( this, "Please fill the Recrd title and records", Toast.LENGTH_SHORT ).show ();
            }




        }
        if(myintent.hasExtra ( "locationreturn" )){

            _location_Select.setText ( getIntent ().getExtras ().getString ( "locationreturn" )  );


        }





        Button CancelDialog=findViewById ( R.id.btn_Cancel );
        Image_add.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                SelectSection ();
            }
        } );




        back_menu.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                Intent Home=new Intent (getApplicationContext (),Diary_Menu.class);
                startActivity ( Home );
                finish ();



            }
        } );

        _lbl_date.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                setDate ( _lbl_date );


            }


        } );


        _lbl_Time.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                selectTime ( _lbl_Time );
            }
        } );

        _location_Select.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                   Intent mapintent=new Intent (Add_Diary.this,map_location.class);
                   //startActivity ( mapintent );
                startActivityForResult (mapintent,0);


            }
        } );

       save_button.setOnClickListener ( new View.OnClickListener () {
           @Override
           public void onClick(View v) {
               DataClass data = new DataClass ();
               try {

                   data.setText_Title ( _Tile.getText ().toString () );
                   data.setText_Record ( _Record.getText ().toString () );
                   data.setText_Date ( _lbl_date.getText ().toString () );
                   data.setText_Time ( _lbl_Time.getText ().toString () );
                   data.setText_Location ( _location_Select.getText ().toString () );

                  if (Image_add != null) {
                       byte[] sv = ConverImg ( Image_add );

                       data.setImage ( sv );
                       data.Save ( Db );
                       Toast.makeText ( Add_Diary.this, "Save Record", Toast.LENGTH_LONG ).show ();
                       Intent intent=new Intent (Add_Diary.this,pinlog.class);
                       startActivity (intent);
                   }


               }catch (Exception e){
                   data.Save ( Db );
                   Toast.makeText ( Add_Diary.this, "Save Record", Toast.LENGTH_LONG ).show ();
                   Intent intent=new Intent (Add_Diary.this,pinlog.class);
                   startActivity (intent);

               }







           }
       } );


       _update.setOnClickListener ( new View.OnClickListener () {
           @Override
           public void onClick(View v) {

                         DataClass udata=new DataClass ();
                         udata.setText_Title ( _Tile.getText ().toString () );
                         udata.setText_Record ( _Record.getText ().toString () );
                         udata.setText_Date ( _lbl_date.getText ().toString () );
                         udata.setText_Time ( _lbl_Time.getText ().toString () );
                         udata.setText_Location ( _location_Select.getText ().toString () );

                         udata.setID ( Integer.valueOf ( lblid.getText ().toString () ) );


                          try{
                              if(Image_add!=null){
                                  udata.setImage ( ConverImg ( Image_add ) );
                                  udata.Update ( Db );
                                  Toast.makeText ( Add_Diary.this, "Record Updated", Toast.LENGTH_LONG ).show ();
                                  Intent intent=new Intent (Add_Diary.this,ViewDiary.class);
                                  startActivity (intent);

                              }




                          }catch (Exception e){
                              udata.Update ( Db );
                              Toast.makeText ( Add_Diary.this, "Record Updated", Toast.LENGTH_LONG ).show ();
                              Intent intent=new Intent (Add_Diary.this,ViewDiary.class);
                              startActivity (intent);

                          }




           }
       } );

       back_menu.setOnClickListener ( new View.OnClickListener () {
           @Override
           public void onClick(View v) {
               DiaryBack();


           }
       } );

    }

    private void SelectSection() {
        AlertDialog.Builder builder = new AlertDialog.Builder ( Add_Diary.this );
        LayoutInflater inflater = getLayoutInflater ();
        View dialog = inflater.inflate ( R.layout.dialog_box, null );
        builder.setCancelable ( false );
        builder.setView ( dialog );
        ImageView cam = dialog.findViewById ( R.id.cmaer_img );
        ImageView gal = dialog.findViewById ( R.id.gal_img );
        Button CancelDialog=dialog.findViewById ( R.id.btn_Cancel );
        final AlertDialog alertBox = builder.create ();
        alertBox.show ();
        cam.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                if(checkPermission ()){
                    Camimg ();


                }
                alertBox.cancel ();

            }
        } );
        gal.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {

                takeImgfromgal();
                alertBox.cancel ();
            }
        } );


        CancelDialog.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                alertBox.cancel ();
            }
        } );




    }
    private byte[]ConverImg(ImageView imgview){
        if(imgview!=null){
            Bitmap bitmap=((BitmapDrawable)imgview.getDrawable ()).getBitmap ();
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream ();
            bitmap.compress ( Bitmap.CompressFormat.JPEG,80,byteArrayOutputStream );
            return  byteArrayOutputStream.toByteArray ();
        }
        else {
            return null;
        }


    }

    private Bitmap BitmapConvert(byte[] imagebyte){
        if(imagebyte!=null){
            return BitmapFactory.decodeByteArray ( imagebyte,0,imagebyte.length );
        }
        else{
            return null;
        }



    }


    private void takeImgfromgal()
    {

      Intent pickimg=new Intent (Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
      startActivityForResult ( pickimg,1 );



    }
    private void Camimg(){

      Intent camerintent=new Intent (MediaStore.ACTION_IMAGE_CAPTURE);
      try {


      if(camerintent.resolveActivity ( getPackageManager () )!=null){

          startActivityForResult ( camerintent,2 );

      }}catch (ActivityNotFoundException e){
          
      }
    }




    private boolean checkPermission(){
           if(Build.VERSION.SDK_INT>=23){
                int camerpermisions= ActivityCompat.checkSelfPermission ( Add_Diary.this, Manifest.permission.CAMERA );

                if(camerpermisions== PackageManager.PERMISSION_DENIED){
                    ActivityCompat.requestPermissions ( Add_Diary.this,new String[]{Manifest.permission.CAMERA},20 );
                    return false;
                }

           }
           return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult ( requestCode, permissions, grantResults );
        DataClass datareturn=new DataClass();
        Intent myintent=getIntent ();

        if(requestCode==20 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                Camimg ();
        }
        else
        {

            Toast.makeText ( this, "Permission not Granted", Toast.LENGTH_SHORT ).show ();
        }

    }




    public void setDate(TextView _lbl_date)
{
    final Calendar calendar=Calendar.getInstance ();
    Calender_Date=calendar.get ( Calendar.DATE );
    Calender_month=calendar.get ( Calendar.MONTH );
    Calender_Year=calendar.get ( Calendar.YEAR );
    DatePickerDialog datePickerDialog=new DatePickerDialog ( Add_Diary.this, android.R.style.Theme_DeviceDefault_Dialog, new DatePickerDialog.OnDateSetListener () {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            _lbl_date.setText ( dayOfMonth+"/"+month+"/"+year );
        }
    },Calender_Year,Calender_month,Calender_Date );

    datePickerDialog.getDatePicker ().setMaxDate ( System.currentTimeMillis ()-1000 );

    datePickerDialog.show ();

}


    private void selectTime(TextView lbl_Time)
    {
        Calendar current_Time=Calendar.getInstance ();
        Time_Hour=  current_Time.get ( Calendar.HOUR_OF_DAY );
        Time_Min=current_Time.get (Calendar.MINUTE  );
        TimePickerDialog timePickerDialog=new TimePickerDialog ( Add_Diary.this, new TimePickerDialog.OnTimeSetListener () {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String hour=""+hourOfDay;
                String min=""+minute;
                if(hourOfDay<10){
                    hour="0"+hour;

                }
                if(minute<10){
                    min="0"+min;
                }

                String time=hour+":"+min;
                lbl_Time.setText ( time );




            }
        } ,Time_Hour,Time_Min, true);
        timePickerDialog.show ();



    }

    public String datevalue(String date)
    {
        date=date.toString ();

        return date;

    }



    public void DataBase()
    {



        try {
            Db=openOrCreateDatabase ( "DiaryDataBase",MODE_PRIVATE,null );
            String CreateTable="CREATE TABLE IF NOT EXISTS  \"Diary\" (\n" +
                    "\t\"ID\"\tINTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "\t\"title\"\tTEXT,\n" +
                    "\t\"textrecord\"\tTEXT,\n" +
                    "\t\"recorddate\"\tNUMERIC,\n" +
                    "\t\"recordtime\"\tNUMERIC,\n" +
                    "\t\"location\"\tTEXT,\n" +
                    "\t\"image\"\tBLOB\n" +
                    ");";

            Db.execSQL ( CreateTable );

        }catch (Exception ex){
            Toast.makeText ( getApplicationContext (),
                    "DataBase Error",Toast.LENGTH_LONG ).show ();


        }




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult ( requestCode, resultCode, data );

        Imgeselect(requestCode,resultCode,data);
        getLocation(requestCode, data);


    }




public int getLocation(int requestCode,Intent data)
{
    if(requestCode==0){


        if(data.getStringExtra ( "locationreturn" ) ==null){

        }else{
            _location_Select.setText ( data.getStringExtra ( "locationreturn" ) );
        }


    }
    return 4;
}

public void Imgeselect(int requestCode,int resultCode,Intent data)
{

    switch (requestCode){


        case 1:

            if(resultCode==RESULT_OK){
                Uri selectimguri=data.getData ();
                Image_add.setImageURI ( selectimguri );

            }
            break;

        case 2:

            if(resultCode== Activity.RESULT_OK){

                Bundle bundle=data.getExtras ();
                Bitmap bitmapimg=(Bitmap) bundle.get ( "data" );
                Image_add.setImageBitmap ( bitmapimg );

            }
            break;





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
