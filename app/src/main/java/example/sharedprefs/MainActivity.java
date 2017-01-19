package example.sharedprefs;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    EditText notesEditText;
    Button btnSettings;
    private static final int SETTINGS_INFO = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==SETTINGS_INFO)
            updateNotesText();
    }

    private void updateNotesText() {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if(prefs.getBoolean("pref_text_bold",false))
            notesEditText.setTypeface(null, Typeface.BOLD);
        else notesEditText.setTypeface(null, Typeface.NORMAL);

        String textSizeStr = prefs.getString("pref_text_size","16");
        float textSizeFloat = Float.parseFloat(textSizeStr);
        notesEditText.setTextSize(textSizeFloat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesEditText = (EditText) findViewById(R.id.notesEditText);
        if(savedInstanceState!=null)
        {
            String notes = savedInstanceState.getString("NOTES");
            notesEditText.setText(notes);
        }

        String spNotes = getPreferences(Context.MODE_PRIVATE).getString("NOTES","EMPTY");
        if(spNotes.equals("EMPTY"))
            notesEditText.setText(spNotes);


        btnSettings = (Button) findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Settings.class);
                startActivityForResult(intent,SETTINGS_INFO);
            }
        });


    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("NOTES",notesEditText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private void saveSettings(){
        SharedPreferences.Editor editor = getPreferences(Context.MODE_PRIVATE).edit();
        editor.putString("NOTES",notesEditText.getText().toString());
        editor.commit();
    }
}
