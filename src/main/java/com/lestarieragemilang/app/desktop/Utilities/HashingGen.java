package com.lestarieragemilang.app.desktop.Utilities;

public class HashingGen {
    public static String hash(String password) {
        return org.mindrot.jbcrypt.BCrypt.hashpw(password, org.mindrot.jbcrypt.BCrypt.gensalt());
    }
    
    public static void main(String[] args) {
        System.out.println(hash("ronaldo"));
    }
    
}
