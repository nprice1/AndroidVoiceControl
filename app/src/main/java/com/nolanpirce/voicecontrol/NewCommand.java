package com.nolanpirce.voicecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nolanpirce.voicecontrol.db.CommandDbHelper;
import com.nolanpirce.voicecontrol.model.Command;

/**
 * Created by nolanprice on 8/18/17.
 */

public class NewCommand extends Activity {

    private Button saveButton;
    private Button backButton;

    private EditText voiceCommand;
    private EditText actionUrl;
    private EditText jsonPayload;

    private CommandDbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_command);

        saveButton = findViewById(R.id.btn_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCommand();
            }
        });

        backButton = findViewById(R.id.btn_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(NewCommand.this, CommandList.class);
                startActivity(myIntent);
            }
        });

        voiceCommand = findViewById(R.id.voiceCommand);
        actionUrl = findViewById(R.id.actionUrl);
        jsonPayload = findViewById(R.id.jsonPayload);

        dbHelper = new CommandDbHelper(this.getApplicationContext());
    }

    public void saveCommand() {
        Command command = new Command();
        command.setVoiceCommand(voiceCommand.getText().toString());
        command.setActionUrl(actionUrl.getText().toString());
        command.setJsonPayload(jsonPayload.getText().toString());
        dbHelper.storeCommand(command);
        Toast.makeText(this, "Command added", Toast.LENGTH_LONG).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

}
