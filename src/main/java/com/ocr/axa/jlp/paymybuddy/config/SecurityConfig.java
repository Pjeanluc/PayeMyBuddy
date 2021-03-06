package com.ocr.axa.jlp.paymybuddy.config;

import java.util.ResourceBundle;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final Logger logger = LogManager.getLogger(SecurityConfig.class);
    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests().anyRequest().authenticated().and().httpBasic();
//        http
//        .requiresChannel()
//        .anyRequest()
//        .requiresSecure();
        
//        http.authorizeRequests().antMatchers("/").permitAll().and()
//        .authorizeRequests().antMatchers("/console/**").permitAll();
//        
//        http.csrf().disable();
//        http.headers().frameOptions().disable();
//        http.headers().frameOptions().sameOrigin();
    }
    
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        logger.info("security start");
        ResourceBundle bundle = ResourceBundle.getBundle("application");
        String myUser = bundle.getString("application.security.user.name");
        String myPwd = bundle.getString("application.security.user.password");
        myPwd = "{noop}" +myPwd;
        logger.info( myUser + "  -  " +myPwd);
     auth.inMemoryAuthentication()
                .withUser(myUser)
                .password(myPwd)
                .roles("USER");
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
    
    @Bean
    public HttpTraceRepository htttpTraceRepository()
    {
        return new InMemoryHttpTraceRepository();
    }

  
//    public class WebConfiguration {
//        @Bean
//        ServletRegistrationBean h2servletRegistration(){
//            ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet());
//            registrationBean.addUrlMappings("/console/*");
//            return registrationBean;
//        }
//    }

}
