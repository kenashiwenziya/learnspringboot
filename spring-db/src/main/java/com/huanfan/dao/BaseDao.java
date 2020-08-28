package com.huanfan.dao;

import java.util.List;
import java.util.Map;

/**
 * 基础Dao(还需在XML文件里，有对应的SQL语句)
 * @author lijie
 * 
 * @param <T>
 *          对象
 */
public interface BaseDao<T> {
	
    /**
     * 保存
     * @param t
     *          对象
     */
    void save(T t);
	
	/**
	 * 保存
	 * @param map
	 *         键值对
	 */
    void save(Map<String, Object> map);
	
	/**
	 * 批量保存
	 * @param list
	 *         列表
	 */
    void saveBatch(List<T> list);
	
	/**
	 * 更新
	 * @param t
	 *         对象
	 * @return
	 *         结果
	 */
    int update(T t);
	
    /**
     * 更新
     * @param map
     *          键值对
     * @return
     *          结果
     */
    int update(Map<String, Object> map);
	
	/**
	 * 删除
	 * @param id
	 *         索引
	 * @return
	 *         结果
	 */
    int delete(Object id);
	
    /**
     * 删除
     * @param map
     *          键值对
     * @return
     *          结果
     */
    int delete(Map<String, Object> map);
	
	/**
	 * 批量删除 
	 * @param id
	 *           索引
	 * @return
	 *           结果
	 */
    int deleteBatch(Object[] id);

    /**
     * 查询
     * @param id
     *          索引
     * @return
     *          对象
     */
    T queryObject(Object id);
	
    /**
     * 列表查询
     * @param map
     *          键值对
     * @return
     *          列表
     */
    List<T> queryList(Map<String, Object> map);
	
    /**
     * 列表查询
     * @param id
     *          索引
     * @return
     *          列表
     */
    List<T> queryList(Object id);
	
    /**
     * 记录总数
     * @param map
     *          键值对
     * @return
     *          总数
     */
    int queryTotal(Map<String, Object> map);

    /**
     * 记录总数
     * @return
     *      总数
     */
    int queryTotal();
}
