// Tevin Hamilton
// JAV2 - 2003
// File - EmployeeDisplayFragment

package com.example.hamiltontevin_ce02.employeeFragment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.ListFragment;

import com.example.hamiltontevin_ce02.DetailEmployeeActivity;
import com.example.hamiltontevin_ce02.modal.Employee;
import com.example.hamiltontevin_ce02.R;
import com.example.hamiltontevin_ce02.employeeAdapter.EmployeeCustomAdapter;

public class EmployeeListFragment extends ListFragment {

    private  Context mContext;
    private static Cursor mCursor;

    private static final String PASS_EMPLOYEE_OBJECT = "PASS_EMPLOYEE_OBJECT";

    public EmployeeListFragment() {
    }

    public static EmployeeListFragment newInstance( Cursor cursor) {
        Bundle args = new Bundle();
        mCursor = cursor;
        EmployeeListFragment fragment = new EmployeeListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_employee_list,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() != null){
            mContext = getActivity();
        }
        if(mCursor.getCount() > 0){
            setListAdapter(new EmployeeCustomAdapter(getContext(),android.R.layout.simple_list_item_2,mCursor));
        }
    }

    @Override
    public void onListItemClick(@NonNull ListView l, @NonNull View v, int position, long id) {
        mCursor.moveToPosition(position);
        Log.i("day2", ""+mCursor.getCount());
        if(mCursor != null){
            int mainId  = mCursor.getInt(0);
            String fname = mCursor.getString(1);
            String lname = mCursor.getString(2);
            int eId = mCursor.getInt(3);
            String hireDate = mCursor.getString(4);
            String status = mCursor.getString(5);
            Employee newEmployee = new Employee(fname,lname,eId,hireDate,status);
            newEmployee.setmId(mainId);

            Intent detailIntent = new Intent(mContext,DetailEmployeeActivity.class);
            detailIntent.putExtra(PASS_EMPLOYEE_OBJECT,newEmployee);
            startActivity(detailIntent);
        }

    }
}
