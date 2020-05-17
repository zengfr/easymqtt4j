package com.zengfr.easymqtt4j.plugins;

import org.apache.activemq.artemis.spi.core.security.jaas.PropertiesLoginModule;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

/**
 * Created by zengfr on 2020/5/15.
 */
public class ArtemisPropertiesLoginModule implements LoginModule {
    private Properties users;
    private CallbackHandler callbackHandler;
    private boolean loginSucceeded;
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        PropertiesLoginModule propertiesLoginModule;
        this.callbackHandler = callbackHandler;
        this.loginSucceeded=false;
    }

    @Override
    public boolean login() throws LoginException {
        Callback[] callbacks = new Callback[]{new NameCallback("Username: "), new PasswordCallback("Password: ", false)};

        try {
            this.callbackHandler.handle(callbacks);
        } catch (IOException var6) {
            throw new LoginException(var6.getMessage());
        } catch (UnsupportedCallbackException var7) {
            throw new LoginException(var7.getMessage() + " not available to obtain information from user");
        }

         String  userName = ((NameCallback)callbacks[0]).getName();
        char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
        if (userName == null) {
            throw new FailedLoginException("User is null");
        } else {
            String password = this.users.getProperty(userName);
            if (password == null) {

            }
            //loginSucceeded=password.equalsIgnoreCase(tmpPassword);
        }
        return loginSucceeded;
    }

    @Override
    public boolean commit() throws LoginException {
        return false;
    }

    @Override
    public boolean abort() throws LoginException {
        return false;
    }

    @Override
    public boolean logout() throws LoginException {
        return false;
    }
}
