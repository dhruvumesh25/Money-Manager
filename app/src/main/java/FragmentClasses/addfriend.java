package FragmentClasses;

import android.app.Fragment;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.dhruv.umesh.moneymanager.R;

/**
 * Created by umesh on 18/11/16.
 */

public class addfriend extends Fragment {
    private String fname;
    private int froll;
    private int froom;
    private Button save;
    private EditText text_name;
    private EditText text_roll;
    private EditText text_room;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.addfriend, container, false);
        save = (Button) view.findViewById(R.id.saveFriend);
        save.setOnClickListener(new btnListener());

        text_name = (EditText) view.findViewById(R.id.name);
        text_roll = (EditText) view.findViewById(R.id.roll);
        text_room = (EditText) view.findViewById(R.id.room);

        return view;
    }

    public class btnListener implements View.OnClickListener{
        @Override
        public void onClick(View v){
            try{
                fname = text_name.getText().toString();
            }catch (NullPointerException e){
                Toast.makeText(getActivity(),"NullPointerException", Toast.LENGTH_SHORT).show();
            }
            try{
                froll = Integer.valueOf(text_roll.getText().toString());
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(),"Roll number must be Integer", Toast.LENGTH_SHORT).show();
            }
            try{
                froom = Integer.valueOf(text_room.getText().toString());
            }catch (NumberFormatException e){
                Toast.makeText(getActivity(),"Room number must be Integer", Toast.LENGTH_SHORT).show();
            }

            FriendDbHelper mDbHelper = new FriendDbHelper(getActivity());

            SQLiteDatabase db = mDbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(Friend.FriendEntry.FRIEND_NAME, fname);
            values.put(Friend.FriendEntry.FRIEND_AMOUNT, 0);
//            values.put(Friend.FriendEntry.ROOM_NUMBER, froom);
//            Toast.makeText(getActivity(),name+roll+room, Toast.LENGTH_SHORT).show();

            db.insert(Friend.FriendEntry.TABLE_NAME, null, values);
        }
    }

}
