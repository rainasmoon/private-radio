package com.blogspot.rainasmoon.familytree.entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 统一定义id的entity基类.
 * 
 * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * 子类可重载getId()函数重定义id的列名映射和生成策略.
 * 
 * @author calvin
 */
//JPA 基类的标识
@MappedSuperclass
public abstract class IdEntity implements java.io.Serializable {

    private static final long serialVersionUID = 2866881533714351441L;

    protected Long id;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "uuid")
    @JsonProperty
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
