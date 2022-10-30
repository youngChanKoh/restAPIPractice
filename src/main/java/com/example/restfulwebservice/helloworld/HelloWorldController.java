package com.example.restfulwebservice.helloworld;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
public class HelloWorldController {

    @Autowired
    private MessageSource messageSource;
    // GET
    // /hello-world (endPoint)
    @GetMapping(path = "/hello-world")
    public String helloWorld(){
        return "Hello World";
    }

    // alt + enter
    @GetMapping(path = "/hello-world-bean")
    public HelloWorldBean helloWorldBean(){
        return new HelloWorldBean("Hello World");   // RestController 사용하면 JSON으로 변환해 Return한다
    }

    @GetMapping(path = "/hello-world-bean/path-varible/{name}")
    public HelloWorldBean helloWorldBean(@PathVariable(value = "name") String name){
        return new HelloWorldBean(String.format("Hello World, %s", name));   // format의 첫 argument는 전달하고자 하는 문자 형태, 두번째 argument는 가변값
    }

    @GetMapping(path = "/hello-world-internationalized")
    public String helloWorldInternationalized( @RequestHeader(name = "Accept-Language", required = false) Locale locale) { // requestHeader의 locale값 받아오기
        System.out.println(locale);
        return messageSource.getMessage( "greeting.message", null, locale);
    }

}
