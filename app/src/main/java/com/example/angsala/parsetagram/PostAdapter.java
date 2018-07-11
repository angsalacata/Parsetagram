package com.example.angsala.parsetagram;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.angsala.parsetagram.models.Post;
import com.parse.ParseException;

import java.io.File;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    public static final String POST_DESCRIPTION = "postDescription";
    private List<Post> adapterPosts;
    Context context;
    File imageFile;

    public PostAdapter(List<Post> postsIn){
        this.adapterPosts = postsIn;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.item_post, parent, false);
        return new ViewHolder(postView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Post post = adapterPosts.get(position);
      //(post.getImage());
        try {
             imageFile = post.getImage().getFile();
        } catch (ParseException e) {
            e.printStackTrace();
        }
       Bitmap image = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
        holder.imvImage.setImageBitmap(image);
        holder.txtvDescription.setText(post.getDescription());
    }

    @Override
    public int getItemCount() {
        return adapterPosts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView imvImage;
        TextView txtvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            imvImage = (ImageView) itemView.findViewById(R.id.imvPicture);
            txtvDescription = (TextView) itemView.findViewById(R.id.txtvDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int viewPosition = getAdapterPosition();
            if(viewPosition != RecyclerView.NO_POSITION){
                Post post = adapterPosts.get(viewPosition);

                Intent detailsIntent = new Intent(context, DetailsActivity.class);
                detailsIntent.putExtra(POST_DESCRIPTION, post.getDescription());
                context.startActivity(detailsIntent);
            }


        }
    }

    //clean all elements
    public void clear(){
        adapterPosts.clear();
        notifyDataSetChanged();
    }
    public void addAll(List<Post> list){
        adapterPosts.addAll(list);
        notifyDataSetChanged();
    }

}
