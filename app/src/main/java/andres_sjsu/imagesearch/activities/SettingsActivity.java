package andres_sjsu.imagesearch.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import andres_sjsu.imagesearch.R;
import andres_sjsu.imagesearch.models.Settings;

/**
 * Created by andres on 9/27/15.
 */
public class SettingsActivity  extends  Activity implements OnItemSelectedListener  {
    private Spinner spImageSize;
    private Spinner spColorFilter;
    private Spinner spImageType;
    private TextView etSiteFilter;

    private Settings settings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_settings);

        settings = (Settings) getIntent().getSerializableExtra("settings");

        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spColorFilter = (Spinner) findViewById(R.id.spColorFilter);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etSiteFilter = (TextView) findViewById(R.id.etSiteFilter);

        ArrayAdapter<CharSequence> sizeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_sizes, android.R.layout.simple_spinner_item);
        sizeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageSize.setAdapter(sizeAdapter);
        //spImageSize.setSelection(sizeAdapter.getPosition(settings.getSize()));
        //spImageSize.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> colorAdapter = ArrayAdapter.createFromResource(this,
                R.array.color_filter, android.R.layout.simple_spinner_item);
        colorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spColorFilter.setAdapter(colorAdapter);
        //spColorFilter.setSelection(colorAdapter.getPosition(settings.getColor()));
        //spColorFilter.setOnItemSelectedListener(this);

        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this,
                R.array.image_types, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spImageType.setAdapter(typeAdapter);
        //spImageType.setSelection(typeAdapter.getPosition(settings.getType()));
        //spImageType.setOnItemSelectedListener(this);

       //etSiteFilter.setText(settings.getSite());

    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }


    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }




    public void onSave(View view) {
       // settings.setSite(etSiteFilter.getText().toString());

        Intent intent = new Intent();
        intent.putExtra("settings", settings);
        setResult(RESULT_OK, intent);
        finish();
    }

}