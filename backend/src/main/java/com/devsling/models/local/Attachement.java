package com.devsling.models.local;

import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

import com.devsling.constants.FileExtension;
import com.devsling.constants.FileType;
import com.devsling.models.config.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Attachement extends BaseEntity {

  private String ext;

  private FileExtension extension;

  private String originalFileName;

  private String path;

  private Long fileSize;

  private FileType fileType;

}
