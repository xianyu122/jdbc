package com.hfxt.dao;

import java.sql.ResultSet;
import java.util.List;

public interface BaseDao {
	/**
	 * 查询方法
	 * @param sql 查询的sql语句
	 * @param params 传入的参数
	 * @return 返回查找到的集合
	 */
	public <T> List<T> searchDao(String sql, Class<T> c, Object... params);
	/**
	 * 修改方法
	 * @param sql
	 * @param params
	 * @return 返回修改成功的行数
	 */
	public int update(String sql, Object... params);
	/**
	 * 根据id查找
	 * @param sql
	 * @param id
	 * @return
	 */
	public <T> T searchDaoByParam(String sql, Object param, Class<T> c);
	
	/**
	 * 获取所有条数
	 * @return
	 */
	public int getAllCount(String sql,,Object... params);
	
	/**
     * 将数据封装到bean中
     *
     * @param res
     * @return
     */
    public <T> T objectSet(ResultSet res, Class<T> c);
	
}
