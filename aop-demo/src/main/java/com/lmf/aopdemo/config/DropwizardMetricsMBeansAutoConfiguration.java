package com.lmf.aopdemo.config;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.aop.AopAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.management.MBeanServer;

@Configuration
@ComponentScan({"com.lmf.aopdemo.aop", "com.lmf.aopdemo.lifecycle"})
@AutoConfigureAfter(AopAutoConfiguration.class)
public class DropwizardMetricsMBeansAutoConfiguration {
    @Value("${metrics.mbeans.domain.name:com.keevol.metrics}")
    String metricsMBeansDomainName;
    @Autowired
    MBeanServer mbeanServer;
    @Autowired
    MetricRegistry metricRegistry;

    @Bean
    public JmxReporter jmxReporter() {
        JmxReporter reporter = JmxReporter.forRegistry(metricRegistry).inDomain(metricsMBeansDomainName).registerWith(mbeanServer).build();
        return reporter;
    }
}
