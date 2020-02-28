package com.example.demo.vo;

import com.example.demo.validator.MobileCheck;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

/**
 * LoginVo class
 *
 * @author maco
 * @data 2019/10/24
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginVo {
    @NotNull
    @MobileCheck
    private String mobile;

    @NotNull
    @Length(min = 32)
    private String password;

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
