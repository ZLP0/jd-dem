package com.example.jddemo.sharding;

import java.util.List;

/**
 * (TOrder)表数据库访问层
 *
 * @author makejava
 * @since 2021-08-08 14:32:58
 */
public interface TOrderDao {

    /**
     * 通过实体作为筛选条件查询
     *
     * @param tOrder 实例对象
     * @return 对象列表
     */
    List<TOrder> queryAll(TOrder tOrder);

    /**
     * 新增数据
     *
     * @param tOrder 实例对象
     * @return 影响行数
     */
    int insert(TOrder tOrder);

}

