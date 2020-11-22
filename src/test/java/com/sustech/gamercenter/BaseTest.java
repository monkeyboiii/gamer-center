//package com.sustech.gamercenter;
//
//import com.zaxxer.hikari.HikariDataSource;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
//import org.springframework.boot.test.context.ConfigFileApplicationContextInitializer;
//import org.springframework.boot.test.context.TestConfiguration;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.env.Environment;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.JpaVendorAdapter;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.naming.NamingException;
//import javax.persistence.EntityManagerFactory;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@RunWith(SpringRunner.class)
//@ContextConfiguration(initializers = ConfigFileApplicationContextInitializer.class)
//public class BaseTest {
//
//    @TestConfiguration
//    @TestPropertySource(locations = "classpath:application.yml")
//    @ActiveProfiles("local")
//    public static class TestContextConfiguration {
//
//        @Autowired
//        private Environment environment;
//
//        @Value("${datasource.sampleapp.maxPoolSize:10}")
//        private int maxPoolSize;
//
//        /*
//         * 将 application.yml 的配置填充到 SpringBoot 中的 DataSourceProperties 对象里面
//         */
//        @Bean
//        @Primary
//        public DataSourceProperties dataSourceProperties() {
//            DataSourceProperties dataSourceProperties = new DataSourceProperties();
//            dataSourceProperties.setDriverClassName(environment.getRequiredProperty("datasource.sampleapp.driverClassName"));
//            dataSourceProperties.setUrl(environment.getRequiredProperty("datasource.sampleapp.url"));
//            dataSourceProperties.setUsername(environment.getRequiredProperty("datasource.sampleapp.username"));
//            dataSourceProperties.setPassword(environment.getRequiredProperty("datasource.sampleapp.password"));
//            return dataSourceProperties;
//        }
//
//        /*
//         * 配置 HikariCP 连接池数据源.
//         */
//        @Bean
//        public DataSource dataSource() {
//            DataSourceProperties dataSourceProperties = dataSourceProperties();
//            HikariDataSource dataSource = (HikariDataSource) DataSourceBuilder
//                    .create(dataSourceProperties.getClassLoader())
//                    .driverClassName(dataSourceProperties.getDriverClassName())
//                    .url(dataSourceProperties.getUrl())
//                    .username(dataSourceProperties.getUsername())
//                    .password(dataSourceProperties.getPassword())
//                    .type(HikariDataSource.class)
//                    .build();
//            dataSource.setMaximumPoolSize(maxPoolSize);
//            return dataSource;
//        }
//
//        /*
//         * Entity Manager Factory 配置.
//         */
//        @Bean
//        public LocalContainerEntityManagerFactoryBean entityManagerFactory() throws NamingException {
//            LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
//            factoryBean.setDataSource(dataSource());
//            factoryBean.setPackagesToScan(new String[]{"com.ckjava.test.domain"});
//            factoryBean.setJpaVendorAdapter(jpaVendorAdapter());
//            factoryBean.setJpaProperties(jpaProperties());
//            return factoryBean;
//        }
//
//        /**
//         * 指定 hibernate 为 jpa 的持久化框架
//         *
//         * @return
//         */
//        @Bean
//        public JpaVendorAdapter jpaVendorAdapter() {
//            HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
//            return hibernateJpaVendorAdapter;
//        }
//
//        /*
//         * 从 application.yml 中读取 hibernate 相关配置
//         */
//        private Properties jpaProperties() {
//            Properties properties = new Properties();
//            properties.put("hibernate.dialect", environment.getRequiredProperty("datasource.sampleapp.hibernate.dialect"));
//            properties.put("hibernate.hbm2ddl.auto", environment.getRequiredProperty("datasource.sampleapp.hibernate.hbm2ddl.auto"));
//            properties.put("hibernate.show_sql", environment.getRequiredProperty("datasource.sampleapp.hibernate.show_sql"));
//            properties.put("hibernate.format_sql", environment.getRequiredProperty("datasource.sampleapp.hibernate.format_sql"));
//            if (StringUtils.isNotEmpty(environment.getRequiredProperty("datasource.sampleapp.defaultSchema"))) {
//                properties.put("hibernate.default_schema", environment.getRequiredProperty("datasource.sampleapp.defaultSchema"));
//            }
//            return properties;
//        }
//
//        @Bean
//        public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
//            JpaTransactionManager txManager = new JpaTransactionManager();
//            txManager.setEntityManagerFactory(entityManagerFactory);
//            return txManager;
//        }
//
//        @Bean
//        public TaskService taskService() {
//            return new TaskService();
//        }
//
//        @Bean
//        public TaskController taskController() {
//            return new TaskController();
//        }
//
//        @Bean
//        public MockMvc mockMvc() {
//            Object[] contorllers = {taskController()};
//            return MockMvcBuilders.standaloneSetup(contorllers).build();
//        }
//    }
//
//}