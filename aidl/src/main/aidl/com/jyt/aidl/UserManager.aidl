// UserManager.aidl
package com.jyt.aidl;

// Declare any non-default types here with import statements
import com.jyt.aidl.User;

interface UserManager {

   List<User> getUserList();

   void addUser(in User user);
}
