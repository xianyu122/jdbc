package com.hfxt.dao;

import com.hfxt.jdbcutils.JdbcUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDaoImpl implements BaseDao{

    /**
     * 查询方法
     *
     * @param sql
     *            查询的sql语句
     * @param params
     *            传入的参数
     * @return 返回查找到的集合
     */
	 @Override
    public <T> List<T> searchDao(String sql, Class<T> c, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        List<T> list = new ArrayList<T>();
        try {
            conn = JdbcUtil.getConn();
            stmt = conn.prepareStatement(sql);
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            res = stmt.executeQuery();
            while (res.next()) {
                T obj = objectSet(res,c);
                list.add(obj);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(conn, res, stmt);
        }
        return list;
    }

    /**
     * 修改方法
     *
     * @param sql
     * @param params
     * @return 返回修改成功的行数
     */
	 @Override
    public int update(String sql, Object... params) {
        Connection conn = null;
        PreparedStatement stmt = null;
        int row = 0;
        try {
            conn = JdbcUtil.getConn();
            stmt = conn.prepareStatement(sql);
            if (params.length > 0) {
                for (int i = 0; i < params.length; i++) {
                    stmt.setObject(i + 1, params[i]);
                }
            }
            row = stmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(conn, null, stmt);
        }
		
        return row;
    }

    /**
     * 根据id查找
     *
     * @param sql
     * @param id
     * @return
     */
	 @Override
    public <T> T searchDaoByParam(String sql, Object param, Class<T> c) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        T obj = null;
        try {
            conn = JdbcUtil.getConn();
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            res = stmt.executeQuery();
            if (res.next()) {
                obj = objectSet(res,c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(conn, res, stmt);
        }
        return obj;
    }


    /**
     * 根据sql获取所有条数
     *
     * @param sql
     * @return
     */
	 @Override
    public int getAllCount(String sql,,Object... params){
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet res = null;
        int count = 0;
        try {
            conn = JdbcUtil.getConn();
            stmt = conn.prepareStatement(sql);
			if(params!=null&&params.length>0){
                for (int i=0;i<params.length;i++){
                    stmt.setObject(i+1,params[i]);
                }
            }
            res = stmt.executeQuery();
            if (res.next()) {
                count = res.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtil.closeAll(conn, res, stmt);
        }
        return count;
    }

    /**
     * 将数据封装到bean中
     *
     * @param res
     * @return
     */
	 @Override
    public <T> T objectSet(ResultSet res, Class<T> c)  {
        Field[] field = c.getDeclaredFields();                //通过反射获取所有属性
        T obj = null;           
        try {
            obj = c.newInstance(); 			//通过反射构造一个T类型的实例
            for(int i = 0; i < field.length; i++) {        //
                field[i].setAccessible(true);            //设置属性的可访问性(可以访问私有属性)
                try {
                    field[i].set(obj, res.getObject(field[i].getName()));    //通过属性名获取结果集中的值赋值到实例对象中
                }catch (SQLException e) {
					continue;
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return obj;
    }
	
	
}
