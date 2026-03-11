package com.example.devicesspring.globals.devices;

import static com.example.devicesspring.globals.URLMappings.*;

public class UrlMappingDevice {
    public static final String DEVICE_PATH = "/DEVICE";
    public static final String DEVICE_POST = DEVICE_PATH + POST_PATH;
    public static final String DEVICE_GET = DEVICE_PATH + GET_PATH + ID_PATH;
    public static final String DEVICE_PUT = DEVICE_PATH + PUT_PATH + ID_PATH;
    public static final String DEVICE_DELETE = DEVICE_PATH + DELETE_PATH + ID_PATH;
    public static final String DEVICE_GET_ALL = DEVICE_PATH + GET_PATH + ALL_PATH;
    public static final String DEVICE_GET_NAME = DEVICE_PATH + GET_PATH + NAME_PATH;
}
