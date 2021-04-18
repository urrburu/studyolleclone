package com.studyolle.studyolle.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter@Setter@EqualsAndHashCode(of="id")
@Builder@AllArgsConstructor@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    //Account와 Tag는 many to many 관계

}
