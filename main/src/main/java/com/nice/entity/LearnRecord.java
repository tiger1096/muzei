package com.nice.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.sql.Timestamp;

/**
 * id 标识符 int
 * user_id 用户id int
 * item_id 学习内容数据库中的id int
 * learn_time 学习事件 datetime
 */
@Table("LearnRecord")
public class LearnRecord {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    @Column("user_id")
    public int user_id;

    @Column("item_id")
    public int item_id;

    @Column("timestamp")
    public Timestamp timestamp;
}
