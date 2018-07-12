package com.example.angsala.parsetagram;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.parsetagram.models.Post;
import com.parse.ParseException;

import org.parceler.Parcels;

import java.io.File;

public class DetailsActivity extends AppCompatActivity {
    ImageView detailsImage;
    TextView detailsDescription;
    String textDescription;
    String textUsername;
    File imageFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsImage = (ImageView) findViewById(R.id.imvDetailsImage);
        detailsDescription = (TextView) findViewById(R.id.txtvDetailsDescription);

    final Post receivedPost = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

        try {
            imageFile = receivedPost.getImage().getFile();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        detailsImage.setImageBitmap(image);
        textDescription = receivedPost.getDescription();
        detailsDescription.setText(textDescription);


    }
}
