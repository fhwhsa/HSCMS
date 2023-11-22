package com.fws.mvc.daoArc;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.fws.mvc.dao.DAO;

public class CommonDaoArc<T> implements DAO<T> {

	private QueryRunner querRunner;
	private Class<T> type;
	
	@SuppressWarnings("unchecked")
	public CommonDaoArc() {
		querRunner  = new QueryRunner();
		type = getSuperClassGenricType(getClass(), 0);
	}

	@Override
	public T fetch(Connection connection, String sql, Object[] params) throws SQLException {
		BeanHandler<T> bh = new BeanHandler<T>(type);
		return querRunner.query(connection, sql, bh, params);
	}

	@Override
	public List<T> fetchList(Connection connection, String sql, Object[] params) throws SQLException {
		BeanListHandler<T> blh = new BeanListHandler<T>(type);
		return querRunner.query(connection, sql, blh, params);
	}

	@Override
	public void update(Connection connection, String sql, Object[] params) throws SQLException {
		querRunner.update(connection, sql, params);
	}

	@Override
	public <E> E fetchScaler(Connection connection, String sql, Object[] params) throws SQLException {
		ScalarHandler<E> sh = new ScalarHandler<E>();
		return querRunner.query(connection, sql, sh, params);
	}
	
    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public BookManager extends GenricManager<Book>
     * 
     * @param clazz clazz The class to introspect
     * @param index the Index of the generic ddeclaration,start from 0.
     */
    @SuppressWarnings("rawtypes")
	public static Class getSuperClassGenricType(Class clazz, int index)
            throws IndexOutOfBoundsException {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }
}
