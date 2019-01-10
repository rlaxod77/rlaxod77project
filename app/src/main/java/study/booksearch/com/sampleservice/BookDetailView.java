package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_view);


        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String author = intent.getStringExtra("author");
        String ImageUrl = intent.getStringExtra("ImageUrl");

        ImageView BookImg = findViewById(R.id.DetailImageView);
        TextView BookTitle = findViewById(R.id.DetailTitleVeiw);
        TextView BookAuthor = findViewById(R.id.DetailAuthorVeiw);

        BookTitle.setText(title);
        BookAuthor.setText(author);
        Glide.with(this).load(ImageUrl).into(BookImg);




    }
}
