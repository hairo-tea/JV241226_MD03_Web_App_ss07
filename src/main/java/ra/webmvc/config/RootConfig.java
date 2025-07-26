package ra.webmvc.config;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@EnableTransactionManagement
public class gitRootConfig {
  @Bean
  public DataSource dataSource() {
    DriverManagerDataSource dataSource = new DriverManagerDataSource();
    dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
    dataSource.setUrl("jdbc:mysql://localhost:3306/session07db?createDatabaseIfNotExist=true");
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
    return properties;
  }

  @Bean
  public SessionFactory sessionFactory() {
    LocalSessionFactoryBuilder sessionFactoryBuilder = new LocalSessionFactoryBuilder(dataSource());
    sessionFactoryBuilder.scanPackages("ra.webmvc.entity");
    sessionFactoryBuilder.addProperties(hibernateProperties());
    return sessionFactoryBuilder.buildSessionFactory();
  }
  @Bean
  public HibernateTransactionManager transactionManager() {
    HibernateTransactionManager tstManager = new HibernateTransactionManager();
    tstManager.setSessionFactory(sessionFactory());
    return tstManager;
  }
}
