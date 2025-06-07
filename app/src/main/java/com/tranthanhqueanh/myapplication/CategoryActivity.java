package com.tranthanhqueanh.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.tranthanhqueanh.myapplication.adapters.CategoryAdapter;
import com.tranthanhqueanh.myapplication.models.Category;

public class CategoryActivity extends AppCompatActivity {
    private ListView lvCategories;
    private DatabaseHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        lvCategories = findViewById(R.id.lvCategories);
        dbHelper = new DatabaseHelper(this);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        CategoryAdapter adapter = new CategoryAdapter(this, dbHelper.getAllCategories());
        lvCategories.setAdapter(adapter);
        lvCategories.setOnItemClickListener((parent, view, position, id) -> {
            Category category = (Category) parent.getItemAtPosition(position);
            Intent intent = new Intent(CategoryActivity.this, ProductActivity.class);
            intent.putExtra("CATEGORY_ID", category.getCategoryID());
            startActivity(intent);
        });
    }
}