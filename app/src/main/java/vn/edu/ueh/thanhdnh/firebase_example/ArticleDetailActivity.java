package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class ArticleDetailActivity extends AppCompatActivity {
    ImageView imgCover;
    TextView txtTitle;
    TextView txtContent;
    TextView txtView;
    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_detail);

        imgCover = findViewById(R.id.img_cover_detail);
        txtTitle = findViewById(R.id.txt_title_detail);
        txtContent = findViewById(R.id.txt_content_detail);
        txtView = findViewById(R.id.txt_view_detail);
        db = FirebaseFirestore.getInstance();
        String id = getIntent().getStringExtra("id");
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        String imgCoverUrl = getIntent().getStringExtra("img_cover");
        int view = getIntent().getIntExtra("view", 0);
        txtTitle.setText(title);
        txtContent.setText(content);
        txtView.setText("View: " + (view + 1));
        Glide.with(this).load(imgCoverUrl).into(imgCover);
        if (id != null) {
            db.collection("articles").document(id).update("view", FieldValue.increment(1));
        }
    }
}