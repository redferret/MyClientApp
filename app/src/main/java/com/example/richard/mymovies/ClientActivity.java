package com.example.richard.mymovies;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import static com.example.richard.mymovies.R.color.Black;

public class ClientActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView selectedTitle;

    public void addTitleTextView(View view){

        LinearLayout layout = (LinearLayout) findViewById(R.id.movie_titles);

        TextView movieTitle = (TextView) findViewById(R.id.movie_title);

        TextView title = new TextView(this);
        title.setText(movieTitle.getText());
        title.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        title.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT));
        title.setClickable(true);
        title.setOnClickListener(this);
        layout.addView(title);
        movieTitle.setText("");
    }

    @Override
    public void onClick(View v) {
        if (v instanceof TextView){
            selectedTitle = (TextView) v;
            boolean appStarted = startApp("apackage.richard.com.fontchooser");
            if (!appStarted){
                Toast.makeText(this, "FontChooser was not found on this Device", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                int fontStyle = extras.getInt("FontStyle");
                int fontSize = extras.getInt("FontSize");
                int fontType = extras.getInt("FontTypeface");
                int fontColor = extras.getInt("FontColor");

                Typeface typeFace;

                switch (fontType){
                    case 1:
                        typeFace = Typeface.MONOSPACE;
                        break;
                    case 2:
                        typeFace = Typeface.SERIF;
                        break;
                    case 3:
                        typeFace = Typeface.SANS_SERIF;
                        break;
                    case 0:
                    default:
                        typeFace = Typeface.SERIF;
                }

                selectedTitle.setTextColor(fontColor);
                selectedTitle.setTextSize(fontSize);
                selectedTitle.setTypeface(typeFace, fontStyle);
            }
        }
    }

    public boolean startApp(String packageName) {
        PackageManager manager = this.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(packageName);
        if (intent == null) {
            return false;
        }
        startActivityForResult(intent, 0);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

    }

}
