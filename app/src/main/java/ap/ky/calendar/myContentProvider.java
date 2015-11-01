package ap.ky.calendar;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

/**
 * Created by kylin25 on 2015/11/1.
 */
public class myContentProvider extends ContentProvider {
    private static final String AUTHORITY ="ap.ky.calendar.myContentProvider";
    private static final String DBFILE="calendar.db";
    private static final String DBTABLE="calendar";

    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + DBTABLE);
    private static final UriMatcher urimatcher = new UriMatcher(0);//uri root
    static {
        urimatcher.addURI(AUTHORITY,DBTABLE,1); //db friends
    }
    private SQLiteDatabase db;
    SQLHelper sqlHelper;
    @Override
    public boolean onCreate() {
        sqlHelper = new SQLHelper(getContext());
        db = sqlHelper.getWritableDatabase();
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = db.query(true,DBTABLE,projection,selection,null,null,null,null,null);
        c.setNotificationUri(getContext().getContentResolver(),uri);
        return c;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        long rowid = db.insert(DBTABLE, null, values);
        Uri uri1 = ContentUris.withAppendedId(CONTENT_URI,rowid);
        getContext().getContentResolver().notifyChange(uri1,null);
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private class SQLHelper extends SQLiteOpenHelper{

        public SQLHelper(Context context){
            super(context,DBFILE,null,1);

        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + DBTABLE + "(_ID INTEGER PRIMARY KEY,eventText TEXT)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
