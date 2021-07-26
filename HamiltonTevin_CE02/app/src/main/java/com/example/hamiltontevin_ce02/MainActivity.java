// Tevin Hamilton
// JAV2 - 2003
// File - MainActivity
package com.example.hamiltontevin_ce02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.hamiltontevin_ce02.employeeFragment.EmployeeListFragment;
import com.example.hamiltontevin_ce02.employeeDatabase.DataBaseHelper;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    public static final String PREFS = "com.example.hamiltontevin_ce02_preferences";

    private Spinner sortBySpinner = null;
    private Spinner displayOrderSpinner = null;
    private Cursor mCursor;
    private String orderByString;
    private int sortByNum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadSpinner();
        DataBaseHelper helper =  DataBaseHelper.getInstance(this);

        if(helper != null){
            mCursor = DataBaseHelper.getAllData(0,"ASC");
        }
        //add fragment to view database data
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_EmployeeDisplayFragment, EmployeeListFragment.newInstance(mCursor)).commit();

        sortBySpinner.setOnItemSelectedListener(this);
        displayOrderSpinner.setOnItemSelectedListener(this);
    }

    //inflate menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main_menu,menu);
        return true;
    }

    //load up to date data
    @Override
    protected void onResume() {
        super.onResume();
        if(orderByString != null) {

            mCursor = DataBaseHelper.getAllData(sortByNum, orderByString);
            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_EmployeeDisplayFragment, EmployeeListFragment.newInstance(mCursor)).commit();
        }
    }

    //let user add employee or go to setting
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_menu_setting){
            Intent settingIntent = new Intent(this,SettingActivity.class);
            startActivityForResult(settingIntent, 0);
        }
        else{
            Intent addEmployeeIntent = new Intent(this, AddEmployeeActivity.class);
            startActivity(addEmployeeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    //add data to spinner
    private void loadSpinner(){
        sortBySpinner = findViewById(R.id.spinner_SortBy);
        displayOrderSpinner = findViewById(R.id.spinner_displayOrder);

        String[] sortArray =  getResources().getStringArray(R.array.sortByArray);
        String[] orderArray =  getResources().getStringArray(R.array.orderByArray);


        ArrayAdapter<String> sortAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,sortArray);
        sortAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sortBySpinner.setAdapter(sortAdapter);

        ArrayAdapter<String> orderAdapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,orderArray);
        orderAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        displayOrderSpinner.setAdapter(orderAdapter);
    }




    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        if(id == R.id.spinner_displayOrder){
            sortByNum = sortBySpinner.getSelectedItemPosition();
            if(position == 0){
                orderByString = "ASC";
            }
            else{
                orderByString = "DESC";
            }
        }else{
            int orderNum = displayOrderSpinner.getSelectedItemPosition();
            sortByNum = position;
            if(orderNum == 0){
                orderByString = "ASC";
            }
            else{
                orderByString = "DESC";
            }
        }
        mCursor = DataBaseHelper.getAllData(sortByNum,orderByString);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout_EmployeeDisplayFragment, EmployeeListFragment.newInstance(mCursor)).commit();

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
