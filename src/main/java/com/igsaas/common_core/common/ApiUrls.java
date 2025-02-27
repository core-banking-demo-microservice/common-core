package com.igsaas.common_core.common;

public class ApiUrls {
    public static final String BASE_URL = "api/v1/portal";
    public static class Account {
        public static final String CREATE_ACCOUNT = "/account/create";
        public static final String GET_ACCOUNT_BY_ACCOUNT_NUMBER = "/account/{accountNumber}";
        public static final String UPDATE_ACCOUNT_BY_ACCOUNT_NUMBER = "/account/{accountNumber}";
        public static final String DELETE_ACCOUNT = "/account/{accountNumber}";
    }
}
