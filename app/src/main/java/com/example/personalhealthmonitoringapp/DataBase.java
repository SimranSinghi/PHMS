package com.example.personalhealthmonitoringapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Arrays;
import java.util.List;


public class DataBase extends SQLiteOpenHelper {

    private static final int DB_V=1;
    private static final String DB_Name="details.db";
    private static final String TABLE_Name="details";
    private static final String COL_ID="Id";
    private static final String COL_Name="name";
    private static final String COL_EMAIL="EmailId";
    private static final String COL_USERNAME="UserName";
    private static final String COL_PASSWORD="Password";
    private static final String COL_notes="notes";
    private static final String TABLENOTE_NAME="dietDetails";
    private static final String COLnote_ID="NID";
    private static final String COLnote_note="note";
    private static final String COLnote_FKUSERNAME="FK_dietuser";
    private static final String COL_Inch="Inch";

    private static final String COL_weight="weight";
    private static final String COL_height="height";
    private static final String COL_age="age";
    private static final String COL_gender="gender";
    private static final String COL_health="  health_conditions";


    SQLiteDatabase db;


    private static final String TABLE_CREATE = "create table "+ TABLE_Name+" ( Id integer primary key not null , name text not null , EmailId text not null ,UserName text not null , Password text not null);";
    private static final String TABLENOTE_CERATE = "create table "+TABLENOTE_NAME+" ( NID integer primary key , weight String, height String, age String, gender String,Inch String, health_conditions String, FK_dietuser String ,FOREIGN KEY(FK_dietuser) REFERENCES details(UserName));";

    DataBase(Context context){
        super(context,DB_Name,null,DB_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        db.execSQL(TABLENOTE_CERATE);
        this.db=db;
    }

    public void registerDetails(Details d){
        db= this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String query = "select * from "+TABLE_Name;
        Cursor cursor = db.rawQuery(query,null);
        int ct = cursor.getCount();

        cv.put(COL_ID,ct);
        cv.put(COL_Name, d.getName());
        cv.put(COL_EMAIL,d.getEmailId());
        cv.put(COL_USERNAME,d.getUserName());
        cv.put(COL_PASSWORD,d.getPassword());

        db.insert(TABLE_Name,null,cv);

    }

    public void insertdiet(String username, String weight, String height, String age, String gender,String Inch, String health_conditions){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String query = "select * from "+TABLENOTE_NAME;
        Cursor cursor = db.rawQuery(query,null);
        int ct = cursor.getCount();

        cv.put(COL_weight,weight);
        cv.put(COL_height,height);
        cv.put(COL_age,age);
        cv.put(COL_gender,gender);
        cv.put(COL_Inch,Inch);
        cv.put(COL_health, health_conditions);


        cv.put(COLnote_ID,ct);
        //  cv.put(COLnote_note, "welcome to the note");
        cv.put(COLnote_FKUSERNAME,username);

        db.insert(TABLENOTE_NAME, null, cv);

    }

    public String checkPassword(String username){
        db=this.getReadableDatabase();
        String query="select UserName,Password from "+TABLE_Name;
        Cursor cursor = db.rawQuery(query,null);
        String un,pass;
        pass="not found";
        if(cursor.moveToFirst()){
            do{
                un = cursor.getString(0);
                if(un.equals(username)){
                    pass= cursor.getString(1);
                    break;
                }
            }while(cursor.moveToNext());
        }
        return pass;
    }

    public List<Object> getDiet(String username){

        db=this.getReadableDatabase();
        String query ="select  FK_dietuser,weight, height, age, gender,Inch, health_conditions from "+TABLENOTE_NAME;
        Cursor cursor= db.rawQuery(query,null);

        String uname, health_condition, gender, weight, height, age, Inch;
        health_condition=null;
        gender =null;
        weight =null;
        height =null;
        Inch = null;
        age =null;

        //  note ="start";
        if(cursor.moveToFirst()){
            do {
                uname = cursor.getString(0);
                if (uname.equals(username)) {
                    weight = cursor.getString(1);
                    height = cursor.getString(2);
                    age = cursor.getString(3);
                    gender = cursor.getString(4);
                    Inch = cursor.getString(5);
                    health_condition = cursor.getString(6);
                    break;
                }


            }while(cursor.moveToNext());
        }

        //return weight, height, age, gender, health_condition;
        return Arrays.asList(weight, height, age, gender,Inch, health_condition);
    }


    /* public void addNotes(String d,String username){

         String query = "update details set notes = '"+d+"' where UserName = '"+username+"';";
         db.execSQL(query);

     }
 */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query="DROP TABLE IF EXISTS "+TABLE_Name;
        db.execSQL(query);
        this.onCreate(db);
    }
}
