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
  - [INCR / INCRBY / DECR / DECRBY (for number)](#incr--incrby--decr--decrby-for-number)

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
- `SET key value [EX seconds] [PX milliseconds] [EXAT timestamp] [PXAT timestamp_milliseconds] [NX|XX] [KEEPTTL] [get]`
  - `SET user:age 30`
  - `SET user:age 30 EX 3600` set key value and expire after 3600 seconds
  - `SET user:age 30 PX 3600000` set key value and expire after 3600000 milliseconds
  - `SET user:age 30 NX` set key value if not exists
  - `SET user:age 30 XX` set key value if exists
  - `SET user:age 30 EXAT 1620000000` set key value and expire at 1620000000 (unix timestamp)
  - `SET user:age 30 get` set new key value and return old value
  - `SET user:age 30 KEEPTTL` set key value and keep ttl
- `MSET key value [key value ...]`: set multiple key value (None exec if any error)
- `MGET key [key ...]`: get multiple key value
- `MSETNX key value [key value ...]`: set multiple key value if not exists (None exec if any error)

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
```redis
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

## INCR / INCRBY / DECR / DECRBY (for number)

