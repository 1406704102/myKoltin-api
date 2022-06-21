package com.my.kotlin.system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "question_info")
public class QuestionInfo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "question_title")
    private String questionTitle;

    @Column(name = "question_answer")
    private String questionAnswer;

    @Column(name = "question_focus")
    private String questionFocus;

    @Column(name = "question_type")
    private String questionType;

    @Column(name = "index_show")
    private String indexShow;

    @Column(name = "enable")
    private String enable;

    @Column(name = "create_time")
    private Timestamp createTime;

}
