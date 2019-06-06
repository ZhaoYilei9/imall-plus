package com.imall.pojo;

import javax.persistence.*;

@Table(name = "tb_category")
public class Category {
    /**
     * 类目id
     */
    @Id
    private Long id;

    /**
     * 类目名称
     */
    private String name;

    /**
     * 父类目id,顶级类目填0
     */
    @Column(name = "parent_id")
    private Long parentId;

    /**
     * 是否为父节点，0为否，1为是
     */
    @Column(name = "is_parent")
    private Boolean isParent;

    /**
     * 排序指数，越小越靠前
     */
    private Integer sort;

    /**
     * 获取类目id
     *
     * @return id - 类目id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置类目id
     *
     * @param id 类目id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取类目名称
     *
     * @return name - 类目名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置类目名称
     *
     * @param name 类目名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取父类目id,顶级类目填0
     *
     * @return parent_id - 父类目id,顶级类目填0
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父类目id,顶级类目填0
     *
     * @param parentId 父类目id,顶级类目填0
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取是否为父节点，0为否，1为是
     *
     * @return is_parent - 是否为父节点，0为否，1为是
     */
    public Boolean getIsParent() {
        return isParent;
    }

    /**
     * 设置是否为父节点，0为否，1为是
     *
     * @param isParent 是否为父节点，0为否，1为是
     */
    public void setIsParent(Boolean isParent) {
        this.isParent = isParent;
    }

    /**
     * 获取排序指数，越小越靠前
     *
     * @return sort - 排序指数，越小越靠前
     */
    public Integer getSort() {
        return sort;
    }

    /**
     * 设置排序指数，越小越靠前
     *
     * @param sort 排序指数，越小越靠前
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
}