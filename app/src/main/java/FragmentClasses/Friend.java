package FragmentClasses;

import android.provider.BaseColumns;

/**
 * Created by umesh on 18/11/16.
 */

public final class Friend {
    private Friend(){}

    public static class FriendEntry implements BaseColumns {
        public static final String TABLE_NAME = "friend";
        public static final String FRIEND_NAME = "name";
        public static final String FRIEND_AMOUNT = "amount";
    }
}


