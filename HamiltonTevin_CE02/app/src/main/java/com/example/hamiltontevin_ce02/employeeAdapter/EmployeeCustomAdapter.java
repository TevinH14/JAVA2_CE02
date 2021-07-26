// Tevin Hamilton
// JAV2 - 2003
// File - EmployeeCustomAdapter

package com.example.hamiltontevin_ce02.employeeAdapter;
import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.TextView;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import com.example.hamiltontevin_ce02.employeeDatabase.DataBaseHelper;

public class EmployeeCustomAdapter extends ResourceCursorAdapter {

    public EmployeeCustomAdapter(Context context, int layout, Cursor c) {
        super(context, layout, c, 0);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tv = view.findViewById(android.R.id.text1);
        String result = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_FNAME));
        tv.setText(result);

        tv = view.findViewById(android.R.id.text2);
        result = cursor.getString(cursor.getColumnIndex(DataBaseHelper.COLUMN_EMPLOYEE_ID));
        tv.setText(result);
    }
}
