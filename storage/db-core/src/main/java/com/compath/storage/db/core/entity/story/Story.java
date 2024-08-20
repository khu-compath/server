package com.compath.storage.db.core.entity.story;

import com.compath.storage.db.core.entity.BaseEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;

@Getter
@Entity
@Table(name = "story")
public class Story extends BaseEntity {
}
