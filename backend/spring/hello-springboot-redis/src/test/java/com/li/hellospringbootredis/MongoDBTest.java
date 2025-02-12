package com.li.hellospringbootredis;

import com.li.hellospringbootredis.pojo.TestCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testMongoCreateCol() {
        if (!mongoTemplate.collectionExists("test_collection")) {
            mongoTemplate.createCollection("test_collection");
            System.out.println("create collection test_collection");
        } else System.out.println("collection test_collection exists");
        mongoTemplate.insert(new TestCollection("1", "test"));
    }
}
