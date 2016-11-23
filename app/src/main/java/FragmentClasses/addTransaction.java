package FragmentClasses;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.dhruv.umesh.moneymanager.R;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

import static android.os.Build.ID;

/**
 * Created by umesh on 23/11/16.
 */

public class addTransaction extends Fragment {
    private Long friendId;
    private Long amount;
    private String dateString;
    private String description;
    private String[] friendsArr;
    private HashMap<String,Long> hashMap;

    private EditText amountText;
    private EditText descriptionText;
    private EditText dateText;
    private Spinner spinner;
    private Button saveButton;
    private Button dateButton;

    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener date;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.addtransaction, container, false);

        getAllFriends();
        amountText = (EditText) view.findViewById(R.id.amount);
        descriptionText = (EditText) view.findViewById(R.id.description);
        dateText = (EditText) view.findViewById(R.id.dateText);
        spinner = (Spinner) view.findViewById(R.id.friends);
        saveButton = (Button) view.findViewById(R.id.saveButton);
        dateButton = (Button) view.findViewById(R.id.dateButton);
        friendId = new Long(0);

        saveButton.setOnClickListener(new btnListener());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_spinner_item, friendsArr);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String fName = parent.getItemAtPosition(position).toString();
                Long fId = hashMap.get(fName);
                if(fId == null)fId = new Long(0);
                friendId = fId;
                Toast.makeText(getActivity(),parent.getItemAtPosition(position).toString()+fId, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        dateButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(getActivity(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        return view;
    }

    public void updateLabel () {
        String myFormat = "yyyy/MM/dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateText.setText(sdf.format(myCalendar.getTime()));
    }

    public class btnListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            boolean b = (friendId != 0);
            try {
                amount = Long.parseLong(amountText.getText().toString());
            } catch (NumberFormatException e) {
                b = false;
                Toast.makeText(getActivity(), "NumberFomatException", Toast.LENGTH_SHORT).show();
            }
            try {
                dateString = dateText.getText().toString();
            } catch (NullPointerException e) {
                b = false;
                Toast.makeText(getActivity(), "Date NullPointerException", Toast.LENGTH_SHORT).show();
            }
            try {
                description = descriptionText.getText().toString();
            } catch (NullPointerException e) {
                b = false;
                Toast.makeText(getActivity(), "Description NullPointerException", Toast.LENGTH_SHORT).show();
            }

            if (b) {
                FriendDbHelper mDbHelper = new FriendDbHelper(getActivity());
                SQLiteDatabase db = mDbHelper.getReadableDatabase();

                String quer = "SELECT amount FROM " + Friend.FriendEntry.TABLE_NAME + " where _id = '" + friendId +"'";
                Cursor cursor = db.rawQuery(quer,null);
                cursor.moveToFirst();

                Long prevAmount = cursor.getLong(cursor.getColumnIndex("amount"));
                Long newAmount = prevAmount + amount;

                ContentValues data = new ContentValues();
                data.put("amount", newAmount);
                db.update(Friend.FriendEntry.TABLE_NAME, data, "_id=" + friendId, null);
            }
        }
    }

    public void getAllFriends(){
        hashMap = new HashMap<>();
        FriendDbHelper mDbHelper = new FriendDbHelper(getActivity());
        SQLiteDatabase myDB = mDbHelper.getReadableDatabase();

        Cursor crs = myDB.rawQuery("SELECT * FROM " + Friend.FriendEntry.TABLE_NAME, null);

        friendsArr = new String[crs.getCount()+1];
        friendsArr[0] = "Select Friends From List";
        int i = 1;
        while(crs.moveToNext()){
            String uname = crs.getString(crs.getColumnIndex("name"));
            Long id = crs.getLong(crs.getColumnIndex("_id"));
            hashMap.put(uname,id);
            friendsArr[i] = uname;
            i++;
//            System.out.println(uname);
        }
    }
}
