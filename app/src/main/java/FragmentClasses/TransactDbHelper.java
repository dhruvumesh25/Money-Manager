package FragmentClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by umesh on 23/11/16.
 */

public class TransactDbHelper extends SQLiteOpenHelper {
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + Transaction.TransactionEntry.TABLE_NAME + " (" +
                    Transaction.TransactionEntry._ID + " INTEGER PRIMARY KEY," +
                    Transaction.TransactionEntry.FRIEND_ID + INTEGER_TYPE + COMMA_SEP +
                    Transaction.TransactionEntry.FRIEND_AMOUNT + INTEGER_TYPE + COMMA_SEP +
                    Transaction.TransactionEntry.DATE_OF_TRANSACTION + TEXT_TYPE + COMMA_SEP +
                    Transaction.TransactionEntry.DESCRIPTION + TEXT_TYPE +
                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Transaction.TransactionEntry.TABLE_NAME;

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Transaction.db";

    public TransactDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
