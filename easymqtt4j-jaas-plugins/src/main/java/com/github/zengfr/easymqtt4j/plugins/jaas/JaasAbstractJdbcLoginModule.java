package com.github.zengfr.easymqtt4j.plugins.jaas;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.*;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;

/**
 * Created by zengfr on 2020/6/5.
 */
public abstract class JaasAbstractJdbcLoginModule extends JaasAbstractLoginModule {

    private static String userSql = "select password from User u where u.name=? and enabled=1";
    private static String userRoleSql = "select name from UserRole r where r.username=? and enabled=1";
    private static String userGroupSql = "select name from UserGroup g where g.username=? and enabled=1";
    private static String jdbcUrl = "jdbc:mysql://127.0.0.1:3306/db?user=me&password=1234567";
    private static String propFileName = "jdbc.properties";

    private Properties prop;

    public JaasAbstractJdbcLoginModule() {
        super();
        this.prop = new Properties();
    }

    @Override
    protected void init(boolean debug, Map<String, ?> options) {
        if (Files.exists(Paths.get(propFileName))) {
            loadProp(prop, propFileName);
            jdbcUrl = prop.getProperty("jdbc.url", jdbcUrl);
            userSql = prop.getProperty("user.sql", userSql);
            userRoleSql = prop.getProperty("userrole.sql", userRoleSql);
            userGroupSql = prop.getProperty("usergroup.sql", userGroupSql);
        } else {
            prop.setProperty("module.class", this.getClass().getName());
            prop.setProperty("jdbc.url", jdbcUrl);
            prop.setProperty("user.sql", userSql);
            prop.setProperty("userrole.sql", userRoleSql);
            prop.setProperty("usergroup.sql", userGroupSql);
            storeProp(prop, propFileName);
        }
    }

    @Override
    protected String getUserPassword(String userName) {
        AtomicReference<String> password = new AtomicReference<>("");
        Consumer<ResultSet> rsFunc = (rs) -> {
            try {
                password.set(rs.getString(1));
            } catch (SQLException e) {
                log.error("", e);
            }
        };
        query(jdbcUrl, userSql, userName, rsFunc);
        return password.get();
    }

    @Override
    protected Set<String> getUserRoles(String userName) {
        Set<String> roles = new HashSet<>();
        Consumer<ResultSet> rsFunc = (rs) -> {
            String role = null;
            try {
                role = rs.getString(1);
                roles.add(role);
            } catch (SQLException e) {
                log.error("", e);
            }

        };

        query(jdbcUrl, userRoleSql, userName, rsFunc);
        return roles;
    }

    @Override
    protected Set<String> getUserGroups(String userName) {
        Set<String> roles = new HashSet<>();
        Consumer<ResultSet> rsFunc = (rs) -> {
            String role = null;
            try {
                role = rs.getString(1);
                roles.add(role);
            } catch (SQLException e) {
                log.error("", e);
            }

        };

        query(jdbcUrl, userGroupSql, userName, rsFunc);
        return roles;
    }

    protected static void query(String jdbcUrl, String sql, String userName, Consumer<ResultSet> rsFunc) {
        try (Connection conn = DriverManager.getConnection(jdbcUrl)) {
            try (PreparedStatement st = conn.prepareStatement(sql)) {
                st.setString(1, userName);
                try (ResultSet rs = st.executeQuery()) {
                    while (rs.next()) {
                        rsFunc.accept(rs);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("", e);
        }
    }

}
