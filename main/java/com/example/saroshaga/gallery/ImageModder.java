package com.example.saroshaga.gallery;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;

public class ImageModder extends AppCompatActivity {


    private ImageView iv;

    private File f;
    private String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_modder);
        String name;
        TextView t=(TextView)findViewById(R.id.textView);
        Bundle bundle=getIntent().getExtras();
        path=bundle.getString("path");

        f=new File(path);
        name=path.substring(path.lastIndexOf('/')+1);
        t.setText(name);
        iv=(ImageView)findViewById(R.id.imageView);
        iv.setImageBitmap(BitmapFactory.decodeFile(path));




    }


    public void back(View v)
    {
        finish();
    }

    public void rename(View v) throws FileNotFoundException
    {
        EditText t=(EditText)findViewById(R.id.Rename_text);
        String name=t.getText().toString();
        int sub_path=path.lastIndexOf('/');
        String new_name=path.substring(0,sub_path);
        new_name=new_name+"/"+name;
        File f1=new File(new_name);

        f.renameTo(f1);
        delete(v);


        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(f1)));

        Toast.makeText(getApplicationContext(),"File renamed successfully, new path: "+new_name,Toast.LENGTH_LONG).show();



    }

    public void delete(View v)
    {
        getApplicationContext().getContentResolver().delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,MediaStore.Images.Media.DATA+"=?",new String[]{f.getAbsolutePath()});

    }
}