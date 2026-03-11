package com.example.userspring.globals.users;

import static com.example.userspring.globals.URLMappings.*;

public class UrlMappingUserRole {
    public static final String USER_ROLE_PATH = "/user_role";
    private static final String USERNAME = "/username";
    private static final String ROLE = "/{role}";

    public static final String USER_ROLE_POST = USER_ROLE_PATH + POST_PATH;

    public static final String USER_ROLE_GET = USER_ROLE_PATH + GET_PATH;

    public static final String USER_ROLE_EMAIL_GET = USER_ROLE_PATH + GET_PATH + EMAIL_PATH;
    public static final String USER_ROLE_USERNAME_GET = USER_ROLE_PATH +USERNAME+ GET_PATH + USERNAME_PATH;

    public static final String USER_ROLE_PUT = USER_ROLE_PATH + PUT_PATH;

    public static final String USER_ROLE_DELETE = USER_ROLE_PATH + DELETE_PATH + EMAIL_PATH;

    public static final String USER_GET_ALL_BY_ROLE = USER_ROLE_PATH + GET_PATH + ALL_PATH + ROLE;


}
