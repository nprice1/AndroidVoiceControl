package com.nolanpirce.voicecontrol.model;

import java.io.Serializable;

/**
 * Created by nolanprice on 8/18/17.
 */

public class Command implements Serializable {

    private String voiceCommand;
    private String actionUrl;
    private String jsonPayload;

    public String getVoiceCommand() {
        return voiceCommand;
    }

    public void setVoiceCommand(String voiceCommand) {
        this.voiceCommand = voiceCommand;
    }

    public String getActionUrl() {
        return actionUrl;
    }

    public void setActionUrl(String actionUrl) {
        this.actionUrl = actionUrl;
    }

    public String getJsonPayload() {
        return jsonPayload;
    }

    public void setJsonPayload(String jsonPayload) {
        this.jsonPayload = jsonPayload;
    }

}
