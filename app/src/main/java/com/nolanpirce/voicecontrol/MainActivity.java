package com.nolanpirce.voicecontrol;

import android.app.Activity;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.nolanpirce.voicecontrol.db.CommandDbHelper;
import com.nolanpirce.voicecontrol.model.Command;

import java.util.ArrayList;

public class MainActivity extends Activity implements View.OnClickListener {

    private ListView mList;
    private CommandDbHelper dbHelper;

    private ArrayList<Command> possibleCommands;

    public static final int VOICE_RECOGNITION_REQUEST_CODE = 1234;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = findViewById(R.id.list);

        Button speakButton = findViewById(R.id.btn_speak);
        speakButton.setOnClickListener(this);

        Button configButton = findViewById(R.id.btn_config);
        configButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(MainActivity.this, CommandList.class);
                startActivity(myIntent);
            }

        });

        dbHelper = new CommandDbHelper(getApplicationContext());

        possibleCommands = dbHelper.getAllCommands();
    }

    public void startVoiceRecognitionActivity() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent, VOICE_RECOGNITION_REQUEST_CODE);
    }

    public void onClick(View v) {
        startVoiceRecognitionActivity();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == VOICE_RECOGNITION_REQUEST_CODE && resultCode == RESULT_OK) {
            // Fill the list view with the strings the recognizer thought it
            // could have heard
            ArrayList matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            mList.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1, matches));

            for (Command command : possibleCommands) {
                if (matches.contains(command.getVoiceCommand().toLowerCase())) {
                    Intent myIntent = new Intent(MainActivity.this, ExecuteCommand.class);
                    myIntent.putExtra(ExecuteCommand.COMMAND_DATA_KEY, command);
                    startActivity(myIntent);
                    break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }
}
