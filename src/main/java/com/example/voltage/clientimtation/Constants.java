package com.example.voltage.clientimtation;

import java.util.AbstractMap;

public class Constants {

    public  static final String urlProtect = "https://172.16.163.124/vibesimple/rest/v1/protect";
    public static  final String urlAccess = "https://172.16.163.90/vibesimple/rest/v1/access";
    public static  final AbstractMap.SimpleImmutableEntry<String ,String> contentTypePair = new AbstractMap.SimpleImmutableEntry<>("Content-Type","application/json");

    public static  final AbstractMap.SimpleImmutableEntry<String ,String> authorizationPair = new AbstractMap.SimpleImmutableEntry<>("Authorization","VSAuth vsauth_method=\"sharedSecret\", vsauth_data=\"JCVmcmlzbyMyISkyNTZE\", vsauth_identity_ascii=\"anandi@opentext.com\", vsauth_version=\"200\"");
}
