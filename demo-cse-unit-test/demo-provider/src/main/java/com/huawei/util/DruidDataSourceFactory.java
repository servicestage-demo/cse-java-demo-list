package com.huawei.util;

import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.datasource.DataSourceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

public class DruidDataSourceFactory implements DataSourceFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(DruidDataSourceFactory.class);

  private Properties props;

  @Override
  public DataSource getDataSource() {
    DruidDataSource dds = new DruidDataSource();
    dds.setDriverClassName(this.props.getProperty("driver"));
    dds.setUrl(this.props.getProperty("url"));
    //		dds.setUrl("jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=GMT%2B8");
    dds.setUsername(this.props.getProperty("username"));
    dds.setPassword(this.props.getProperty("password"));

    dds.setTestWhileIdle(true);
    dds.setValidationQuery("SELECT 1");

    try {
      LOGGER.info("Start initialization DruidDataSource.");
      dds.init();
    } catch (SQLException e) {
      LOGGER.error("Initialization DruidDataSource failed, error: {}", e);
      e.printStackTrace();
    }
    return dds;
  }

  @Override
  public void setProperties(Properties props) {
    this.props = props;
  }
}
