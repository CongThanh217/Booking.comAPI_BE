package com.hotel.api.dto;

public class ErrorCode {
    /**
     * General error code
     * */
    public static final String GENERAL_ERROR_REQUIRE_PARAMS = "ERROR-GENERAL-0000";
    public static final String GENERAL_ERROR_STORE_LOCKED = "ERROR-GENERAL-0001";
    public static final String GENERAL_ERROR_ACCOUNT_LOCKED = "ERROR-GENERAL-0002";
    public static final String GENERAL_ERROR_SHOP_LOCKED = "ERROR-GENERAL-0003";
    public static final String GENERAL_ERROR_STORE_NOT_FOUND = "ERROR-GENERAL-0004";
    public static final String GENERAL_ERROR_ACCOUNT_NOT_FOUND = "ERROR-GENERAL-0005";

    /**
     * Starting error code Account
     * */
    public static final String ACCOUNT_ERROR_UNKNOWN = "ERROR-ACCOUNT-0000";
    public static final String ACCOUNT_ERROR_USERNAME_EXIST = "ERROR-ACCOUNT-0001";
    public static final String ACCOUNT_ERROR_NOT_FOUND = "ERROR-ACCOUNT-0002";
    public static final String ACCOUNT_ERROR_WRONG_PASSWORD = "ERROR-ACCOUNT-0003";
    public static final String ACCOUNT_ERROR_WRONG_HASH_RESET_PASS = "ERROR-ACCOUNT-0004";
    public static final String ACCOUNT_ERROR_LOCKED = "ERROR-ACCOUNT-0005";
    public static final String ACCOUNT_ERROR_OPT_INVALID = "ERROR-ACCOUNT-0006";
    public static final String ACCOUNT_ERROR_LOGIN = "ERROR-ACCOUNT-0007";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_DEVICE = "ERROR-ACCOUNT-0008";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_ERROR_STORE = "ERROR-ACCOUNT-0009";
    public static final String ACCOUNT_ERROR_MERCHANT_LOGIN_WRONG_STORE = "ERROR-ACCOUNT-0010";
    public static final String ACCOUNT_ERROR_MERCHANT_SERVICE_NOT_REGISTER = "ERROR-ACCOUNT-0011";
    public static final String ACCOUNT_ERROR_NOT_ALLOW_DELETE_SUPPER_ADMIN = "ERROR-ACCOUNT-0012";
    public static final String ACCOUNT_ERROR_PHONE_EXIST = "ERROR-ACCOUNT-0013";
    public static final String ACCOUNT_ERROR_EMAIL_EXIST = "ERROR-ACCOUNT-00014";

    /**
     * Starting error code Customer
     * */
    public static final String CUSTOMER_ERROR_UNKNOWN = "ERROR-CUSTOMER-0000";
    public static final String CUSTOMER_ERROR_EXIST = "ERROR-CUSTOMER-0002";
    public static final String CUSTOMER_ERROR_UPDATE = "ERROR-CUSTOMER-0003";
    public static final String CUSTOMER_ERROR_NOT_FOUND = "ERROR-CUSTOMER-0004";


    /**
     * Starting error code SHOP ACCOUNT
     * */
    public static final String SHOP_ACCOUNT_ERROR_UNKNOWN = "ERROR-SHOP_ACCOUNT-0000";
    /**
     * Starting error code NATION
     * */
    public static final String NATION_ERROR_NOT_FOUND = "ERROR-NATION-0001";
    public static final String NATION_ERROR_NOT_ALLOW_HAVE_PARENT = "ERROR-NATION-0002";
    public static final String NATION_ERROR_PARENT_INVALID = "ERROR-NATION-0003";
    public static final String NATION_ERROR_NOT_ALLOW_UPDATE_KIND = "ERROR-NATION-0004";
    public static final String NATION_ERROR_CANT_DELETE_RELATIONSHIP_WITH_ADDRESS = "ERROR-NATION-0005";
    /**
     * Starting error code ADDRESS
     * */
    public static final String ADDRESS_ERROR_NOT_FOUND = "ERROR-ADDRESS-0001";

    /**
     * Starting error code CATEGORY
     * */
    public static final String CATEGORY_ERROR_NOT_FOUND = "ERROR-CATEGORY-0000";
    public static final String CATEGORY_ERROR_EXIST = "ERROR-CATEGORY-0001";

    /**
     * Starting error code News
     * */
    public static final String NEWS_ERROR_NOT_FOUND = "ERROR-NEWS-0000";
    public static final String NEWS_ERROR_EXISTED = "ERROR-NEWS-0001";
    /**
     * Starting error code Settings
     * */
    public static final String SETTINGS_ERROR_NOT_FOUND = "ERROR-SETTING-0000";
    public static final String SETTINGS_ERROR_SETTING_KEY_EXISTED = "ERROR-SETTING-0001";

    /**
     * Starting error code USER
     *
     */
    public static final String USER_ERROR_EXIST = "ERROR-USER-0000";
    public static final String USER_ERROR_NOT_FOUND = "ERROR-USER-0001";
    public static final String USER_ERROR_LOGIN_FAILED = "ERROR-USER-0002";
    public static final String USER_ERROR_WRONG_PASSWORD = "ERROR-USER-0003";
    /**
     * Starting error code DATABASE_ERROR
     *
     */
    public static final String  ERROR_DB_QUERY = "ERROR-DB-QUERY-0000";

    /**
     * Starting error code PRODUCT CATEGORY
     *
     */
    public static final String  PRODUCT_CATEGORY_ERROR_EXISTED = "ERROR-PRODUCT-CATEGORY-0000";

    /**
     *
     * starting error code HOTEL
     */
    public static final String HOTEL_ERROR_NOT_FOUND = "ERROR-HOTEL-0000";
    /**
     *
     * starting error code KIND OF ROOM
     */

    public static final String KIND_OF_ROOM_ERROR_NOT_FOUND = "ERROR-KIND-OF-ROOM-0000";

    /**
     *
     * starting error code IMAGES
     */

    public static final String IMAGE_ERROR_NOT_FOUND = "ERROR-IMAGE-0000";

    /**
     *
     * starting error code service kind of room
     */
    public static final String SERVICE_ERROR_NOT_FOUND = "ERROR-SERVICE-0000";
    public static final String SERVICE_ERROR_EXITED = "ERROR-SERVICE-0001";

    /**
     *
     * starting error code booking
     */
    public static final String BOOKING_ERROR_NOT_FOUND = "ERROR-BOOKING-0000";


}
