package com.chenrong;

import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

public class StudentTest {
private SqlSessionFactory factory;

    //作用:在测试方法前执行这个方法
    @Before
    public void FirstDo() throws Exception{
        //加载配置文件
        String resource = "sqlconfig.xml";
        //通过流将核心配置文件读取进来
        InputStream inputStream = Resources.getResourceAsStream(resource);
        //通过核心配置文件输入流来创建会话工厂
        factory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 根据id查询对象。
     */
    @Test
    public void findStuById(){//通过工厂创建会话
        SqlSession openSession = factory.openSession();
        //第一个参数:所调用的sql语句= namespace+.+sql的ID。
        //查询单个对象，使用selectOne方法即可
        Student student = openSession.selectOne("student.findStuById", 2);
        System.out.println(student);
        openSession.close();
    }

    /**
     * 通过name进行模糊查询，返回结果应该是一个list集合，所以使用SqlSession的selectList方法。
     */
    @Test
    public void findStuByName(){//通过工厂创建会话
        SqlSession openSession = factory.openSession();
        //如果返回结果为集合,调用selectList方法,这个方法返回的结果就是一个集合
        List<Student> list = openSession.selectList("student.findStuByName", "小");
        System.out.println(list);
        openSession.close();
    }

    /**
     * 插入数据，先创建表对应的对象，设置属性，然后调用insert方法。
     */
    @Test
    public void insertStu(){//通过工厂创建会话
        SqlSession openSession = factory.openSession();
        Student student=new Student();
        student.setAge(25);
        student.setSex("女");
        student.setTel("58938939");
        student.setName("貂蝉");

        openSession.insert("student.insertStu", student);
        //提交事务(mybatis会自动开启事务,但是它不知道何时提交,所以需要手动提交事务)
        openSession.commit();
        System.out.println("id值" + student.getId());
    }

    /**
     * 通过id删除数据
     */
    @Test
    public void delStuById(){
        SqlSession openSession = factory.openSession();
        //如果返回结果为集合,调用selectList方法,这个方法返回的结果就是一个集合
        openSession.delete("student.delStuById", 5);
        //提交事务
        openSession.commit();
        openSession.close();
    }

    /**
     * 更新数据，需要新创建一个对象。
     * 如果where条件是id，那么这个对象就需要设置id属性。
     */
    @Test
    public void updateStuById(){
        SqlSession openSession = factory.openSession();
        //如果返回结果为集合,调用selectList方法,这个方法返回的结果就是一个集合
        Student student=new Student();
        student.setId(2);
        student.setName("刘备");
        openSession.delete("student.updateStuById",student);
        //提交事务
        openSession.commit();
        openSession.close();
    }

}