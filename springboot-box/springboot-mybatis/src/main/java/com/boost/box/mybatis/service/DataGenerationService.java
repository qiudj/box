package com.boost.box.mybatis.service;

/**
 * 数据生成服务
 * @author qdj
 */
public interface DataGenerationService {
    /**
     * 随机生成一定数量的用户
     * @param cnt 随机生成用户数量
     */
    void generateUser(int cnt);
}
