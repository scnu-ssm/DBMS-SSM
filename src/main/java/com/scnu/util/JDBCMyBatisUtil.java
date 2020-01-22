package com.scnu.util;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.ibatis.builder.BaseBuilder;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.datasource.DataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.chenrong.bean.ConnectInfo;
import com.chenrong.dao.ConnectInfoMapper;

@Component
public class JDBCMyBatisUtil {
	
	@Autowired
	public ConnectInfoMapper connectInfoMapper;
	
	static {
		System.out.println("JDBCMyBatisUtil 被加载了");
	}
	
	public final static String type = "pooled";
	
	public final static String DriverMySQL = "com.mysql.cj.jdbc.Driver";
	
	public final static String MyBatisConfigPath = "mybatis-scnu.xml";
	
	public final static String prefix = "jdbc:mysql://";
	
	public final static String suffix = "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
	
	// 该类用于获取对应连接的SqlSessionFactory
	class SCNUSqlSessionFactoryBuilder extends BaseBuilder{

		public SCNUSqlSessionFactoryBuilder(Configuration configuration) {
			super(configuration);
		}
		
		// 核心，根据connectInfo获取相应的SqlSessionFactory
		public SqlSessionFactory getSqlSessionFactory(ConnectInfo connectInfo) throws Exception {
			  InputStream  inputStream = Resources.getResourceAsStream(MyBatisConfigPath);
	          XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, null, null);
	          // 解析 mybatis-scnu.xml 配置文件获取 Configuration， 用于创建sqlSessionFactory
	          Configuration config = parser.parse();
	          // 根据自己的连接信息创建对应的dataSource， 修改 Config 配置类的数据源内容
	          DataSourceFactory factory = (DataSourceFactory) resolveClass(type).newInstance();
	          Properties props = new Properties();
	          props.put("driver", DriverMySQL);
	          props.put("url", prefix + connectInfo.getHost() + ":" + connectInfo.getPort() + suffix);
	          System.out.println("URL: " + prefix + connectInfo.getHost() + ":" + connectInfo.getPort() + suffix);
	          props.put("username", connectInfo.getUsername());
	          props.put("password", connectInfo.getPassword());
	          factory.setProperties(props);
	          DataSource dataSource = factory.getDataSource();
	          Environment environment = config.getEnvironment();
	          String id = environment.getId();
	          TransactionFactory transactionFactory = environment.getTransactionFactory();
	          config.setEnvironment(new Environment(id, transactionFactory, dataSource));
			  return new SqlSessionFactoryBuilder().build(config);
		}
		
	}
	
	// 根据connectId获取 sqlSessionFactory
	public SqlSessionFactory getSqlSessionFactory(String connectId) throws Exception{
		ConnectInfo connectInfo = connectInfoMapper.selectByConnectId(connectId);
		SCNUSqlSessionFactoryBuilder sqlSessionFactoryBuilder = this.new SCNUSqlSessionFactoryBuilder(new Configuration());
		return sqlSessionFactoryBuilder.getSqlSessionFactory(connectInfo);
	}

}
