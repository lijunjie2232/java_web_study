package com.li.hellospringbootredis;

import com.li.hellospringbootredis.pojo.TestCollection;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.Index;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
public class MongoDBTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    public void testMongoCreateCol() {
        if (mongoTemplate.collectionExists("test_collection")) {
            mongoTemplate.dropCollection("test_collection");
        }
        mongoTemplate.createCollection("test_collection");
    }

    @Test
    public void testMongoIndex() {
//        Index index = new Index("name", Sort.Direction.ASC);
        Index index = new Index("name", Sort.Direction.DESC);
        mongoTemplate.indexOps("test_collection").ensureIndex(index);
    }

    @Test
    public void testMongoInsert() {
        mongoTemplate.insert(new TestCollection("1", "test1", ""));
        mongoTemplate.insert(new TestCollection("2", "test2", ""));
        mongoTemplate.insert(new TestCollection("3", "test3", ""));
        mongoTemplate.insert(new TestCollection("4", "test4", ""));
        mongoTemplate.insert(new TestCollection("5", "test5", ""));
        mongoTemplate.insert(new TestCollection("6", "test6", ""));
        mongoTemplate.insert(new TestCollection("7", "test7", ""));
        mongoTemplate.insert(new TestCollection("8", "test8", ""));
    }

    @Test
    public void testMongoQuery() {
        System.out.println(mongoTemplate.findOne(new Query(), TestCollection.class));

        // 查询单个 TestCollection 对象，name 为 "test1"
        Query query = new Query(Criteria.where("name").is("test1"));
        TestCollection testCollection = mongoTemplate.findOne(query, TestCollection.class);
        System.out.println(testCollection);

        System.out.println(mongoTemplate.findAll(TestCollection.class));

    }

    @Test
    public void testMongoUpdate() {
        Query query = new Query(Criteria.where("name").is("test1"));
        System.out.println(mongoTemplate.findOne(query, TestCollection.class));
        Update update = new Update().set("name", "Updata test1");
        System.out.println(mongoTemplate.findOne(query, TestCollection.class));
        mongoTemplate.updateFirst(query, update, TestCollection.class);
        System.out.println(mongoTemplate.findOne(
                new Query(
                        Criteria.where("id")
                                .is("1")
                ),
                TestCollection.class)
        );
    }

    @Test
    public void testMongoDelete() {
        Query query = new Query(Criteria.where("name").is("Updata test1"));
        mongoTemplate.remove(query, TestCollection.class);
        System.out.println(mongoTemplate.findAll(TestCollection.class));
//        mongoTemplate.dropCollection("test_collection");
        mongoTemplate.remove(new Query(), TestCollection.class);
        System.out.println(mongoTemplate.findAll(TestCollection.class));
    }

}
