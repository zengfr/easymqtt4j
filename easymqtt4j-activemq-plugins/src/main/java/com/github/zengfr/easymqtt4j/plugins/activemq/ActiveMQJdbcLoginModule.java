package com.github.zengfr.easymqtt4j.plugins.activemq;


import com.github.zengfr.easymqtt4j.plugins.jaas.JaasAbstractJdbcLoginModule;
import org.apache.activemq.jaas.GroupPrincipal;
import org.apache.activemq.jaas.UserPrincipal;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

/**
 * Created by zengfr on 2020/6/5.
 */
public class ActiveMQJdbcLoginModule extends JaasAbstractJdbcLoginModule {


    public ActiveMQJdbcLoginModule() {
        super();
    }


    @Override
    protected boolean commitPrincipals(Subject subject, Context context) {
        String userName = context.userName;
        Set<Principal> principals = subject.getPrincipals();
        UserPrincipal userPrincipal = new UserPrincipal(userName);
        principals.add(userPrincipal);

        Set<String> groups = getUserGroups(userName);
        log.debug(String.format("groups:%s,%s", userName, groups));
        if (groups != null) {
            context.groups = groups;
            for (String entry : context.groups) {
                principals.add(new GroupPrincipal(entry));
            }
        }
        return true;
    }


}
