package com.blogspot.rainasmoon.familytree.entity.people;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.blogspot.rainasmoon.familytree.entity.IdEntity;

@Entity
@Table(name = "PEOPLE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "isMale",
		"isFemale", "SEX_MALE", "SEX_FEMALE", "isEmpty" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class People extends IdEntity {

	private static final long serialVersionUID = -5487260053435046018L;
	@Transient
	public static final String SEX_MALE = "Male";
	@Transient
	public static final String SEX_FEMALE = "Female";

	private String name;

	private String sex;

	@Transient
	public boolean isEmpty() {
		return name == null || name.isEmpty();
	}

	@Transient
	public boolean isMale() {
		return SEX_MALE.equalsIgnoreCase(sex);
	}

	@Transient
	public boolean isFemale() {
		return SEX_FEMALE.equalsIgnoreCase(sex);
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@JsonProperty
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

}
