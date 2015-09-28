package andres_sjsu.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import andres_sjsu.imagesearch.R;
import andres_sjsu.imagesearch.models.ImageResult;

/**
 * Created by andres on 9/27/15.
 */
public class ImageDisplayActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);

        String url = getIntent().getStringExtra("url");
        ImageResult result = (ImageResult) getIntent().getSerializableExtra("result");
        ImageView ivImageResult =(ImageView)findViewById(R.id.ivImageResult);

        Picasso.with(this).load(result.fullUrl).into(ivImageResult);
        //Log.i("INFO", url);
    }

    public void Share(View v) {

        // Toast.makeText(this, "Searh for:" + query, Toast.LENGTH_SHORT).show();

        // Get access to bitmap image from view
        ImageView ivImage = (ImageView)findViewById(R.id.ivImageResult);
        // Get access to the URI for the bitmap
        Uri bmpUri = getLocalBitmapUri(ivImage);



        if (bmpUri != null) {
            // Construct a ShareIntent with link to image
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_STREAM, bmpUri);
            shareIntent.setType("image/*");
            // Launch sharing dialog for image
            startActivity(Intent.createChooser(shareIntent, "Share Image"));


        } else {
            // ...sharing failed, handle error
            //  Toast.makeText(getApplicationContext(), "nulllll", Toast.LENGTH_SHORT).show();
        }

    }
    // Returns the URI path to the Bitmap displayed in specified ImageView
    public Uri getLocalBitmapUri(ImageView imageView) {
        // Extract Bitmap from ImageView drawable
        Drawable drawable = imageView.getDrawable();
        Bitmap bmp = null;
        if (drawable instanceof BitmapDrawable){
            bmp = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
        } else {
            return null;
        }
        // Store image to default external storage directory
        Uri bmpUri = null;
        try {
            File file =  new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOWNLOADS), "share_image_" + System.currentTimeMillis() + ".png");
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.close();
            bmpUri = Uri.fromFile(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmpUri;
    }



}
