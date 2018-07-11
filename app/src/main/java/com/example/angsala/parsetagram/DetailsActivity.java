package com.example.angsala.parsetagram;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.parsetagram.models.Post;

import org.parceler.Parcels;

public class DetailsActivity extends AppCompatActivity {
    ImageView detailsImage;
    TextView detailsDescription;
    String textDescription;
    String textUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsImage = (ImageView) findViewById(R.id.imvDetailsImage);
        detailsDescription = (TextView) findViewById(R.id.txtvDetailsDescription);

    final Post receivedPost = (Post) Parcels.unwrap(getIntent().getParcelableExtra(Post.class.getSimpleName()));

       // Intent intent = getIntent();
       // objectId = intent.getStringExtra(PostAdapter.POST_ID);


//        ParseQuery<Post> postsQuery = ParseQuery.getQuery(Post.class);
//
//        postsQuery.getInBackground(
//        objectId,
//        new GetCallback<Post>() {
//          @Override
//          public void done(Post post, ParseException e) {
//            if (e == null) {
//              textDescription = post.getDescription();
//            } else {
//              Toast.makeText(DetailsActivity.this, "Failed to extract description", Toast.LENGTH_SHORT).show();
//            }
//          }
//        });
        textDescription = receivedPost.getDescription();
        detailsDescription.setText(textDescription);


    }
}
