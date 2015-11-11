package com.itacit.healthcare.data.network;

import com.itacit.healthcare.data.entries.AccessToken;
import com.itacit.healthcare.global.bus.RxBus;
import com.itacit.healthcare.global.errors.AuthError;

public final class AccessTokenHandler {
        private static AccessToken ACCESS_TOKEN;
        public static AccessToken getAccessToken() {
            if (ACCESS_TOKEN == null) {
                RxBus.getInstance().send(new AuthError());
            }

            return ACCESS_TOKEN;
        }
        
        public static void setAccessToken(AccessToken accessToken) {
            ACCESS_TOKEN = accessToken;
        }
    }