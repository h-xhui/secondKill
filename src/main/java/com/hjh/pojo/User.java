package com.hjh.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author 洪锦辉
 * 2022/2/15
 */
@Data
@ToString
public class User {
    private Long id;
    private String userName;
    private String phone;
    private String password;
    private String salt;
    private String head;
    private Integer loginCount;
    private Date registerDate;
    private Date lastLoginDate;
}
