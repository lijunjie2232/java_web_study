package com.li.hellospringbootredis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;import org.springframework.data.mongodb.core.mapping.Document;import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "test_collection")
public class TestCollection {
    @Id
    private String id;
    @Field("name")
    private String name;
    @Transient
    private String desc;
}
