- [redis config](#redis-config)
- [reids cli](#reids-cli)
  - [hello world](#hello-world)
- [Basic data type](#basic-data-type)
  - [String](#string)
  - [List](#list)
  - [Hash](#hash)

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
each hash key maps to multiple hash fields like:  hash key -> {field1: value1, field2: value2, ...}

```redis
HSET user:info name "Alice" age 25 city "Beijing"
HGET user:info name
HGETALL user:info
```