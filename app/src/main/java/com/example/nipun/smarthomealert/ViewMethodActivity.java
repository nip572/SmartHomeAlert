package com.example.nipun.smarthomealert;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ViewMethodActivity extends AppCompatActivity {

    TextView tvViewMethod;
    ImageView imgViewImage;
    TextView tvTitle;
    String method;
    String imageUrl;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_method);

        Bundle bundle = getIntent().getExtras();
        method = bundle.getString("method");
        imageUrl = bundle.getString("imageUrl");
        title = bundle.getString("title");


        tvViewMethod = (TextView) findViewById(R.id.view_method);
        imgViewImage= (ImageView) findViewById(R.id.view_method_image);

        tvViewMethod.setText(method);
        Picasso.with(this).load(imageUrl).into(imgViewImage);
        imgViewImage.setColorFilter(Color.rgb(175, 175, 175), android.graphics.PorterDuff.Mode.MULTIPLY);


    }
}
