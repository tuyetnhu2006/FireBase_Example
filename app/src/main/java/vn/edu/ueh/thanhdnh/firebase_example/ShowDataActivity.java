package vn.edu.ueh.thanhdnh.firebase_example;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ShowDataActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    Button btnAddArticle;
    List<Article> articles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        recyclerView = findViewById(R.id.reclyclerview);
        btnAddArticle = findViewById(R.id.btn_add_article);
        ArticleViewAdapter adapter = new ArticleViewAdapter(this, articles);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .addSnapshotListener(
                        new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(
                                    @Nullable QuerySnapshot snapshots,
                                    @Nullable FirebaseFirestoreException error) {
                                if (snapshots != null) {
                                    articles.clear();
                                    for (QueryDocumentSnapshot q : snapshots) {
                                        Map<String, Object> data = q.getData();
                                        String id = q.getId();
                                        String title = (String) data.get("title");
                                        String content = (String) data.get("content");
                                        String imgCover = (String) data.get("img_cover");
                                        Number viewNumber = (Number) data.get("view");
                                        int view = 0;
                                        if (viewNumber != null) {
                                            view = viewNumber.intValue();
                                        }
                                        Article article = new Article(id, title, content, imgCover, view);
                                        articles.add(article);
                                    }
                                    adapter.update(articles);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        }
                );

        btnAddArticle.setOnClickListener(view -> {

            Intent intent = new Intent(
                    ShowDataActivity.this,
                    AddArticleActivity.class
            );

            startActivity(intent);
        });
    }
}