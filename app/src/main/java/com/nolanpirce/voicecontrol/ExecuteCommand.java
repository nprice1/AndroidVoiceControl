package com.nolanpirce.voicecontrol;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.nolanpirce.voicecontrol.model.Command;

import org.json.JSONObject;

/**
 * Created by nolanprice on 8/21/17.
 */

public class ExecuteCommand extends Activity {

    public static final String COMMAND_DATA_KEY = "command";

    private TextView responseView;
    private RequestQueue requestQueue;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_execute_command);

        responseView = findViewById(R.id.execute_response);

        Button backButton = findViewById(R.id.execute_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(ExecuteCommand.this, MainActivity.class);
                startActivity(myIntent);
            }
        });

        requestQueue = Volley.newRequestQueue(this);

        Command command = (Command) getIntent().getSerializableExtra(COMMAND_DATA_KEY);
        executeCommand(command);
    }

    private void executeCommand(Command command) {
        String url = command.getActionUrl();

        try {
            JSONObject jsonPayload = new JSONObject(command.getJsonPayload());
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonPayload,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            responseView.append("Response is: "+ response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("executeError", "Error from request server", error);
                            responseView.append("Error from request server: " + error.getMessage());
                        }
                    }
            );
            // Add the request to the RequestQueue.
            requestQueue.add(request);
        } catch (Exception e) {
            Log.e("requestError", "Failed to send request", e);
            responseView.append("Failed to send request: " + e.getMessage());
        }
    }

}
