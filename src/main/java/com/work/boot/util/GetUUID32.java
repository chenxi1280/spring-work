package com.work.boot.util;

import java.util.UUID;

public class GetUUID32 {
    public static String getuuid32() {
        UUID id = UUID.randomUUID();
        String[] idd = id.toString().split("-");
        return idd[0] + idd[1] + idd[2] + idd[3] + idd[4];
    }

//    public static void main(String[] args) {
//
//        System.out.println(getuuid32());
//    }
}
