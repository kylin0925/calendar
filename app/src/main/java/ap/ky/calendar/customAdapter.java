package ap.ky.calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kylin25 on 2015/10/18.
 */
public class customAdapter extends BaseAdapter{
    LayoutInflater layoutInflater;
    //Bitmap[] bitmap = new Bitmap[31];
    Bitmap bittmp ;
    String TAG ="customAdapter";
    Context context;
    public static int clickpostition = -1;
    public customAdapter(Context context){
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }
    public void setBitmaps(Bitmap[] bitmap){
        Log.e(TAG,"setBitmaps");
     //   this.bitmap = bitmap;
    }
    public void setBitmaps(ArrayList<Bitmap> bitmap){
       // for(int i = 0;i<bitmap.size();i++)
       //     this.bitmap[i] = (Bitmap)bitmap.get(i);
    }
    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        viewTag viewTag;
        if(convertView == null){

            convertView = layoutInflater.inflate(R.layout.row,null);
            TextView txt = (TextView)convertView.findViewById(R.id.txt);
            ImageView img = (ImageView)convertView.findViewById(R.id.imageView);
            Button button = (Button) convertView.findViewById(R.id.button);
            viewTag = new viewTag(txt,img,button);
            convertView.setTag(viewTag);
        }else{
            viewTag = (viewTag)convertView.getTag();
        }

        viewTag.button.setTag(position);
        viewTag.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = (int) v.getTag();
                Log.e(TAG, "position " + position + " " + i);

                clickpostition = position;
                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String strfile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
                            getAbsolutePath()+ "/pict"+position +".jpg";
                File imgfile = new File(strfile);
                Uri uriImg = Uri.fromFile(imgfile);
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                intent.putExtra(MediaStore.EXTRA_OUTPUT,uriImg);
                ((Activity) context).startActivityForResult(intent, 0);
            }
        });
        //Log.e(TAG,"bitmap " + bitmap[position]);
        //img.setImageBitmap(bitmap[position]);
        String strfile = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).
                getAbsolutePath()+ "/pict"+position +".jpg";
        File file = new File(strfile);
        if(file.exists()) {
            Log.e(TAG,"set img file " + strfile);
            bittmp = BitmapFactory.decodeFile(strfile);
            viewTag.img.setImageBitmap(Bitmap.createScaledBitmap(bittmp,160,120,false));
        }

        return convertView;
    }
    class viewTag{
        ImageView img;
        TextView txt;
        Button button;
        public viewTag(TextView txt,ImageView img,Button button){
            this.txt = txt;
            this.img = img;
            this.button = button;

        }
    }
}
