// Tevin Hamilton
// JAV2 - 2003
// File - DetailEmployeeFragment

package com.example.hamiltontevin_ce02.employeeFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.hamiltontevin_ce02.MainActivity;
import com.example.hamiltontevin_ce02.modal.Employee;
import com.example.hamiltontevin_ce02.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailEmployeeFragmment extends Fragment {

    private static final String PASS_EMPLOYEE_OBJECT = "PASS_EMPLOYEE_OBJECT";

    public DetailEmployeeFragmment() {
    }

    public static DetailEmployeeFragmment newInstance(Employee employee) {

        Bundle args = new Bundle();
        args.putSerializable(PASS_EMPLOYEE_OBJECT,employee);

        DetailEmployeeFragmment fragment = new DetailEmployeeFragmment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_employee_detail,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Context mContext = getActivity();

        if(getView() != null){

            TextView tv_fName = getView().findViewById(R.id.tv_detail_firstname);
            TextView tv_lName = getView().findViewById(R.id.tv_detail_lastname);
            TextView tv_date = getView().findViewById(R.id.tv_detail_hireDate);
            TextView tv_status = getView().findViewById(R.id.tv_detail_status);
            TextView tv_id= getView().findViewById(R.id.tv_detail_idNum);

            if(getArguments() != null){
                Employee  de = (Employee)getArguments().getSerializable(PASS_EMPLOYEE_OBJECT);

                if(de != null){
                    tv_lName.setText(de.getmLAstName());
                    tv_fName.setText(de.getmFirstName());
                    tv_status.setText(de.getmEmploymentStatus());
                }
                String idString = "";
                try {
                    if(de != null) {
                        idString = Integer.toString(de.getmEmployyeID());
                    }
                }catch (NullPointerException e){
                    e.printStackTrace();
                }
                tv_id.setText(idString);
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy",Locale.US);
                Date dateValue = new Date();

                assert mContext != null;
                SharedPreferences formatSetting = mContext.getSharedPreferences(MainActivity.PREFS, Context.MODE_PRIVATE);
                String value = formatSetting.getString(getString(R.string.dateFormatKey),"");
                try{
                    if(de != null){
                        dateValue = dateFormat.parse(de.getmHireDate());
                    }
                }catch (ParseException e){
                    e.printStackTrace();
                }
                SimpleDateFormat sdf = new SimpleDateFormat(value, Locale.US);
                String formatDate = "";
                if(dateValue != null){
                    formatDate = sdf.format(dateValue);
                }
                tv_date.setText(formatDate);
            }
        }
    }
}
