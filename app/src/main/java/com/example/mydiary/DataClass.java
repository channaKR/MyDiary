package com.example.mydiary;

import android.app.DownloadManager;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DataClass {

    private String Text_Title;
    private String Text_Record;
    private String Text_Date;
    private String Text_Time;
    private String Text_Location;
    private byte[] image;
    private int ID;
    private  String username;
    private String password;
    private String pin;
    public DataClass() {
    }

    public DataClass
            (String _Title, String _Record, String _Date, String _Time, String _Location, byte[] img) {
        Text_Title = _Title;
        Text_Record = _Record;
        Text_Date = _Date;
        Text_Time = _Time;
        Text_Location = _Location;
        image = img;

    }


    public DataClass(byte[] img) {

        image = img;

    }


    //Set Data

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setText_Title(String text_Title) {
        Text_Title = text_Title;
    }

    public void setText_Record(String text_Record) {
        Text_Record = text_Record;
    }

    public void setText_Date(String text_Date) {
        Text_Date = text_Date;
    }

    public void setText_Time(String text_Time) {
        Text_Time = text_Time;
    }

    public void setText_Location(String text_Location) {
        Text_Location = text_Location;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public void setUsername(String username) { this.username = username; }

    public void setPassword(String password) {this.password = password;}

    public void setPin(String pin) { this.pin = pin;}
    //__________________________________________________________________________


    //<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<getdata>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public int getID() {
        return ID;
    }

    public String getText_Title() {
        return Text_Title;
    }

    public String getText_Record() {
        return Text_Record;
    }

    public String getText_Date() {
        return Text_Date;
    }

    public String getText_Time() {
        return Text_Time;
    }

    public String getText_Location() {
        return Text_Location;
    }

    public byte[] getImage() {
        return image;
    }

    public String getUsername() { return username;}

    public String getPassword() {return password;}

    public String getPin() {return pin;}
    //___________________________________________________________________________


    public boolean Save(SQLiteDatabase db) {
        try {

            ContentValues cv=new ContentValues ();
            cv.put ( "title",Text_Title );
            cv.put ( "textrecord",Text_Record );
            cv.put ( "recorddate",Text_Date);
            cv.put ( "recordtime",Text_Time );
            cv.put ( "location",Text_Location );
            cv.put ( "image",image );
            //SQLiteDatabase db=this.getWritableDatabase ();
            db.insert ( "Diary",null ,cv);

            return true;

        }catch (Exception e){
            e.printStackTrace ();
            return  false;

        }

    }


    public List<DataClass> GetDiary(SQLiteDatabase Db) {
        try {
            List<DataClass> dataClasses = new ArrayList<> ();

            String query = "SELECT title,textrecord,recorddate,recordtime,location,image,ID from Diary";
            Cursor cursor = Db.rawQuery ( query, null );
            if (cursor.moveToFirst ()) {

                do {

                    DataClass data = new DataClass ( cursor.getString ( 0 ), cursor.getString ( 1 ),
                            cursor.getString ( 2 ), cursor.getString ( 3 ),
                            cursor.getString ( 4 ), cursor.getBlob ( 5 ) );


                    data.setID ( cursor.getInt ( 6 ) );

                    dataClasses.add ( data );


                } while (cursor.moveToNext ());
            }

            return dataClasses;

        } catch (Exception ex) {
            throw ex;


        }


    }

    public void Delete(SQLiteDatabase db) {
        try {
            String query = "Delete from Diary where ID ='" + ID + "'";
            db.execSQL ( query );


        } catch (Exception ex) {
            throw ex;
        }
    }


    public boolean Update(SQLiteDatabase db){
        try {

            ContentValues cv=new ContentValues ();
            cv.put ( "title",Text_Title );
            cv.put ( "textrecord",Text_Record );
            cv.put ( "recorddate",Text_Date);
            cv.put ( "recordtime",Text_Time );
            cv.put ( "location",Text_Location );
            cv.put ( "image",image );
            //SQLiteDatabase db=this.getWritableDatabase ();
            db.update ( "Diary",cv,"ID="+ID,null );

            return true;

        }catch (Exception e){
            e.printStackTrace ();
            return  false;

        }
    }




public  void CreateAccount(SQLiteDatabase db)
{
    try {
        String query = "INSERT INTO Account (username,password) VALUES ('" + username + "','" + password + "')";
        db.execSQL ( query );


    } catch (Exception ex) {
        throw ex;
    }


}
    public  Cursor Selectadmin(SQLiteDatabase db){


       Cursor cursor=db.rawQuery ( "SELECT username,password from Account ", null);
        //Cursor cursor = Db.rawQuery ( query, null );
       return cursor;

    }



    public  Boolean login(SQLiteDatabase db){


        Cursor cursor=db.rawQuery ( "SELECT username,password from Account WHERE username='"+username+"' AND password='"+password+"'", null);
        //Cursor cursor = Db.rawQuery ( query, null );
        if(cursor.getCount ()>0){
            return true;

        }else{
            return false;
        }
}

    public List<DataClass> Search(SQLiteDatabase Db,String _Text) {
        try {
            List<DataClass> dataClasses = new ArrayList<> ();

            String query = "SELECT title,textrecord,recorddate,recordtime,location,image,ID from Diary where " +
                    " title like '%"+ _Text+"%' or " +
                    "textrecord like '%"+ _Text+"%' or" +
                    " recorddate like '%"+ _Text+"%' or " +
                    "recordtime like '%"+ _Text+"%'";


            Cursor cursor = Db.rawQuery ( query, null );
            if (cursor.moveToFirst ()) {

                do {

                    DataClass data = new DataClass ( cursor.getString ( 0 ), cursor.getString ( 1 ),
                            cursor.getString ( 2 ), cursor.getString ( 3 ),
                            cursor.getString ( 4 ), cursor.getBlob ( 5 ) );


                    data.setID ( cursor.getInt ( 6 ) );

                    dataClasses.add ( data );


                } while (cursor.moveToNext ());
            }

            return dataClasses;

        } catch (Exception ex) {
            throw ex;


        }


    }

    public  Cursor Pin(SQLiteDatabase db){


        Cursor cursor=db.rawQuery ( "SELECT pin from pin ", null);
        //Cursor cursor = Db.rawQuery ( query, null );
        return cursor;

    }
    public  void setpin(SQLiteDatabase db)
    {
        try {
            String query = "INSERT INTO pin (pin) VALUES ('" + pin + "')";
            db.execSQL ( query );


        } catch (Exception ex) {
            throw ex;
        }




    }
    public  Boolean Pinlogin(SQLiteDatabase db){


        Cursor cursor=db.rawQuery ( "SELECT pin from pin WHERE pin='"+pin+"'", null);
        //Cursor cursor = Db.rawQuery ( query, null );
        if(cursor.getCount ()>0){
            return true;

        }else{
            return false;
        }
    }




    public void  restPin(SQLiteDatabase db){
        //Cursor cursor=db.rawQuery ( "DELETE FROM pin WHERE pin='"+pin+"'",null );
        try {
            String query = "DELETE FROM pin WHERE pin='"+pin+"'";
            db.execSQL ( query );


        } catch (Exception ex) {
            throw ex;
        }


    }


}
