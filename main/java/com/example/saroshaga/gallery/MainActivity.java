package com.example.saroshaga.gallery;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;


public class MainActivity extends AppCompatActivity {
    private GridView g;
    private Cursor c;
    private int path_column;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File f=Environment.getExternalStorageDirectory();



    }

    @Override
    protected void onResume() {
        super.onResume();
        String[] projection={MediaStore.Images.Media.DATA};
        c=getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection,null,null,null);
        path_column=c.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

        g=(GridView)findViewById(R.id.img_view);
        g.setAdapter(new ImageAdapter(this));

        g.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                c.moveToPosition(position);

                String imagepath=c.getString(path_column);

                Intent i=new Intent(MainActivity.this,ImageModder.class);
                Bundle data=new Bundle();

                data.putString("path",imagepath);
                i.putExtras(data);

                startActivity(i);


            }
        });


    }

    private class ImageAdapter extends BaseAdapter
    {

        private Context context;


        public ImageAdapter(Context Localcontext)
        {
            context=Localcontext;
        }
        @Override
        public int getCount() {
            return c.getCount();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView myImage;

            if(convertView==null)
            {
                myImage=new ImageView(context);
            }
            else
            {
                myImage=(ImageView)convertView;
            }

            c.moveToPosition(position);
            String image_path=c.getString(path_column);
            Bitmap thumbnail= ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(image_path),100,100);
            myImage.setImageBitmap(thumbnail);
            myImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            myImage.setPadding(8,8,8,8);
            myImage.setLayoutParams(new GridView.LayoutParams(250,250));

            return myImage;
        }
    }





}
