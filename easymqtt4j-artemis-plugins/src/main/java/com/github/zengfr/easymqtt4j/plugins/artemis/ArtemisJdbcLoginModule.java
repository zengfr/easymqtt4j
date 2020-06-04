package com.github.zengfr.easymqtt4j.plugins.artemis;

import com.github.zengfr.easymqtt4j.plugins.jaas.JaasAbstractJdbcLoginModule;
import org.apache.activemq.artemis.spi.core.security.jaas.RolePrincipal;
import org.apache.activemq.artemis.spi.core.security.jaas.UserPrincipal;

import javax.security.auth.Subject;
import java.security.Principal;
import java.util.Set;

/**
 * Created by zengfr on 2020/6/5.
 */
public class ArtemisJdbcLoginModule extends JaasAbstractJdbcLoginModule {


    public ArtemisJdbcLoginModule() {
        super();

    }

    @Override
    protected boolean commitPrincipals(Subject subject, Context context) {
        String userName = context.userName;
        Set<Principal> principals = subject.getPrincipals();
        UserPrincipal userPrincipal = new UserPrincipal(userName);
        principals.add(userPrincipal);

        Set<String> roles = getUserRoles(userName);
        log.debug(String.format("roles:%s,%s", userName, roles));
        if (roles != null) {
            context.roles = roles;
            for (String entry : context.roles) {
                principals.add(new RolePrincipal(entry));
            }
        }
        return true;
    }

}
