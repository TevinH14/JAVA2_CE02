// Tevin Hamilton
// JAV2 - 2003
// File - EmployeeSettingPreferenceFragment

package com.example.hamiltontevin_ce02.employeeFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.example.hamiltontevin_ce02.AddEmployeeActivity;
import com.example.hamiltontevin_ce02.R;
import com.example.hamiltontevin_ce02.employeeDatabase.DataBaseHelper;


public class EmployeeSettingPreferenceFragment extends PreferenceFragmentCompat {

    private Activity mActivity;

    public EmployeeSettingPreferenceFragment() {
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.perferences,rootKey);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = getActivity();
        Preference prefList = getPreferenceManager().findPreference(getString(R.string.dateFormatKey));
        Preference prefDelete = getPreferenceManager().findPreference(getString(R.string.deleteEmployeePreference));
        if(prefList != null && prefDelete != null) {
            prefDelete.setOnPreferenceClickListener(deleteEmployeesListener);
            prefList.setOnPreferenceClickListener(setDateFormat);
        }
    }

    private final Preference.OnPreferenceClickListener deleteEmployeesListener = new Preference.OnPreferenceClickListener() {
       @Override
       public boolean onPreferenceClick(Preference preference) {
           AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
           builder.setTitle(R.string.delete_all);
           builder.setMessage(R.string.delete_message)
                   .setPositiveButton(R.string.delete_all, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           DataBaseHelper.deleteAllEmployee();
                           Intent returnIntent = new Intent();
                           returnIntent.putExtra(AddEmployeeActivity.Return_STATUS_STRING,"delete_all");
                           mActivity.setResult(Activity.RESULT_OK, null);
                           mActivity.finish();
                       }
                   })
                   .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                       public void onClick(DialogInterface dialog, int id) {
                           dialog.cancel();
                       }
                   });
           builder.show();
       return false;
       }
   };


    private final Preference.OnPreferenceClickListener setDateFormat = new Preference.OnPreferenceClickListener() {
        @Override
        public boolean onPreferenceClick(Preference preference) {
            return false;
        }
    };
}
