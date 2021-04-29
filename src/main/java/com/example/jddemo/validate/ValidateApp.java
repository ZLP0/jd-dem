package com.example.jddemo.validate;

import com.example.jddemo.copy.Person;
import com.example.jddemo.response.CommonResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Slf4j
@RestController
public class ValidateApp {
    /**
     * 对应依赖
     * <dependency>
     * <groupId>org.springframework.boot</groupId>
     * <artifactId>spring-boot-starter-validation</artifactId>
     * </dependency>
     */


    @RequestMapping(value = "/validate")
    public CommonResponse<String> test(@NotNull @Validated ValidatePerson person, BindingResult bindingResult) {

        CommonResponse<String> response = new CommonResponse<>();
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                log.info("error:{}", error.getDefaultMessage());
                response.setCode(0);
                response.setMessage(error.getDefaultMessage());
                return response;
            }
        }
        response.setData("校验成功");
        response.setCode(1);
        return response;
    }

    @RequestMapping(value = "/validate2")
    public CommonResponse<String> test2() {

        CommonResponse<String> response = new CommonResponse<>();

        ValidatePerson person = new ValidatePerson();
        person.setAge(11);
        person.setName("张三");
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<ValidatePerson>> violations = validator.validate(person);
        if (violations != null) {
            for (ConstraintViolation e : violations) {
                log.info("校验信息：" + e.getMessage());
                response.setCode(0);
                response.setMessage(e.getMessage());
                return response;
            }
        }
        response.setData("校验成功");
        response.setCode(1);
        return response;
    }

}
