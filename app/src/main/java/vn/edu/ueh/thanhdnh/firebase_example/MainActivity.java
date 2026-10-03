package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    FirebaseFirestore db;
    RecyclerView recyclerView;
    List<Article> articles = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                }
        );

        FirebaseApp.initializeApp(this);
        recyclerView = findViewById(R.id.reclyclerview);
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
    }
}