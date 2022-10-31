package com.example.restfulwebservice.user;

import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
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

    @GetMapping("/users/{id}")
    public MappingJacksonValue retrieveUser(@PathVariable int id){
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

}
