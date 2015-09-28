package andres_sjsu.imagesearch.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import andres_sjsu.imagesearch.adapters.EndlessScroll;
import andres_sjsu.imagesearch.adapters.ImageResultsAdapter;
import andres_sjsu.imagesearch.models.ImageResult;
import andres_sjsu.imagesearch.R;
import andres_sjsu.imagesearch.models.Settings;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends Activity {
    private static final int REQUEST_CODE = 1;

    private EditText etQuery;
    private GridView gridView;
    private ArrayList<ImageResult> imageResults;
    private ImageResultsAdapter aImagesResults;
    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getNetworkInfo(0);
        if (netInfo != null && netInfo.isConnected()) {

            setupViews();
            //Creates data source
            imageResults= new ArrayList<ImageResult>();
            //Attaches the data source to an adapter
            aImagesResults = new ImageResultsAdapter(this,imageResults);
            gridView.setAdapter(aImagesResults);

            Toast.makeText(this, "Internet Connected" , Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Internet NOT Connected, please turn on your Internet" , Toast.LENGTH_SHORT).show();
        }
    }




    private void setupViews() {
         etQuery = (EditText) findViewById(R.id.etQuery);
         gridView = (GridView)findViewById(R.id.gridView);

             gridView.setOnItemClickListener(new OnItemClickListener() {

                 @Override
                 public void onItemClick(AdapterView<?> parent, View view,
                                         int position, long id) {
                     Intent i = new Intent(MainActivity.this, ImageDisplayActivity.class);
                     ImageResult result = imageResults.get(position);
                     i.putExtra("result", result);

                     startActivity(i);
                 }

             });


        gridView.setOnScrollListener(new EndlessScroll() {

            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMore(totalItemsCount);
            }

        });
    }



    private void loadMore(int startOffset) {
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String searchUrl = "https://ajax.googleapis.com/ajax/services/search/images?v=1.0&q=" + query +
                "&rsz=8" ;
        client.get(searchUrl, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers,
                                  JSONObject response) {
                Log.d("DEBUG", response.toString());
                try {
                    JSONArray imageResultsJson = response.getJSONObject("responseData").getJSONArray("results");
                    aImagesResults.addAll(ImageResult.fromJSONArray(imageResultsJson));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.d("INFO", "foo");
            }

        });
    }


    public void onImageSearch(View v)
    {
        aImagesResults.clear();
        etQuery.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etQuery.getWindowToken(), 0);
        loadMore(0);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //showSettingsAction
    public void showSettingsAction(MenuItem mi) {
        // handle click here

        Intent i = new Intent(MainActivity.this, SettingsActivity.class);
        i.putExtra("settings", settings);
        //i.putExtra("url", result.fullUrl);
        startActivityForResult(i, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            settings = (Settings) data.getSerializableExtra("settings");
        }}

}
