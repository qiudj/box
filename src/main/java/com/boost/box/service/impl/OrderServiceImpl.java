package com.boost.box.service.impl;

import com.boost.box.common.DistributedLock;
import com.boost.box.common.RedisClusterPool;
import com.boost.box.service.OrderService;
import redis.clients.jedis.JedisCluster;

import java.util.Random;

public class OrderServiceImpl implements OrderService {

    public void doOrder() {
        JedisCluster jedis = RedisClusterPool.getJedisCluster();
        int cnt = 0;
        String ret = jedis.get("YY"); //先查询缓存中此项目是否有值
        if (null == ret){  // 此时Redis缓存中没有项目，要查询DB更新数据...
            cnt = new Random().nextInt(10) + 2;

            // 这里尝试获取锁时可以设置一个时间值T，如果循环时间超过了T还没有获取到(或者循环次数)，
            // 就先返回，如返回人数太多...
            for (;;){
                if (jedis.get("YY") != null) //其它线程已经完成查库更新的操作
                    break;
                boolean acquired = DistributedLock.tryLock("LYY",
                        String.valueOf(Thread.currentThread().getId() + 10000),
                        1000 * 60);
                if (acquired && jedis.get("YY") == null){ //是第一个查DB更新的线程
                    jedis.set("YY", String.valueOf(cnt));
                    DistributedLock.unlock("LYY",
                            String.valueOf(Thread.currentThread().getId() + 10000));
                    break;
                }
            }
        } else {
            cnt = Integer.parseInt(ret);
        }

        if (cnt > 0){
            for (;;){
                boolean acquired = DistributedLock.tryLock("LYY",
                        String.valueOf(Thread.currentThread().getId() + 10000), //模拟请求id
                        1000 * 60);
                if (acquired){
                    cnt = Integer.parseInt(jedis.get("YY"));
                    if (cnt > 0){
                        jedis.set("YY", String.valueOf(cnt - 1));

                        // 处理逻辑...如果处理逻辑时间超过了给锁设置的过期时间
                        // 需要给当前处理线程设置守护线程，当时间快要过期时，延长锁定时间...
                        // 当然这里也可以说是超时处理不完就回滚操作
                        System.out.println(Thread.currentThread().getName() + " 抢到票" + cnt);
                        //处理完释放锁
                        DistributedLock.unlock("LYY",
                                String.valueOf(Thread.currentThread().getId() + 10000));
                    } else {
                        //处理完释放锁
                        DistributedLock.unlock("LYY",
                                String.valueOf(Thread.currentThread().getId() + 10000));
                        //抢完了
                        break;
                    }

                }
            }
        }
        System.out.println(Thread.currentThread().getName() + " 没有抢到票...");
    }
}