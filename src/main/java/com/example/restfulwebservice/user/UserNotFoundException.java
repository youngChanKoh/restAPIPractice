package com.example.restfulwebservice.user;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// HTTP Status Code
// 2XX -> OK
// 4XX -> Client가 잘못했을때
// 5XX -> Server측의 오류
@ResponseStatus(HttpStatus.NOT_FOUND)       // 해당 예외 Class는 500번대 error가 아니라 400번대 NOT_FOUND error로 상태코드 반환.
                                            // 500번대 에러는 에러메시지에 코드가 노출되기 때문에 이런식으로 처리해주면 좋다.
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
