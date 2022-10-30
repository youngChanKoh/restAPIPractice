package com.example.restfulwebservice.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
@AllArgsConstructor
@JsonIgnoreProperties(value= {"password"})   // @JsonIgnoreProperties를 통해 classLevel에서 무시할 값을 설정할 수 있다.
public class User {
    private Integer Id;
    @Size(min=2, message = "Name은 2글자 이상 입력해 주세요.")            // 회원의 이름은 최소 2글자 여야한다.
    private String name;
    @Past                   // 회원의 가입날짜는 미래Data는 사용할 수 없고 과거Data만 사용할 수 있다.
    private Date joinDate;
//    @JsonIgnore             // @JsonIgnore를 통해 해당 data값을 무시하도록 처리할 수 있다.
    private String password;
//    @JsonIgnore
    private String ssn;
}
