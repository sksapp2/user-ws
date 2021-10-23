package com.infc.ms.user.config.common;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
@Configuration
public class MessageConfig {
	@Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
       messageSource.setBasenames("classpath:messages");
       messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

  @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(MessageSource messageSource) {
        LocalValidatorFactoryBean localValidatorFactoryBean = new LocalValidatorFactoryBean();
        localValidatorFactoryBean.setValidationMessageSource(messageSource);
        return localValidatorFactoryBean;
    }


}
