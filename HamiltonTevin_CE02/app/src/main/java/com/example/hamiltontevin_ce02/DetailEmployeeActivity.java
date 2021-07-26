// Tevin Hamilton
// JAV2 - 2003
// File - DetailEmployeeActivity

package com.example.hamiltontevin_ce02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.hamiltontevin_ce02.employeeFragment.DetailEmployeeFragmment;
import com.example.hamiltontevin_ce02.modal.Employee;
import com.example.hamiltontevin_ce02.employeeDatabase.DataBaseHelper;

public class DetailEmployeeActivity extends AppCompatActivity {

    public static final String EXTRA_SEND_BOOL = "EXTRA_SEND_BOOL";
    public static final String PASS_EMPLOYEE_OBJECT = "PASS_EMPLOYEE_OBJECT";
    private Employee mEmployee;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_employee);
        if(getIntent() != null){
            Intent getIntentData = getIntent();
            mEmployee = (Employee) getIntentData.getSerializableExtra(PASS_EMPLOYEE_OBJECT);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_detailContainer, DetailEmployeeFragmment.newInstance(mEmployee)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_view_employee_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    //check if employee is being updated or deleted
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.main_menu_Edit){
           Intent updateIntent = new Intent(this,AddEmployeeActivity.class);
           updateIntent.putExtra(EXTRA_SEND_BOOL,true);
           updateIntent.putExtra(PASS_EMPLOYEE_OBJECT,mEmployee);
           startActivity(updateIntent);
        }
        else{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(R.string.delete_employee);
            builder.setMessage(R.string.delete_Employee_message)
                    .setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            DataBaseHelper.deleteEmployee(mEmployee.getmEmployyeID());
                            Intent returnIntent = new Intent();
                            returnIntent.putExtra(AddEmployeeActivity.Return_STATUS_STRING,"delete_all");
                            setResult(Activity.RESULT_OK, null);
                            finish();
                        }
                    })
                    .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            builder.show();
        }
        return super.onOptionsItemSelected(item);
    }
}
