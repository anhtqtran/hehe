package com.tranthanhqueanh.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.tranthanhqueanh.myapplication.models.Category;
import com.tranthanhqueanh.myapplication.models.Product;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "vikings.sqlite";
    private static final int DATABASE_VERSION = 1;
    private final Context myContext;
    private String DB_PATH;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.myContext = context;
        DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
    }

    public void checkAndCopyDatabase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        if (!dbFile.exists()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (Exception e) {
                throw new RuntimeException("Error copying database", e);
            }
        }
    }

    private void copyDBFile() throws java.io.IOException {
        InputStream mInput = myContext.getAssets().open(DATABASE_NAME);
        String outFileName = DB_PATH + DATABASE_NAME;
        OutputStream mOutput = new FileOutputStream(outFileName);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0) {
            mOutput.write(mBuffer, 0, mLength);
        }
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Không cần vì dùng database có sẵn
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xử lý nâng cấp nếu cần
    }
    public boolean checkLogin(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {"Username"};
        String selection = "Username = ? AND Password = ?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query("Account", columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count > 0;
    }
    public ArrayList<Category> getAllCategories() {
        ArrayList<Category> categories = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Category", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("CategoryID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("CategoryName"));
                categories.add(new Category(id, name));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return categories;
    }
    public ArrayList<Product> getProductsByCategory(int categoryId) {
        ArrayList<Product> products = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products WHERE CategoryID = ?", new String[]{String.valueOf(categoryId)});

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("ProductID"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("ProductName"));
                double price = cursor.getDouble(cursor.getColumnIndexOrThrow("UnitPrice"));
                String image = cursor.getString(cursor.getColumnIndexOrThrow("ImageLink"));
                products.add(new Product(id, name, price, image));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return products;
    }
}
