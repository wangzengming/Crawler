package xyz.jfjk.utils;


import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;
import tk.mybatis.mapper.entity.Config;
import tk.mybatis.mapper.mapperhelper.MapperHelper;

public class MybatisHelper {
    private static SqlSessionFactory sqlSessionFactoryLocal;

    static {
        try {
            sqlSessionFactoryLocal = new SqlSessionFactoryBuilder().build(Resources.getResourceAsReader("mybatis-config.xml"), "local");
            SqlSession sessionLocal = null;
            try {
                sessionLocal = sqlSessionFactoryLocal.openSession();
                MapperHelper mapperHelper = new MapperHelper();
                Config config = new Config();
                config.setEnableMethodAnnotation(true);
                config.setNotEmpty(true);
                mapperHelper.setConfig(config);
                mapperHelper.registerMapper(Mapper.class);
                mapperHelper.registerMapper(MySqlMapper.class);
                mapperHelper.processConfiguration(sessionLocal.getConfiguration());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (sessionLocal != null) {
                    sessionLocal.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static SqlSession getSqlSessionLocal() {
        return sqlSessionFactoryLocal.openSession();
    }
}
