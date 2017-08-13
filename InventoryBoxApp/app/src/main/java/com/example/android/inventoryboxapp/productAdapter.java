package com.example.android.inventoryboxapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class productAdapter extends CursorAdapter {

    private LayoutInflater mLayoutInflator;

    public productAdapter(Context context, Cursor c, int flag) {
        super(context, c, flag);
        this.mLayoutInflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return mLayoutInflator.inflate(R.layout.list_item, viewGroup, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        LinearLayout listItemView = (LinearLayout) view.findViewById(R.id.listitem);
        TextView productName = (TextView) view.findViewById(R.id.product_name);
        TextView productQuantity = (TextView) view.findViewById(R.id.product_quantity);
        TextView productPrice = (TextView) view.findViewById(R.id.item_price);
        Button buyButton = (Button) view.findViewById(R.id.Buy);

        final int id = cursor.getInt(cursor.getColumnIndex(DbContract.productTable._ID));
        final String mProductName = cursor.getString(cursor.getColumnIndex(DbContract.productTable.COL_PRODUCT_NAME));
        final int mProductQuantity = cursor.getInt(cursor.getColumnIndex(DbContract.productTable.COL_QUANTITY));
        final double mProductPrice = cursor.getDouble(cursor.getColumnIndex(DbContract.productTable.COL_PRICE));
        final String mImageUrl = cursor.getString(cursor.getColumnIndex(DbContract.productTable.COL_IMAGE));

        productName.setText(mProductName);
        productQuantity.setText("" + mProductQuantity);
        productPrice.setText("$ " + mProductPrice);

        buyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int newProductQuantity = mProductQuantity - 1;
                if (newProductQuantity < 0) {
                    newProductQuantity = 0;
                }
                ContentValues values = new ContentValues();
                values.put(DbContract.productTable.COL_QUANTITY, newProductQuantity);
                String selection = DbContract.productTable._ID + " = ? ";
                String[] selectionArgs = {String.valueOf(id)};
                DbQuery.getInstance(context).updateData(DbContract.productTable.TABLE_NAME, values, selection, selectionArgs);
                MainActivity.refreshCursor();
            }
        });

        listItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("id", id);
                bundle.putString("productName", mProductName);
                bundle.putInt("quantity", mProductQuantity);
                bundle.putDouble("price", mProductPrice);
                bundle.putString("imageUrl", mImageUrl);
                Intent i = new Intent(context, productDetail.class);
                i.putExtras(bundle);
                context.startActivity(i);
            }
        });
    }

}
