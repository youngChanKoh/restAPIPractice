package com.example.restfulwebservice.helloworld;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

// lombok 사용하면 setter/getter/생성자/toString/equals 자동 생성해준다.
// structure 확인 해 보면 해당 method들 만들어져 있는거 확인 가능
@Data               // setter/getter 생성기능
@AllArgsConstructor // member field에 있는 모든 변수 parameter로 생성자 생성해주는 annotation
@NoArgsConstructor  // parameter없는 default 생성자
public class HelloWorldBean {
    private String message;

}
