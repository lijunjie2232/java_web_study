- [redis config](#redis-config)
- [reids cli](#reids-cli)
  - [hello world](#hello-world)
- [Basic data type](#basic-data-type)
  - [String](#string)
  - [List](#list)
  - [Hash](#hash)
  - [Set](#set)
  - [ZSet](#zset)
  - [GEO](#geo)
  - [HyperLogLog](#hyperloglog)
  - [BitMap](#bitmap)
  - [BitField](#bitfield)
  - [Stream](#stream)
- [Redis Command](#redis-command)
- [key and value](#key-and-value)
- [db and index](#db-and-index)
- [String](#string-1)
  - [GETRANGE / SETRANGE](#getrange--setrange)
  - [INCR / INCRBY / DECR / DECRBY / INCRBYFLOAT (for number)](#incr--incrby--decr--decrby--incrbyfloat-for-number)
  - [STRLEN / APPEND](#strlen--append)
- [List](#list-1)
  - [basic command](#basic-command)
- [Hash](#hash-1)
  - [Basic Commands](#basic-commands)
  - [Use Case](#use-case)
- [Set](#set-1)
  - [Basic Commands](#basic-commands-1)
  - [Set Operations](#set-operations)
  - [Use Case:](#use-case-1)
- [Zset](#zset-1)
  - [Basic Commands](#basic-commands-2)
  - [Use Case:](#use-case-2)
- [Bitmap](#bitmap-1)
  - [Basic Commands](#basic-commands-3)
  - [Use Case:](#use-case-3)
- [HyperLogLog](#hyperloglog-1)
  - [Basic Commands](#basic-commands-4)
  - [Use Case:](#use-case-4)
- [Geo](#geo-1)
  - [Basic Commands](#basic-commands-5)
  - [Use Case:](#use-case-5)
- [Stream](#stream-1)
  - [special character:](#special-character)
  - [Basic Commands](#basic-commands-6)
- [bitfield](#bitfield-1)
  - [Basic Comands](#basic-comands)
- [RDB](#rdb)
  - [config](#config)
  - [recovery from dumped backup](#recovery-from-dumped-backup)
  - [manual backup](#manual-backup)
  - [Advantage](#advantage)
  - [Disadvantage](#disadvantage)
  - [repair dunp file](#repair-dunp-file)
  - [times of backup](#times-of-backup)
  - [disable RDB](#disable-rdb)
  - [other config of rdb](#other-config-of-rdb)
- [AOF (Append Only File)](#aof-append-only-file)
  - [config config](#config-config)
  - [repair aof file](#repair-aof-file)
  - [Advantage](#advantage-1)
  - [Disadvantage](#disadvantage-1)
  - [aof rewrite](#aof-rewrite)
  - [other config of aof](#other-config-of-aof)
- [RDB and AOF](#rdb-and-aof)
  - [config](#config-1)
  - [work](#work)
- [pure cache (disable durable on redis)](#pure-cache-disable-durable-on-redis)
- [Transaction](#transaction)
  - [Basic Command](#basic-command-1)
  - [ERROR Handling](#error-handling)
  - [WATCH and UNWATCH](#watch-and-unwatch)
- [Redis Pipe](#redis-pipe)
  - [Usage](#usage)
  - [Tips](#tips)
- [PUB/SUB](#pubsub)
  - [基本概念](#基本概念)
  - [基本命令](#基本命令)
  - [示例](#示例)
    - [订阅频道](#订阅频道)
    - [发布消息](#发布消息)
    - [接收消息](#接收消息)
    - [取消订阅](#取消订阅)
    - [模式订阅](#模式订阅)
  - [注意事项](#注意事项)

# redis config

- `daemonize yes`: run redis as a daemon
- `protected-mode yes`: config yes if no clients from other hosts connect
- `bind [ip]`: if ip config `127.0.0.1`, only local host could connect to redis
- `requirepass xxx`: set redis paddword

# reids cli

## hello world

```bash
❯ redis-cli -a xxx
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
127.0.0.1:6379> ping
PONG
127.0.0.1:6379> set hello world
OK
127.0.0.1:6379> get hello
"world"
127.0.0.1:6379> shutdown
```

# Basic data type

## String

String is the most basic data type in Redis. It is a binary safe type which can be of any data context including <font color="#0984e3">serialized data</font> not more than <font color="orange">512MB</font>.

- `set key value`: set key value
- `get key`: get key value

```redis
SET user:age 30
GET user:age
```

## List

List is a "Doubly Linked List", could store at most <font color="orange">2<sup>32</sup> - 1 = 4294967295</font> items

## Hash

Hash is a key-value map like dict in python, could store at most <font color="orange">2<sup>32</sup> - 1 = 4294967295</font> items;
each hash key maps to multiple hash fields like: hash key -> {field1: value1, field2: value2, ...}

```redis
HSET user:info name "Alice" age 25 city "Beijing"
HGET user:info name
HGETALL user:info
```

## Set

Set is an <font color="orange">un-ordered</font> collection of unique elements, it is build by <font color="orange">intest or hashtable</font>;

Set could store at most <font color="orange">2<sup>32</sup> - 1 = 4294967295</font> items

Set uses hash table to store elements, operations like add, remove, check existence are all <font color="orange">O(1)</font>

| 命令                           | 描述                                                             |
| ------------------------------ | ---------------------------------------------------------------- |
| `SADD key member [member ...]` | 向 `Set` 中添加一个或多个成员。如果成员已存在，则不会重复添加。  |
| `SMEMBERS key`                 | 返回 `Set` 中的所有成员。                                        |
| `SISMEMBER key member`         | 检查某个成员是否存在于 `Set` 中。返回 1 表示存在，0 表示不存在。 |
| `SCARD key`                    | 返回 `Set` 中成员的数量。                                        |
| `SREM key member [member ...]` | 从 `Set` 中移除一个或多个成员。如果成员不存在，则忽略。          |
| `SPOP key [count]`             | 移除并返回 `Set` 中的一个或多个随机成员。                        |
| `SRANDMEMBER key [count]`      | 返回 `Set` 中的一个或多个随机成员，但不移除它们。                |
| `SINTER key [key ...]`         | 返回多个 `Set` 的交集。                                          |
| `SUNION key [key ...]`         | 返回多个 `Set` 的并集。                                          |
| `SDIFF key [key ...]`          | 返回第一个 `Set` 中存在但其他 `Set` 中不存在的成员。             |

```redis
# 添加成员到 set
SADD myset "apple" "banana" "orange"

# 查看 set 中的所有成员
SMEMBERS myset

# 检查某个成员是否存在
SISMEMBER myset "banana"

# 获取 set 中成员的数量
SCARD myset

# 移除某个成员
SREM myset "orange"
```

## ZSet

ZSet is an <font color="orange">ordered and sorted</font> set

```redis
# 添加成员到 zset 并指定分数
ZADD myzset 1 "apple" 2 "banana" 3 "orange"

# 获取所有成员及其分数（按分数升序）
ZRANGE myzset 0 -1 WITHSCORES

# 获取所有成员及其分数（按分数降序）
ZREVRANGE myzset 0 -1 WITHSCORES

# 获取某个成员的排名（按分数升序）
ZRANK myzset "banana"

# 获取某个成员的排名（按分数降序）
ZREVRANK myzset "banana"

# 获取某个成员的分数
ZSCORE myzset "banana"
```

## GEO

Geo is a data type used to store geographic coordinates and perform distance calculations. 

## HyperLogLog

A space-efficient way to count unique elements in a set.

## BitMap

A data structure that allows you to store a large number of bits and perform operations on them quickly.

## BitField

Operate in multiple bit fields at once.

## Stream

For message Queue

# Redis Command

# key and value
- `SET key value [EX seconds] [PX milliseconds] [EXAT timestamp] [PXAT timestamp_milliseconds] [NX|XX] [KEEPTTL] [GET]`
  - `SET user:age 30`
  - `SET user:age 30 EX 3600` set key value and expire after 3600 seconds
  - `SET user:age 30 PX 3600000` set key value and expire after 3600000 milliseconds
  - `SET user:age 30 NX` set key value if not exists
  - `SET user:age 30 XX` set key value if exists
  - `SET user:age 30 EXAT 1620000000` set key value and expire at 1620000000 (unix timestamp)
  - `SET user:age 30 GET`  set new key value and return old value
  - `SET user:age 30 KEEPTTL` set key value and keep ttl
- `GETSET key value` == `SET key value GET`
- `MSET key value [key value ...]`: set multiple key value (None exec if any error)
- `MGET key [key ...]`: get multiple key value
- `MSETNX key value [key value ...]`: set multiple key value if not exists (None exec if any error)
- `SETEX / SETNX` is atomic operation for multi thread
  - `SETEX key <expire_seconds> <value>`: set key value and expire after seconds
  - `SETNX key value`: set key value if not exists

- `TYPE key`: get key type
- `EXISTS key`: check key exists
- `EXPIRE key <seconds>`: set key expire
- `DEL key`: delete key
- `UNLINK key`: delete key without blocking other threads
- `TTL key`: get the remaining seconds of key to expire (`-1` means key does not expire for ever ,`-2` means key has already expired)

# db and index
- `MOVE key <db index>`: move key to db
- `SELECT <db index>`: use db
- `DBSIZE`: get db size
- `FLUSHDB`: remove all from current db
- `FLUSHALL`: remove all from all db

# String
## GETRANGE / SETRANGE
```bash
127.0.0.1:6379> set ip 127.0.0.1
OK
127.0.0.1:6379> GETRANGE ip 0,5
(error) ERR wrong number of arguments for 'getrange' command
127.0.0.1:6379> GETRANGE ip 0 5
"127.0."
127.0.0.1:6379> GETRANGE ip 0 -1
"127.0.0.1"
127.0.0.1:6379> SETRANGE ip 1 192
(integer) 9
127.0.0.1:6379> get ip
"11920.0.1"
127.0.0.1:6379> SETRANGE ip -1 192
(error) ERR offset is out of range
```

## INCR / INCRBY / DECR / DECRBY / INCRBYFLOAT (for number)

```bash
127.0.0.1:6379> set vol 100 EX 500
OK
127.0.0.1:6379> ttl vol
(integer) 389
127.0.0.1:6379> INCR vol
(integer) 101
127.0.0.1:6379> get vol
"101"
127.0.0.1:6379> ttl vol
(integer) 370
127.0.0.1:6379> decr vol
(integer) 100
127.0.0.1:6379> INCRBY vol -5
(integer) 95
127.0.0.1:6379> deCRBY vol -5
(integer) 100
127.0.0.1:6379> INCRBYFLOAT vol 1.23456
"101.23456"
```

## STRLEN / APPEND
```bash
127.0.0.1:6379> STRLEN ip
(integer) 9
127.0.0.1:6379> APPEND ip ":8888"
(integer) 14
127.0.0.1:6379> get ip
"11920.0.1:8888"
```

# List
- List is implemented as a doubly linked list, primarily used for queue operations such as push and pop.

## basic command
- LPUSH / RPUSH: add element to list
- LPOP / RPOP: remove element from list
- LRANGE (no rrange): get element from list
  ```bash
  127.0.0.1:6379> LPUSH msg "hellp"
  (integer) 1
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "hellp"
  127.0.0.1:6379> LPUSH msg "hello" "helli"
  (integer) 3
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "helli"
  2) "hello"
  3) "hellp"
  127.0.0.1:6379> RPUSH msg "world"
  (integer) 4
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "helli"
  2) "hello"
  3) "hellp"
  4) "world"
  127.0.0.1:6379> LPOP msg
  "helli"
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "hello"
  2) "hellp"
  3) "world"
  127.0.0.1:6379> RPOP msg
  "world"
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "hello"
  2) "hellp"
  ```

- `LINDEX <key> <index>`: get element from list by index
- `LLEN <key>`: get list length
  ```bash
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "hello"
  2) "hellp"
  127.0.0.1:6379> LINDEX msg 1
  "hellp"
  127.0.0.1:6379> LINDEX msg -1
  "hellp"
  127.0.0.1:6379> LINDEX msg -2
  "hello"
  127.0.0.1:6379> LINDEX msg 0
  "hello"
  127.0.0.1:6379> llen msg
  (integer) 2
  ```

- `LREM <key> <count> <value>`: remove element from list by value, if count < 0, delete value from tail to head
- `LTRIM <key> <start> <stop>`: `key (new)` = `key[start:stop+1]`
  ```bash
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "0"
  2) "0"
  3) "2"
  4) "1"
  5) "0"
  6) "0"
  7) "0"
  127.0.0.1:6379> LREM msg -2 0
  (integer) 2
  127.0.0.1:6379> LRANGE msg 0 -1
  1) "0"
  2) "0"
  3) "2"
  4) "1"
  5) "0"
  127.0.0.1:6379> del msg
  (integer) 1
  127.0.0.1:6379> lpush msg 0 1 2 3 4 5 6 7 8 9
  (integer) 10
  127.0.0.1:6379> lrange msg 0 -1
  1) "9"
  2) "8"
  3) "7"
  4) "6"
  5) "5"
  6) "4"
  7) "3"
  8) "2"
  9) "1"
  10) "0"
  127.0.0.1:6379> LTRIM msg 1 -3
  OK
  127.0.0.1:6379> lrange msg 0 -1
  1) "8"
  2) "7"
  3) "6"
  4) "5"
  5) "4"
  6) "3"
  7) "2"
  ```

- `RPOPLPUSH <src> <dst>`: `RPOP` element from src and `LPUSH` to dst, return removed element
  ```bash
  127.0.0.1:6379> RPOPLPUSH msg msg2
  "2"
  127.0.0.1:6379> lrange msg2 0 -1
  1) "2"
  127.0.0.1:6379> RPOPLPUSH msg msg2
  "3"
  127.0.0.1:6379> lrange msg2 0 -1
  1) "3"
  2) "2"
  ```

- `LSET <key> <index> <value>`: set element by index
  ```bash
  127.0.0.1:6379> lrange msg2 0 -1
  1) "3"
  2) "2"
  127.0.0.1:6379> LSET msg2 0 5
  OK
  127.0.0.1:6379> lrange msg2 0 -1
  1) "5"
  2) "2"
  ```

- `INSERT <key> <BEFORE|AFTER> <pivot> <value>`: insert element after or before pivot
  ```bash
  127.0.0.1:6379> lrange msg2 0 -1
  1) "5"
  2) "2"
  127.0.0.1:6379> LINSERT msg2 BEFORE 2 3
  (integer) 3
  127.0.0.1:6379> lrange msg2 0 -1
  1) "5"
  2) "3"
  3) "2"
  ```

# Hash
- hash: `Map<String, Map<Object,Object>>`

## Basic Commands
- `HSET <key> <field> <value> [<field2> <value2> ...]`: set field value
- `HGET <key> <field>`: get field value
  ```bash
  127.0.0.1:6379> hset user:011 id 11 name 11 age 11
  (integer) 3
  127.0.0.1:6379> hget user:011 id name age
  (error) ERR wrong number of arguments for 'hget' command
  127.0.0.1:6379> hget user:011 id
  "11"
  ```

- `HMSET <key> <field> <value> [<field2> <value2> ...]`: set field value
- `HMGET <key> <field> [<field2> ...]`: get field value
- `HDEL <key> <field> [<field2> ...]`: delete field
  ```bash
  127.0.0.1:6379> HDEL user:011 age
  (integer) 1
  127.0.0.1:6379> HMGET user:011 id name age
  1) "11"
  2) "11"
  3) (nil)
  ```
- `HEXISTS <key> <field>`: check field exists or not
- `HGETALL <key>`: get all field value
- `HLEN <key>`: get field count
- `HKEYS <key>`: get all field
- `HVALS <key>`: get all value
  ```bash
  127.0.0.1:6379> HLEN uesr:011
  (integer) 0
  127.0.0.1:6379> HLEN user:011
  (integer) 2
  127.0.0.1:6379> HGETALL user:011
  1) "id"
  2) "11"
  3) "name"
  4) "11"
  127.0.0.1:6379> HEXISTS user:011 age
  (integer) 0
  127.0.0.1:6379> HKEYS user:011
  1) "id"
  2) "name"
  127.0.0.1:6379> HVALS user:011
  1) "11"
  2) "11"
  ```

- `HICRBYTE <key> <field> <increment>`: increment field value by increment, return new value
- `HINCRBYFLOAT <key> <field> <increment>`: increment field value by increment, return new value
  ```bash
  127.0.0.1:6379> HGETALL user:011
  1) "id"
  2) "11"
  3) "name"
  4) "11"
  127.0.0.1:6379> HKEYS user:011
  1) "id"
  2) "name"
  127.0.0.1:6379> HGETALL user:011
  1) "id"
  2) "12"
  3) "name"
  4) "11"
  127.0.0.1:6379> HINCRBYFLOAT user:011 id 0.1
  "12.1"
  127.0.0.1:6379> HGETALL user:011
  1) "id"
  2) "12.1"
  3) "name"
  4) "11"
  ```

- `HSETNX <key> <field> <value>`: set field value if not exists, return 1 if set, 0 if not set

## Use Case
1. online shoping:
   1. add item into Shopping Cart: `HSETNX user:011:cart:011 <goods_id> <amount>`
   2. increment amount: `HINCBY user:011:cart:011 <goods_id> 1`
   3. get numbers of goods type: `HLEN user:011:cart:011`
   4. get all goods and calculate price: `HGETALL user:011:cart:011`

# Set
- Set in redis is a hashset

## Basic Commands
- `SADD <key> <member> [<member2> ...]`: add member to set
- `SMEMBER <key> <member>`: check member exists or not
- `SISMEMBER <key> <member>`: check member exists or not, return 1 if exists, 0 if not exists
- `SREM <key> <member> [<member2> ...]`: remove member from set
- `SCARD <key>`: get set size
  ```bash
  127.0.0.1:6379> SADD ms 1 2 3 4 5 6 3 2 2 4 1 4 5 6 3 1 4 5 6 1 4
  (integer) 6
  127.0.0.1:6379> SMEMBERS ms
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "5"
  6) "6"
  127.0.0.1:6379> SISMEMBER ms 5
  (integer) 1
  127.0.0.1:6379> SISMEMBER ms 15
  (integer) 0
  127.0.0.1:6379> SREM ms 15
  (integer) 0
  127.0.0.1:6379> SREM ms 5
  (integer) 1
  127.0.0.1:6379> SISMEMBER ms 5
  (integer) 0
  127.0.0.1:6379> SMEMBERS ms
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "6"
  127.0.0.1:6379> SCARD ms
  (integer) 5
  ```

- `SRANDMEMBER <key> <count>`: get random member from set, if count < 0, get one member, if count == 0, get no member
- `SPOP <key> <count>`: remove and return random members from set
  ```bash
  127.0.0.1:6379> SMEMBERS ms
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "6"
  127.0.0.1:6379> SRANDMEMBER ms
  "6"
  127.0.0.1:6379> SRANDMEMBER ms -1
  1) "6"
  127.0.0.1:6379> SRANDMEMBER ms 0
  (empty array)
  127.0.0.1:6379> SRANDMEMBER ms 5
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "6"
  127.0.0.1:6379> SRANDMEMBER ms 2
  1) "2"
  2) "4"
  127.0.0.1:6379> SMEMBERS ms
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "6"
  127.0.0.1:6379> SPOP ms 2
  1) "1"
  2) "6"
  127.0.0.1:6379> SMEMBERS ms
  1) "2"
  2) "3"
  3) "4"
  ```
- `SMOVE <src> <dst> <member>`: move member from src to dst, return 1 if moved, 0 if not moved
  ```bash
  127.0.0.1:6379> SMEMBERS ms
  1) "2"
  2) "3"
  3) "4"
  127.0.0.1:6379> SMOVE ms ns 2
  (integer) 1
  127.0.0.1:6379> SMEMBERS ms
  1) "3"
  2) "4"
  127.0.0.1:6379> SMEMBERS ns
  1) "2"
  127.0.0.1:6379> SADD ms 2
  (integer) 1
  127.0.0.1:6379> SMEMBERS ms
  1) "2"
  2) "3"
  3) "4"
  127.0.0.1:6379> SMOVE ms ns 2
  (integer) 1
  127.0.0.1:6379> SMEMBERS ms
  1) "3"
  2) "4"
  127.0.0.1:6379> SMEMBERS ns
  1) "2"
  ```

## Set Operations
- `SADD <key> <member> [<member2> ...]`: add member to set
- `SMEMBER <key> <member>`: check member exists or not
- `SISMEMBER <key> <member>`: check member exists or not, return 1 if exists, 0 if not exists
  ```bash
  127.0.0.1:6379> SADD ms 1 2 8
  (integer) 3
  127.0.0.1:6379> SMEMBERS ms
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "8"
  127.0.0.1:6379> SADD ns 3 8 9 10
  (integer) 4
  127.0.0.1:6379> SMEMBERS ns
  1) "2"
  2) "3"
  3) "8"
  4) "9"
  5) "10"
  ```
- ms: `[1,2,3,4,8]`
- ns: `[2,3,8,9,10]`

- `SDIFF <key> <key2>`: get difference of sets <font color="orange"> key1 - key2</font>
  ```bash
  127.0.0.1:6379> SDIFF ms ns
  1) "1"
  2) "4"
  127.0.0.1:6379> SDIFF ns ms
  1) "9"
  2) "10"
  ```
- `SUNION <key> <key2>`: get union of sets key1 + key2 (or key1 | key2)
  ```bash
  127.0.0.1:6379> SUNION ms ns
  1) "1"
  2) "2"
  3) "3"
  4) "4"
  5) "8"
  6) "9"
  7) "10"
  ```
- `SINTER <key> <key2>`: get intersection of sets key1 & key2
- `SINTERCARD <number> <key> [<key2> ...] [limit <limit>]`(<font color="orange">redis7+</font>): get intersection of the first `number` sets, and return the "count" of elements in result; if `limit` is set, return: "count" if count <= `limit` else `limit`
  ```bash
  127.0.0.1:6379> SINTER ms ns
  1) "2"
  2) "3"
  3) "8"
  127.0.0.1:6379> SINTERCARD 2 ms ns
  (integer) 3
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 0
  (integer) 3
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 1
  (integer) 1
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 2
  (integer) 2
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 3
  (integer) 3
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 4
  (integer) 3
  127.0.0.1:6379> SINTERCARD 2 ms ns limit 5
  (integer) 3
  ```

## Use Case:
1. `SPOP` for Lottery
2. twitter like:
   1. `SADD like:msg_id> <user_id>` to add liked user
   2. `SREM like:msg_id> <user_id>` to remove liked user
   3. `SCARD like:msg_id>`: get like count
   4. `SMEMBERS like:msg_id>`: get all liked user
3. use `SINTERCARD` to calculate same like user and recommend to user

# Zset
- Zset is a hashset with score

## Basic Commands
- `ZADD <key> <score> <member> [<score> <member> ...]`: add member to zset, if member exists, <font color="orange">score will be updated</font>
- `ZCARD <key>`: get zset size
- `ZSCORE <key> <member>`: get score of member
- `ZRANGE/ZREVRANGE <key> <start> <stop> [WITHSCORES]`: get members from zset ordered by score ascend/descend, if WITHSCORES, return members and scores
- `ZREVRANGE ...` == `ZRANGE ... REV`
  ```bash
  127.0.0.1:6379> ZADD mvp 15 a 9 b 12 c 6 d 7.8 e
  (integer) 5127.0.0.1:6379> ZCARD mvp
  (integer) 5
  127.0.0.1:6379> ZSCORE mvp a
  "15"
  127.0.0.1:6379> ZRANGE mvp 0 -1
  1) "d"
  2) "e"
  3) "b"
  4) "c"
  5) "a"
  127.0.0.1:6379> ZREVRANGE mvp 0 -1
  1) "a"
  2) "c"
  3) "b"
  4) "e"
  5) "d"
  127.0.0.1:6379> ZRANGE mvp 0 -1 REV WITHSCORES
  1) "a"
  2) "15"
  3) "c"
  4) "12"
  5) "b"
  6) "9"
  7) "e"
  8) "7.8"
  9) "d"
  10) "6"
  ```
- `ZRANGEBYSCORE/ZREVRANGEBYSCORE <key> <min> <max> [LIMIT <offset> <count>] [WITHSCORES]`: get members from zset ordered by score ascend/descend, if WITHSCORES, return members and scores
  ```bash
  127.0.0.1:6379> ZRANGEBYSCORE mvp 10 15 withscores
  1) "c"
  2) "12"
  3) "a"
  4) "15"
  127.0.0.1:6379> ZRANGEBYSCORE mvp 9 15 withscores
  1) "b"
  2) "9"
  3) "c"
  4) "12"
  5) "a"
  6) "15"
  127.0.0.1:6379> ZRANGEBYSCORE mvp 9 15 withscores LIMIT 0 2
  1) "b"
  2) "9"
  3) "c"
  4) "12"
  127.0.0.1:6379> ZRANGEBYSCORE mvp (9 15 withscores LIMIT 0 2
  1) "c"
  2) "12"
  3) "a"
  4) "15"
  127.0.0.1:6379> ZRANGEBYSCORE mvp (9 (15 withscores LIMIT 0 2
  1) "c"
  2) "12"
  ```
- `ZREM <key> <member> [<member2> ...]`: remove member from zset
  ```bash
  127.0.0.1:6379> ZREM mvp a e
  (integer) 2
  127.0.0.1:6379> ZRANGE mvp 0 -1 REV
  1) "c"
  2) "b"
  3) "d"
  ```

- `ZINCRBY <key> <increment> <member>`: increment score of member, if member not exists, add member to zset
  ```bash
  127.0.0.1:6379> ZRANGE mvp 0 -1 REV
  1) "c"
  2) "b"
  3) "d"
  127.0.0.1:6379> ZINCRBY mvp 5 a
  "5"
  127.0.0.1:6379> ZRANGE mvp 0 -1 REV
  1) "c"
  2) "b"
  3) "d"
  4) "a"
  127.0.0.1:6379> ZRANGE mvp 0 -1 REV WITHSCORES
  1) "c"
  2) "12"
  3) "b"
  4) "9"
  5) "d"
  6) "6"
  7) "a"
  8) "5"
  ```

- `ZCOUNT <key> <min> <max>`: count members in zset with score between min and max
  ```bash
  127.0.0.1:6379> ZCOUNT mvp 0 9
  (integer) 3
  127.0.0.1:6379> ZCOUNT mvp 0 (9
  (integer) 2
  ```

- `ZMPOP <numberkeys> <key> [<key2> ...] min|max [COUNT <count>]` <font color="orange">(redis7+)</font>: pop the minimum/maximum `count` members from zset
  ```bash
  127.0.0.1:6379> ZMPOP 1 mvp min count 3
  1) "mvp"
  2) 1) 1) "a"
        2) "5"
    2) 1) "d"
        2) "6"
    3) 1) "b"
        2) "9"
  127.0.0.1:6379> ZRANGE mvp 0 -1 withscores
  1) "c"
  2) "12"
  ```

- `ZRANK/ZREVRANK <key> <member> [WITHSCORE]`: get rank of member in zset, if member not exists, return nil
  ```bash
  127.0.0.1:6379> ZRANGE mvp 0 -1 withscores
  1) "d"
  2) "6"
  3) "e"
  4) "8"
  5) "b"
  6) "9"
  7) "c"
  8) "12"
  9) "a"
  10) "15"
  127.0.0.1:6379> ZRANK mvp v
  (nil)
  127.0.0.1:6379> ZRANK mvp a
  (integer) 4
  127.0.0.1:6379> ZRANK mvp a withscore
  1) (integer) 4
  2) "15"
  127.0.0.1:6379> ZREVRANK mvp a withscore
  1) (integer) 0
  2) "15
  ```

## Use Case:
1. `ZRANGE` for best sale or hot lives

# Bitmap
- bitmap : 010101000011101...

## Basic Commands
- `SETBIT <key> <offset> <value>`: set bit at offset to value, <font color="orange">offset >= 0 </font>
- `GETBIT <key> <offset>`: get bit at offset
  ```bash
  127.0.0.1:6379> SETBIT sign 0 1
  (integer) 0
  127.0.0.1:6379> SETBIT sign 1 1
  (integer) 0
  127.0.0.1:6379> SETBIT sign 0 0
  (integer) 1
  127.0.0.1:6379> GETBIT sign 1
  (integer) 1
  127.0.0.1:6379> GETBIT sign 0
  (integer) 0
  ```
- `STRLEN <key>`: get bitmap length, <font color="orange">length = ceil(bit_len / 8)</font>
  ```bash
  127.0.0.1:6379> STRLEN sign
  (integer) 1
  127.0.0.1:6379> GETBIT sign 0
  (integer) 0
  127.0.0.1:6379> GETBIT sign 1
  (integer) 1
  127.0.0.1:6379> SETBIT sign 11 0
  (integer) 0
  127.0.0.1:6379> STRLEN sign
  (integer) 2
  ```

- `BITCOUNT <key>`: count 1 in bitmap
  ```bash
  127.0.0.1:6379> BITCOUNT sign
  (integer) 1
  127.0.0.1:6379> SETBIT sign 3 1
  (integer) 0
  127.0.0.1:6379> SETBIT sign 6 1
  (integer) 0
  127.0.0.1:6379> BITCOUNT sign
  (integer) 3
  ```
- `BITOP AND|OR|XOR|NOT <destkey> <key> [key2] [key3] ...`

## Use Case:
1. continue sign days:
   1. `HSET uid:map 0 <user0:uid>`;`HSET uid:map 1 <user1:uid>`;...
   2. `SETBIT sign:20250101 0 1` means user0 sign at 20250101 and `SETBIT sign:20250101 0 0` means user0 not sign at 20250101
   3. `BITOP AND sign_two_days sign:20250101 sign:20250102`: get both-two-days-sign user bitmap

# HyperLogLog
- HyperLogLog is a probabilistic data structure for estimating the cardinality of a set
- HyperLogLog is for statictics, <font color="orange">could not get content of  item</font>
- HyperLogLog is not precise, could contains standard error of 0.81%

## Basic Commands
- `PFADD <key> <element> [<element> ...]`: add element to HyperLogLog, if element exists, do nothing
- `PFCOUNT <key> [<key2> ...]`: get HyperLogLog size
- `PFMERGE <destkey> <sourcekey> [<sourcekey2> ...]`: merge HyperLogLog
  ```bash
  127.0.0.1:6379> PFADD ids 0 1 2 36 6 68 4 3 5 47 8 9 6 1 4 6 21 0
  (integer) 1
  127.0.0.1:6379> PFCOUNT ids
  (integer) 13
  127.0.0.1:6379> PFADD ids2 2 1 3 5 6 4 78 9 2 0 5 6 1 4 6 
  (integer) 1
  127.0.0.1:6379> PFCOUNT ids2
  (integer) 9
  127.0.0.1:6379> PFMERGE ids3 ids ids2
  OK
  127.0.0.1:6379> PFCOUNT ids3
  (integer) 14
  ```

## Use Case:
1. unique visitor count



# Geo
- Geo is a data structure for storing and querying points in a 2D plane

## Basic Commands
- example position:
  - 139.66032 35.782643 point1
  - 139.707751 35.751717 point2
  - 139.862691 35.703664 point3
  - 139.750079 35.693141
- `GEOADD <key> <longitude> <latitude> <member> [<longitude> <latitude> <member> ...]`: add point to Geo, if member exists, update point
- `GEOPOS <key> <member> [<member> ...]`: get point of member
- `GEOHASH <key> <member1> <member2>`: get hash of member
- `GEODIST <key> <member1> <member2> [M(default)|KM|FT|MI]`: get distance between member1 and member2, unit: m, km, mi, ft
- `GEORADIUS <key> <longitude> <latitude> <radius> M|KM|FT|MI [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT <count>] [ASC|DESC](default not sorted, by add order) [STORE <dest>] [STOREDIST <destination>]`: get point in radius, if WITHCOORD, return point coordinate, if WITHDIST, return point distance, if WITHHASH, return point hash (mainly for debug), if STORE, store result to destination.
- `GEMRADIUSBYMEMBER <key> <member> <radius> M|KM|FT|MI [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT <count>] [ASC|DESC](default not sorted, by add order) [STORE <dest>] [STOREDIST <destination>]`: get point in radius by member, if WITHCOORD, return point coordinate, if WITHDIST, returnpoint distance, if WITHHASH, return point hash (mainly for debug

  ```bash
  127.0.0.1:6379> GEOADD pos 139.66032 35.782643 point1 139.707751 35.751717 point2 139.862691 35.703664 point3
  (integer) 3
  127.0.0.1:6379> GEOPOS pos point1
  1) 1) "139.66031938791275024"
    1) "35.78264213070043098"
  127.0.0.1:6379> GEOHASH pos point1
  1) "xn77d418cr0"
  127.0.0.1:6379> GEODIST pos point1 point2 M
  "5491.5048"
  127.0.0.1:6379> GEODIST pos point1 point2
  "5491.5048"
  127.0.0.1:6379> GEORADIUS pos 139.750079 35.693141 10000 M WITHDIST
  1) 1) "point2"
    1) "7553.4831"
  127.0.0.1:6379> GEORADIUS pos 139.750079 35.693141 20000 M WITHDIST
  1) 1) "point3"
    1) "10239.0834"
  2) 1) "point2"
    1) "7553.4831"
  3) 1) "point1"
    1) "12836.2504"
  127.0.0.1:6379> GEORADIUS pos 139.750079 35.693141 20000 M WITHDIST ASC
  1) 1) "point2"
    1) "7553.4831"
  2) 1) "point3"
    2) "10239.0834"
  3) 1) "point1"
    1) "12836.2504"
  127.0.0.1:6379> GEORADIUSBYMEMBER pos point2 200 KM WITHDIST ASC
  1) 1) "point2"
    1) "0.0000"
  2) 1) "point1"
    1) "5.4915"
  3) 1) "point3"
    1) "14.9763"
  ```

## Use Case:
1. find nearby people of destination

# Stream
- a MQ data structure

## special character:
- `>`: not sent yet to consumer id, update the customer id
- `*`: auto generate id
- `$`: only send the newest message, get the biggest msg id
- `- `/`+`: the min or max id
  
## Basic Commands
- `XADD <key> <id> <field> <value> [<field> <value> ...]`: add stream, if id is `*`, auto generate id, the generated msg id is `{timestamp(ms)}-{msg_count_in_the_ms}`
  ```bash
  127.0.0.1:6379> XADD msgs * user li msg "hello world"
  "1738562890769-0"
  127.0.0.1:6379> XADD msgs * user li msg "logined"
  "1738562910436-0"
  ```
- `XRANGE <key> <start> <end> [COUNT <count>]`: get stream
  ```bash
  127.0.0.1:6379> XRANGE msgs - +
  1) 1) "1738562890769-0"
    1) 1) "user"
        1) "li"
        2) "msg"
        3) "hello world"
  2) 1) "1738562910436-0"
    1) 1) "user"
        1) "li"
        2) "msg"
        3) "logined"
  ```

- `XREVRANGE <key> <start> <end> [COUNT <count>]`: get stream in reverse order
  ```bash
  127.0.0.1:6379> XREVRANGE msgs + -
  1) 1) "1738562910436-0"
    1) 1) "user"
        1) "li"
        2) "msg"
        3) "logined"
  2) 1) "1738562890769-0"
    1) 1) "user"
        1) "li"
        2) "msg"
        3) "hello world"
  ```

- `XLEN <key>`: get stream length
  ```bash
  127.0.0.1:6379> XLEN msgs
  (integer) 2
  ```

- `XDEL <key> <id> [<id> ...]`: delete stream by id
  ```bash
  127.0.0.1:6379> XRANGE msgs - +
  1) 1) "1738562890769-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "hello world"
  2) 1) "1738562910436-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "logined"
  3) 1) "1738563353298-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "test"
  127.0.0.1:6379> XDEL msgs 1738563353298-0
  (integer) 1
  127.0.0.1:6379> XRANGE msgs - +
  1) 1) "1738562890769-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "hello world"
  2) 1) "1738562910436-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "logined"
  ```
- `XTRIM <key> MAXLEN|MINID [=|~] <threshold> [LIMIT <count>]`: trim stream
  - <font color="orange">all the items in result will be append into stream and the origin items will lost</font>
  - `MAXLEN` will reserve the newest `threshold` msg
    ```bash
    127.0.0.1:6379> XRANGE msgs - +
    1) 1) "1738562890769-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "hello world"
    2) 1) "1738562910436-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "logined"
    3) 1) "1738563920892-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test"
    4) 1) "1738563927438-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test1"
    5) 1) "1738563929610-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test2"
    127.0.0.1:6379> XTRIM msgs MAXLEN 4
    (integer) 1
    127.0.0.1:6379> XRANGE msgs - +
    6) 1) "1738562910436-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "logined"
    7) 1) "1738563920892-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test"
    8) 1) "1738563927438-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test1"
    9) 1) "1738563929610-0"
       1) 1) "user"
          1) "li"
          2) "msg"
          3) "test2"
    ```
  - `MINID` will reserve the items new than `threshold`, `threshold` is an id
    ```bash
    127.0.0.1:6379> XTRIM msgs MINID 1738563920892-0
    (integer) 1
    127.0.0.1:6379> XRANGE msgs - +
    1) 1) "1738563920892-0"
      1) 1) "user"
          1) "li"
          2) "msg"
          3) "test"
    2) 1) "1738563927438-0"
      1) 1) "user"
          1) "li"
          2) "msg"
          3) "test1"
    3) 1) "1738563929610-0"
      1) 1) "user"
          1) "li"
          2) "msg"
          3) "test2"
    ```
- `XREAD [COUNT <count>] [BLOCK <milliseconds>] STREAMS <key> <key ...> <id> [<id> ...]`: read stream
  - `BLOCK <milliseconds>`: wait for new msg, always wait if `milliseconds` is 0 (could not be negative)
  ```bash
  127.0.0.1:6379> XREAD STREAMS msgs 0-0
  1) 1) "msgs"
    2) 1) 1) "1738563920892-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test"
        2) 1) "1738563927438-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test1"
        3) 1) "1738563929610-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test2"
        4) 1) "1738564446910-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "hello"
        5) 1) "1738564450674-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "world"
  127.0.0.1:6379> XREAD COUNT 2 STREAMS msgs 0-0
  1) 1) "msgs"
    2) 1) 1) "1738563920892-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test"
        2) 1) "1738563927438-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test1"
  ```
- `XGROUP CREATE <key> <groupname> <id> [MKSTREAM]`: create group
- `XREADGROUP [COUNT <count>] [BLOCK <milliseconds>] [NOACK] GROUP <groupname> <consumername> STREAMS <key> <id> [<id> ...]`: read stream by group
  - <font color="orange">the same msg could not be read twice by customers in the same group</font>
  ```bash
  127.0.0.1:6379> XRANGE msgs - +
  1) 1) "1738563920892-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "test"
  2) 1) "1738563927438-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "test1"
  3) 1) "1738563929610-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "test2"
  4) 1) "1738564446910-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "hello"
  5) 1) "1738564450674-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "world"
  6) 1) "1738565413243-0"
    2) 1) "user"
        2) "li"
        3) "msg"
        4) "hello"
  127.0.0.1:6379> XGROUP CREATE msgs msgs_g 0
  OK
  127.0.0.1:6379> XGROUP CREATE msgs msgs_g1 $
  OK
  127.0.0.1:6379> XREADGROUP COUNT 1 GROUP msgs_g cs1 STREAMS msgs >
  1) 1) "msgs"
    2) 1) 1) "1738563920892-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test"
  127.0.0.1:6379> XREADGROUP COUNT 1 GROUP msgs_g cs1 STREAMS msgs >
  1) 1) "msgs"
    2) 1) 1) "1738563927438-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test1"
  127.0.0.1:6379> XREADGROUP COUNT 1 GROUP msgs_g cs2 STREAMS msgs >
  1) 1) "msgs"
    2) 1) 1) "1738563929610-0"
          2) 1) "user"
              2) "li"
              3) "msg"
              4) "test2"
  ```
- `XPENDING key group [[IDLE <min-idle-time>] <start> <end> <count> [<consumer>]]`: 
  ```bash
  127.0.0.1:6379> XPENDING msgs msgs_g
  1) (integer) 3  # number of msg read by group
  2) "1738563920892-0"  # min msg id read by group
  3) "1738563929610-0"  # max msg id read by group
  4) 1) 1) "cs1"
        1) "2"  # number of msg read by cs1
    1) 1) "cs2"
        1) "1"  # number of msg read by cs2
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10
  1) 1) "1738563920892-0"
    2) "cs1"
    3) (integer) 336472
    4) (integer) 1
  2) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 332363
    4) (integer) 1
  3) 1) "1738563929610-0"
    2) "cs2"
    3) (integer) 328063
    4) (integer) 1
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10 cs1
  1) 1) "1738563920892-0"
    2) "cs1"
    3) (integer) 343157
    4) (integer) 1
  2) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 339048
    4) (integer) 1
  ```

- `XACK key group id [id ...]`: ack msg
  ```bash
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10
  1) 1) "1738563920892-0"
    2) "cs1"
    3) (integer) 336472
    4) (integer) 1
  2) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 332363
    4) (integer) 1
  3) 1) "1738563929610-0"
    2) "cs2"
    3) (integer) 328063
    4) (integer) 1
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10 cs1
  1) 1) "1738563920892-0"
    2) "cs1"
    3) (integer) 343157
    4) (integer) 1
  2) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 339048
    4) (integer) 1
  127.0.0.1:6379> XACK msgs msgs_g 1738563920892-0
  (integer) 1
  127.0.0.1:6379> XPENDING msgs msgs_g
  1) (integer) 2
  2) "1738563927438-0"
  3) "1738563929610-0"
  4) 1) 1) "cs1"
        2) "1"
    2) 1) "cs2"
        2) "1"
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10 cs1
  1) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 496652
    4) (integer) 1
  127.0.0.1:6379> XPENDING msgs msgs_g - + 10
  1) 1) "1738563927438-0"
    2) "cs1"
    3) (integer) 505170
    4) (integer) 1
  2) 1) "1738563929610-0"
    2) "cs2"
    3) (integer) 500870
    4) (integer) 1
  ```

# bitfield
- bitfield could modify a string in bit level

## Basic Comands

- `BITFIELD <key> [[GET type offest] ...]|[OVERFLOW WRAP|SAT`: get one bit from key value at offset position
- `BITFIELD <key> SET type offset value [GET type offset] ...`: set one bit to key value at offset position
  ```bash
  127.0.0.1:6379> SET user li
  OK
  127.0.0.1:6379> BITFIELD user GET i8 0
  1) (integer) 108
  127.0.0.1:6379> BITFIELD user SET i8 0 104
  1) (integer) 108
  127.0.0.1:6379> GET user
  "hi"
  ```
- `BITFIELD <key> INCRBY type offset increment [GET type offset] ...`: increment one bit to key value at offset position
- `BITFIELD <key> OVERFLOW WRAP|SAT|FAIL`: set overflow mode
- `BITFIELD <key> TEST OVERFLOW WRAP|SAT|FAIL (SET type offset value)|(GET type offset)`: handle value first to avoid overflow before set/get
  - if VOERFLOW set to `FAIL`, it will return error if overflow
  - if VOERFLOW set to `WRAP`, it will wrap around if overflow
  - if VOERFLOW set to `SAT`, it will set to max/min if up/down overflow

# RDB
- name of rdb file is `dump.rdb`
## config
- `save 300 10` in config means save rdb file every 300s if there are at least 10 keys changed

- default setting:
  1. old version redis:
     ```conf
     save 900 1
     save 300 10
     save 60 10000
     ```
  2. redis 6.2+
      ```conf
      save 3600 1
      save 300 100
      save 60 10000
      ```
- specify `dir /path/to/dir` in config to specify rdb file save <font color="orange">dir</font>

## recovery from dumped backup
- move the target backup file before redis shutdown
- shutdown redis and overwrite moved backup file to redis backup file (redis will automaticly backup in shutdown)
- after start redis, data will automaticly restore from backup file

## manual backup
- `SAVE` and `BGSAVE` command: redis fork a child process to write rdb file
  - `SAVE` will <font color="orange">block</font> main process which could not be able to access from backend
  - <font color="orange">ONLY `BGSAVE` is allowed to use in prodction environment</font>
- `LASTSAVE` command: get last rdb backup time

## Advantage
- RDB 的配置和使用简单，备份过程对 Redis 性能的影响较小。
- RDB 文件是紧凑且高效的二进制文件，对于大规模数据集恢复速度快。
- RDB 文件是一个完整的快照，适合用于灾难恢复或需要完整数据备份的场景。
- RDB 文件是压缩后的二进制文件，相比 AOF 日志文件，它占用的空间更少。

## Disadvantage
- 如果 Redis 实例在两次快照之间崩溃，那么最后一次快照之后的数据将会丢失。
- RDB 是全量备份，fork时内存全量复制，将导致占用内存翻倍，同时备份会影响IO性能。
- 备份操作 fork 期间可能会增加系统开销，导致服务器请求延迟，尤其是在大数据量的情况下。

## repair dunp file
- `redis-check-rdb /path/to/dump/file`: check rdb file and repair it

## times of backup
- amount of changed data reach the rdb save config
- redis will automaticly backup when `shutdown`
- redis will backup when `SAVE` or `BGSAVE` command
- redis will backup when `FLUSHDB` or `FLUSHALL` command

## disable RDB
- `CONFIG SET save ""` to disable rdb backup in redis-cli
- `redis-cli CONFIG SET save ""` to disable rdb backup in bash
- set `save ""` in config file or annotate `save` lines in config file

## other config of rdb
- `rdbcompression`: whether to compress rdb file, default is `yes`
- `stop-writes-on-bgsave-error`: whether to stop redis when bgsave error, default is `yes`
- `rdbchecksum`: whether to crc64 check rdb file, default is `yes`


# AOF (Append Only File)
- AOF is a log file that records all the write commands that have been executed on the Redis server.
- AOF File allows <font color="orange">append</font> operation only.
- name of AOF backup file is `appendonly.aof`

## config config
- `appendonly`: whether to enable AOF, default is `no`
- `appendfsync`: when to flush AOF to disk, default is `everysec`
  - `everysec`: flush AOF to disk every second
  - `always`: flush AOF to disk every write command
  - `no`: flushed by operation system
- save path:
  - redis6: the same as rdb dir(`dir /path/to/dir`in config)
    - `dir`: the same as rdb dir
    - `appendfilename xxx.aof`: name of aof file
  - redis7: add `appenddirname` in config, the final path is `dir + /appenddirname + /xxx.aof`
    - `dir`: the same as rdb dir
    - `appenddirname xxx`: aof dir under `dir`
    - multipart aof
      - file `appendonly.aof.1.base.rdb`: base aof file name
      - file `appendonly.aof.1.incr.aof`: increment file name
      - file `appendonly.aof.2.incr.aof`: increment file name, "2" means rewited from "1"
      - file `appendonly.aof.manifest`: manifest file
  
## repair aof file
- `redis-check-aof --fix /path/to/aof/file`: check aof file and repair it, only repain `incr` file

## Advantage
- AOF 文件记录的是每个写命令，因此可以精确地恢复到任意时间点的数据状态。
- 对于需要频繁写入且要求高数据完整性的场景，AOF 提供了更好的保障。
- AOF 的 everysec 模式可以在几乎不影响性能的情况下提供较高的数据安全性，适合用于生产环境中的高可用性需求。
- AOF 文件是纯文本格式，包含的是 Redis 命令，因此便于人工检查和编辑。如果需要手动调整或修复数据，可以直接修改 AOF 文件。

## Disadvantage
- AOF 文件记录了所有的写操作，因此随着时间的推移，文件会变得非常大，占用较多的磁盘空间。尽管可以通过 BGREWRITEAOF 命令进行压缩优化，但仍然无法避免文件逐渐增大的趋势。
- 相比 RDB 文件，AOF 文件的恢复速度较慢，因为它需要逐条重放写命令。对于大规模数据集，AOF 的恢复过程可能会消耗较多的时间和资源。
- AOF 文件会随着写操作的增加而变得冗长，需要定期进行重写（BGREWRITEAOF）以保持文件大小可控。文件重写过程中会消耗额外的 CPU 和内存资源，尤其是在大数据量的情况下。
- AOF 文件记录的是增量变化，不适合用于频繁的全量备份。
- efficient:
  - runing: AOF < RDB
  - sync
    - aof_sync_always < rdb
    - aof_sync_everysec > rdb
    - aof_sync_no = rdb


## aof rewrite
- config:
  - `auto-aof-rewrite-percentage 100`: percentage of `incr` aof file size compared with `incr` after last rewrite, default is 100
  - `auto-aof-rewrite-min-size 64mb`: min size of `incr` aof file to rewrite, default is 64mb
  - rewrite will execute only on <font color="orange">both</font> `auto-aof-rewrite-percentage` and `auto-aof-rewrite-min-size` meet

- manual rewrite:
  - `BGREWRITEAOF` in redis-cli
  - `redis-cli BGREWRITEAOF` in bash


## other config of aof
- `no-appendfsync-on-rewrite`: whether to disable fsync when rewrite aof, default is `no`


# RDB and AOF
- aof file has higher priority than rdb file on data recovery

## config
- `aof-use-rdb-preamble yes`: whether to enable both aof and rdb at the same time

## work
- on data recovery, data will load from rdb file, then load the increment data from aof file
- on aof rewrite, data will be dumped into rdb file, then aof incr file will be clear
- on backup, data will write into aof file only

# pure cache (disable durable on redis)
- set both `appendonly no` and `save ""` will disable bot aof and rdb, redis will totally run in RAM
- `BGSAVE` / `SAVE` / `BEREWRITE` will still work by manually execute


# Transaction
Redis 事务通过 `MULTI`, `EXEC`, `DISCARD` 和 `WATCH` 命令来实现。Redis 事务并不支持传统数据库中的 ACID 特性，而是提供了一种简单的命令队列执行机制。

Redis Transaction put command lines into a queue to execute, but:
1. <font color="orange">do not ensure atomic (all the command success of fail)</font>
2. <font color="orange">redis does not support rollback</font>
3. <font color="orange">command execute in redis is serial execution, so there's no need of isolation</font>
4. <font color="orange">could ensure no other command execute while transaction executing</font>

## Basic Command

- **MULTI**：标记事务的开始，之后的所有命令都会被放入队列中，直到遇到 `EXEC`。
  
- **EXEC**：执行所有之前在 `MULTI` 中排队的命令，并返回结果列表。如果在 `MULTI` 和 `EXEC` 之间有错误发生，事务中的其他命令仍然会被执行，除非使用了 `DISCARD`。

- **DISCARD**：取消事务，放弃执行所有在 `MULTI` 中排队的命令。

- **WATCH**：用于乐观锁，监视一个或多个键，在执行 `EXEC` 之前检查这些键是否被修改过。如果有任何一个被修改，则整个事务会失败。

  ```bash
  127.0.0.1:6379> MULTI
  OK
  127.0.0.1:6379> SET key1 "Hello"
  QUEUED
  127.0.0.1:6379> SET key2 "World"
  QUEUED
  127.0.0.1:6379> EXEC
  1) OK
  2) OK
  ```

## ERROR Handling

- 在 `EXEC` 之前调用 `DISCARD` 命令，所有 `MULTI` 之后的命令都会被取消。
- 如果有命令有语法错误，那么在添加到队列时就会报错，同时该队列直接取消，之后 `EXEC` 会直接报错`EXECABORT`，所有命令均不执行。
- 如果在 `MULTI` 和 `EXEC` 之间的某个命令执行错误，那么 `EXEC` 执行时会报告该错误，但其他（前后）命令仍会被执行。
- 如果希望在遇到任何错误时停止整个事务，可以在代码逻辑中进行判断并调用 `DISCARD`。

## WATCH and UNWATCH
<font color="orange">乐观锁策略：当前提交的版本必须大于当前记录的版本才可以执行提交的更新</font>

- `WATCH <key> [key ...]`: 监视一个或多个键，在执行 EXEC 之前检查这些键是否被修改过。如果有任何一个被修改，则整个事务会失败。
  ```bash
  # 初始化库存
  127.0.0.1:6379> SET inventory:product1 10
  OK
  # 开始事务前先 WATCH 库存键
  127.0.0.1:6379> WATCH inventory:product1
  OK
  # 检查当前库存
  127.0.0.1:6379> GET inventory:product1
  "10"
  # 开始事务
  127.0.0.1:6379> MULTI
  OK
  # 扣减库存
  127.0.0.1:6379> DECR inventory:product1
  QUEUED
  # 提交事务
  127.0.0.1:6379> EXEC
  1) (integer) 9
  # 再次检查库存
  127.0.0.1:6379> GET inventory:product1
  "9"
  ```
- if `inventory:product1` be modified after WATCH:
  ```bash
  # 提交事务
  127.0.0.1:6379> EXEC
  (nil)
  ```
- `UNWATCH`: use before `MULTI` after `WATCH` some keys to set all watched keys not being watched

# Redis Pipe
- Redis Pipe is a feature that allows you to execute multiple commands in a single request.

## Usage
create command lines in `pip_cmd.txt`:
```bash
HSET user:011 name 011
HSET user:011 password 111111
HSET user:011 email 011@11.11
```
cat `pip_cmd.txt` into redis-cli:
```bash
❯ cat pip_cmd.txt | redis-cli -a redis --pipe
Warning: Using a password with '-a' or '-u' option on the command line interface may not be safe.
All data transferred. Waiting for the last reply...
Last reply received from server.
errors: 0, replies: 3
```
## Tips
- pipe is not atomic, even if one command failed, the whole pipe will continue
- A pipe has additional resource costs. It is better if one pipe does not contain more than 10k lines.


# PUB/SUB
发布/订阅（Pub/Sub）是一种消息通信模式，允许客户端订阅特定的频道（channels），并接收其他客户端发布到这些频道的消息。

## 基本概念

1. **频道（Channel）**：消息发布的主题，客户端可以订阅一个或多个频道。
2. **发布者（Publisher）**：向频道发送消息的客户端。
3. **订阅者（Subscriber）**：接收频道消息的客户端。

## 基本命令

- **SUBSCRIBE <channel> [channel ...]**：订阅一个或多个频道。
- **PSUBSCRIBE <pattern> [pattern ...]**：订阅与给定模式匹配的所有频道。
- **PUBLISH <channel> <message>**：向指定的频道发布消息。
- **UNSUBSCRIBE [channel [channel ...]]**：取消订阅一个或多个频道。
- **PUNSUBSCRIBE [pattern [pattern ...]]**：取消订阅与给定模式匹配的所有频道。
- **PUBSUB NUMPAT**: 返回模式订阅的模式数量。

## 示例

### 订阅频道

假设我们有一个客户端订阅名为 `news` 的频道：

```bash
127.0.0.1:6379> SUBSCRIBE news
Reading messages... (press Ctrl-C to quit)
1) "subscribe"
2) "news"
3) (integer) 1
```

### 发布消息

在另一个客户端中，我们可以向 `news` 频道发布消息：

```bash
127.0.0.1:6379> PUBLISH news "Hello, Redis!"
(integer) 1
```

### 接收消息

订阅客户端会收到发布的消息：

```bash
1) "message"
2) "news"
3) "Hello, Redis!"
```

### 取消订阅

要取消订阅，可以使用 `UNSUBSCRIBE` 命令：

```bash
127.0.0.1:6379> UNSUBSCRIBE news
1) "unsubscribe"
2) "news"
3) (integer) 0
```

### 模式订阅

模式订阅允许客户端订阅符合特定模式的所有频道。例如，订阅所有以 `news:` 开头的频道：

```bash
127.0.0.1:6379> PSUBSCRIBE news:*
Reading messages... (press Ctrl-C to quit)
1) "psubscribe"
2) "news:*"
3) (integer) 1
```

发布消息到匹配的频道：

```bash
127.0.0.1:6379> PUBLISH news:sports "Sports news update"
(integer) 1
127.0.0.1:6379> PUBLISH news:politics "Politics news update"
(integer) 1
```

订阅客户端会收到所有匹配的消息：

```bash
1) "pmessage"
2) "news:*"
3) "news:sports"
4) "Sports news update"
1) "pmessage"
2) "news:*"
3) "news:politics"
4) "Politics news update"
```

## 注意事项

- 发布/订阅模式是无阻塞的，发布者不会等待订阅者的响应。
- 订阅者可以订阅多个频道或模式。
- 发布/订阅模式适用于实时消息传递和事件通知系统。
- <font color="orange">SUB has no ack</font>
- <font color="orange">SUB has no durable</font>

