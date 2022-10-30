package com.example.restfulwebservice.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
public class UserController {
    private UserDaoService service;

    /*
     * Spring Container에 등록된 Bean들은 개발자들이 프로그램 실행
     * 도중에 변경할 수 없기 때문에 일관성 있게 사용할 수 있다.
     */
    @Autowired
    public UserController(UserDaoService service){
        this.service = service;
    }

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return service.findAll();
    }

    @GetMapping("/users/{id}")
    public User retrieveUser(@PathVariable int id){
        User user = service.findOne(id);

        if(user == null){
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
        return user;
    }

    // 서버에서 반환하고자 하는 값을 ResponseEntity에 담아서 전달해보자. (즉, 상세보기 정보가 가능한 URI값을 전달해보자)
    @PostMapping("/users")                                        // postMan 통해 post요청 보낼 수 있다.
    public ResponseEntity<User> createUsers(@Valid @RequestBody User user){ // Json,XML 과 같은 Object형태의 data를 받기 위해선 PatamerType에 RequestBody를 선언해 주어야 한다.
                                                                            // 사용자로부터 전달받은 JSON타입의 User객체를 @Valid에 의해 유효성 검사를 실시한다.
        User savedUser = service.save(user);

        // ServletUriComponentsBuilder: 사용자에게 요청값을 반환해주기 위한 class
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();            // 201 Stauts로 Return. 서버로부터 요청결과값의 적절한 상태코드를 전달시켜주는 것이 좋은 RESTAPI를 설계하는 방법중 하나다.
                                                                    // 생성하려고 하는 사용자의 id값은 server에서 자동으로 생성되기 때문에 client측에서 알 수 없다.
                                                                    // 따라서 지금 생성된 id를 알아내기 위해선 client측에서 server에게 또한번 물어봐야 한다.
                                                                    // 이렇게 POST Method의 실행 결과값으로 id를 전달받게 되면 그만큼 network Trafic이 감소되고 효율적인 Application을 만들 수 있다.
                                                                    // 또한, Client의 요청을 CRUD에 따라 GET/PUT/POST/DELETE로 나누어서 보내줘야 한다.
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id){
        User user = service.deleteById(id);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", id));
        }
    }

    @PutMapping("/users/{id}/{name}")
    public void updateByIdAndName(@PathVariable int id, @PathVariable String name){
        service.updateByIdAndName(id, name);
    }
}
