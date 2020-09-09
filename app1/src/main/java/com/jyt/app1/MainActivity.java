package com.jyt.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;

import com.jyt.aidl.User;
import com.jyt.aidl.UserManager;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    static String TAG =  MainActivity.class.getSimpleName();
    UserManager userManager;
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG,"onServiceConnected");
            Log.e(TAG,name.toShortString());
            userManager = UserManager.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            userManager = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn).setOnClickListener(this);
        Intent intent = new Intent("android.intent.action.UserService");
        intent.setPackage("com.jyt.app2");
        bindService(intent,serviceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                if (userManager!=null){
                    try {
                        userManager.addUser(new User("user"+new Random().nextDouble()*100));
                        List<User> users = userManager.getUserList();
                        if (users!=null){
                            for (int i=0;i<users.size();i++){
                                System.out.println(users.get(i).getName());
                            }
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }else {
                    Log.e(TAG,"userManager is null");
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(serviceConnection);
    }
}
