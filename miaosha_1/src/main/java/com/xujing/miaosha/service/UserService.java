package com.xujing.miaosha.service;

import com.xujing.miaosha.dao.UserDao;
import com.xujing.miaosha.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    public User getById(Integer id){
        return  userDao.getById(id);
    }

    @Transactional
    public boolean tx(){
        User user1 = new User();
        user1.setId(2);
        user1.setName("222");
        userDao.insert(user1);

        User user2 = new User();
        user2.setId(3);
        user2.setName("1111");
        userDao.insert(user2);

        return true;
    }

    public List<User> findAll(){
        return  userDao.findAll();
    }


}
