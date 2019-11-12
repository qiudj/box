package com.boost.box.mybatis.service.impl;

import com.boost.box.mybatis.common.Constant;
import com.boost.box.mybatis.dao.UserMapper;
import com.boost.box.mybatis.model.po.UserEntity;
import com.boost.box.mybatis.service.DataGenerationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 数据生成
 * @author: qdj
 * @date: 2019-11-08 15:42
 **/
@Service
public class DataGenerationServiceImpl implements DataGenerationService {

    @Autowired
    private UserMapper userMapper;

    private static final AtomicInteger accountCnt = new AtomicInteger(10000);

    private static final Logger logger = LoggerFactory.getLogger(DataGenerationServiceImpl.class);
    /**
     *
     * @param cnt 随机生成用户的数量
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void generateUser(int cnt){
        for (int i = 0; i < cnt ; i++) {
            UserEntity user = new UserEntity();
            user.setAccount(String.valueOf(accountCnt.getAndIncrement()));
            user.setBirthday(generateBirthday());
            user.setGender(String.valueOf(new Random().nextInt(2)));

            String fullName = Constant.FAMILY_NAMES[new Random().nextInt(Constant.FAMILY_NAMES.length)] +
                    (new Random().nextInt() >= 3 ? Constant.NAMES[new Random().nextInt(Constant.NAMES.length)] : "")
                    + Constant.NAMES[new Random().nextInt(Constant.NAMES.length)];
            user.setName(fullName);
            userMapper.insertUser(user);
            logger.info("用户{},{} 生成。", user.getName(), user.getAccount());
        }
        logger.info("此次共生成{}个用户数据\n\n", cnt);
    }

    private String generateBirthday(){
        Random random = new Random();
        int year = random.nextInt(10) + 1990;
        int month = random.nextInt(12) + 1;
        int day = random.nextInt(28) + 1;
        String date = "" + year;
        if (month < 10) {
            date = date + "0" + month;
        } else {
            date = date + month;
        }

        if (day < 10){
            date = date + "0" + day;
        } else {
            date = date + day;
        }
        return date;
    }

}
