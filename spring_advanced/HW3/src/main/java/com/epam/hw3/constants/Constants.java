package com.epam.hw3.constants;

/**
 * Created by Andrii_Pinchuk on 11/27/2015.
 */
public class Constants {
    public static final String EVENTS_TABLE_NAME = "events";
    public static final String TICKETS_TABLE_NAME = "tickets";
    public static final String USER_ACCOUNTS_TABLE_NAME = "user_accounts";
    public static final String USERS_TABLE_NAME = "users";
    public static final String PRIMARY_KEY = "Primary_key";

    /*events*/
    public static final String EVENTS_COLUMN_ID = "id";
    public static final String EVENTS_COLUMN_TITLE = "title";
    public static final String EVENTS_COLUMN_DATE = "date";
    public static final String EVENTS_COLUMN_TICKET_PRICE = "ticket_price";

    /*tickets*/
    public static final String TICKETS_COLUMN_ID = "id";
    public static final String TICKETS_COLUMN_PLACE = "place";
    public static final String TICKETS_COLUMN_EVENT_ID = "event_id";
    public static final String TICKETS_COLUMN_USER_ID = "user_id";
    public static final String TICKETS_COLUMN_CATEGORY_ID = "category_id";

    /*user_accounts*/
    public static final String USER_ACCOUNTS_COLUMN_ID = "id";
    public static final String USER_ACCOUNTS_COLUMN_MONEY = "money";
    public static final String USER_ACCOUNTS_COLUMN_USER_ID = "user_id";

    /*users*/
    public static final String USERS_COLUMN_ID = "id";
    public static final String USERS_COLUMN_NAME = "name";
    public static final String USERS_COLUMN_EMAIL = "email";

    /*view name's*/
    public static final String EVENT = "event";
    public static final String USER = "user";
    public static final String TICKET = "ticket";
    public static final String USER_ACCOUNT = "userAccount";


    public static final String EVENTS = "events";
    public static final String USERS = "users";
    public static final String TICKETS = "tickets";
    public static final String ACCOUNT = "account";

    private Constants() {
    }
}
