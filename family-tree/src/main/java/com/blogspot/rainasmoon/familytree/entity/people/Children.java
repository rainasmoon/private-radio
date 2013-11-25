package com.blogspot.rainasmoon.familytree.entity.people;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.blogspot.rainasmoon.familytree.entity.IdEntity;

@Entity
@Table(name = "CHILDREN")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@JsonIgnoreProperties(value = { "hibernateLazyInitializer" })
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.NONE, getterVisibility = JsonAutoDetect.Visibility.NONE)
public class Children extends IdEntity {

    private static final long serialVersionUID = -2863706579709090043L;
    private Marriage marriage;
	private People people;

	public Children() {
	}

	public Children(Marriage marriage, People child) {
		this.marriage = marriage;
		this.people = child;
	}

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "people_id", nullable = false)
    @JsonProperty
	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "marriage_id", nullable = false)
    @JsonProperty
	public Marriage getMarriage() {
		return marriage;
	}

	public void setMarriage(Marriage marriage) {
		this.marriage = marriage;
	}
}
