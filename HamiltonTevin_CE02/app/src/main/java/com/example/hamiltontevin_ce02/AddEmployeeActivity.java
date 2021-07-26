// Tevin Hamilton
// JAV2 - 2003
// File - AddEmployeeActivity
package com.example.hamiltontevin_ce02;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hamiltontevin_ce02.employeeFragment.AddEmployeeFragment;
import com.example.hamiltontevin_ce02.modal.Employee;
import com.example.hamiltontevin_ce02.employeeDatabase.DataBaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class AddEmployeeActivity extends AppCompatActivity {

    private boolean mUpdateDataStatus;
    private Employee mEmployee;
    public static final String Return_STATUS_STRING = "PASS_STATUS_STRING";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_employee);
        if(getIntent() != null){
            Intent startingIntent = getIntent();
            mUpdateDataStatus = startingIntent.getBooleanExtra(DetailEmployeeActivity.EXTRA_SEND_BOOL,false);
            mEmployee = (Employee) startingIntent.getSerializableExtra(DetailEmployeeActivity.PASS_EMPLOYEE_OBJECT);
        }
        getSupportFragmentManager().beginTransaction().add(R.id.frameLayout_addEmployee, AddEmployeeFragment.newInstance(mUpdateDataStatus,mEmployee)).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_add_employee_menu,menu);
        return true;
    }

    //TODO: insert to the is method should work lol.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        EditText et_FName = findViewById(R.id.et_FirstName);
        EditText et_LName = findViewById(R.id.et_LastName);
        EditText et_id = findViewById(R.id.et_IdNum);
        TextView tv_hireDate = findViewById(R.id.tv_date);
        EditText et_status = findViewById(R.id.et_status);

        String fName = et_FName.getText().toString();
        String lName = et_LName.getText().toString();
        String status = et_status.getText().toString();
        String stringId = et_id.getText().toString();
        String hireDate = tv_hireDate.toString();

        int idNum =0;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
        Date date = new Date();
        try{
            idNum = Integer.parseInt(stringId);
            date = dateFormat.parse(hireDate);

        }catch (NullPointerException | ParseException e){
            e.printStackTrace();
        }

        Intent returnIntent = new Intent();
        if(mUpdateDataStatus){
            mEmployee.setmFirstName(fName);
            mEmployee.setmLAstName(lName);
            mEmployee.setmEmploymentStatus(status);
            mEmployee.setmEmployyeID(idNum);
            mEmployee.setmHireDate(String.valueOf(date));

            String whereId = Integer.toString(mEmployee.getmId());
            DataBaseHelper.updateEmployee(mEmployee,whereId);
            startActivity(new Intent(AddEmployeeActivity.this, MainActivity.class));
        }else {
            mEmployee = new Employee(fName,lName,idNum,String.valueOf(date),status);
            DataBaseHelper.insertEmployee(mEmployee);
            returnIntent.putExtra(Return_STATUS_STRING,"added");
        }
        setResult(RESULT_OK, null);
        finish();

        return super.onOptionsItemSelected(item);
    }

}
