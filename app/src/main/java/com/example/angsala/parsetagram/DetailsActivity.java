package com.example.angsala.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {
    ImageView detailsImage;
    TextView detailsDescription;
    String textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsImage = (ImageView) findViewById(R.id.imvDetailsImage);
        detailsDescription = (TextView) findViewById(R.id.txtvDetailsDescription);

        //textDescription = getExtra

    }
}
