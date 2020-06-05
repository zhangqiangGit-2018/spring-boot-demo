package com.xkcoding.orm.jpa.entity;

import com.xkcoding.orm.jpa.entity.base.AbstractAuditModel;
import lombok.*;

import javax.persistence.*;
import java.util.Collection;
import java.util.Date;

/**
 * <p>
 * 用户实体类
 * </p>
 *
 * @package: com.xkcoding.orm.jpa.entity
 * @description: 用户实体类
 * @author: yangkai.shen
 * @date: Created in 2018/11/7 14:06
 * @copyright: Copyright (c)
 * @version: V1.0
 * @modified: 76peter
 */
@EqualsAndHashCode(callSuper = true)  //重写Equals和hashCode方法同时  callSuper
//可以实现父类中继承的字段可以参与比较
@NoArgsConstructor   //无参构造方法
/**
 * 会生成一个包含所有变量，全部参数都会自动生成，同时如果变量使用NotNull 会进行
 * 为空的检验，该注解的作用域只有在实体类上
 */
@AllArgsConstructor
//顺便介绍下还有一个注解可以指定参数
//@RequiredArgsConstructor  会生成一个包含常量 final修饰的属性，还有表示
//@NotNull 的变量的构造方法
@Data // 注在类上，提供类的get、set、equals、hashCode、canEqual、toString方法
@Builder
@Entity
@Table(name = "orm_user")
@ToString(callSuper = true)
public class User extends AbstractAuditModel {
    /**
     * 用户名
     */
    private String name;

    /**
     * 加密后的密码
     */
    private String password;

    /**
     * 加密使用的盐
     */
    private String salt;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone_number")
    private String phoneNumber;

    /**
     * 状态，-1：逻辑删除，0：禁用，1：启用
     */
    private Integer status;

    /**
     * 上次登录时间
     */
    @Column(name = "last_login_time")
    private Date lastLoginTime;

    /**
     * 关联部门表
     * 1、关系维护端，负责多对多关系的绑定和解除
     * 2、@JoinTable注解的name属性指定关联表的名字，joinColumns指定外键的名字，关联到关系维护端(User)
     * 3、inverseJoinColumns指定外键的名字，要关联的关系被维护端(Department)
     * 4、其实可以不使用@JoinTable注解，默认生成的关联表名称为主表表名+下划线+从表表名，
     * 即表名为user_department
     * 关联到主表的外键名：主表名+下划线+主表中的主键列名,即user_id,这里使用referencedColumnName指定
     * 关联到从表的外键名：主表中用于关联的属性名+下划线+从表的主键列名,department_id
     * 主表就是关系维护端对应的表，从表就是关系被维护端对应的表
     */
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "orm_user_dept", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "dept_id", referencedColumnName = "id"))
    private Collection<Department> departmentList;

}
