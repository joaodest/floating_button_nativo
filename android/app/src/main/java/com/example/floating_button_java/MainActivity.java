package com.example.floating_button_java;

import android.media.Image;
import android.os.Bundle;
import android.widget.ImageView;

import com.yhao.floatwindow.FloatWindow;
import com.yhao.floatwindow.Screen;

import androidx.annotation.NonNull;
import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugins.GeneratedPluginRegistrant;

public class MainActivity extends FlutterActivity {
    public static final String CHANNEL = "floating_button";

    @Override
    protected void onCreate(Bundle savedInstaceState) {
        super.onCreate(savedInstaceState);
        GeneratedPluginRegistrant.registerWith(new FlutterEngine(this));

    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

       MethodChannel channel = new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL);
                channel.setMethodCallHandler(
                        (call, result) -> {
                            switch (call.method) {
                                case "create":

                                    ImageView imageView = new ImageView(getApplicationContext());
                                    imageView.setImageResource(R.drawable.plus);

                                    FloatWindow.with(getApplicationContext()).setView(imageView)
                                            .setWidth(Screen.width, 0.15f)
                                            .setHeight(Screen.height, 0.15f)
                                            .setX(Screen.width, 0.8f)
                                            .setY(Screen.height, 0.3f)
                                            .setDesktopShow(true)
                                            .build();
                                    imageView.setOnClickListener(view -> {
                                        channel.invokeMethod("touch", null);
                                    });
                                    break;
                                case "show":
                                    FloatWindow.get().show();
                                    break;
                                case "hide":
                                    FloatWindow.get().hide();
                                    break;
                                case "isShowing":
                                    result.success(FloatWindow.get().isShowing());
                                    break;
                                default:
                                    result.notImplemented();
                            }
                        }
                );
    }


    @Override
    protected void onDestroy() {
        FloatWindow.destroy();
        super.onDestroy();
    }
}
