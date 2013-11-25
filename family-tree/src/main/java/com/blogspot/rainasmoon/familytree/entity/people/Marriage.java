package com.blogspot.rainasmoon.familytree.entity.people;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.blogspot.rainasmoon.familytree.entity.IdEntity;

@Entity
@Table(name = "MARRIAGE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Marriage extends IdEntity {

    private static final long serialVersionUID = 8657022629372111005L;
    private People husband;
	private People wife;

	private List<Children> childrenList;

	public Marriage() {
	}

	public Marriage(People husband, People wife) {
		this.husband = husband;
		this.wife = wife;
	}

	@OneToMany(mappedBy = "marriage", cascade = { CascadeType.REMOVE }, fetch = FetchType.LAZY, orphanRemoval = true)
	@OrderBy(value = "id DESC")
	public List<Children> getChildrenList() {
		return childrenList;
	}

	public void setChildrenList(List<Children> childrenList) {
		this.childrenList = childrenList;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "husband_id", nullable = false)
    @JsonProperty
	public People getHusband() {
		return husband;
	}

	public void setHusband(People husband) {
		this.husband = husband;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wife_id", nullable = false)
    @JsonProperty
	public People getWife() {
		return wife;
	}

	public void setWife(People wife) {
		this.wife = wife;
	}

}
