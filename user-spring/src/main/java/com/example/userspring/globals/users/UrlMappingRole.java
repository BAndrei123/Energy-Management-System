package com.example.userspring.globals.users;

import static com.example.userspring.globals.URLMappings.*;



public class UrlMappingRole {

    public static final String ROLE_PATH = "/role";

    public static final String ROLE_POST = ROLE_PATH + POST_PATH;

    public static final String ROLE_GET = ROLE_PATH + GET_PATH + ID_PATH;


    public static final String ROLE_EMAIL_GET = ROLE_PATH + GET_PATH + EMAIL_PATH;


    public static final String ROLE_PUT = ROLE_PATH + PUT_PATH + ID_PATH;

    public static final String ROLE_DELETE = ROLE_PATH + DELETE_PATH + ID_PATH;
}
