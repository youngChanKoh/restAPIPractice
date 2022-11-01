package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminUserController {
    private UserDaoService service;

    @Autowired
    public AdminUserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public MappingJacksonValue retrieveAllUsers() {     // filtering된 data값을 반환할때는 MappingJacksonValue객체로 변환해 반환해야 한다.
        List<User> Users = service.findAll();

        // Bean의 Property를 제어할 수 있도록 SimpleBeanPropertyFilter를 사용해보자.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "ssn");   // 포함시키고자 하는 filter값 선언

        FilterProvider filters = new SimpleFilterProvider().addFilter("User Info", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(Users);
        mapping.setFilters(filters);

        return mapping;
    }

    // GET /admin/users/1 -> /admin/v1/users/1
    @GetMapping("/v1/users/{id}")
    public MappingJacksonValue retrieveUserV1(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // Bean의 Property를 제어할 수 있도록 SimpleBeanPropertyFilter를 사용해보자.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
//                .filterOutAllExcept("id", "name", "joinDate", "ssn");   // 포함시키고자 하는 filter값 선언
                .filterOutAllExcept("id", "name", "password", "ssn");   // 포함시키고자 하는 filter값 선언
        FilterProvider filters = new SimpleFilterProvider().addFilter("User Info", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(user);
        mapping.setFilters(filters);

        return mapping;
    }

    @GetMapping("/v2/users/{id}")
    public MappingJacksonValue retrieveUserV2(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }

        // User -> User2
        UserV2 userV2 = new UserV2();
        // BeanUtils란 SpringFrameWork에서 제공해주는 UtilClass로, Bean들 간의 관련되어 있는 작업들을 도와준다.
        // 즉, 두 instance간에 공통적인 field가 있을 경우 해당값을 copy하는 기능도 BeanUtils에 포함되어 있다.
        BeanUtils.copyProperties(user, userV2);
        userV2.setGrade("VIP");


        // Bean의 Property를 제어할 수 있도록 SimpleBeanPropertyFilter를 사용해보자.
        SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name", "joinDate", "grade");   // 포함시키고자 하는 filter값 선언
        FilterProvider filters = new SimpleFilterProvider().addFilter("UserInfoV2", filter);

        MappingJacksonValue mapping = new MappingJacksonValue(userV2);
        mapping.setFilters(filters);

        return mapping;
    }

}
