package com.pinyougou.pojo;

import javax.persistence.*;

/**
 * Brand 实体类
 *
 * @version 1.0
 * @date 2019-02-18 13:03:19
 */

@Table(name = "tb_brand")
public class Brand implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "first_char")
    private String firstChar;

    /**
     * setter and getter method
     */
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setFirstChar(String firstChar) {
        this.firstChar = firstChar;
    }

    public String getFirstChar() {
        return this.firstChar;
    }

}