package com.nolanpirce.voicecontrol.db;

import android.provider.BaseColumns;

/**
 * Created by nolanprice on 8/18/17.
 */

public class CommandSchema implements BaseColumns {
    static final String TABLE_NAME = "command";
    static final String COLUMN_NAME_VOICE_COMMAND = "voiceCommand";
    static final String COLUMN_NAME_ACTION_URL = "actionUrl";
    static final String COLUMN_NAME_JSON_PAYLOAD = "jsonPayload";
}
