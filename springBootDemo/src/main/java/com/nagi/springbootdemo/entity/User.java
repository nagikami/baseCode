package com.nagi.springbootdemo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@TableName("user")
public class User {
    @TableId("id")
    private Integer id;
    @TableField("name")
    private String name;
    private Double salary;
    private String department;
    private Integer age;
    @Version
    private Integer version;
}
