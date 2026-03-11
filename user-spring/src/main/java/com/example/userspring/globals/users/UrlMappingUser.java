package com.example.userspring.globals.users;

import static com.example.userspring.globals.URLMappings.*;

public class UrlMappingUser {


    private static final String USER_PATH = "/user";

    private static final String USER_NAME = "/user_name";
    public static final String USER_POST = USER_PATH + POST_PATH;

    public static final String USER_GET = USER_PATH + GET_PATH;

    public static final String USER_EMAIL_GET = USER_PATH + GET_PATH + EMAIL_PATH;
    public static final String USER_USERNAME_GET = USER_PATH + USER_NAME+GET_PATH + USERNAME_PATH;
    public static final String USER_PUT = USER_PATH + PUT_PATH;

    public static final String USER_DELETE = USER_PATH + DELETE_PATH + EMAIL_PATH;


}
