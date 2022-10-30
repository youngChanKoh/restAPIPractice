package com.example.restfulwebservice.user;

import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;

@Service
public class UserDaoService {
    /* 사용자에 대한 Business Logic */
    private static List<User> users = new ArrayList<>();

    private static int usersCount = 3;

    static {
        users.add(new User(1, "Kenneth", new Date(), "test1", "701010-1111111"));
        users.add(new User(2, "Alice", new Date(), "test2", "801111-2222222"));
        users.add(new User(3, "Elena", new Date(), "test3", "901313-2222222"));
    }

    public List<User> findAll() {
        return users;
    }

    public User save(User user) {
        if (user.getId() == null){
            user.setId(++usersCount);
        }

        users.add(user);
        return user;
    }

    public User findOne(int id) {
        for(User user : users){
            if (user.getId() == id){
                return user;
            }
        }
        return null;
    }

    public User deleteById(int id){
        Iterator<User> iter = users.iterator();

        while(iter.hasNext()){
            User user = iter.next();

            if(user.getId() == id){
                iter.remove();
                return user;
            }
        }

        return null;
    }

    public void updateByIdAndName(int id, String name){
        User user = users.get(id);
        user.setName(name);
    }
}
