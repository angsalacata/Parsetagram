package com.example.angsala.parsetagram;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.angsala.parsetagram.models.Post;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class HomeTimelineActivity extends AppCompatActivity {

    String TAG = "HomeActivity";
    private static final String imagePath = "/storage/emulated/0/DCIM/Camera/IMG_20180709_175243.jpg";
    private EditText inputDescription;
    private Button buttonRefresh;
    private Button buttonCreate;
    private ImageView testImage;
    public final static int PICK_PHOTO_CODE = 1046;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    //this is from the codepath, capture intent article
    public final String APP_TAG = "MyParsetagram";
    public String photoFilename = "photo.jpg";
    File photofile;
    String mCurrentPhotoPath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ParseObject.registerSubclass(Post.class);

        inputDescription = (EditText) findViewById(R.id.inputDescription);
        buttonRefresh = (Button) findViewById(R.id.buttonRefresh);
        buttonCreate = (Button) findViewById(R.id.buttonCreate);


        buttonRefresh.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadTopPosts();
                    }
                });

        buttonCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // instance objects for createPosts
                        final String description = inputDescription.getText().toString();
                        final ParseUser currentUser = ParseUser.getCurrentUser();

                        final File file = getPhotoFileUri(photoFilename);
                        final ParseFile picture = new ParseFile(file);
                        //this ParseFile has to be saved
                        picture.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {
                                createPosts(description, picture, currentUser);
                            }
                        });

                    }
                });

        loadTopPosts();

        //button to take pictures, it is a floating action button
        FloatingActionButton camera = (FloatingActionButton) findViewById(R.id.fab);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
                }
        });
    }

    private void createPosts(String description, ParseFile imageFile, ParseUser user) {
        Post newPost = new Post();
        newPost.setDescription(description);
        newPost.setImage(imageFile);
        newPost.setUser(user);

        newPost.saveInBackground(
                new SaveCallback() {
                    @Override
                    public void done(ParseException e) {
                        if (e == null) {
                            Log.d(TAG, "Successfully posted new post");
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void loadTopPosts() {
        final Post.Query postsQuery = new Post.Query();
        postsQuery.getTop().withUser();

        postsQuery.findInBackground(
                new FindCallback<Post>() {
                    @Override
                    public void done(List<Post> posts, ParseException e) {
                        if (e == null) {
                            for (int i = 0; i < posts.size(); i++) {
                                Log.d(
                                        TAG,
                                        "Post number "
                                                + i
                                                + " description: "
                                                + posts.get(i).getDescription()
                                                + "\n username = "
                                                + posts
                                                .get(i)
                                                .getUser()
                                                .getUsername()); // user has been attached to the post
                            }
                        } else {
                            e.printStackTrace();
                        }
                    }
                });
    }


//TODO- how to pick a photo from the emulator
 /*   public void onPickPhoto(View view){
        //Create intent for picking a photo from the gallery
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

    }*/


    private void dispatchTakePictureIntent(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager())!=null){
            File photoFile = null;
            try{
                photoFile = createImageFile();}
            catch(IOException e){
                e.printStackTrace();
            }
            if(photoFile != null){
                mCurrentPhotoPath = photoFile.getAbsolutePath();
                Uri photoURI = FileProvider.getUriForFile(HomeTimelineActivity.this,"com.codepath.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }

            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

        }
    }

    private File getPhotoFileUri(String photoFilename) {
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        //create storage directory if not in existence
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
            Log.d(APP_TAG, "failed to create directory"); }
        File file = new File(mediaStorageDir.getPath() + File.separator + photoFilename);
        return file;
    }

    //use this to trigger that the file was loaded
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            Log.d(TAG, "successfully saved photo");
            //this will still load the pic into an imageview
         Bundle extras = data.getExtras();
            testImage = (ImageView) findViewById(R.id.imvTestGettingCamera);
       Bitmap imageBitmap = (Bitmap) extras.get("data");
         testImage.setImageBitmap(imageBitmap);
        }

    }

    private File createImageFile() throws IOException{
        String timeStamp = "7/10";
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(timeStamp, imageFileName, storageDir);
        return image;
    }



}
