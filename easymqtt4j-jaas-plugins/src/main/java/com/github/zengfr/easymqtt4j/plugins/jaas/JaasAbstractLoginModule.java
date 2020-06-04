package com.github.zengfr.easymqtt4j.plugins.jaas;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.Subject;
import javax.security.auth.callback.*;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * Created by zengfr on 2020/6/5.
 */
public abstract class JaasAbstractLoginModule implements LoginModule {

    public class Context {
        public boolean loginSuccess = false;
        public String userName;
        public Set<String> roles = new HashSet<>();
        public Set<String> groups = new HashSet<>();
    }

    protected static final Logger log = LoggerFactory.getLogger(JaasAbstractLoginModule.class);
    private static int userNameMinLength = 6;

    private static int userMaxTimes = 3;
    private static Map<String, Long> times = new HashMap<>();

    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;
    private Subject subject;
    private boolean debug;
    private Context context;

    public JaasAbstractLoginModule() {
        context = new Context();
    }

    protected abstract void init(boolean debug, Map<String, ?> options);

    protected abstract String getUserPassword(String userName);

    protected abstract Set<String> getUserRoles(String userName);

    protected abstract Set<String> getUserGroups(String userName);

    protected abstract boolean commitPrincipals(Subject subject, Context context);

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {

        this.callbackHandler = callbackHandler;
        this.subject = subject;
        this.sharedState = sharedState;
        this.options = options;
        if (options.containsKey("debug")) {
            debug = "true".equalsIgnoreCase((String) options.get("debug"));
        }
        this.init(debug, this.options);
    }


    @Override
    public boolean login() throws LoginException {
        boolean result = false;
        Context c = context;
        c.loginSuccess = result;

        Callback[] callbacks = new Callback[]{new NameCallback("username:"), new PasswordCallback("password:", false)};
        try {
            this.callbackHandler.handle(callbacks);
            String userName = ((NameCallback) callbacks[0]).getName();
            char[] password = ((PasswordCallback) callbacks[1]).getPassword();

            c.userName = userName;
            if (this.validateUserName(userName) && password != null && password.length > 0) {
                if (this.checkTimesOK(userName)) {
                    String password2 = getUserPassword(userName);
                    result = this.validatePassword(password, password2);
                }
            }
            log.debug(String.format("login:%s,%s", userName, result));
        } catch (IOException ex1) {
            throw new LoginException(ex1.getMessage());
        } catch (UnsupportedCallbackException ex2) {
            throw new LoginException(ex2.getMessage());
        }
        c.loginSuccess = result;
        return result;
    }


    @Override
    public boolean commit() throws LoginException {
        Context c = context;
        boolean result = c.loginSuccess;
        try {
            if (result) {
                String userName = c.userName;
                if (this.validateUserName(userName)) {
                    this.commitPrincipals(subject, c);
                }
            }
        } catch (Exception ex) {
            log.error("", ex);
        }
        return true;
    }

    @Override
    public boolean abort() throws LoginException {
        this.logout();
        return true;
    }

    @Override
    public boolean logout() throws LoginException {
        Context c = context;
        c.loginSuccess = false;
        subject.getPrincipals().clear();
        times.put(c.userName, 0L);
        return true;
    }

    private static boolean checkTimesOK(String userName) {
        Long time = times.getOrDefault(userName, 0L);
        times.put(userName, ++time);
        return time <= userMaxTimes;
    }

    protected static boolean validateUserName(String userName) {

        return userName != null && userName.length() >= userNameMinLength;
    }

    private static boolean validatePassword(char[] password, String password2) {

        return password != null && password2 != null && password2.equalsIgnoreCase(new String(password));
    }

    protected static void loadProp(Properties prop, String fileName) {
        try {
            FileInputStream fis = new FileInputStream(fileName);
            prop.load(fis);
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    protected static void storeProp(Properties prop, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            prop.store(fos, JaasAbstractLoginModule.class.getName());
        } catch (FileNotFoundException e) {
            log.error("", e);
        } catch (IOException e) {
            log.error("", e);
        }
    }
}
