package com.example.monitoringspring.globals;

import static com.example.monitoringspring.globals.URLMappings.ID_PATH;

public class UrlMappingDeviceConsumption {

    public static final String DEVICE_CONSUMPTION_PATH = "/DEVICE_CONSUMPTION";
    public static final String DATE = "/{date}";
    public static final String DEVICE_CONSUMPTION_GET_CONSUMPTION = DEVICE_CONSUMPTION_PATH + DATE + ID_PATH;

}
