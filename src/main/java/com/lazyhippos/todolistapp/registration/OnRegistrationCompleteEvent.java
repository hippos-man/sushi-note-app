package com.lazyhippos.todolistapp.registration;

import com.lazyhippos.todolistapp.domain.model.Users;
import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final Users users;

    public OnRegistrationCompleteEvent (final Users users, final Locale locale, final String appUrl) {
        super(users);
        this.users = users;
        this.locale = locale;
        this.appUrl = appUrl;
    }

    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public Users getUsers() {
        return users;
    }
}
