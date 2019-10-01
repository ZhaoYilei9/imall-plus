package com.imall.user.pojo;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Data
@Table(name = "tb_user")
public class User {
    //useGeneratedKeys如果设置为true则这个设置强制使用自动生成主键
    @Id
    @KeySql(useGeneratedKeys = true)
    private Long id;

    @Length(max = 30, min = 4, message = "用户名长度只能在4-30之间")
    private String username;// 用户名

    @JsonIgnore
    @Length(max = 30, min = 4, message = "密码长度只能在4-30之间")
    private String password;// 密码

    @Pattern(regexp = "^1[35678]\\d{9}$",message = "手机号格式不正确")
    private String phone;// 电话

    private Date created;// 创建时间

    @JsonIgnore
    private String salt;// 密码的盐值
}
