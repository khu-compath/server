package com.compath.storage.db.core.entity.university;

import com.compath.storage.db.core.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "college")
public class College extends BaseEntity {
	@Column(name = "name")
	private String name;
}
