package com.github.zengfr.easymqtt4j.plugins;

import org.apache.activemq.artemis.core.security.Role;
import org.apache.activemq.artemis.core.server.SecuritySettingPlugin;
import org.apache.activemq.artemis.core.settings.HierarchicalRepository;

import java.util.Map;
import java.util.Set;

/**
 * Created by zengfr on 2020/5/15.
 */
public class ArtemisSecuritySettingPlugin implements SecuritySettingPlugin {
    @Override
    public SecuritySettingPlugin init(Map<String, String> map) {
        return null;
    }

    @Override
    public SecuritySettingPlugin stop() {
        return null;
    }

    @Override
    public Map<String, Set<Role>> getSecurityRoles() {
        return null;
    }

    @Override
    public void setSecurityRepository(HierarchicalRepository<Set<Role>> hierarchicalRepository) {
       // hierarchicalRepository.
    }
}
