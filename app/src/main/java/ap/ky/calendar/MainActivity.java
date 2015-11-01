package ap.ky.calendar;

import android.app.Activity;
//import android.support.v7.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
String TAG = "calendar";
    customAdapter customAdapter;
    public static Bitmap[] bitmaps = new Bitmap[31];
    private static ContentResolver contentResolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customAdapter = new customAdapter(this);
        ListView listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
        for(int i = 0;i<bitmaps.length;i++){
            bitmaps[i] = null;
        }
        contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("eventText", "test");
        contentResolver.insert(myContentProvider.CONTENT_URI,contentValues);

        String projection[]={"eventText"};
        Cursor c = contentResolver.query(myContentProvider.CONTENT_URI,projection,null,null,null);
        Log.e(TAG,"count " + c.getCount() );
        c.moveToFirst();
        Log.e(TAG,"value " + c.getString(0));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e(TAG, "onActivityResult " + ap.ky.calendar.customAdapter.clickpostition);
        //Bundle bundle = data.getExtras();
        //Bitmap bitmap = (Bitmap) bundle.get("data");
        //bitmaps[ap.ky.calendar.customAdapter.clickpostition] = bitmap;

        //customAdapter.setBitmaps(bitmaps);
        //customAdapter.notifyDataSetChanged();

    }
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
