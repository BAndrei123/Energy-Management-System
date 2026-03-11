package com.example.userspring.globals.users;


import static com.example.userspring.globals.URLMappings.*;

public class UrlMappingCredentials {

  public static final String CREDENTIALS_PATH = "/credentials";

  public static final String CREDENTIALS_POST = CREDENTIALS_PATH + POST_PATH;

  public static final String CREDENTIALS_GET = CREDENTIALS_PATH + GET_PATH;

  public static final String CREDENTIALS_EMAIL_GET = CREDENTIALS_PATH + GET_PATH + EMAIL_PATH;

  public static final String CREDENTIALS_PUT = CREDENTIALS_PATH + PUT_PATH;

  public static final String CREDENTIALS_DELETE = CREDENTIALS_PATH + DELETE_PATH + EMAIL_PATH;

  public static final String CREDENTIALS_EMAIL_GET_ALL = CREDENTIALS_PATH + GET_PATH + ALL_PATH;
}
