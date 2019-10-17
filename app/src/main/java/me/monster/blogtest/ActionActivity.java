package me.monster.blogtest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jeremyliao.liveeventbus.LiveEventBus;

/**
 * @description
 * @author: Created jiangjiwei in 2019-10-17 10:11
 */
public class ActionActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, ActionActivity.class);
        context.startActivity(starter);
    }

    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action);

        Button btnClose = findViewById(R.id.btn_action_closeBefore);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
//                EventBus.getDefault().post(count == 2);
                LiveEventBus.get("close")
                        .post(count == 2);
            }
        });
    }
}
