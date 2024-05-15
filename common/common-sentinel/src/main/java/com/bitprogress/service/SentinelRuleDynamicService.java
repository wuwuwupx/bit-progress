package com.bitprogress.service;

import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.bitprogress.property.RuleProperties;
import com.bitprogress.property.SentinelRuleProperties;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import static com.bitprogress.util.CollectionUtils.emptyList;
import static com.bitprogress.util.StringUtils.isEmpty;

/**
 * @author wuwuwupx
 * sentinel 规则动态监听服务
 */
@Service
public class SentinelRuleDynamicService {

    private static final Logger log = LoggerFactory.getLogger(SentinelRuleDynamicService.class);

    @Autowired
    private SentinelRuleProperties sentinelRuleProperties;

    @Autowired
    private ConfigService configService;

    /**
     * 监听流控规则文件
     */
    @PostConstruct
    public void flowRuleListener() {
        RuleProperties flow = sentinelRuleProperties.getFlow();
        // 没有配置对应的配置文件
        if (Objects.isNull(flow)) {
            return;
        }
        String dataId = flow.getDataId();
        String group = flow.getGroup();

        try {
            // 初始化流控规则
            String configInfo = configService.getConfig(dataId, group, 5000);
            updateFlowRule(configInfo);

            // 监听流控规则
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateFlowRule(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("nacos fetchFlowRuleConfigError", e);
        }
    }

    /**
     * 监听熔断规则文件
     */
    @PostConstruct
    public void degradeRuleListener() {
        RuleProperties degrade = sentinelRuleProperties.getDegrade();
        // 没有配置对应的配置文件
        if (Objects.isNull(degrade)) {
            return;
        }
        String dataId = degrade.getDataId();
        String group = degrade.getGroup();

        try {
            // 初始化熔断规则
            String configInfo = configService.getConfig(dataId, group, 5000);
            updateDegradeRule(configInfo);

            // 监听熔断规则
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateDegradeRule(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("nacos fetchDegradeRuleConfigError", e);
        }
    }

    /**
     * 监听热点规则文件
     */
    @PostConstruct
    public void paramFlowRuleListener() {
        RuleProperties paramFlow = sentinelRuleProperties.getParamFlow();
        // 没有配置对应的配置文件
        if (Objects.isNull(paramFlow)) {
            return;
        }
        String dataId = paramFlow.getDataId();
        String group = paramFlow.getGroup();

        try {
            // 初始化热点规则
            String configInfo = configService.getConfig(dataId, group, 5000);
            updateParamFlowRule(configInfo);

            // 监听热点规则
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateParamFlowRule(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("nacos fetchSystemRuleConfigError", e);
        }
    }

    /**
     * 监听系统规则文件
     */
    @PostConstruct
    public void systemRuleListener() {
        RuleProperties system = sentinelRuleProperties.getSystem();
        // 没有配置对应的配置文件
        if (Objects.isNull(system)) {
            return;
        }
        String dataId = system.getDataId();
        String group = system.getGroup();

        try {
            // 初始化系统规则
            String configInfo = configService.getConfig(dataId, group, 5000);
            updateSystemRule(configInfo);

            // 监听系统规则
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateSystemRule(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("nacos fetchSystemRuleConfigError", e);
        }
    }

    /**
     * 监听授权规则文件
     */
    @PostConstruct
    public void authorityRuleListener() {
        RuleProperties authority = sentinelRuleProperties.getAuthority();
        // 没有配置对应的配置文件
        if (Objects.isNull(authority)) {
            return;
        }
        String dataId = authority.getDataId();
        String group = authority.getGroup();

        try {
            // 初始化授权规则
            String configInfo = configService.getConfig(dataId, group, 5000);
            updateAuthorityRule(configInfo);

            // 监听授权规则
            configService.addListener(dataId, group, new Listener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    updateAuthorityRule(configInfo);
                }

                @Override
                public Executor getExecutor() {
                    return null;
                }
            });
        } catch (NacosException e) {
            log.error("nacos fetchAuthorityRuleConfigError", e);
        }
    }

    /**
     * 更新流控规则
     *
     * @param configInfo 流控规则信息
     */
    public void updateFlowRule(String configInfo) {
        List<FlowRule> rules = isEmpty(configInfo) ? emptyList() : JSON.parseArray(configInfo, FlowRule.class);
        FlowRuleManager.loadRules(rules);
    }

    /**
     * 更新熔断规则
     *
     * @param configInfo 熔断规则信息
     */
    public void updateDegradeRule(String configInfo) {
        List<DegradeRule> rules = isEmpty(configInfo) ? emptyList() : JSON.parseArray(configInfo, DegradeRule.class);
        DegradeRuleManager.loadRules(rules);
    }

    /**
     * 更新热点规则
     *
     * @param configInfo 热点规则信息
     */
    public void updateParamFlowRule(String configInfo) {
        List<ParamFlowRule> rules = isEmpty(configInfo) ? emptyList() : JSON.parseArray(configInfo, ParamFlowRule.class);
        ParamFlowRuleManager.loadRules(rules);
    }

    /**
     * 更新系统规则
     *
     * @param configInfo 系统规则信息
     */
    public void updateSystemRule(String configInfo) {
        List<SystemRule> rules = isEmpty(configInfo) ? emptyList() : JSON.parseArray(configInfo, SystemRule.class);
        SystemRuleManager.loadRules(rules);
    }

    /**
     * 更新授权规则
     *
     * @param configInfo 授权规则信息
     */
    public void updateAuthorityRule(String configInfo) {
        List<AuthorityRule> rules = isEmpty(configInfo) ? emptyList() : JSON.parseArray(configInfo, AuthorityRule.class);
        AuthorityRuleManager.loadRules(rules);
    }

}
