// Tevin Hamilton
// JAV2 - 2003
// File - DataBaseHelper
package com.example.hamiltontevin_ce02.employeeDatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.hamiltontevin_ce02.modal.Employee;

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_FILE = "employeeDatabase.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "employee";
    private static final String COLUMN_ID = "_id";
    public static final String COLUMN_FNAME = "firstName";
    private static final String COLUMN_LNAME = "lastName";
    public static final String COLUMN_EMPLOYEE_ID = "employeeId";
    private static final String COLUMN_HIRE_DATE ="hireDate";
    private static final String COLUMN_STATUS = "status";


    private static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + "(" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_FNAME + "  TEXT, " +
            COLUMN_LNAME + " TEXT, " +
            COLUMN_EMPLOYEE_ID + " INTEGER, " +
            COLUMN_HIRE_DATE + " DATETIME, " +
            COLUMN_STATUS + " TEXT)";

    private static SQLiteDatabase mDatabase;
    private static DataBaseHelper mInstance = null;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private DataBaseHelper(Context _context) {
        super(_context, DATABASE_FILE, null, DATABASE_VERSION);
        mDatabase = getWritableDatabase();
    }
    
    public static DataBaseHelper getInstance(Context _context) {
        if(mInstance == null) {
            mInstance = new DataBaseHelper(_context);
        }
        return mInstance;
    }


    public static void insertEmployee(Employee employee){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FNAME,employee.getmFirstName());
        cv.put(COLUMN_LNAME,employee.getmLAstName());
        cv.put(COLUMN_EMPLOYEE_ID,employee.getmEmployyeID());
        cv.put(COLUMN_HIRE_DATE, employee.getmHireDate());
        cv.put(COLUMN_STATUS,employee.getmEmploymentStatus());

        mDatabase.insert(TABLE_NAME, null, cv);

    }
    
    public static void updateEmployee(Employee employee, String whereId){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_FNAME,employee.getmFirstName());
        cv.put(COLUMN_LNAME,employee.getmLAstName());
        cv.put(COLUMN_EMPLOYEE_ID,employee.getmEmployyeID());
        cv.put(COLUMN_HIRE_DATE, employee.getmHireDate());
        cv.put(COLUMN_STATUS,employee.getmEmploymentStatus());


        String where = COLUMN_ID + "=?";

        String[] whereArgs = {whereId};

        mDatabase.update(TABLE_NAME, cv, where, whereArgs);
    }

    public static void deleteAllEmployee(){
        mDatabase.delete(TABLE_NAME,null,null);
    }

    public static void deleteEmployee(int employeeId){
        mDatabase.delete(TABLE_NAME,COLUMN_EMPLOYEE_ID +"= "+ employeeId,null);
    }

    public static Cursor getAllData(int columnChoice,String _order) {
        String selectedColumn ;
        if(columnChoice == 0){
            selectedColumn = COLUMN_STATUS;
        }else {
            selectedColumn = COLUMN_EMPLOYEE_ID;
        }
        return mDatabase.query(TABLE_NAME, null, null, null, null, null, selectedColumn+" "+_order);
    }
}
