package FragmentClasses;

import android.app.ListFragment;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.dhruv.umesh.moneymanager.R;

/**
 * Created by umesh on 18/11/16.
 */

public class viewfriend extends ListFragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.viewfriend, container, false);

        FriendDbHelper mDbHelper = new FriendDbHelper(getActivity());

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + Friend.FriendEntry.TABLE_NAME, null);

        String[] columns = new String[]{
                Friend.FriendEntry._ID,
                Friend.FriendEntry.FRIEND_NAME,
                Friend.FriendEntry.FRIEND_AMOUNT
        };
        int[] widgets = new int[]{
                R.id.friendID,
                R.id.friendName,
                R.id.friendAmount,
        };

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(getActivity(), R.layout.friend_info, cursor,
                columns, widgets, 0);

//        prepareListData();
        setListAdapter(cursorAdapter);
//        setRetainInstance(true);
        return rootView;
    }

    public void onListItemClick(ListView l, View view, int position, long id) {
//        ViewGroup viewg=(ViewGroup)view;
//        TextView tv=(TextView)viewg.findViewById(R.id.txtitem);
//        Toast.makeText(getActivity(), tv.getText().toString(),Toast.LENGTH_LONG).show();

    }

}
