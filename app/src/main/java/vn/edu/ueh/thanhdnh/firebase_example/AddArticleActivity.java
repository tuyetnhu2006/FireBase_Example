package vn.edu.ueh.thanhdnh.firebase_example;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddArticleActivity extends AppCompatActivity {
    EditText edtTitle;
    EditText edtContent;
    EditText edtImgCover;

    Button btnSaveArticle;

    FirebaseFirestore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_article);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtTitle = findViewById(R.id.edt_title);
        edtContent = findViewById(R.id.edt_content);
        edtImgCover = findViewById(R.id.edt_img_cover);

        btnSaveArticle = findViewById(R.id.btn_save_article);

        db = FirebaseFirestore.getInstance();

        btnSaveArticle.setOnClickListener(view -> {

            String title = edtTitle.getText().toString().trim();
            String content = edtContent.getText().toString().trim();
            String imgCover = edtImgCover.getText().toString().trim();

            if (title.isEmpty() ||
                    content.isEmpty() ||
                    imgCover.isEmpty()) {

                Toast.makeText(
                        AddArticleActivity.this,
                        "Please enter all information",
                        Toast.LENGTH_SHORT
                ).show();

                return;
            }

            Map<String, Object> article = new HashMap<>();

            article.put("title", title);
            article.put("content", content);
            article.put("img_cover", imgCover);
            article.put("view", 0);

            db.collection("articles")
                    .add(article)
                    .addOnSuccessListener(documentReference -> {

                        Toast.makeText(
                                AddArticleActivity.this,
                                "Article added successfully",
                                Toast.LENGTH_SHORT
                        ).show();

                        finish();

                    })
                    .addOnFailureListener(e -> {

                        Toast.makeText(
                                AddArticleActivity.this,
                                "Add article failed",
                                Toast.LENGTH_SHORT
                        ).show();

                    });
        });
    }
}