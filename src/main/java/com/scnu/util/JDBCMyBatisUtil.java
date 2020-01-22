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
		System.out.println("JDBCMyBatisUtil ��������");
	}
	
	public final static String type = "pooled";
	
	public final static String DriverMySQL = "com.mysql.cj.jdbc.Driver";
	
	public final static String MyBatisConfigPath = "mybatis-scnu.xml";
	
	public final static String prefix = "jdbc:mysql://";
	
	public final static String suffix = "?serverTimezone=UTC&useUnicode=true&characterEncoding=utf8&useSSL=false";
	
	// �������ڻ�ȡ��Ӧ���ӵ�SqlSessionFactory
	class SCNUSqlSessionFactoryBuilder extends BaseBuilder{

		public SCNUSqlSessionFactoryBuilder(Configuration configuration) {
			super(configuration);
		}
		
		// ���ģ�����connectInfo��ȡ��Ӧ��SqlSessionFactory
		public SqlSessionFactory getSqlSessionFactory(ConnectInfo connectInfo) throws Exception {
			  InputStream  inputStream = Resources.getResourceAsStream(MyBatisConfigPath);
	          XMLConfigBuilder parser = new XMLConfigBuilder(inputStream, null, null);
	          // ���� mybatis-scnu.xml �����ļ���ȡ Configuration�� ���ڴ���sqlSessionFactory
	          Configuration config = parser.parse();
	          // �����Լ���������Ϣ������Ӧ��dataSource�� �޸� Config �����������Դ����
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
	
	// ����connectId��ȡ sqlSessionFactory
	public SqlSessionFactory getSqlSessionFactory(String connectId) throws Exception{
		ConnectInfo connectInfo = connectInfoMapper.selectByConnectId(connectId);
		SCNUSqlSessionFactoryBuilder sqlSessionFactoryBuilder = this.new SCNUSqlSessionFactoryBuilder(new Configuration());
		return sqlSessionFactoryBuilder.getSqlSessionFactory(connectInfo);
	}

}
