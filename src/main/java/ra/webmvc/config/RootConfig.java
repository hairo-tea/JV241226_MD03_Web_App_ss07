package ra.webmvc.config;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class RootConfig {
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/session07db");
    dataSource.setUsername("root");
    dataSource.setPassword("123456@");
    return dataSource;
  }
  private Properties hibernateProperties() {
    Properties properties = new Properties();
    properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL8Dialect");
    properties.put("hibernate.hbm2ddl.auto", "update");
    properties.put("hibernate.show_sql", "true");
    properties.put("hibernate.format_sql", "true");
    properties.setProperty("hibernate.dialect.storage_engine", "innodb");
    return properties;
  }

//  @Bean
//  public SessionFactory sessionFactory() {
//    LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
//    sessionFactoryBuilder.scanPackages("ra.webmvc.entity");
//    sessionFactoryBuilder.addProperties(hibernateProperties());
//    return sessionFactoryBuilder.buildSessionFactory();
////  }
//  @Bean
//  public HibernateTransactionManager transactionManager() {
//    HibernateTransactionManager tstManager = new HibernateTransactionManager();
//    tstManager.setSessionFactory(sessionFactory());
//    return tstManager;
//  }
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
    factoryBean.setDataSource(dataSource());
    factoryBean.setPackagesToScan("ra.webmvc.entity");
    factoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
    factoryBean.setJpaProperties(hibernateProperties());
    return factoryBean;
  }
  @Bean
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }
}
