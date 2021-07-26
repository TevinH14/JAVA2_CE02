// Tevin Hamilton
// JAV2 - 2003
// File - AddEmployeeFragment

package com.example.hamiltontevin_ce02.employeeFragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce02.MainActivity;
import com.example.hamiltontevin_ce02.modal.Employee;
import com.example.hamiltontevin_ce02.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class AddEmployeeFragment extends Fragment {
    private static final String ARGS_UPDATE_KEY = "UPDATE_DATA_STATUS";
    private static final String ARGS_UPDATE_EMPLOYEE  = "ARGS_DATE_FORMAT";
    private Context mContext;
    private Button btn_date;

    public static AddEmployeeFragment newInstance(boolean status,Employee employee) {
        Bundle args = new Bundle();
        args.putSerializable(ARGS_UPDATE_EMPLOYEE,employee);
        args.putBoolean(ARGS_UPDATE_KEY, status);
        AddEmployeeFragment fragment = new AddEmployeeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_employee,container,false);
    }

    //create date picker
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mContext = getActivity();
        if(getView() != null) {
            btn_date = getView().findViewById(R.id.btn_selectDate);
            btn_date.setOnClickListener(mBtnDateDialog);
        }
        if(getArguments() != null) {
            boolean updateStatus = getArguments().getBoolean(ARGS_UPDATE_KEY);
            if (updateStatus) {
                loadEmployeeData();
            }
        }


    }
    private final View.OnClickListener mBtnDateDialog = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == btn_date) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,new DatePickerDialog.OnDateSetListener(){
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        Date date = new GregorianCalendar(year, month, dayOfMonth).getTime();
                        SimpleDateFormat sdf ;

                        SharedPreferences formatSetting = mContext.getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);
                        String value = formatSetting.getString(getString(R.string.dateFormatKey),"MM/dd/yyyy");

                        sdf = new SimpleDateFormat(value, Locale.US);
                        String formatDate = sdf.format(date);
                        if(getView() != null) {

                            TextView tvDate = getView().findViewById(R.id.tv_date);
                            tvDate.setText(formatDate);
                        }
                        Log.i("day2",formatDate);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        }
    };

    //load data into input fields to be updated
    private void loadEmployeeData(){
        if(getView() != null){

            EditText et_FName = getView().findViewById(R.id.et_FirstName);
            EditText et_LName = getView().findViewById(R.id.et_LastName);
            EditText et_id = getView().findViewById(R.id.et_IdNum);
            TextView tv_hireDate = getView().findViewById(R.id.tv_date);
            EditText et_status = getView().findViewById(R.id.et_status);
            if(getArguments()!= null) {
                Employee ue = (Employee) getArguments().getSerializable(ARGS_UPDATE_EMPLOYEE);
                if(ue != null) {
                    et_FName.setText(ue.getmFirstName());
                    et_LName.setText(ue.getmLAstName());
                    et_status.setText(ue.getmEmploymentStatus());

                    String idString = "";
                    try {
                        idString = Integer.toString(ue.getmEmployyeID());
                    }catch (NullPointerException e){
                        e.printStackTrace();
                    }
                    et_id.setText(idString);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
                    Date dateValue = new Date();

                    SharedPreferences formatSetting = mContext.getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);
                    String value = formatSetting.getString(getString(R.string.dateFormatKey),"MM/dd/yyyy");
                    try{
                        dateValue = dateFormat.parse(ue.getmHireDate());
                    }catch (ParseException e){
                        e.printStackTrace();
                    }
                    SimpleDateFormat sdf = new SimpleDateFormat(value, Locale.US);
                    String formatDate = "";
                    if(dateValue != null){
                        formatDate = sdf.format(dateValue);
                    }
                    tv_hireDate.setText(formatDate);
                }
            }
        }
    }

}
