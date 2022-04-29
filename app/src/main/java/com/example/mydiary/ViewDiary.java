package com.example.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.List;

public class ViewDiary extends AppCompatActivity {
       SQLiteDatabase Db;
       ImageButton btnsearch,back;
       EditText txtSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_view_diary );
        btnsearch=findViewById ( R.id.btn_SearchData );
        txtSearch=findViewById ( R.id.Text_Search );
        back=findViewById ( R.id.btn_back );


        DataBase ();

        RecyclerView recyclerView=findViewById ( R.id.RecyclerData );
        DataClass db=new DataClass ();


        List<DataClass>diary=db.GetDiary ( Db );
        DiaryAdapter adapter=new DiaryAdapter (diary ,Db );
                recyclerView.setLayoutManager ( new LinearLayoutManager ( this ) );
                recyclerView.setAdapter ( adapter );

                btnsearch.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {
                //diary.clear ();
                List<DataClass> diary=db.Search ( Db,txtSearch.getText ().toString ());
               DiaryAdapter adapter=new DiaryAdapter (diary ,Db );
               recyclerView.setLayoutManager ( new LinearLayoutManager ( getApplicationContext () ) );
                recyclerView.setAdapter ( adapter );


            }
        } );
                back.setOnClickListener ( new View.OnClickListener () {
                    @Override
                    public void onClick(View v) {

                        Intent intent=new Intent (ViewDiary.this,Diary_Menu.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
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


}