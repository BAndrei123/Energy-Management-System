package com.example.devicesspring.globals.devices;

import static com.example.devicesspring.globals.URLMappings.*;

public class UrlMappingDeviceUser {

    public static final String DEVICE_USER_PATH = "/DEVICE_USER";
    public static final String DEVICE_USER_POST = DEVICE_USER_PATH + POST_PATH;
    public static final String DEVICE_USER_GET = DEVICE_USER_PATH + GET_PATH + ID_PATH;
    public static final String DEVICE_USER_PUT = DEVICE_USER_PATH + PUT_PATH + ID_PATH;
    public static final String DEVICE_USER_DELETE = DEVICE_USER_PATH + DELETE_PATH + ID_PATH;
    public static final String DEVICE_USER_GET_ALL_USER_ID = DEVICE_USER_PATH + GET_PATH +  ID_PATH + PAGE_PATH;

}
