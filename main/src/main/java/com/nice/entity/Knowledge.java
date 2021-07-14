package com.nice.entity;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * id 标识符 int
 * catagory 知识类别 String
 * knowledge_id 知识单一类别的标识符 int
 * name 知识名字 String
 * type 知识列别 String
 * author 知识作者 String
 * desc 知识介绍 String
 * text 知识内容 String
 */
@Table("knowledge")
public class Knowledge {
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    public int id;

    @Column("catagory")
    public String catagory;

    @Column("knowledge_id")
    public int knowledge_id;

    @Column("name")
    public String name;

    @Column("type")
    public String type;

    @Column("author")
    public String author;

    @Column("desc")
    public String desc;

    @Column("text")
    public String text;

    public Knowledge() {
        this.catagory = "catagory";
        this.knowledge_id = 1;
        this.name = "name";
        this.type = "type";
        this.author = "author";
        this.desc = "desc";
        this.text = "text" + System.currentTimeMillis();
    }

    public Knowledge(String catagory, int knowledge_id, String name, String type, String author, String desc, String text) {
        this.catagory = catagory;
        this.knowledge_id = knowledge_id;
        this.name = name;
        this.type = type;
        this.author = author;
        this.desc = desc;
        this.text = text;
    }
}
