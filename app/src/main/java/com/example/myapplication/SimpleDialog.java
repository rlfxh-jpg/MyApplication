package com.example.myapplication;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleDialog extends Dialog {
    public TextView dialog_title;
    public EditText dialog_message;
    public Button bt_cancel, bt_confirm;
    protected Context mContext;
    private View customView;
    ImageView icon;


    public SimpleDialog(Context context) {
        super(context);
        mContext = context;
        customView = LayoutInflater.from(context).inflate(R.layout.dialog_simple, null);

        icon = (ImageView) customView.findViewById(R.id.icon);

        dialog_title = (TextView) customView.findViewById(R.id.dialog_title);
        setTitle("Alarm!");
        dialog_message = (EditText) customView.findViewById(R.id.dialog_message);
        dialog_message.clearFocus();
        bt_confirm = (Button) customView.findViewById(R.id.dialog_confirm);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(customView);
    }

    public SimpleDialog setClickListener(View.OnClickListener listener) {
        bt_confirm.setOnClickListener(listener);
        return this;
    }

    public SimpleDialog setMessage(String message) {
        dialog_message.setText(message);
        return this;
    }

    public SimpleDialog setTitle(String title) {
        dialog_title.setText(title);
        return this;
    }
}
