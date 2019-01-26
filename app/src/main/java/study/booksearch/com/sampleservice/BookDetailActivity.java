package study.booksearch.com.sampleservice;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class BookDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail_view);

        Intent intent = getIntent();
        String title = intent.getStringExtra(Constant.BOOK_TITLE);
        String title2 = Constant.BOOK_TITLE2;
        String title3 = title2 + title;
        String author = intent.getStringExtra(Constant.BOOK_AUTHOR);
        String autor2 = Constant.BOOK_AUTHOR2;
        String autor3 = autor2 + author;
        String ImageUrl = intent.getStringExtra(Constant.BOOK_IMAGEURL);

        ImageView BookImg = findViewById(R.id.DetailImageView);
        TextView BookTitle = findViewById(R.id.DetailTitleVeiw);
        TextView BookAuthor = findViewById(R.id.DetailAuthorVeiw);

        BookTitle.setText(title3);
        BookAuthor.setText(autor3);
        Glide.with(this).load(ImageUrl).into(BookImg);


    }


}
