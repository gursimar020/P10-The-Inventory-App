package com.example.android.inventoryboxapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class productDetail extends AppCompatActivity {

    // Views USED
    TextView productname;
    TextView productPrice;
    TextView productQuantity;
    ImageView productImage;

    // Buttons USED
    Button incrementButton;
    Button decrementButton;
    Button orderItem;
    Button deleteItem;

    // variables required for intent data
    int mProductId;
    String mProductName;
    int mProductQuantity;
    String mImageUrl;
    double mProductPrice;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_detail);

        //set the id for each references
        productname = (TextView) findViewById(R.id.productName);
        productPrice = (TextView) findViewById(R.id.productPrice);
        productQuantity = (TextView) findViewById(R.id.productQuantity);
        productImage = (ImageView) findViewById(R.id.productImage);
        incrementButton = (Button) findViewById(R.id.increment_button);
        decrementButton = (Button) findViewById(R.id.decrement_button);
        orderItem = (Button) findViewById(R.id.OrderMore);
        deleteItem = (Button) findViewById(R.id.Delete);

        // get the intent content and set them to respective objects
        Intent i = getIntent();
        if (i != null) {
            Bundle bundle = i.getExtras();
            mProductId = bundle.getInt("id");
            mProductName = bundle.getString("productName");
            mProductQuantity = bundle.getInt("quantity");
            mProductPrice = bundle.getDouble("price");
            mImageUrl = bundle.getString("imageUrl");
        }

        productname.setText(mProductName);
        productQuantity.setText("Quantity: " + mProductQuantity);
        productPrice.setText("$ " + mProductPrice);

        //Increment Quantity
        incrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProductQuantity++;
                productQuantity.setText("Quantity: " + mProductQuantity);
                ContentValues values = new ContentValues();
                values.put(DbContract.productTable.COL_QUANTITY, mProductQuantity);
                String selection = DbContract.productTable._ID + " =  ? ";
                String[] selectionArgs = {String.valueOf(mProductId)};
                DbQuery.getInstance(context).updateData(DbContract.productTable.TABLE_NAME, values, selection, selectionArgs);
                MainActivity.refreshCursor();
            }
        });

        //Decrease the quantity
        decrementButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProductQuantity--;
                if (mProductQuantity < 0) {
                    mProductQuantity = 0;
                }
                productQuantity.setText("Quantity: " + mProductQuantity);
                ContentValues values = new ContentValues();
                values.put(DbContract.productTable.COL_QUANTITY, mProductQuantity);
                String selection = DbContract.productTable._ID + " = ? ";
                String[] selectionArgs = {String.valueOf(mProductId)};
                DbQuery.getInstance(context).updateData(DbContract.productTable.TABLE_NAME, values, selection, selectionArgs);
                MainActivity.refreshCursor();
            }
        });

        //to delete the product
        deleteItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(context)
                        .setTitle("Warning")
                        .setMessage("Are you sure you want to delete ?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selection = DbContract.productTable._ID + " = ? ";
                                String[] selectionArgs = {String.valueOf(mProductId)};
                                DbQuery.getInstance(context).deleteEntry(DbContract.productTable.TABLE_NAME, selection, selectionArgs);
                                Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                MainActivity.refreshCursor();
                                finish();
                            }

                        })
                        .setNegativeButton("No", null)
                        .show();
            }
        });

        //to order more product
        orderItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String subject = "Reorder  of product";
                String message = "Product Name: " + mProductName +
                        "\nProduct Price: " + mProductPrice +
                        "\nQuantity To be ordered: 80";
                String[] emails = {"gursimarsingh020@gmail.com"};
                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL, emails);
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, message);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });
    }

}
