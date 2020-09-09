package com.jyt.app2;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.Nullable;

import com.jyt.aidl.User;
import com.jyt.aidl.UserManager;

import java.util.ArrayList;
import java.util.List;

public class MService extends Service {
    static String TAG = MService.class.getSimpleName();
    //数据
    List<User> users;
    //初始化一下数据
    {
        users = new ArrayList<>();
        users.add(new User("xiaoming"));
        users.add(new User("xiaoming1"));
        users.add(new User("xiaoming2"));
    }

    UserManager.Stub  userManager  = new UserManager.Stub(){
        @Override
        public List<User> getUserList() throws RemoteException {
            return users;
        }
        @Override
        public void addUser(User user) throws RemoteException {
            Log.e(TAG,"addUser "+user.getName());
            users.add(user);
        }
    };
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return userManager;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG,"onCreate");
    }
}
