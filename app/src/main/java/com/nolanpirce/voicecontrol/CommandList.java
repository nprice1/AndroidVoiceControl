package com.nolanpirce.voicecontrol;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.nolanpirce.voicecontrol.db.CommandDbHelper;

import java.util.ArrayList;

/**
 * Created by nolanprice on 8/18/17.
 */

public class CommandList extends Activity {

    ArrayAdapter<String> adapter;
    ArrayList<String> commands;

    private CommandDbHelper dbHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command_list);

        Button addCommandButton = findViewById(R.id.btn_add_config);
        addCommandButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CommandList.this, NewCommand.class);
                startActivity(myIntent);
            }
        });
        Button backButton = findViewById(R.id.list_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(CommandList.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        dbHelper = new CommandDbHelper(getApplicationContext());

        ListView commandList = findViewById(R.id.list);
        commands = dbHelper.getAllCommandNames();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, commands);
        commandList.setAdapter(adapter);
        commandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder builder;
                final String commandName = commands.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(view.getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(view.getContext());
                }
                builder.setTitle("Delete entry")
                    .setMessage("Are you sure you want to delete this entry?")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dbHelper.deleteCommand(commandName);
                            refreshCommands();
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            }
        });
    }

    @Override
    public void onResume() {
        refreshCommands();
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        dbHelper.close();
        super.onDestroy();
    }

    private void refreshCommands() {
        commands = dbHelper.getAllCommandNames();
        adapter.clear();
        adapter.addAll(commands);
    }

}
