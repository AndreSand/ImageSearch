package andres_sjsu.imagesearch.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import andres_sjsu.imagesearch.R;
import andres_sjsu.imagesearch.models.ImageResult;
import cz.msebera.android.httpclient.Header;

/**
 * Created by andres on 9/27/15.
 */
public class ImageResultsAdapter extends ArrayAdapter<ImageResult>{

    public ImageResultsAdapter(Context context, List<ImageResult> images){
        super(context, R.layout.item_image_result, images);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        ImageResult imageInfo = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_image_result, parent, false);
        }
        // Lookup view for data population
        ImageView ivImage  = (ImageView) convertView.findViewById(R.id.ivView);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTittle);
        // Populate the data into the template view using the data object
        ivImage.setImageResource(0);
        tvTitle.setText(imageInfo.title);

        Picasso.with(getContext()).load(imageInfo.thumbUrl).into(ivImage);

        // Return the completed view to render on screen
        return convertView;
    }



}
