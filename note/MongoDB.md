- [Tips](#tips)
- [Mongodb install](#mongodb-install)
  - [install in ubuntu](#install-in-ubuntu)
  - [install in docker](#install-in-docker)
- [Mongosh](#mongosh)
  - [basic operation](#basic-operation)
  - [backup / restore / export / import](#backup--restore--export--import)
    - [backup data](#backup-data)
    - [restore data](#restore-data)
    - [export data](#export-data)
    - [import data](#import-data)
  - [Roles](#roles)
    - [内置角色](#内置角色)
      - [数据库用户角色（Database User Roles)](#数据库用户角色database-user-roles)
      - [数据库管理角色（Database Admininstration Roles)](#数据库管理角色database-admininstration-roles)
      - [​备份和还原角色（Backup and Restoration Roles)​​​​​​​](#备份和还原角色backup-and-restoration-roles)
      - [跨库角色（All-Database Roles)](#跨库角色all-database-roles)
      - [集群管理角色（Cluster Administration Roles)](#集群管理角色cluster-administration-roles)
    - [自定义角色](#自定义角色)
    - [角色管理命令](#角色管理命令)
  - [Users](#users)
    - [切换到管理数据库](#切换到管理数据库)
    - [创建管理员用户](#创建管理员用户)
    - [认证管理员用户](#认证管理员用户)
    - [创建其他数据库用户](#创建其他数据库用户)
    - [查看用户列表](#查看用户列表)
    - [修改用户密码](#修改用户密码)
    - [删除用户](#删除用户)
    - [查看角色](#查看角色)
    - [将角色分配给用户](#将角色分配给用户)
    - [撤销用户角色](#撤销用户角色)
  - [Collections](#collections)
    - [create](#create)
    - [show](#show)
    - [drop](#drop)
    - [collection info](#collection-info)
  - [Documents](#documents)
    - [ObjectID](#objectid)
    - [基本数据类型](#基本数据类型)
  - [CRUD](#crud)
    - [Insert](#insert)
      - [insertOne](#insertone)
      - [insertMany](#insertmany)
    - [Update](#update)
      - [updateOne](#updateone)
      - [updateMany](#updatemany)
      - [replaceOne](#replaceone)
    - [Delete](#delete)
      - [deleteMany](#deletemany)
      - [deleteOne](#deleteone)
    - [Query](#query)
      - [find](#find)
      - [findOne](#findone)
      - [sort / limit / skip](#sort--limit--skip)
    - [Condition of Query](#condition-of-query)
  - [Index](#index)
    - [createIndex](#createindex)
    - [dropIndex / dropIndexes](#dropindex--dropindexes)
  - [Aggregate \& Pipeline](#aggregate--pipeline)
    - [Aggregate](#aggregate)
    - [Pipeline](#pipeline)
      - [常见聚合阶段](#常见聚合阶段)
      - [示例](#示例)
    - [常见的 MongoDB 聚合案例](#常见的-mongodb-聚合案例)
      - [1. **按字段分组并计算总和**](#1-按字段分组并计算总和)
      - [2. **按字段分组并计算平均值**](#2-按字段分组并计算平均值)
- [查询操作符](#查询操作符)
  - [查询选择器](#查询选择器)
    - [Usage](#usage)
    - [对比](#对比)
      - [Usage](#usage-1)
    - [逻辑](#逻辑)
      - [Usage](#usage-2)
    - [元素](#元素)
      - [MongoDB字段类型](#mongodb字段类型)
      - [Usage](#usage-3)
    - [求值](#求值)
      - [Usage](#usage-4)
    - [地理空间](#地理空间)
    - [阵列](#阵列)
      - [Usage](#usage-5)
    - [Bitwise](#bitwise)
  - [投影操作符](#投影操作符)
      - [Usage](#usage-6)
  - [其他操作符](#其他操作符)
      - [Usage](#usage-7)
- [更新操作符](#更新操作符)
  - [语法](#语法)
  - [行为](#行为)
  - [字段](#字段)
  - [阵列](#阵列-1)
    - [操作符](#操作符)
    - [Modifiers](#modifiers)
  - [Bitwise](#bitwise-1)
  - [常见更新表达式](#常见更新表达式)
    - [示例](#示例-1)
- [聚合操作符](#聚合操作符)
  - [Usage](#usage-8)
  - [算术表达式操作符](#算术表达式操作符)
  - [数组表达式操作符](#数组表达式操作符)
  - [按位操作符](#按位操作符)
  - [布尔表达式操作符](#布尔表达式操作符)
  - [比较表达式操作符](#比较表达式操作符)
  - [条件表达式操作符](#条件表达式操作符)
  - [自定义聚合表达式操作符](#自定义聚合表达式操作符)
  - [数据大小操作符](#数据大小操作符)
  - [日期表达式操作符](#日期表达式操作符)
  - [字面值表达式操作符](#字面值表达式操作符)
  - [其他操作符](#其他操作符-1)
  - [对象表达式操作符](#对象表达式操作符)
  - [集表达式操作符](#集表达式操作符)
  - [字符串表达式操作符](#字符串表达式操作符)
  - [文本表达式操作符](#文本表达式操作符)
  - [时间戳表达式操作符](#时间戳表达式操作符)
  - [三角函数表达式操作符](#三角函数表达式操作符)
  - [类型表达式操作符](#类型表达式操作符)
  - [累加器 (`$group, $bucket, $bucketAuto, $setWindowFields`)](#累加器-group-bucket-bucketauto-setwindowfields)
  - [累加器（其他阶段中）](#累加器其他阶段中)
  - [变量表达式操作符](#变量表达式操作符)
  - [窗口运算符](#窗口运算符)
- [聚合阶段](#聚合阶段)
  - [db.collection.aggregate() Stages](#dbcollectionaggregate-stages)
  - [注意](#注意)
  - [db.aggregate() Stages](#dbaggregate-stages)
  - [可更新的阶段](#可更新的阶段)

# Tips
1. `$addToSet` and `$push`
   - `$push`: 无条件地添加元素，可能导致数组中出现重复项。
   - `$addToSet`: 仅在元素不存在时添加，确保数组中没有重复项。

# Mongodb install
## install in ubuntu
```bash
sudo apt-get install gnupg curl
curl -fsSL https://www.mongodb.org/static/pgp/server-8.0.asc | \
   sudo gpg -o /usr/share/keyrings/mongodb-server-8.0.gpg \
   --dearmor
echo "deb [ arch=amd64,arm64 signed-by=/usr/share/keyrings/mongodb-server-8.0.gpg ] https://repo.mongodb.org/apt/ubuntu noble/mongodb-org/8.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-8.0.list
sudo apt-get update
sudo apt-get install -y mongodb-org
sudo systemctl daemon-reload
sudo systemctl enable mongod --now
```

- 数据目录: `/var/lib/mongodb`
- 日志目录: `/var/log/mongodb`
- 配置文件: `/etc/mongod.conf`


## install in docker
```bash
docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest
```

# Mongosh
Mongosh grammer as JavaScript.
## basic operation
- `db`: print current database
- `use <db>`: switch to database
- `show`
  - `show databases` / `show dbs`: show all databases
  - `show collections` / `show tables`: show all collections
  - `show profile`: show db.system.profile
  - `show users`: show all users
  - `show roles`: show all roles, role is a set of users with permissions
  - `show logs`: show all log name
  - `show log <logname>`: show log content
- `it [<cursor>]`: iterate cursor, `<cursor>` could be ignored
- `load <file>`: load JavaScript file into mongosh
- `cls`: clear screen
- `print(xxx(a,b))`
- `sleep <ms>`: sleep for ms milliseconds
- `db.getMongo()`: get mongodb connection url

## backup / restore / export / import
### backup data
```bash
mongodump -h <host:port> -d <db> -u <user> -p <password> -o <output_dir>
```
### restore data
```bash
mongorestore -h <host:port> -d <db> -u <user> -p <password> --dir <input_dir> --drop
```
- `--drop`: drop if target collection exists before restore
### export data
```bash
mongoexport -h <host:port> -d <db> -c <collection> -u <user> -p <password> -o <output_file> --type json/csv -f <field1,field2,...>
```
- `--type json/csv`: export to json or csv
- `-f <field1,field2,...>`: export only specified fields
### import data
```bash
mongoimport -h <host:port> -d <db> -c <collection> -u <user> -p <password> -type json/csv --file <input_file> --headerline --jsonArray --drop
```
- `--headerline`: use headerline in csv file
- `--jsonArray`: import json array

## Roles
### 内置角色
#### 数据库用户角色（Database User Roles)
- read : 授权User只读数据的权限，允许用户读取指定的数据库
- readWrite  授权User读/写数据的权限，允许用户读/写指定的数据库
#### 数据库管理角色（Database Admininstration Roles)
- dbAdmin：在当前的数据库中执行管理操作，如索引的创建、删除、统计、查看等
- dbOwner：在当前的数据库中执行任意操作，增、删、改、查等
- userAdmin ：在当前的数据库中管理User，创建、删除和管理用户。
#### ​备份和还原角色（Backup and Restoration Roles)​​​​​​​
- backup
- restore
#### 跨库角色（All-Database Roles)
- readAnyDatabase：授权在所有的数据库上读取数据的权限，只在 admin 中可用
- readWriteAnyDatabase：授权在所有的数据库上读写数据的权限，只在 admin 中可用
- userAdminAnyDatabase：授权在所有的数据库上管理User的权限，只在 admin中可用
- dbAdminAnyDatabase： 授权管理所有数据库的权限，只在 admin 中可用
#### 集群管理角色（Cluster Administration Roles)
- clusterAdmin：授权管理集群的最高权限，只在 admin中可用
- clusterManager：授权管理和监控集群的权限
- clusterMonoitor：授权监控集群的权限，对监控工具具有readonly的权限
- hostManager：管理server

### 自定义角色
除了内置角色外，MongoDB 还允许用户创建自定义角色。自定义角色可以包含特定的权限组合，以满足特定的安全需求。创建自定义角色的示例如下：

```javascript
use admin
db.createRole({
  role: "customRole",
  privileges: [
    { resource: { db: "myDatabase", collection: "" }, actions: [ "find", "insert" ] },
    { resource: { db: "myDatabase", collection: "myCollection" }, actions: [ "update", "remove" ] }
  ],
  roles: []
})
```

### 角色管理命令
以下是一些常用的命令，用于管理角色：

- **创建角色**：
  ```javascript
  db.createRole({ role: "roleName", privileges: [], roles: [] })
  ```

- **查看角色**：
  ```javascript
  db.getRoles({ showBuiltinRoles: true })
  ```

- **修改角色**：
  ```javascript
  db.updateRole("roleName", { privileges: [], roles: [] })
  ```

- **删除角色**：
  ```javascript
  db.dropRole("roleName")
  ```

## Users
### 切换到管理数据库
用户管理通常在 `admin` 数据库中进行。使用以下命令切换到 `admin` 数据库：

```javascript
use admin
```

### 创建管理员用户
如果还没有管理员用户，首先需要创建一个管理员：

```javascript
db.createUser({
  user: "adminUser",
  pwd: "adminPassword",
  roles: [ { role: "userAdminAnyDatabase", db: "admin" } ]
})
```

### 认证管理员用户
创建管理员用户后需要进行认证。使用以下命令进行认证：

```javascript
db.auth("adminUser", "adminPassword")
```

### 创建其他数据库用户
在认证为管理员后，为其他数据库创建用户。例如，为 `myDatabase` 数据库创建一个用户：

```javascript
use myDatabase
db.createUser({
  user: "myUser",
  pwd: "myPassword",
  roles: [ { role: "readWrite", db: "myDatabase" } ]
})
```

### 查看用户列表
查看特定数据库中的用户列表。例如，查看 `myDatabase` 数据库中的用户：

```javascript
use myDatabase
db.getUsers()
```

### 修改用户密码
修改用户的密码：

```javascript
use myDatabase
db.changeUserPassword("myUser", "newPassword")
```

### 删除用户
删除一个用户：

```javascript
use myDatabase
db.dropUser("myUser")
```

### 查看角色
查看 MongoDB 中可用的角色列表：

```javascript
use admin
db.getRoles({showBuiltinRoles: true})
```

### 将角色分配给用户
将自定义角色分配给用户：

```javascript
use myDatabase
db.grantRolesToUser("myUser", ["customRole"])
```

### 撤销用户角色
如果需要撤销用户的某个角色，可以使用以下命令：

```javascript
use myDatabase
db.revokeRolesFromUser("myUser", ["customRole"])
```

## Collections
- there's no need to create collections manually but some times collectinos need some options to specify
### create
```javascript
db.createCollection("myCollection", {
  capped: true,
  size: 100000,
  max: 5000
})
```
- `capped`: if true, the collection will be a capped collection, which means it will be a fixed size collection. `capped` is usually used in log collection.
- `max`: max number of documents in the collection, if the collection is capped, it will delete the oldest documents when the collection reaches the max size
- `size`: max size of the collection, if the collection is capped, it will delete the oldest documents when the collection reaches the max size

### show
- use `show collections` or `db.getCollectionNames()` to show all collections in current database

### drop
- `db.<collection>.drop()`: drop a collection

### collection info
- `db.<collection>.stats()`: show collection info
- `db.printCollections()`: show all collections in current database
- count
  - `db.<collection>.count()`: (deprecated) show collection size
  - `db.<collection>.countDocuments()`: show collection size
  - `db.<collection>.estimatedDocumentCount()`: show collection size
- `db.<collection>.totalSize()`: show collection size

## Documents

### ObjectID
ObjectID 是 MongoDB 中的一个特殊数据类型，用于唯一标识数据库中的文档。它由 12 个字节组成，其中前 4 个字节是时间戳，5 个字节的客服端生成的随机数，最后 3 个字节是进程标识符和自增计数器。

### 基本数据类型

1. **String**
   - 用于存储文本数据。
   - 示例：
     ```json
     { "name": "Alice" }
     ```

2. **Integer**
   - 用于存储整数值。MongoDB 支持 32 位整数（`int32`）和 64 位整数（`int64`）。
   - 示例：
     ```json
     { "age": 30 }
     ```

3. **Double**
   - 用于存储浮点数值。
   - 示例：
     ```json
     { "height": 5.9 }
     ```

4. **Boolean**
   - 用于存储布尔值（`true` 或 `false`）。
   - 示例：
     ```json
     { "isStudent": true }
     ```

5. **Object**
   - 用于存储嵌套文档。
   - 示例：
     ```json
     { "address": { "street": "123 Main St", "city": "New York" } }
     ```

6. **Array**
   - 用于存储有序的值列表，可以包含不同类型的值。
   - 示例：
     ```json
     { "tags": ["mongodb", "database", "nosql"] }
     ```

7. **Binary Data**
   - 用于存储二进制数据。
   - 示例：
     ```json
     { "image": BinData(0, "aGVsbG8gd29ybGQ=") }
     ```

8. **ObjectId**
   - 用于存储唯一的对象标识符，通常用于文档的唯一标识。
   - 示例：
     ```json
     { "_id": ObjectId("507f1f77bcf86cd799439011") }
     ```

9. **Date**
   - 用于存储日期和时间。
   - 示例：
     ```json
     { "createdAt": ISODate("2023-10-01T12:00:00Z") }
     ```

10. **Null**
    - 用于表示空值。
    - 示例：
      ```json
      { "middleName": null }
      ```

11. **Regular Expression**
    - 用于存储正则表达式。
    - 示例：
      ```json
      { "pattern": /abc/i }
      ```

12. **JavaScript**
    - 用于存储 JavaScript 代码。
    - 示例：
      ```json
      { "script": function() { return "hello"; } }
      ```

13. **Timestamp**
    - 用于存储时间戳，通常用于记录文档的创建或修改时间。
    - 示例：
      ```json
      { "timestamp": Timestamp(1633072800, 1) }
      ```

## CRUD
### Insert
#### insertOne
```javascript
use sample_mflix

db.movies.insertOne(
  {
    title: "The Favourite",
    genres: [ "Drama", "History" ],
    runtime: 121,
    rated: "R",
    year: 2018,
    directors: [ "Yorgos Lanthimos" ],
    cast: [ "Olivia Colman", "Emma Stone", "Rachel Weisz" ],
    type: "movie"
  }
)
```

#### insertMany
```javascript
db.inventory.insertMany( [
   { item: "canvas", qty: 100, size: { h: 28, w: 35.5, uom: "cm" }, status: "A" },
   { item: "journal", qty: 25, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "mat", qty: 85, size: { h: 27.9, w: 35.5, uom: "cm" }, status: "A" },
   { item: "mousepad", qty: 25, size: { h: 19, w: 22.85, uom: "cm" }, status: "P" },
   { item: "notebook", qty: 50, size: { h: 8.5, w: 11, uom: "in" }, status: "P" },
   { item: "paper", qty: 100, size: { h: 8.5, w: 11, uom: "in" }, status: "D" },
   { item: "planner", qty: 75, size: { h: 22.85, w: 30, uom: "cm" }, status: "D" },
   { item: "postcard", qty: 45, size: { h: 10, w: 15.25, uom: "cm" }, status: "A" },
   { item: "sketchbook", qty: 80, size: { h: 14, w: 21, uom: "cm" }, status: "A" },
   { item: "sketch pad", qty: 95, size: { h: 22.85, w: 30.5, uom: "cm" }, status: "A" }
] );
```
### Update
```javascript
db.<collection>.updateOne(
  <query>,
  <update>,
  <options>
)
```
- `<update>`
  ```javascript
  {
    <update operator>: { <field1>: <value1>, ... },
    <update operator>: { <field2>: <value2>, ... },
    ...
  }
  ```
- `<options>`
  ```javascript
  {
    upsert: <boolean>,  // 默认为false, 如果为true，则如果查询条件没有匹配到任何文档，则插入一条新文档
    multi: <boolean>,  // 默认为false, 如果为true，则更新所有匹配到的文档
    arrayFilters: [ { "element.qty": { $gt: 10 } } ],  //  用于指定更新操作中数组字段的过滤条件
    hint: { name: 1 },  // 指定索引
    bypassDocumentValidation: true,  // 如果设置为 true，则跳过文档验证
    writeConcern: { w: "majority", wtimeout: 5000 } //  指定写操作的写关注级别
  }
  ```

#### updateOne
```javascript
db.inventory.updateOne(
   { item: "paper" },
   {
     $set: { "size.uom": "cm", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```

#### updateMany
```javascript
db.inventory.updateMany(
   { "qty": { $lt: 50 } },
   {
     $set: { "size.uom": "in", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```
```javascript
// mongodb对所有price大于1的文档quantity减1
db.<collection>.updateMany(
  { price: { $gt: 1 } },
  { $inc: { quantity: -1 } }
)
// mongodb对所有price大于1的文档quantity减半
db.<collection>.updateMany(
  { price: { $gt: 1 } },
  [
    { $set: { quantity: { $floor: { $multiply: ["$quantity", 0.5] } } } }
  ]
)

```
#### replaceOne
```javascript
db.inventory.replaceOne(
   { item: "paper" },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 40 } ] }
)
```

### Delete
#### deleteMany
```javascript
db.inventory.deleteMany({})
db.inventory.deleteMany({ status : "A" })
db.inventory.deleteMany({ "qty": { $lt: 50 } })
```
#### deleteOne
```javascript
db.inventory.deleteOne( { status: "D" } )
```


### Query
#### find
```javascript
db.inventory.find( {} )
db.inventory.find( { status: "D" } )
```
#### findOne
```javascript
db.inventory.findOne( {} )
db.inventory.findOne( { status: "D" } )
```

#### sort / limit / skip
```javascript
// order by status asc
db.inventory.find( {} ).sort( { status: 1 } )
// order by status desc
db.inventory.find( {} ).sort( { status: -1 } )
// order by status asc, item desc
db.inventory.find( {} ).sort( { status: 1, item: -1 } )
// show first 5
db.inventory.find( {} ).sort( { status: 1, item: -1 } ).limit( 5 )
// show 3rd to 7th
db.inventory.find( {} ).sort( { status: 1, item: -1 } ).limit( 5 ).skip( 2 )
```

### Condition of Query
- AND: `db.inventory.find( { status: "A", qty: { $lt: 30 } } )`
- OR: `db.inventory.find( { $or: [ { status: "A" }, { qty: { $lt: 30 } } ] } )`
- AND + OR:
  ```javascript
  {
    status: 'A',
    $or: [
      { qty: { $lt: 30 } }, { item: { $regex: '^p' } }
    ]
  }
  ```

## Index
- a collection could at most have 64 indexes
- one indexes could at most contains 31 keys
- name of index could not be longer than 127 bytes
- index mongodb managed in system.indexes collection, which only support `createIndex` and `dropIndex` operation

### createIndex
```javascript
db.collection.createIndex(
  {
      <key_1>: 1,
      <key_2>: 1,
  },
  {
      unique: true,  // 默认为false, 如果为true，则不允许重复
      sparse: true,  // 默认为false, 如果为true，则不索引空值
      expireAfterSeconds: 3600  // 默认为null, 如果不为null，则设置索引过期时间，单位为秒
  }
)
```

### dropIndex / dropIndexes
```javascript
// 删除单个索引
db.<collection>.dropIndex("<index1>")
// 删除多个索引
db.<collection>.dropIndexes( [ "<index1>", "<index2>", "<index3>" ] )
// 删除 _id 索引之外的所有索引
db.<collection>.dropIndexes()
```


## Aggregate & Pipeline
### Aggregate
Aggregate is a pipeline of stages that process documents in a collection. 
Basic aggregate is: `db.collection.aggregate(<pipeline(s)>, <options>)`
  ```javascript
  db.<collection>.aggregate(
    [
      { $match: { <query> },
      { $group: { _id: "<expression>", <field1>: { <accumulator1>: "<expression1>" }, ... } },
    ]
  )
  ```
### Pipeline
pipeline 是一个数组，包含多个聚合阶段。每个阶段都是一个对象，定义了如何处理输入文档并输出到下一个阶段。
`<pipeline>`: `[ { <stage1> }, { <stage2> }, ... ]`
- 常见聚合阶段
  - **`$match`**：过滤文档。
  - **`$group`**：按字段分组并计算聚合值。
  - **`$sort`**：对文档进行排序。
  - **`$project`**：选择或重命名字段，或计算新字段。
  - **`$limit`**：限制输出的文档数量。
  - **`$skip`**：跳过指定数量的文档。
  - **`$unwind`**：将数组字段拆分为多个文档。
  - **`$lookup`**：连接集合。
  - **`$facet`**：同时执行多个聚合管道。
  - **`$addFields`**：添加新字段。
  - **`$redact`**：控制文档访问。
#### 常见聚合阶段

- **`$match`**：过滤文档，只传递符合条件的文档。

  ```javascript
  { $match: { status: "A" } }
  ```

- **`$group`**：按指定字段对文档进行分组，并计算聚合值。

  ```javascript
  { $group: { _id: "$category", total: { $sum: "$quantity" } } }
  ```

- **`$sort`**：对文档进行排序。

  ```javascript
  { $sort: { age: 1 } } // 1 表示升序，-1 表示降序
  ```

- **`$project`**：选择或重命名字段，或计算新字段。

  ```javascript
  { $project: { name: 1, total: 1, _id: 0 } }
  ```

- **`$limit`**：限制输出的文档数量。

  ```javascript
  { $limit: 10 }
  ```

- **`$skip`**：跳过指定数量的文档。

  ```javascript
  { $skip: 5 }
  ```

- **`$unwind`**：将数组字段拆分为多个文档。

  ```javascript
  { $unwind: "$tags" }
  ```

#### 示例

假设有一个 `orders` 集合，包含以下文档：

```json
[
  { _id: 1, customer: "A", amount: 100, status: "completed" },
  { _id: 2, customer: "B", amount: 200, status: "pending" },
  { _id: 3, customer: "A", amount: 150, status: "completed" }
]
```

我们可以使用聚合管道来计算每个客户的总订单金额：

```javascript
db.orders.aggregate([
  { $match: { status: "completed" } }, // 过滤出状态为 "completed" 的订单
  { $group: { _id: "$customer", totalAmount: { $sum: "$amount" } } }, // 按客户分组并计算总金额
  { $sort: { totalAmount: -1 } } // 按总金额降序排序
])
```

结果将如下：

```json
[
  { _id: "A", totalAmount: 250 },
  { _id: "B", totalAmount: 0 }
]
```

### 常见的 MongoDB 聚合案例

#### 1. **按字段分组并计算总和**
**案例描述**：计算每个客户的总订单金额。

**集合示例**：
```json
[
  { _id: 1, customer: "A", amount: 100, status: "completed" },
  { _id: 2, customer: "B", amount: 200, status: "pending" },
  { _id: 3, customer: "A", amount: 150, status: "completed" }
]
```

**聚合管道**：
```javascript
db.orders.aggregate([
  { $match: { status: "completed" } }, // 过滤出状态为 "completed" 的订单
  { $group: { _id: "$customer", totalAmount: { $sum: "$amount" } } }, // 按客户分组并计算总金额
  { $sort: { totalAmount: -1 } } // 按总金额降序排序
])
```

**解释**：
- **`$match`**：过滤出 `status` 为 `"completed"` 的订单。
- **`$group`**：按 `customer` 字段分组，并使用 `$sum` 计算每个客户的总金额。
- **`$sort`**：按 `totalAmount` 字段降序排序。

#### 2. **按字段分组并计算平均值**
**案例描述**：计算每个产品的平均评分。

**集合示例**：
```json
[
  { _id: 1, product: "X", rating: 4.5 },
  { _id: 2, product: "Y", rating: 3.0 },
  { _id: 3, product: "X", rating: 5.0 },
  { _id: 4, product: "Y", rating: 4.0 }
]
```

**聚合管道**：
```javascript
db.reviews.aggregate([
  { $group: { _id: "$product", averageRating: { $avg: "$rating" } } }, // 按产品分组并计算平均评分
  { $sort: { averageRating: -1 } } // 按平均评分降序排序
])
```

**解释**：
- **`$group`**：按 `product` 字段分组，并使用 `$avg` 计算每个产品的平均评分。
- **`$sort`**：按 `averageRating` 字段降序排序。

3. **按字段分组并计算文档数量**
**案例描述**：统计每个类别的文档数量。

**集合示例**：
```json
[
  { _id: 1, category: "A", name: "Item1" },
  { _id: 2, category: "B", name: "Item2" },
  { _id: 3, category: "A", name: "Item3" },
  { _id: 4, category: "C", name: "Item4" }
]
```

**聚合管道**：
```javascript
db.items.aggregate([
  { $group: { _id: "$category", count: { $sum: 1 } } }, // 按类别分组并计算每个类别的文档数量
  { $sort: { count: -1 } } // 按文档数量降序排序
])
```

**解释**：
- **`$group`**：按 `category` 字段分组，并使用 `$sum` 计算每个类别的文档数量。
- **`$sort`**：按 `count` 字段降序排序。

4. **使用 `$unwind` 处理数组字段**
**案例描述**：统计每个标签出现的次数。

**集合示例**：
```json
[
  { _id: 1, tags: ["red", "blue"], name: "Item1" },
  { _id: 2, tags: ["green"], name: "Item2" },
  { _id: 3, tags: ["red", "green", "blue"], name: "Item3" }
]
```

**聚合管道**：
```javascript
db.items.aggregate([
  { $unwind: "$tags" }, // 将 tags 数组拆分为多个文档
  { $group: { _id: "$tags", count: { $sum: 1 } } }, // 按 tags 字段分组并计算每个标签的出现次数
  { $sort: { count: -1 } } // 按出现次数降序排序
])
```

**解释**：
- **`$unwind`**：将 `tags` 数组拆分为多个文档，每个文档包含一个标签。
- **`$group`**：按 `tags` 字段分组，并使用 `$sum` 计算每个标签的出现次数。
- **`$sort`**：按 `count` 字段降序排序。

5. **使用 `$lookup` 进行集合连接**
**案例描述**：连接 `orders` 和 `customers` 集合，获取每个订单的客户信息。

**集合示例**：
```json
// orders 集合
[
  { _id: 1, customerId: 1, amount: 100 },
  { _id: 2, customerId: 2, amount: 200 }
]

// customers 集合
[
  { _id: 1, name: "Alice" },
  { _id: 2, name: "Bob" }
]
```

**聚合管道**：
```javascript
db.orders.aggregate([
  {
    $lookup: {
      from: "customers", // 要连接的集合
      localField: "customerId", // 当前集合中的字段
      foreignField: "_id", // 要连接的集合中的字段
      as: "customerInfo" // 结果字段
    }
  },
  { $unwind: "$customerInfo" }, // 将数组拆分为多个文档
  { $project: { _id: 0, orderId: "$_id", amount: 1, customerName: "$customerInfo.name" } } // 选择字段
])
```

**解释**：
- **`$lookup`**：将 `orders` 集合与 `customers` 集合连接，通过 `customerId` 和 `_id` 字段进行匹配，并将结果存储在 `customerInfo` 字段中。
- **`$unwind`**：将 `customerInfo` 数组拆分为多个文档。
- **`$project`**：选择需要的字段，包括订单 ID、金额和客户名称。

6. **使用 `$facet` 进行多阶段聚合**
**案例描述**：同时计算每个客户的总订单金额和订单数量。

**集合示例**：
```json
[
  { _id: 1, customer: "A", amount: 100, status: "completed" },
  { _id: 2, customer: "B", amount: 200, status: "pending" },
  { _id: 3, customer: "A", amount: 150, status: "completed" }
]
```

**聚合管道**：
```javascript
db.orders.aggregate([
  { $match: { status: "completed" } }, // 过滤出状态为 "completed" 的订单
  {
    $facet: {
      totalAmount: [
        { $group: { _id: "$customer", totalAmount: { $sum: "$amount" } } } // 计算每个客户的总金额
      ],
      orderCount: [
        { $group: { _id: "$customer", orderCount: { $sum: 1 } } } // 计算每个客户的订单数量
      ]
    }
  },
  { $project: { _id: 0, customer: "$totalAmount._id", totalAmount: "$totalAmount.totalAmount", orderCount: "$orderCount.orderCount" } } // 选择字段
])
```

**解释**：
- **`$match`**：过滤出 `status` 为 `"completed"` 的订单。
- **`$facet`**：同时执行多个聚合管道，分别计算每个客户的总金额和订单数量。
- **`$project`**：选择需要的字段，包括客户、总金额和订单数量。

7. **使用 `$addFields` 添加新字段**
**案例描述**：为每个订单添加一个折扣字段。

**集合示例**：
```json
[
  { _id: 1, amount: 100 },
  { _id: 2, amount: 200 }
]
```

**聚合管道**：
```javascript
db.orders.aggregate([
  { $addFields: { discount: { $multiply: ["$amount", 0.1] } } }, // 添加折扣字段
  { $project: { _id: 0, amount: 1, discount: 1 } } // 选择字段
])
```

**解释**：
- **`$addFields`**：添加一个新的 `discount` 字段，其值为 `amount` 的 10%。
- **`$project`**：选择需要的字段，包括金额和折扣。

8. **使用 `$redact` 控制文档访问**
**案例描述**：根据用户权限过滤文档。

**集合示例**：
```json
[
  { _id: 1, name: "Alice", accessLevel: "admin" },
  { _id: 2, name: "Bob", accessLevel: "user" }
]
```

**聚合管道**：
```javascript
db.users.aggregate([
  {
    $redact: {
      $cond: {
        if: { $eq: ["$accessLevel", "admin"] }, // 如果 accessLevel 为 "admin"
        then: "$$DESCEND", // 包含所有子文档
        else: "$$PRUNE" // 排除该文档
      }
    }
  }
])
```

**解释**：
- **`$redact`**：根据条件过滤文档。如果 `accessLevel` 为 `"admin"`，则包含该文档及其子文档；否则排除该文档。
- **`$$DESCEND`**：包含文档及其子文档。
- **`$$PRUNE`**：排除文档。


# 查询操作符
## 查询选择器
### Usage
  ```javascript
  db.<collection>.find( { <field1>: { <operator1>: <value1> }, ... } )
  ```

### 对比

关于不同 BSON 类型值的比较，请参阅[指定的 BSON 比较顺序。](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-type-comparison-order/#std-label-bson-types-comparison-order)

| 名称                                                                                                    | 说明                       |
| :------------------------------------------------------------------------------------------------------ | :------------------------- |
| [`$eq`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/eq/#mongodb-query-op.-eq)    | 匹配等于指定值的值。       |
| [`$gt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/gt/#mongodb-query-op.-gt)    | 匹配大于指定值的值。       |
| [`$gte`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/gte/#mongodb-query-op.-gte) | 匹配大于等于指定值的值。   |
| [`$in`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/in/#mongodb-query-op.-in)    | 匹配数组中指定的任何值。   |
| [`$lt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/lt/#mongodb-query-op.-lt)    | 匹配小于指定值的值。       |
| [`$lte`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/lte/#mongodb-query-op.-lte) | 匹配小于等于指定值的值。   |
| [`$ne`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/ne/#mongodb-query-op.-ne)    | 匹配所有不等于指定值的值。 |
| [`$nin`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/nin/#mongodb-query-op.-nin) | 不匹配数组中指定的任何值。 |
#### Usage
- `$eq / $ne / $gt / $gte / $lt / $lte`
  ```javascript
  { <field>: { $eq: <value> } }
  ```
- `$in / $nin`
  ```javascript
  { <field>: { <$in>: [<value1>, <value2>, ... <valueN> ] } }
  ```

### 逻辑

| 名称                                                                                                    | 说明                                                              |
| :------------------------------------------------------------------------------------------------------ | :---------------------------------------------------------------- |
| [`$and`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/and/#mongodb-query-op.-and) | 使用逻辑 `AND` 连接查询子句将返回与两个子句的条件匹配的所有文档。 |
| [`$not`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/not/#mongodb-query-op.-not) | 反转查询谓词的效果，并返回与查询谓词*不*匹配的文档。              |
| [`$nor`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/nor/#mongodb-query-op.-nor) | 使用逻辑 `NOR` 的联接查询子句会返回无法匹配这两个子句的所有文档。 |
| [`$or`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/or/#mongodb-query-op.-or)    | 使用逻辑 `OR` 连接多个查询子句会返回符合任一子句条件的所有文档。  |

#### Usage
- `$and / $nor / $or`
  ```javascript
  db.example.find( {
    $and: [
        { x: { $ne: 0 } },
        { $expr: { $eq: [ { $divide: [ 1, "$x" ] }, 3 ] } }
    ]
  } )
  ```
- `$not`
  ```javascript
  db.example.find( { $not: { x: { $gt: 0 } } } )
  ```

### 元素

| 名称                                                                                                             | 说明                             |
| :--------------------------------------------------------------------------------------------------------------- | :------------------------------- |
| [`$exists`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/exists/#mongodb-query-op.-exists) | 匹配具有指定字段的文档。         |
| [`$type`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/type/#mongodb-query-op.-type)       | 如果字段为指定类型，则选择文档。 |

#### MongoDB字段类型
| 类型       | 数值 | 别名         | 注意     |
| :--------- | :--- | :----------- | :------- |
| double     | 1    | "double"     |          |
| 字符串     | 2    | "string"     |          |
| 对象       | 3    | "object"     |          |
| 阵列       | 4    | "array"      |          |
| 二进制数据 | 5    | "binData"    |          |
| 未定义     | 6    | "undefined"  | 已弃用。 |
| ObjectId   | 7    | "objectId"   |          |
| 布尔       | 8    | "bool"       |          |
| Date       | 9    | "date"       |          |
| null       | 10   | "null"       |          |
| 正则表达式 | 11   | "regex"      |          |
| 数据库指针 | 12   | "dbPointer"  | 已弃用。 |
| JavaScript | 13   | "javascript" |          |
| 符号       | 14   | "symbol"     | 已弃用。 |
| 32 位整数  | 16   | "int"        |          |
| 时间戳     | 17   | "timestamp"  |          |
| 64 位整型  | 18   | "long"       |          |
| Decimal128 | 19   | "decimal"    |          |
| Min key    | -1   | "minKey"     |          |
| Max key    | 127  | "maxKey"     |          |

#### Usage
- `$exists`
  ```javascript
  db.spices.find( { saffron: { $exists: true } } )
  ```
- `$type`
  ```javascript
  db.addressBook.find( { zipCode : { $type : 2 } } );
  db.addressBook.find( { zipCode : { $type : "string" } } );
  ```


### 求值

| 名称                                                                                                                         | 说明                                                                                                                                                                                                                    |
| :--------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$expr`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/expr/#mongodb-query-op.-expr)                   | 允许在查询语言中使用聚合表达式。                                                                                                                                                                                        |
| [`$jsonSchema`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/jsonSchema/#mongodb-query-op.-jsonSchema) | 根据给定的 JSON 模式验证文档。                                                                                                                                                                                          |
| [`$mod`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/mod/#mongodb-query-op.-mod)                      | 对字段值执行模运算，并选择具有指定结果的文档。                                                                                                                                                                          |
| [`$regex`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/regex/#mongodb-query-op.-regex)                | 选择值匹配指定正则表达式的文档。                                                                                                                                                                                        |
| [`$text`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/text/#mongodb-query-op.-text)                   | 执行文本搜索。`$text` 提供了自管理（非 Atlas）部署的文本查询功能。对于托管在 MongoDB Atlas 上的数据，MongoDB 提供了一种改进的全文查询解决方案，[Atlas Search](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/)。 |
| [`$where`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/where/#mongodb-query-op.-where)                | 匹配满足 JavaScript 表达式的文档。                                                                                                                                                                                      |
#### Usage
- `$expr`
  ```javascript
  // find all documents where the spent amount is greater than the budget amount
  db.monthlyBudget.find( { $expr: { $gt: [ "$spent" , "$budget" ] } } )
  ```
- `$jsonSchema`
- `$mod`
  ```javascript
  // find all documents that quantity%4==0
  db.inventory.find( { qty: { $mod: [ 4, 0 ] } } )
  ```
- `$regex`
  ```javascript
  // { <field>: { $regex: /pattern/, $options: '<options>' } }
  // { "<field>": { "$regex": "pattern", "$options": "<options>" } }
  // { <field>: { $regex: /pattern/<options> } }
  // 匹配以 789 结尾的 sku
  db.products.find( { sku: { $regex: /789$/ } } )
  // 使用 m 选项为多行字符串匹配以字母 S 开头的行
  db.products.find( { description: { $regex: /^S/, $options: 'm' } } )
  // 利用 i 选项对 sku 值以 ABC 开头的文档执行不区分大小写的匹配
  db.products.find( { sku: { $regex: /^ABC/i } } )
  ```
  - `<options>`

  | 选项 | 说明                                                                                                                                                                                                                                                                                                                                                                                             |
  | :--- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
  | `i`  | 不区分大小写，以匹配大小写。 [有关示例，请参阅执行不区分大小写的正则表达式匹配。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/regex/#std-label-regex-case-insensitive)                                                                                                                                                                                                    |
  | `m`  | 对于包含锚点的模式（即 `^` 表示开始，`$` 表示结束），匹配具有多行值的字符串的每行的开头或结尾。 如果没有此选项，这些锚点将在字符串的开头或结尾匹配。 [有关示例，请参阅以指定模式开头的行的多行匹配。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/regex/#std-label-regex-multiline-match)如果模式不包含锚点，或者字符串值没有换行符（如 `\n`），则 `m` 选项没有任何作用。 |
  | `x`  | “扩展”功能，可忽略 `$regex` 模式中的所有空白字符，除非转义或包含在字符类中。此外，其还会忽略未转义的哈希/磅 (`#`) 字符和下一新行（含）之间的字符，因此您可以在复杂的模式中加入注释。这种情况只适用于数据字符；空白字符绝不能出现在模式中的特殊字符序列中。`x` 选项不影响对 VT 字符的处理（如代码 11）。                                                                                          |
  | `s`  | 允许点字符（即 `.`）匹配所有字符，包括换行符。有关示例，请参阅使用[ `.`点字符匹配新行。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/regex/#std-label-regex-dot-new-line)                                                                                                                                                                                                 |
  | `u`  | 支持 Unicode。此标记被接受，但多余。在 `$regex` 操作符中，UTF 是默认设置的，因此不必使用 `u` 选项。                                                                                                                                                                                                                                                                                              |
- `$text`
  ```javascript
  /*{
    $text: {
      $search: <string>,
      $language: <string>,  // 可选。用于确定查询停用词列表以及词干分析器和分词器规则的语言。
      $caseSensitive: <boolean>,  // 可选。用于启用或禁用区分大小写的布尔标志。默认值为 false
      $diacriticSensitive: <boolean>  // 可选。一个布尔标志，用于启用或禁用针对版本 3 文本索引的变音符号敏感性。默认为 false。
    }
  }*/
  // 创建一个文本索引
  db.articles.createIndex( { subject: "text" } )
  // subject 字段中包含 bake、coffee 或 cake 词干的文档
  db.articles.find( { $text: { $search: "bake coffee cake" } } )
  // subject 字段匹配短语 coffee shop
  db.articles.find( { $text: { $search: "\"coffee shop\"" } } )
  // subject 字段匹配 coffee 但不匹配 shop
  db.articles.find( { $text: { $search: "coffee -shop" } } )
  // 指定 es，即西班牙语, subject 字段匹配 leche
  db.articles.find( { $text: { $search: "leche", $language: "es" } } )
  // subject 字段区分大小写匹配 Coffee
  db.articles.find( { $text: { $search: "Coffee", $caseSensitive: true } } )
  ```
- `$where`
  ```javascript
  // find all players whose name matches specific MD5 hash
  // method 1:
  db.players.find( { $where: function() {
    return (hex_md5(this.name) == "9b53e667f30cd329dca1ec9e6a83e994")
  } } );
  // method 2:
  db.players.find( {$expr: { $function: {
        body: function(name) { return hex_md5(name) == "9b53e667f30cd329dca1ec9e6a83e994"; },
        args: [ "$name" ],
        lang: "js"
  } } } )
  ```
  - avilable methods: 
    - assert()
    - BinData()
    - DBPointer()
    - DBRef()
    - doassert()
    - emit()
    - gc()
    - HexData()
    - hex_md5()
    - isNumber()
    - isObject()
    - ISODate()
    - isString()
    - Map()
    - MD5()
    - NumberInt()
    - NumberLong()
    - ObjectId()
    - print()
    - printjson()
    - printjsononeline()
    - sleep()
    - Timestamp()
    - tojson()
    - tojsononeline()
    - tojsonObject()
    - UUID()
    - version()
  - avilable Properties:
    - args
    - MaxKey
    - MinKey

### 地理空间

| 名称                                                                                                                                  | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| :------------------------------------------------------------------------------------------------------------------------------------ | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$geoIntersects`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/geoIntersects/#mongodb-query-op.-geoIntersects) | 选择与 [GeoJSON](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-GeoJSON) 几何图形相交的几何图形。[2dsphere](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2dsphere/#std-label-2dsphere-index) 索引支持 [`$geoIntersects`。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/geoIntersects/#mongodb-query-op.-geoIntersects)                                                                                                                              |
| [`$geoWithin`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/geoWithin/#mongodb-query-op.-geoWithin)             | 选择在边界 [GeoJSON 几何图形](https://www.mongodb.com/zh-cn/docs/manual/reference/geojson/#std-label-geospatial-indexes-store-geojson)内的几何图形。[2dsphere](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2dsphere/#std-label-2dsphere-index) 和 [2d](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2d/#std-label-2d-index) 索引支持 [`$geoWithin`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/geoWithin/#mongodb-query-op.-geoWithin)。 |
| [`$near`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/near/#mongodb-query-op.-near)                            | 返回接近某个点的地理空间对象。需要地理空间索引。[2dsphere](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2dsphere/#std-label-2dsphere-index) 和 [2d](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2d/#std-label-2d-index) 索引支持 [`$near`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/near/#mongodb-query-op.-near)。                                                                                                                    |
| [`$nearSphere`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/nearSphere/#mongodb-query-op.-nearSphere)          | 返回与球面上的某个点接近的地理空间对象。需要地理空间索引。[2dsphere](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2dsphere/#std-label-2dsphere-index) 和 [2d](https://www.mongodb.com/zh-cn/docs/manual/core/indexes/index-types/geospatial/2d/#std-label-2d-index) 索引支持 [`$nearSphere`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/nearSphere/#mongodb-query-op.-nearSphere)。                                                                                        |



### 阵列

| 名称                                                                                                                      | 说明                                                                                                                                                                                |
| :------------------------------------------------------------------------------------------------------------------------ | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$all`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/all/#mongodb-query-op.-all)                   | 匹配包含查询中指定的所有元素的数组。                                                                                                                                                |
| [`$elemMatch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/elemMatch/#mongodb-query-op.-elemMatch) | 如果数组字段中的元素与所有指定的 [`$elemMatch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/elemMatch/#mongodb-query-op.-elemMatch) 条件均匹配，则选择文档。 |
| [`$size`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/size/#mongodb-query-op.-size)                | 如果数组字段达到指定大小，则选择文档。                                                                                                                                              |

#### Usage
- `$elemMatch`
  ```javascript
  // results 数组中至少包含一个大于或等于 80 且小于 85
  db.scores.find(
    { results: { $elemMatch: { $gte: 80, $lt: 85 } } }
  )
  // results 数组中至少包含一个名为 xyz 且分数大于或等于 8 的产品
  db.survey.find(
    { results: { $elemMatch: { product: "xyz", score: { $gte: 8 } } } }
  )
  ```
  - different between `{"results.product":"xyz"}` and `{results:{$elemMatch:{product:"xyz"}}`:
    - `{"results.product":"xyz"}` could match array and object
      - if document `{ _id: 5, results: { product: 'xyz', score: 7 } }` in collection, it will be retuen
      - document line `{ _id: ?, results: [{ product: 'xyz', score: ?? }, ...] }` will be return too
    - `{results:{$elemMatch:{product:"xyz"}}` could only match array
      - only return document like: `{ _id: ?, results: [{ product: 'xyz', score: ?? }, ...] }`

- `$all`
  ```javascript
  // find all documents that include all of the specified tags
  db.inventory.find( { tags: { $all: [ "appliance", "school", "book" ] } } )
  /*
  each document must:
    1. at least one item in qty has a size of "M" and quantity of this item greater than 50
    2. at least one item in qty has a num of 100 and color of "green"
  the two conditions must be satisfied together but could match different item
  */
  db.inventory.find({
    qty: {
      $all: [
        { "$elemMatch": { size: "M", num: { $gt: 50 } } },
        { "$elemMatch": { num: 100, color: "green" } }
      ]
    }
  })
  ```
- `$size`
  - size not accept range query
  ```javascript
  db.collection.find( { field: { $size: 2 } } );
  ```

### Bitwise

| 名称                                                                                                                               | 说明                                                        |
| :--------------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------- |
| [`$bitsAllClear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/bitsAllClear/#mongodb-query-op.-bitsAllClear) | 匹配数字或二进制值，其中一组片段位置*均*包含值`0`。         |
| [`$bitsAllSet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/bitsAllSet/#mongodb-query-op.-bitsAllSet)       | 匹配数字或二进制值，其中一组片段位置*均*包含值`1`。         |
| [`$bitsAnyClear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/bitsAnyClear/#mongodb-query-op.-bitsAnyClear) | 匹配数字或二进制值，其中一组位位置中的*任何* 位的值为 `0`。 |
| [`$bitsAnySet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/bitsAnySet/#mongodb-query-op.-bitsAnySet)       | 匹配数字或二进制值，其中一组位位置中的*任何* 位的值为 `1`。 |



## 投影操作符

| 名称                                                                                                                                  | 说明                                                                                                                                                                                                                                           |
| :------------------------------------------------------------------------------------------------------------------------------------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/projection/positional/#mongodb-projection-proj.-)                  | 对数组中与查询条件匹配的第一个元素进行投影。                                                                                                                                                                                                   |
| [`$elemMatch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/projection/elemMatch/#mongodb-projection-proj.-elemMatch) | 对数组中与指定 [`$elemMatch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/projection/elemMatch/#mongodb-projection-proj.-elemMatch) 条件匹配的第一个元素进行投影。                                                            |
| [`$meta`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/meta/#mongodb-expression-exp.-meta)                | 预测在 `$text` 操作中分配的文件分数。`$text` 提供了自管理（非 Atlas）部署的文本查询功能。对于托管在 MongoDB Atlas 上的数据，MongoDB 提供了一种改进的全文查询解决方案，[Atlas Search](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/)。 |
| [`$slice`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/projection/slice/#mongodb-projection-proj.-slice)             | 限制从数组中投影的元素数量。支持跳过切片和对切片进行数量限制。                                                                                                                                                                                 |

#### Usage
- `$slice`
  ```javascript
  /*
  db.collection.find(
    <query>,
    { <arrayField>: { $slice: <limit:number> } }
  );
  db.collection.find(
    <query>,
    { <arrayField>: { $slice: [ <offset:number>, <limit:number> ] } }
  );
  */
  db.inventory.find( { }, { qty: 1, "details.colors": { $slice: 1 } } )
  ```
- `$`: 投影
  ```javascript
  // 投影 { "grades.$": 1 } 仅比较且返回 grades 数组的第一个元素，该元素大于或等于 85 
  db.students.find(
    { semester: 1, grades: { $gte: 85 } },
    { "grades.$": 1 }
  )
  ```

## 其他操作符

| 名称                                                                                                                       | 说明                                                                                                                                                                                                                                                                                           |
| :------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$rand`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/rand/#mongodb-query-op.-rand)                 | 生成介于 0 和 1 之间的随机浮点数。                                                                                                                                                                                                                                                             |
| [`$natural`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/natural/#mongodb-operator-metaOp.-natural) | 可通过 [`sort()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/cursor.sort/#mongodb-method-cursor.sort) 或 [`hint()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/cursor.hint/#mongodb-method-cursor.hint) 方法提供的特殊提示，可用于强制执行正向或反向集合扫描。 |

#### Usage
- `$rand`
```javascript
db.donors.updateMany(
   {},
   [
      { $set:
         { amount:
            { $floor:
               { $multiply: [ { $rand: {} }, 100 ] }
            }
         }
      }
    ]
)
// $floor: 向下取整
```

# 更新操作符
## 语法
```javascript
{
   <operator1>: { <field1>: <value1>, ... },
   <operator2>: { <field2>: <value2>, ... },
   ...
}
```



## 行为

从 MongoDB 5.0 开始，更新运算符按字典顺序处理具有基于字符串的名称的文档字段。具有数字名称的字段按数字顺序处理。

参考该 [`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/set/#mongodb-update-up.-set) 命令示例：

```
{ $set: { "a.2": <new value>, "a.10": <new value>, } }
```

在 MongoDB 5.0 及更高版本中，`"a.2"` 在 `"a.10"` 之前处理，因为 `2` 按数字顺序排在 `10` 之前。

## 字段

| 名称                                                                                                                              | 说明                                                                             |
| :-------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------- |
| [`$currentDate`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/currentDate/#mongodb-update-up.-currentDate) | 将字段的值设置为当前日期，可以是日期或时间戳。                                   |
| [`$inc`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/inc/#mongodb-update-up.-inc)                         | 将字段的值按指定量递增。                                                         |
| [`$min`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/min/#mongodb-update-up.-min)                         | 仅当指定值小于现有字段值时才更新字段。                                           |
| [`$max`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/max/#mongodb-update-up.-max)                         | 仅当指定值大于现有字段值时才更新字段。                                           |
| [`$mul`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/mul/#mongodb-update-up.-mul)                         | 将字段的值乘以指定量。                                                           |
| [`$rename`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/rename/#mongodb-update-up.-rename)                | 重命名字段。                                                                     |
| [`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/set/#mongodb-update-up.-set)                         | 设置文档中字段的值。                                                             |
| [`$setOnInsert`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/setOnInsert/#mongodb-update-up.-setOnInsert) | 如果某一更新操作导致插入文档，则设置字段的值。对修改现有文档的更新操作没有影响。 |
| [`$unset`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/unset/#mongodb-update-up.-unset)                   | 从文档中删除指定的字段。                                                         |

## 阵列

### 操作符

| 名称                                                                                                                                 | 说明                                                                         |
| :----------------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------- |
| [`$`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/positional/#mongodb-update-up.-)                           | 充当占位符，用于更新与查询条件匹配的第一个元素。                             |
| [`$[\]`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/positional-all/#mongodb-update-up.---)                  | 充当占位符，以更新数组中与查询条件匹配的文档中的所有元素。                   |
| [`$[\]`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/positional-filtered/#mongodb-update-up.---identifier--) | 充当占位符，以更新与查询条件匹配的文档中所有符合 `arrayFilters` 条件的元素。 |
| [`$addToSet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/addToSet/#mongodb-update-up.-addToSet)             | 仅向数组中添加尚不存在于该数组的元素。                                       |
| [`$pop`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/pop/#mongodb-update-up.-pop)                            | 删除数组的第一项或最后一项。                                                 |
| [`$pull`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/pull/#mongodb-update-up.-pull)                         | 删除与指定查询匹配的所有数组元素。                                           |
| [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/push/#mongodb-update-up.-push)                         | 向数组添加一项。                                                             |
| [`$pullAll`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/pullAll/#mongodb-update-up.-pullAll)                | 从数组中删除所有匹配值。                                                     |

### Modifiers

| 名称                                                                                                                     | 说明                                                                                                                                                                                                                                                                               |
| :----------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$each`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/each/#mongodb-update-up.-each)             | 修改 [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/push/#mongodb-update-up.-push) 和 [`$addToSet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/addToSet/#mongodb-update-up.-addToSet) 运算符，以在数组更新时追加多个项目。 |
| [`$position`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/position/#mongodb-update-up.-position) | 修改 [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/push/#mongodb-update-up.-push) 运算符，以指定在数组中添加元素的位置。                                                                                                                           |
| [`$slice`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/slice/#mongodb-update-up.-slice)          | 修改 [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/push/#mongodb-update-up.-push) 运算符以限制更新后数组的大小。                                                                                                                                   |
| [`$sort`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/sort/#mongodb-update-up.-sort)             | 修改 [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/push/#mongodb-update-up.-push) 运算符，以对存储在数组中的文档重新排序。                                                                                                                         |

## Bitwise

| 名称                                                                                                      | 说明                                         |
| :-------------------------------------------------------------------------------------------------------- | :------------------------------------------- |
| [`$bit`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/update/bit/#mongodb-update-up.-bit) | 对整数值执行按位 `AND`、`OR` 和 `XOR` 更新。 |

## 常见更新表达式

1. **`$set`**
   - **说明**: 设置字段的值。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $set: { age: 30 } }
     )
     ```

2. **`$unset`**
   - **说明**: 删除字段。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $unset: { age: 1 } }
     )
     ```

3. **`$inc`**
   - **说明**: 增加字段的值。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $inc: { age: 1 } }
     )
     ```

4. **`$mul`**
   - **说明**: 乘以字段的值。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $mul: { age: 2 } }
     )
     ```

5. **`$rename`**
   - **说明**: 重命名字段。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $rename: { age: "years" } }
     )
     ```

6. **`$min`**
   - **说明**: 只有当字段的值大于指定值时，才更新字段。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $min: { age: 25 } }
     )
     ```

7. **`$max`**
   - **说明**: 只有当字段的值小于指定值时，才更新字段。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $max: { age: 35 } }
     )
     ```

8. **`$currentDate`**
   - **说明**: 设置字段为当前日期或时间戳。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $currentDate: { lastModified: true } }
     )
     ```

9. **`$setOnInsert`**
   - **说明**: 仅在插入新文档时设置字段。
   - **示例**:
     ```javascript
     db.collection.updateOne(
       { name: "John" },
       { $setOnInsert: { createdAt: new Date() } },
       { upsert: true }
     )
     ```

10. **`$addToSet`**
    - **说明**: 向数组字段添加一个元素，仅当该元素不存在时。
    - **示例**:
      ```javascript
      db.collection.updateOne(
        { name: "John" },
        { $addToSet: { hobbies: "reading" } }
      )
      db.collection.updateOne(
        { name: "John" },
        { $addToSet: { hobbies: { $each: [ "reading", "swimming" ] } } }
      )

11. **`$pop`**
    - **说明**: 移除数组字段的第一个或最后一个元素。
    - **示例**:
      ```javascript
      db.collection.updateOne(
        { name: "John" },
        { $pop: { hobbies: 1 } } // 移除最后一个元素
      )
      ```

12. **`$pull`**
    - **说明**: 从数组字段中移除所有匹配的元素。
    - **示例**:
      ```javascript
      db.collection.updateOne(
        { name: "John" },
        { $pull: { hobbies: "reading" } }
      )
      ```

13. **`$push`**
    - **说明**: 向数组字段添加一个或多个元素。
    - **示例**:
      ```javascript
      db.collection.updateOne(
        { name: "John" },
        { $push: { hobbies: "swimming" } }
      )
      db.collection.updateOne(
        { name: "John" },
        { $push: { hobbies: { $each: ["reading", "swimming"] } } }
      )
      ```

14. **`$bit`**
    - **说明**: 对整型字段执行位操作。
    - **示例**:
      ```javascript
      db.collection.updateOne(
        { name: "John" },
        { $bit: { flags: { and: 8 } } }
      )
      ```

### 示例

以下是一个综合示例，展示了如何在一次更新操作中使用多个更新表达式：

```javascript
db.collection.updateOne(
  { name: "John" },
  {
    $set: { age: 30 },
    $inc: { visits: 1 },
    $mul: { salary: 1.1 },
    $rename: { age: "years" },
    $min: { years: 25 },
    $max: { years: 35 },
    $currentDate: { lastModified: true },
    $addToSet: { hobbies: "reading" },
    $pop: { hobbies: -1 }, // 移除第一个元素
    $pull: { hobbies: "swimming" },
    $push: { hobbies: "cycling" },
    $bit: { flags: { and: 8 } }
  }
)
```


# 聚合操作符
## Usage
```javascript
{ <operator>: [ <argument1>, <argument2> ... ] }
{ <operator>: <argument> }
```

## 算术表达式操作符

算术表达式对数字执行数学运算。一些算术表达式也可以支持日期算术。

| 名称                                                                                                                               | 说明                                                                                                                                                                                                                                                                                                 |
| :--------------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$abs`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/abs/#mongodb-expression-exp.-abs)                | 返回一个数字的绝对值。                                                                                                                                                                                                                                                                               |
| [`$add`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/add/#mongodb-expression-exp.-add)                | 添加数字以返回总和，或添加数字和日期以返回新日期。如果添加数字和日期，则将数字视为毫秒。接受任意数量的参数表达式，但一个表达式最多只能解析为一个日期。                                                                                                                                               |
| [`$ceil`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/ceil/#mongodb-expression-exp.-ceil)             | 返回大于或等于指定数字的最小整数。                                                                                                                                                                                                                                                                   |
| [`$divide`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/divide/#mongodb-expression-exp.-divide)       | 返回第一个数字除以第二个数字的结果。接受两个参数表达式。                                                                                                                                                                                                                                             |
| [`$exp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/exp/#mongodb-expression-exp.-exp)                | 将 *e* 提升到指定的指数。                                                                                                                                                                                                                                                                            |
| [`$floor`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/floor/#mongodb-expression-exp.-floor)          | 返回小于或等于指定数字 最大整数。                                                                                                                                                                                                                                                                    |
| [`$ln`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/ln/#mongodb-expression-exp.-ln)                   | 计算数字的自然对数。                                                                                                                                                                                                                                                                                 |
| [`$log`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/log/#mongodb-expression-exp.-log)                | 以指定基数计算数字的对数。                                                                                                                                                                                                                                                                           |
| [`$log10`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/log10/#mongodb-expression-exp.-log10)          | 计算一个数字以 10 为底的对数。                                                                                                                                                                                                                                                                       |
| [`$mod`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/mod/#mongodb-expression-exp.-mod)                | 返回第一个数字除以第二个数字的余数。接受两个参数表达式。                                                                                                                                                                                                                                             |
| [`$multiply`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/multiply/#mongodb-expression-exp.-multiply) | 将数字相乘以返回乘积。接受任意数量的参数表达式。                                                                                                                                                                                                                                                     |
| [`$pow`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/pow/#mongodb-expression-exp.-pow)                | 将一个数字提升到指定的指数。                                                                                                                                                                                                                                                                         |
| [`$round`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/round/#mongodb-expression-exp.-round)          | 将数字舍入到整数*或*指定的小数位。                                                                                                                                                                                                                                                                   |
| [`$sqrt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sqrt/#mongodb-expression-exp.-sqrt)             | 计算平方根。                                                                                                                                                                                                                                                                                         |
| [`$subtract`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/subtract/#mongodb-expression-exp.-subtract) | 返回第一个值减去第二个值后的结果。如果这两个值均为数字，则返回差值。如果这两个值均为日期，则返回以毫秒为单位的差值。如果这两个值一个为日期而另一个为数字（以毫秒为单位），则返回生成的日期。接受两个参数表达式。如果这两个值一个为日期而另一个为数字，请先指定日期参数，因为用数字减去日期没有意义。 |
| [`$trunc`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/trunc/#mongodb-expression-exp.-trunc)          | 将数字截断为整数*或*指定的小数位。                                                                                                                                                                                                                                                                   |



## 数组表达式操作符

| 名称                                                                                                                                              | 说明                                                                                                                                                                  |
| :------------------------------------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$arrayElemAt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/arrayElemAt/#mongodb-expression-exp.-arrayElemAt)       | 返回位于指定数组索引处的元素。                                                                                                                                        |
| [`$arrayToObject`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/arrayToObject/#mongodb-expression-exp.-arrayToObject) | 将键值对数组转换为文档。                                                                                                                                              |
| [`$concatArrays`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/concatArrays/#mongodb-expression-exp.-concatArrays)    | 连接数组以返回连接后的数组。                                                                                                                                          |
| [`$filter`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/filter/#mongodb-expression-exp.-filter)                      | 选择数组的子集，以返回仅包含与筛选条件匹配的元素的数组。                                                                                                              |
| [`$firstN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/firstN/#mongodb-expression-exp.-firstN)                      | 从数组开头返回指定数量的元素。与 [`$firstN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/firstN/#mongodb-group-grp.-firstN) 累加器不同。 |
| [`$in`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/in/#mongodb-expression-exp.-in)                                  | 返回一个布尔值，它可表示指定的值是否在数组中。                                                                                                                        |
| [`$indexOfArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/indexOfArray/#mongodb-expression-exp.-indexOfArray)    | 搜索数组中出现的指定值，并返回首次出现的数组索引。数组索引从零开始。                                                                                                  |
| [`$isArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/isArray/#mongodb-expression-exp.-isArray)                   | 确定操作数是否为数组。返回一个布尔值。                                                                                                                                |
| [`$lastN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lastN/#mongodb-expression-exp.-lastN)                         | 从数组末尾返回指定数量的元素。与 [`$lastN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lastN/#mongodb-group-grp.-lastN) 累加器不同。    |
| [`$map`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/map/#mongodb-expression-exp.-map)                               | 对数组的每个元素应用子表达式，并按顺序返回生成值的数组。接受已命名的参数。                                                                                            |
| [`$maxN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/maxN-array-element/#mongodb-expression-exp.-maxN)              | 返回数组中 `n` 个最大值。与 [`$maxN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/maxN/#mongodb-group-grp.-maxN) 累加器不同。            |
| [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN-array-element/#mongodb-expression-exp.-minN)              | 返回数组中 `n` 个最小值。与 [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN/#mongodb-group-grp.-minN) 累加器不同。            |
| [`$objectToArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/objectToArray/#mongodb-expression-exp.-objectToArray) | 将文档转换为表示键值对的文档数组。                                                                                                                                    |
| [`$range`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/range/#mongodb-expression-exp.-range)                         | 根据用户定义的输入，输出一个包含整数序列的数组。                                                                                                                      |
| [`$reduce`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/reduce/#mongodb-expression-exp.-reduce)                      | 将表达式应用于数组中的每个元素，并将它们组合成一个值。                                                                                                                |
| [`$reverseArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/reverseArray/#mongodb-expression-exp.-reverseArray)    | 返回元素顺序相反的数组。                                                                                                                                              |
| [`$size`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/size/#mongodb-expression-exp.-size)                            | 返回数组中元素的个数。接受单个表达式作为参数。                                                                                                                        |
| [`$slice`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/slice/#mongodb-expression-exp.-slice)                         | 返回数组的子集。                                                                                                                                                      |
| [`$sortArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sortArray/#mongodb-expression-exp.-sortArray)             | 对数组的元素进行排序。                                                                                                                                                |
| [`$zip`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/zip/#mongodb-expression-exp.-zip)                               | 将两个数组进行合并。                                                                                                                                                  |

## 按位操作符

| 名称                                                                                                                         | 说明                                                                                               |
| :--------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------- |
| [`$bitAnd`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bitAnd/#mongodb-expression-exp.-bitAnd) | 返回对 `int` 或`long` 值的数组执行按位 `and` 操作的结果。*6.3 版本中的新功能*。                    |
| [`$bitNot`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bitNot/#mongodb-expression-exp.-bitNot) | 返回对单个参数或包含单个 `int` 或 `long` 值的数组执行按位 `not` 操作的结果。*6.3 版本中的新功能*。 |
| [`$bitOr`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bitOr/#mongodb-expression-exp.-bitOr)    | 返回对 `int` 或`long` 值的数组执行按位 `or` 操作的结果。*6.3 版本中的新功能*。                     |
| [`$bitXor`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bitXor/#mongodb-expression-exp.-bitXor) | 返回对 `int` 和 `long` 值的数组执行按位 `xor`（排他或）操作的结果。*6.3 版本中的新功能*。          |

## 布尔表达式操作符

布尔表达式可将其参数表达式作为布尔值进行计算，并返回一个布尔值以作为结果。

除 `false` 布尔值之外，布尔表达式还将以下值计算为 `false`：`null`、`0` 和 `undefined` 值。布尔表达式将所有其他值计算为 `true`，包括非零数值和数组。

| 名称                                                                                                                | 说明                                                                              |
| :------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------------------------------- |
| [`$and`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/and/#mongodb-expression-exp.-and) | 仅当*所有*表达式的计算结果均为 `true` 时才返回 `true`。接受任意数量的参数表达式。 |
| [`$not`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/not/#mongodb-expression-exp.-not) | 返回与参数表达式相反的布尔值。接受单个参数表达式。                                |
| [`$or`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/or/#mongodb-expression-exp.-or)    | 当*任何* 表达式的计算结果为 `true` 时，返回 `true`。接受任意数量的参数表达式。    |

## 比较表达式操作符

比较表达式返回一个布尔值，但 [`$cmp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/cmp/#mongodb-expression-exp.-cmp) 除外，它返回一个数字。

比较表达式接受两个参数表达式，并使用不同类型的值的[指定 BSON 比较顺序](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-type-comparison-order/#std-label-bson-types-comparison-order)来比较值和类型。

| 名称                                                                                                                | 说明                                                                                                      |
| :------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------------------------------------------------------- |
| [`$cmp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/cmp/#mongodb-expression-exp.-cmp) | 如果两个值相等，则返回 `0`；如果第一个值大于第二个值，则返回 `1`；如果第一个值小于第二个值，则返回 `-1`。 |
| [`$eq`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/eq/#mongodb-expression-exp.-eq)    | 如果这些值相等，则返回 `true`。                                                                           |
| [`$gt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/gt/#mongodb-expression-exp.-gt)    | 如果第一个值大于第二个值，则返回 `true`。                                                                 |
| [`$gte`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/gte/#mongodb-expression-exp.-gte) | 如果第一个值大于等于第二个值，则返回 `true`。                                                             |
| [`$lt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lt/#mongodb-expression-exp.-lt)    | 如果第一个值小于第二个值，则返回 `true`。                                                                 |
| [`$lte`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lte/#mongodb-expression-exp.-lte) | 如果第一个值小于等于第二个值，则返回 `true`。                                                             |
| [`$ne`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/ne/#mongodb-expression-exp.-ne)    | 如果值*不*相等，则返回 `true`。                                                                           |



## 条件表达式操作符

| 名称                                                                                                                         | 说明                                                                                                                                                                                     |
| :--------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$cond`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/cond/#mongodb-expression-exp.-cond)       | 一种三元运算符，它可用于计算一个表达式，并根据结果返回另外两个表达式之一的值。接受有序列表中的三个表达式或三个已命名的参数。                                                             |
| [`$ifNull`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/ifNull/#mongodb-expression-exp.-ifNull) | 返回第一个表达式的非空结果；或者，如果第一个表达式生成空结果，则返回第二个表达式的结果。Null 结果包含未定义值或缺失字段的情况。接受两个表达式以作为参数。第二个表达式的结果可能为 null。 |
| [`$switch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/switch/#mongodb-expression-exp.-switch) | 对一系列 case 表达式求值。当它找到计算结果为 `true` 的表达式时，`$switch` 会执行指定表达式并脱离控制流。                                                                                 |

## 自定义聚合表达式操作符

| 名称                                                                                                                                   | 说明                   |
| :------------------------------------------------------------------------------------------------------------------------------------- | :--------------------- |
| [`$accumulator`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/accumulator/#mongodb-group-grp.-accumulator) | 定义自定义累加器函数。 |
| [`$function`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/function/#mongodb-expression-exp.-function)     | 定义自定义函数。       |

## 数据大小操作符

以下操作符返回数据元素的大小：

| 名称                                                                                                                                     | 说明                                                                                                                                                        |
| :--------------------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$binarySize`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/binarySize/#mongodb-expression-exp.-binarySize) | 返回给定字符串或二进制数据值内容的大小（以字节为单位）。                                                                                                    |
| [`$bsonSize`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bsonSize/#mongodb-expression-exp.-bsonSize)       | 以字节为单位返回给定文档（即 bsontype `Object`) 的大小（当编码为 [BSON](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-BSON) 时）。 |



## 日期表达式操作符

以下操作符返回日期对象或日期对象的组件：

| 名称                                                                                                                                                 | 说明                                                                                                                  |
| :--------------------------------------------------------------------------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------------------------- |
| [`$dateAdd`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateAdd/#mongodb-expression-exp.-dateAdd)                      | 向日期对象添加多个时间单位。                                                                                          |
| [`$dateDiff`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateDiff/#mongodb-expression-exp.-dateDiff)                   | 返回两个日期之间的差值。                                                                                              |
| [`$dateFromParts`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateFromParts/#mongodb-expression-exp.-dateFromParts)    | 根据日期的组成部分构造一个 BSON 日期对象。                                                                            |
| [`$dateFromString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateFromString/#mongodb-expression-exp.-dateFromString) | 将日期/时间字符串转换为日期对象。                                                                                     |
| [`$dateSubtract`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateSubtract/#mongodb-expression-exp.-dateSubtract)       | 从日期对象中减去多个时间单位。                                                                                        |
| [`$dateToParts`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateToParts/#mongodb-expression-exp.-dateToParts)          | 返回包含日期组成部分的文档。                                                                                          |
| [`$dateToString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateToString/#mongodb-expression-exp.-dateToString)       | 以格式化字符串的形式返回日期。                                                                                        |
| [`$dateTrunc`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateTrunc/#mongodb-expression-exp.-dateTrunc)                | 截断日期。                                                                                                            |
| [`$dayOfMonth`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dayOfMonth/#mongodb-expression-exp.-dayOfMonth)             | 以介于 1 和 31 之间的数字返回某一日期的“月中的某一天”。                                                               |
| [`$dayOfWeek`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dayOfWeek/#mongodb-expression-exp.-dayOfWeek)                | 以 1（星期日）和 7（星期六）之间的数字形式返回以星期表示的日期。                                                      |
| [`$dayOfYear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dayOfYear/#mongodb-expression-exp.-dayOfYear)                | 以 1 到 366（闰年）之间的数字形式返回返回日期的年月日。                                                               |
| [`$hour`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/hour/#mongodb-expression-exp.-hour)                               | 以数字形式返回日期中的小时部分（0 到 23）。                                                                           |
| [`$isoDayOfWeek`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/isoDayOfWeek/#mongodb-expression-exp.-isoDayOfWeek)       | 以 ISO 8601 格式返回工作日数字，范围为 `1`（星期一）到 `7`（星期日）。                                                |
| [`$isoWeek`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/isoWeek/#mongodb-expression-exp.-isoWeek)                      | 以 ISO 8601 格式返回周数，范围从 `1` 到 `53`。周数从 `1` 开始，即包含一年中第一个星期四的那个星期（星期一到星期日）。 |
| [`$isoWeekYear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/isoWeekYear/#mongodb-expression-exp.-isoWeekYear)          | 以 ISO 8601 格式返回年份号。一年从第一周的星期一 (ISO 8601) 开始，到最后一周的星期日 (ISO 8601) 结束。                |
| [`$millisecond`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/millisecond/#mongodb-expression-exp.-millisecond)          | 以 0 到 999 之间的数字形式返回日期的毫秒数。                                                                          |
| [`$minute`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minute/#mongodb-expression-exp.-minute)                         | 返回日期的分钟数（0 到 59）。                                                                                         |
| [`$month`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/month/#mongodb-expression-exp.-month)                            | 以 1（一月）到 12（十二月）之间的数字形式返回日期的月份。                                                             |
| [`$second`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/second/#mongodb-expression-exp.-second)                         | 以 0 到 60 之间的数字返回日期的秒数（跳秒）。                                                                         |
| [`$toDate`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toDate/#mongodb-expression-exp.-toDate)                         | 将数值转换为日期。                                                                                                    |
| [`$week`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/week/#mongodb-expression-exp.-week)                               | 以 0（一年中第一个星期日之前的部分周）和 53（闰年）之间的数字形式返回日期的周数。                                     |
| [`$year`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/year/#mongodb-expression-exp.-year)                               | 以数字形式返回日期的年份（例如 2014）。                                                                               |

以下算术操作符可以使用日期操作数：

| 名称                                                                                                                               | 说明                                                                                                                                                                                                                                                               |
| :--------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$add`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/add/#mongodb-expression-exp.-add)                | 添加数字和日期以返回新日期。如果添加数字和日期，则将数字视为毫秒。接受任意数量的参数表达式，但一个表达式最多只能解析为一个日期。                                                                                                                                   |
| [`$subtract`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/subtract/#mongodb-expression-exp.-subtract) | 返回第一个值减去第二个值后的结果。如果这两个值均为日期，则返回以毫秒为单位的差值。如果这两个值一个为日期而另一个为数字（以毫秒为单位），则返回生成的日期。接受两个参数表达式。如果这两个值一个为日期而另一个为数字，请先指定日期参数，因为用数字减去日期没有意义。 |

## 字面值表达式操作符

| 名称                                                                                                                            | 说明                                                                                                                                                                                                                                                             |
| :------------------------------------------------------------------------------------------------------------------------------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$literal`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/literal/#mongodb-expression-exp.-literal) | 返回一个值而不进行解析。用于聚合管道可解释为表达式的值。例如，对以美元符号 (`$`) 开头的字符串使用 [`$literal`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/literal/#mongodb-expression-exp.-literal) 表达式，以避免解析为字段路径。 |

## 其他操作符

| 名称                                                                                                                                                       | 说明                                                                                                                                                                                                                                                          |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [`$getField`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/getField/#mongodb-expression-exp.-getField)                         | 从文档中返回指定字段的值。您可以使用 [`$getField`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/getField/#mongodb-expression-exp.-getField) 检索名称中包含句点 (`.`) 或以美元符号 (`$`) 开头的字段的值。*版本 5.0 中的新增功能*。 |
| [`$rand`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/rand/#mongodb-expression-exp.-rand)                                     | 返回介于 0 和 1 之间的随机浮点数。                                                                                                                                                                                                                            |
| [`$sampleRate`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sampleRate/#mongodb-expression-exp.-sampleRate)                   | 以给定的采样率随机选择文档。虽然每次运行所选文件的确切数量各不相同，但所选数量近似于以文件总数百分比表示的采样率。                                                                                                                                            |
| [`$toHashedIndexKey`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toHashedIndexKey/#mongodb-expression-exp.-toHashedIndexKey) | 使用 MongoDB 用于创建哈希索引的相同哈希函数计算并返回输入表达式的哈希值。                                                                                                                                                                                     |

## 对象表达式操作符

| 名称                                                                                                                                              | 说明                                                                                                                                                                                                                                                                        |
| :------------------------------------------------------------------------------------------------------------------------------------------------ | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$mergeObjects`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/mergeObjects/#mongodb-expression-exp.-mergeObjects)    | 将多个文档合并为一个文档。                                                                                                                                                                                                                                                  |
| [`$objectToArray`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/objectToArray/#mongodb-expression-exp.-objectToArray) | 将文档转换为表示键值对的文档数组。                                                                                                                                                                                                                                          |
| [`$setField`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setField/#mongodb-expression-exp.-setField)                | 添加、更新或删除文档中的指定字段。您可以使用 [`$setField`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setField/#mongodb-expression-exp.-setField) 添加、更新或删除名称包含句点 (`.`) 或以美元符号 (`$`) 开头的字段。*版本 5.0 中的新增功能*。 |



## 集表达式操作符

集合表达式对数组执行集合操作，将数组视为集合。集合表达式会忽略每个输入数组中的重复条目和元素的顺序。

如果集操作返回一个集，则该操作会筛选掉结果中的重复项，以输出仅包含唯一条目的数组。输出数组中元素的顺序未指定。

如果集合包含嵌套数组元素，则集合表达式*不会*进入嵌套数组，而是在顶层计算数组。

| 名称                                                                                                                                                    | 说明                                                                                                                                                                                           |
| :------------------------------------------------------------------------------------------------------------------------------------------------------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$allElementsTrue`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/allElementsTrue/#mongodb-expression-exp.-allElementsTrue) | 如果集合中*没有*元素计算结果为 `false` ，则返回 `true`，否则返回 `false`。接受单个参数表达式。                                                                                                 |
| [`$anyElementTrue`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/anyElementTrue/#mongodb-expression-exp.-anyElementTrue)    | 如果集合中的*任何*元素的计算结果为 `true`，则返回 `true`；否则，返回 `false`。接受单个参数表达式。                                                                                             |
| [`$setDifference`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setDifference/#mongodb-expression-exp.-setDifference)       | 返回集合，其中的元素出现在第一个集合中，但没有出现在第二个集合中；即执行第二个集合相对于第一个集合的[相对补集](http://en.wikipedia.org/wiki/Complement_(set_theory))。实际接受两个参数表达式。 |
| [`$setEquals`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setEquals/#mongodb-expression-exp.-setEquals)                   | 如果输入集具有相同的不同元素，则返回 `true`。接受两个或多个参数表达式。                                                                                                                        |
| [`$setIntersection`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setIntersection/#mongodb-expression-exp.-setIntersection) | 返回一个集，其中包含出现在*所有*输入集中的元素。接受任意数量的参数表达式。                                                                                                                     |
| [`$setIsSubset`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setIsSubset/#mongodb-expression-exp.-setIsSubset)             | 如果第一个集的所有元素都出现在第二个集中，包括当第一个集等于第二个集时，即不是[严格子集](http://en.wikipedia.org/wiki/Subset)，则返回 `true`。实际接受两个参数表达式。                         |
| [`$setUnion`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setUnion/#mongodb-expression-exp.-setUnion)                      | 返回一个集，其中包含出现在*任何*输入集中的元素。                                                                                                                                               |

## 字符串表达式操作符

字符串表达式，除了 [`$concat`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/concat/#mongodb-expression-exp.-concat) 之外，字符串表达式只对 ASCII 字符串有明确定义的行为。

无论使用什么字符，[`$concat`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/concat/#mongodb-expression-exp.-concat) 行为都是明确定义的。

| 名称                                                                                                                                                 | 说明                                                                                                                                                                                                                                                                                            |
| :--------------------------------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$concat`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/concat/#mongodb-expression-exp.-concat)                         | 连接任意数量的字符串。                                                                                                                                                                                                                                                                          |
| [`$dateFromString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateFromString/#mongodb-expression-exp.-dateFromString) | 将日期/时间字符串转换为日期对象。                                                                                                                                                                                                                                                               |
| [`$dateToString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/dateToString/#mongodb-expression-exp.-dateToString)       | 以格式化字符串的形式返回日期。                                                                                                                                                                                                                                                                  |
| [`$indexOfBytes`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/indexOfBytes/#mongodb-expression-exp.-indexOfBytes)       | 搜索字符串中出现的子字符串，并返回首次出现的 UTF-8 字节索引。如果未找到该子字符串，则返回 `-1`。                                                                                                                                                                                                |
| [`$indexOfCP`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/indexOfCP/#mongodb-expression-exp.-indexOfCP)                | 在字符串中搜索子字符串的出现位置，并返回第一次出现时的 UTF-8 码位索引。如果未找到子字符串，则返回 `-1`。                                                                                                                                                                                        |
| [`$ltrim`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/ltrim/#mongodb-expression-exp.-ltrim)                            | 删除字符串开头和结尾的空白或指定字符。                                                                                                                                                                                                                                                          |
| [`$regexFind`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/regexFind/#mongodb-expression-exp.-regexFind)                | 将正则表达式应用于字符串，并返回*第一个*匹配子字符串的信息。                                                                                                                                                                                                                                    |
| [`$regexFindAll`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/regexFindAll/#mongodb-expression-exp.-regexFindAll)       | 将正则表达式 (regex) 应用于字符串，并返回有关所有匹配子字符串的信息。                                                                                                                                                                                                                           |
| [`$regexMatch`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/regexMatch/#mongodb-expression-exp.-regexMatch)             | 将正则表达式 (regex) 应用于字符串并返回一个布尔值，它可表示是否已找到匹配项。                                                                                                                                                                                                                   |
| [`$replaceOne`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceOne/#mongodb-expression-exp.-replaceOne)             | 替换给定输入中匹配字符串的第一个实例。                                                                                                                                                                                                                                                          |
| [`$replaceAll`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceAll/#mongodb-expression-exp.-replaceAll)             | 替换给定输入中匹配字符串的所有实例。                                                                                                                                                                                                                                                            |
| [`$rtrim`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/rtrim/#mongodb-expression-exp.-rtrim)                            | 删除字符串结尾的空白或指定字符。                                                                                                                                                                                                                                                                |
| [`$split`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/split/#mongodb-expression-exp.-split)                            | 根据分隔符将字符串拆分为子字符串。返回子字符串数组。如果在字符串中找不到分隔符，则返回包含原始字符串的数组。                                                                                                                                                                                    |
| [`$strLenBytes`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/strLenBytes/#mongodb-expression-exp.-strLenBytes)          | 返回字符串中 UTF-8 编码的字节数。                                                                                                                                                                                                                                                               |
| [`$strLenCP`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/strLenCP/#mongodb-expression-exp.-strLenCP)                   | 返回字符串中 UTF-8 [代码点的数量。](http://www.unicode.org/glossary/#code_point)                                                                                                                                                                                                                |
| [`$strcasecmp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/strcasecmp/#mongodb-expression-exp.-strcasecmp)             | 执行不区分大小写的字符串比较并返回：如果两个字符串相等，则返回 `0`；如果第一个字符串大于第二个字符串，则返回 `1`；如果第一个字符串小于第二个字符串，则返回 `-1`。                                                                                                                               |
| [`$substr`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/substr/#mongodb-expression-exp.-substr)                         | 已弃用。使用[`$substrBytes`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/substrBytes/#mongodb-expression-exp.-substrBytes) 或 [`$substrCP`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/substrCP/#mongodb-expression-exp.-substrCP)。 |
| [`$substrBytes`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/substrBytes/#mongodb-expression-exp.-substrBytes)          | 返回字符串的子串。从字符串中指定的 UTF-8 字节索引（从零开始）处的字符开始，持续指定的字节数。                                                                                                                                                                                                   |
| [`$substrCP`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/substrCP/#mongodb-expression-exp.-substrCP)                   | 返回字符串的子串。从字符串中指定 UTF-8 [代码点 (CP) 索引](http://www.unicode.org/glossary/#code_point)（从零开始）处的字符开始，持续指定的代码点数。                                                                                                                                            |
| [`$toLower`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toLower/#mongodb-expression-exp.-toLower)                      | 将字符串转换为小写。接受单个参数表达式。                                                                                                                                                                                                                                                        |
| [`$toString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toString/#mongodb-expression-exp.-toString)                   | 将值转换为字符串。                                                                                                                                                                                                                                                                              |
| [`$trim`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/trim/#mongodb-expression-exp.-trim)                               | 删除字符串开头和结尾的空白或指定字符。                                                                                                                                                                                                                                                          |
| [`$toUpper`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toUpper/#mongodb-expression-exp.-toUpper)                      | 将字符串转换为大写。接受单个参数表达式。                                                                                                                                                                                                                                                        |

## 文本表达式操作符

| 名称                                                                                                                   | 说明                                       |
| :--------------------------------------------------------------------------------------------------------------------- | :----------------------------------------- |
| [`$meta`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/meta/#mongodb-expression-exp.-meta) | 访问与聚合操作相关的每个文档的可用元数据。 |

## 时间戳表达式操作符

时间戳表达式操作符从[时间戳](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-types/#std-label-document-bson-type-timestamp)返回值。

| 名称                                                                                                                                        | 说明                                                                                                                                                                                                                                                                                     |
| :------------------------------------------------------------------------------------------------------------------------------------------ | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$tsIncrement`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/tsIncrement/#mongodb-expression-exp.-tsIncrement) | 以 [`long`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json-v1/#mongodb-bsontype-data_numberlong) 形式返回[时间戳](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-types/#std-label-document-bson-type-timestamp)中的递增序数。*5.1 版本中的新功能*。 |
| [`$tsSecond`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/tsSecond/#mongodb-expression-exp.-tsSecond)          | 以 [`long`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json-v1/#mongodb-bsontype-data_numberlong) 形式返回[时间戳](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-types/#std-label-document-bson-type-timestamp)中的秒数。*5.1 版本中的新功能*。     |

## 三角函数表达式操作符

三角表达式对数字执行三角运算。示角度的值始终以弧度为单位输入或输出。使用 [`$degreesToRadians`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/degreesToRadians/#mongodb-expression-exp.-degreesToRadians) 和 [`$radiansToDegrees`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/radiansToDegrees/#mongodb-expression-exp.-radiansToDegrees) 在度数和弧度测量值之间转换。

| 名称                                                                                                                                                       | 说明                                                                                          |
| :--------------------------------------------------------------------------------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------- |
| [`$sin`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sin/#mongodb-expression-exp.-sin)                                        | 返回以弧度为单位测量的某一值的正弦值。                                                        |
| [`$cos`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/cos/#mongodb-expression-exp.-cos)                                        | 返回以弧度为单位测量的某一值的余弦。                                                          |
| [`$tan`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/tan/#mongodb-expression-exp.-tan)                                        | 返回以弧度为单位来测量的某一值的正切值。                                                      |
| [`$asin`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/asin/#mongodb-expression-exp.-asin)                                     | 以弧度为单位返回值的反正弦。                                                                  |
| [`$acos`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/acos/#mongodb-expression-exp.-acos)                                     | 以弧度为单位返回值的反余弦。                                                                  |
| [`$atan`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/atan/#mongodb-expression-exp.-atan)                                     | 以弧度为单位返回某一值的反切值（弧正切值）。                                                  |
| [`$atan2`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/atan2/#mongodb-expression-exp.-atan2)                                  | 以弧度为单位返回 `y / x` 的反正切，其中 `y` 和 `x` 分别为传递给该表达式的第一个值和第二个值。 |
| [`$asinh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/asinh/#mongodb-expression-exp.-asinh)                                  | 以弧度为单位返回数值的反双曲正弦（双曲弧正弦）值。                                            |
| [`$acosh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/acosh/#mongodb-expression-exp.-acosh)                                  | 以弧度为单位返回数值的反双曲余弦（双曲弧余弦）值。                                            |
| [`$atanh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/atanh/#mongodb-expression-exp.-atanh)                                  | 以弧度为单位返回数值的反双曲正切（双曲弧切）值。                                              |
| [`$sinh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sinh/#mongodb-expression-exp.-sinh)                                     | 返回以弧度为单位来测量的某一值的双曲正弦值。                                                  |
| [`$cosh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/cosh/#mongodb-expression-exp.-cosh)                                     | 返回以弧度为单位的值的双曲余弦值。                                                            |
| [`$tanh`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/tanh/#mongodb-expression-exp.-tanh)                                     | 以弧度为单位返回数值的双曲正切值。                                                            |
| [`$degreesToRadians`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/degreesToRadians/#mongodb-expression-exp.-degreesToRadians) | 将值从度数转换为弧度。                                                                        |
| [`$radiansToDegrees`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/radiansToDegrees/#mongodb-expression-exp.-radiansToDegrees) | 将值从弧度转换为度数。                                                                        |

## 类型表达式操作符

| 名称                                                                                                                                     | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| :--------------------------------------------------------------------------------------------------------------------------------------- | :-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$convert`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/convert/#mongodb-expression-exp.-convert)          | 将数值转换为指定类型。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [`$isNumber`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/isNumber/#mongodb-expression-exp.-isNumber)       | 如果指定表达式解析为 [`integer`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json/#mongodb-bsontype-Int32)、[`decimal`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json/#mongodb-bsontype-Int32)、[`double`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json/#mongodb-bsontype-Decimal128) 或 [`long`](https://www.mongodb.com/zh-cn/docs/manual/reference/mongodb-extended-json/#mongodb-bsontype-Double)，则返回布尔值 `true`。如果表达式解析为任何其他 [BSON 类型](https://www.mongodb.com/zh-cn/docs/manual/reference/bson-types/#std-label-bson-types)、`null` 或缺失字段，返回布尔值 `false`。 |
| [`$toBool`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toBool/#mongodb-expression-exp.-toBool)             | 将值转换为布尔值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$toDate`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toDate/#mongodb-expression-exp.-toDate)             | 将数值转换为日期。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$toDecimal`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toDecimal/#mongodb-expression-exp.-toDecimal)    | 将值转换为 Decimal128。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$toDouble`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toDouble/#mongodb-expression-exp.-toDouble)       | 将值转换为 double。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| [`$toInt`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toInt/#mongodb-expression-exp.-toInt)                | 将值转换为整数。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$toLong`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toLong/#mongodb-expression-exp.-toLong)             | 将值转换为长整数。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$toObjectId`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toObjectId/#mongodb-expression-exp.-toObjectId) | 将值转换为 ObjectId。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [`$toString`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toString/#mongodb-expression-exp.-toString)       | 将值转换为字符串。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$type`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/type/#mongodb-expression-exp.-type)                   | 返回字段的 BSON 数据类型。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| [`$toUUID`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/toUUID/#mongodb-expression-exp.-toUUID)             | 将字符串转换为 UUID。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |



## 累加器 (`$group, $bucket, $bucketAuto, $setWindowFields`)

聚合累加器操作符：

- 在文件通过聚合管道时保持其状态。
- 返回总计值、最大值、最小值和其他值。
- 可在以下聚合管道阶段中使用：
  - [`$bucket`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bucket/#mongodb-pipeline-pipe.-bucket)
  - [`$bucketAuto`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bucketAuto/#mongodb-pipeline-pipe.-bucketAuto)
  - [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)
  - [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)，从 MongoDB 5.0 开始（除非使用 [`$accumulator`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/accumulator/#mongodb-group-grp.-accumulator) 或 [`$mergeObjects`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/mergeObjects/#mongodb-expression-exp.-mergeObjects) 操作符，它们不能与 [`$setWindowFields` 一起使用）](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)

*5.0 版本中的更改*。

| 名称                                                                                                                                           | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| :--------------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [`$accumulator`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/accumulator/#mongodb-group-grp.-accumulator)         | 返回用户定义的累加器函数的结果。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [`$addToSet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addToSet/#mongodb-group-grp.-addToSet)                  | 返回每个群组的*唯一*表达式值数组。未定义数组元素的排序。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [`$avg`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/avg/#mongodb-group-grp.-avg)                                 | 返回数值的平均值。忽略非数字值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| [`$bottom`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bottom/#mongodb-group-grp.-bottom)                        | 根据指定的排序顺序返回组内的底部元素。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                            |
| [`$bottomN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bottomN/#mongodb-group-grp.-bottomN)                     | 根据指定的排序顺序，返回群组内后 `n` 个字段的聚合。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                               |
| [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count-accumulator/#mongodb-group-grp.-count)               | 返回群组中的文档数。有别于 [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count/#mongodb-pipeline-pipe.-count) 管道阶段。*5.0 版新增功能*：可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                          |
| [`$first`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/first/#mongodb-group-grp.-first)                           | 返回群组中第一个文档的[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)结果。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                   |
| [`$firstN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/firstN/#mongodb-group-grp.-firstN)                        | 返回群组内前 `n` 个元素的聚合。仅当文档按定义的顺序排列时才有意义。与 [`$firstN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/firstN/#mongodb-expression-exp.-firstN) 数组操作符不同。*5.2 版新增功能*：可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)、[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                               |
| [`$last`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/last/#mongodb-group-grp.-last)                              | 返回群组中最后一份文档的[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)结果。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                 |
| [`$lastN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lastN/#mongodb-group-grp.-lastN)                           | 返回群组内后 `n` 个元素的聚合。仅当文档按定义的顺序排列时才有意义。与 [`$lastN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lastN/#mongodb-expression-exp.-lastN) 数组操作符不同。*5.2 版新增功能*：可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)、[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                  |
| [`$max`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/max/#mongodb-group-grp.-max)                                 | 返回每个群组的最大表达式值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| [`$maxN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/maxN/#mongodb-group-grp.-maxN)                              | 返回群组中 `n` 个最大值元素的聚合。与 [`$maxN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/maxN-array-element/#mongodb-expression-exp.-maxN) 数组操作符不同。*5.2 版本中的新增功能*。在[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 、 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)中可用，也可作为[表达式使用。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)                                          |
| [`$median`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/median/#mongodb-group-grp.-median)                        | 返回[中位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-median)（第 50 [百分位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-percentile)）的近似标量值。*7.0 版本中的新增功能*。此操作符可在以下阶段用作累加器：[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)[`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)它也可用作[聚合表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)。 |
| [`$mergeObjects`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/mergeObjects/#mongodb-expression-exp.-mergeObjects) | 返回通过组合每个组的输入文档创建的文档。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| [`$min`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/min/#mongodb-group-grp.-min)                                 | 返回每个群组的最小表达式值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN/#mongodb-group-grp.-minN)                              | 返回组中 `n` 个最小值元素的聚合。与 [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN-array-element/#mongodb-expression-exp.-minN) 数组操作符不同。*5.2 版本中的新增功能*。在[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 、 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)中可用，也可作为[表达式使用。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)                                            |
| [`$percentile`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/percentile/#mongodb-group-grp.-percentile)            | 返回与指定的各[百分位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-percentile)一一对应的标量值数组。*7.0 版本中的新增功能*。此操作符可在以下阶段用作累加器：[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)[`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)它也可用作[聚合表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)。                                                                                |
| [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/push/#mongodb-group-grp.-push)                              | 返回每组中文档的大量表达式值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| [`$stdDevPop`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevPop/#mongodb-group-grp.-stdDevPop)               | 返回输入值的总体标准偏差。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| [`$stdDevSamp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevSamp/#mongodb-group-grp.-stdDevSamp)            | 返回输入值的样本标准偏差。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| [`$sum`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sum/#mongodb-group-grp.-sum)                                 | 返回数值的总和。忽略非数字值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                                                                                                                                                                      |
| [`$top`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/top/#mongodb-group-grp.-top)                                 | 根据指定的排序顺序返回群组内第一个元素。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                                          |
| [`$topN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/topN/#mongodb-group-grp.-topN)                              | 根据指定的排序顺序，返回群组内前 `n` 个字段的聚合。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                               |

## 累加器（其他阶段中）

一些可用作 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 阶段累加器的操作符也可用于其他阶段，但不能作为累加器。用于其他阶段使用，这些操作符不维护其状态，并且可以将单个参数或多个参数作为输入。有关详细信息，请参阅特定操作符页面。

*5.0 版本中的更改*。

以下累加器操作符在 [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)、[`$addFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields)、[`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set)、（从 MongoDB 5.0 开始）[`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段中也可用。

| 名称                                                                                                                                | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| :---------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [`$avg`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/avg/#mongodb-group-grp.-avg)                      | 返回每个文档中指定表达式或表达式列表的平均值。忽略非数字值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$first`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/first/#mongodb-group-grp.-first)                | 返回群组中第一个文档的[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)结果。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$last`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/last/#mongodb-group-grp.-last)                   | 返回群组中最后一份文档的[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)结果。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$max`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/max/#mongodb-group-grp.-max)                      | 返回每个文档中指定表达式或表达式列表的最大值                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$median`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/median/#mongodb-group-grp.-median)             | 返回[中位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-median)（第 50 [百分位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-percentile)）的近似标量值。*7.0 版本中的新增功能*。此操作符可在以下阶段用作累加器：[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)[`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)它也可用作[聚合表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)。 |
| [`$min`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/min/#mongodb-group-grp.-min)                      | 返回针对每个文档的指定表达式或表达式列表的最小值                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| [`$percentile`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/percentile/#mongodb-group-grp.-percentile) | 返回与指定的各[百分位数](https://www.mongodb.com/zh-cn/docs/manual/reference/glossary/#std-term-percentile)一一对应的标量值数组。*7.0 版本中的新增功能*。此操作符可在以下阶段用作累加器：[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)[`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)它也可用作[聚合表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)。                                                                                |
| [`$stdDevPop`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevPop/#mongodb-group-grp.-stdDevPop)    | 返回输入值的总体标准偏差。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$stdDevSamp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevSamp/#mongodb-group-grp.-stdDevSamp) | 返回输入值的样本标准偏差。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$sum`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sum/#mongodb-group-grp.-sum)                      | 返回数值的总和。忽略非数字值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |

## 变量表达式操作符

| 名称                                                                                                                | 说明                                                                                                     |
| :------------------------------------------------------------------------------------------------------------------ | :------------------------------------------------------------------------------------------------------- |
| [`$let`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/let/#mongodb-expression-exp.-let) | 定义要在子表达式范围内使用的变量，并返回这些子表达式的结果。接受已命名的参数。接受任意数量的参数表达式。 |

## 窗口运算符

*版本 5.0 中的新增功能*。

窗口操作符从集合（称为*窗口*）中定义的文档范围返回值。[窗口](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段定义，从 MongoDB 5.0 开始可用。

以下窗口操作符可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。

| 名称                                                                                                                                            | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| :---------------------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$addToSet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addToSet/#mongodb-group-grp.-addToSet)                   | 返回对每份文档应用[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)所产生的所有唯一值的数组。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                        |
| [`$avg`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/avg/#mongodb-group-grp.-avg)                                  | 返回指定[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)的平均值。忽略非数字值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                    |
| [`$bottom`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bottom/#mongodb-group-grp.-bottom)                         | 根据指定的排序顺序返回组内的底部元素。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                 |
| [`$bottomN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bottomN/#mongodb-group-grp.-bottomN)                      | 根据指定的排序顺序，返回群组内后 `n` 个字段的聚合。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                    |
| [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count-accumulator/#mongodb-group-grp.-count)                | 返回群组或窗口中的文档数。有别于 [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count/#mongodb-pipeline-pipe.-count) 管道阶段。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| [`$covariancePop`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/covariancePop/#mongodb-group-grp.-covariancePop)    | 返回两个数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)的总体协方差。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$covarianceSamp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/covarianceSamp/#mongodb-group-grp.-covarianceSamp) | 返回两个数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)的样本协方差。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$denseRank`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/denseRank/#mongodb-group-grp.-denseRank)                | 返回某文档在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段[分区](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-partitionBy)中相对于其他文档的位置（称为排名）。这些排名没有差异。并列可获得相同排名。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                               |
| [`$derivative`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/derivative/#mongodb-group-grp.-derivative)             | 返回指定[窗口](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)内的平均变化率。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
| [`$documentNumber`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/documentNumber/#mongodb-group-grp.-documentNumber) | 返回文档在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段[分区](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-partitionBy)中的位置（称为文档编号）并列会导致相邻文件编号不同。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                       |
| [`$expMovingAvg`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/expMovingAvg/#mongodb-group-grp.-expMovingAvg)       | 返回数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)的指数移动平均值。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$first`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/first/#mongodb-group-grp.-first)                            | 返回组或 [窗口 中第一个文档的](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)[ 表达式 结果。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                  |
| [`$integral`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/integral/#mongodb-group-grp.-integral)                   | 返回曲线下面积的近似值。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$last`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/last/#mongodb-group-grp.-last)                               | 返回组或 [窗口 中最后一个文档的](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)[ 表达式 结果。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                |
| [`$linearFill`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/linearFill/#mongodb-group-grp.-linearFill)             | 使用[线性插值](https://en.wikipedia.org/wiki/Linear_interpolation)并根据相邻的字段值来填充[窗口](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)中的 `null` 和缺失字段。可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。*5.3 版本中的新增功能*。                                                                                                                                                                                               |
| [`$locf`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/locf/#mongodb-group-grp.-locf)                               | 最后一次的观察结果被延续了下来。将[窗口](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-window)中 `null` 和缺失字段的值设置为该字段的最后一个非 null 值。可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。*5.2 版本中的新增功能*。                                                                                                                                                                                                                     |
| [`$max`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/max/#mongodb-group-grp.-max)                                  | 返回对每份文档应用[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)后的最小值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                      |
| [`$min`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/min/#mongodb-group-grp.-min)                                  | 返回对每份文档应用[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)后的最小值。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                      |
| [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN/#mongodb-group-grp.-minN)                               | 返回组中 `n` 个最小值元素的聚合。与 [`$minN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/minN-array-element/#mongodb-expression-exp.-minN) 数组操作符不同。*5.2 版本中的新增功能*。在[`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 、 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)中可用，也可作为[表达式使用。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions) |
| [`$push`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/push/#mongodb-group-grp.-push)                               | 返回对每个文档应用[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)后所得值的数组。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                  |
| [`$rank`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/rank/#mongodb-group-grp.-rank)                               | 返回一个文档在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段[分区](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-partitionBy)中相对于其他文档的位置（称为排名）。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                   |
| [`$shift`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/shift/#mongodb-group-grp.-shift)                            | 返回在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段的[分区](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-partitionBy)中，应用于目标文档的[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#std-label-setWindowFields-partitionBy)的值。目标文档通过与当前文档的相对位置进行指定。*版本 5.0 中的新增功能*。                                                                                                |
| [`$stdDevPop`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevPop/#mongodb-group-grp.-stdDevPop)                | 返回对每个文档应用数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)所得的总体标准差。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                            |
| [`$stdDevSamp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/stdDevSamp/#mongodb-group-grp.-stdDevSamp)             | 返回对每个文档应用数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)所得的样本标准差。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                            |
| [`$sum`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sum/#mongodb-group-grp.-sum)                                  | 返回对每份文档应用数值[表达式](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-expressions)所得的总和。*5.0 版中的更改*：可在 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                                                  |
| [`$top`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/top/#mongodb-group-grp.-top)                                  | 根据指定的排序顺序返回群组内第一个元素。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。                                                                                                                                                                                                                                                                               |
| [`$topN`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/topN/#mongodb-group-grp.-topN)                               | 根据指定的排序顺序，返回群组内前 `n` 个字段的聚合。*5.2 版本中的新增功能*。可在 [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group) 和 [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields) 阶段使用。算术表达式操作符                                                                                                                                                                                                                                                    |


# 聚合阶段
## db.collection.aggregate() Stages

除 [`$out`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/out/#mongodb-pipeline-pipe.-out)、[`$merge`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/merge/#mongodb-pipeline-pipe.-merge)、[`$geoNear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/geoNear/#mongodb-pipeline-pipe.-geoNear)、[`$changeStream`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/changeStream/#mongodb-pipeline-pipe.-changeStream) 和 [`$changeStreamSplitLargeEvent`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/changeStreamSplitLargeEvent/#mongodb-pipeline-pipe.-changeStreamSplitLargeEvent) 阶段之外的所有阶段都可以在管道中多次出现。

## 注意

有关特定操作符（包括事务语法和示例）的详细信息，请单击该操作符的参考页面链接。

```
db.collection.aggregate( [ { <stage> }, ... ] )
```

| 阶段                                                                                                                                                                                       | 说明                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | :----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$addFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields)                                                       | 为文档添加新字段。与 [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) 类似，[`$addFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields) 重塑了流中的每个文档；具体来说，就是在输出文档中添加新字段，这些输出文档既包含输入文档中的现有字段，也包含新添加的字段。[`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set) 是 [`$addFields` 的别名。](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields) |
| [`$bucket`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bucket/#mongodb-pipeline-pipe.-bucket)                                                                | 根据指定的表达式和存储桶边界将传入的文档分为多个组（称为存储桶）。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$bucketAuto`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/bucketAuto/#mongodb-pipeline-pipe.-bucketAuto)                                                    | 根据指定的表达式，将接收到的文档归类到特定数量的群组中（称为“存储桶”）。自动确定存储桶边界，以尝试将文档均匀地分配到指定数量的存储桶中。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$changeStream`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/changeStream/#mongodb-pipeline-pipe.-changeStream)                                              | 返回集合的 [Change Stream](https://www.mongodb.com/zh-cn/docs/manual/changeStreams/#std-label-changeStreams) 游标。此阶段只能在 aggregation pipeline 中发生一次，并且必须作为第一阶段发生。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$changeStreamSplitLargeEvent`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/changeStreamSplitLargeEvent/#mongodb-pipeline-pipe.-changeStreamSplitLargeEvent) | 将超过 16 MB 的大型 [change stream](https://www.mongodb.com/zh-cn/docs/manual/changeStreams/#std-label-changeStreams) 事件分割成较小的分段，在 change stream 游标中返回。您只能在 `$changeStream` 管道中使用 `$changeStreamSplitLargeEvent`，且它必须为该管道中的最后阶段。                                                                                                                                                                                                                                                                                                                                                                                                                |
| [`$collStats`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/collStats/#mongodb-pipeline-pipe.-collStats)                                                       | 返回有关集合或视图的统计信息。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count/#mongodb-pipeline-pipe.-count)                                                                   | 返回聚合管道此阶段的文档数量计数。有别于 [`$count`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/count-accumulator/#mongodb-group-grp.-count) 聚合累加器。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| [`$densify`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/densify/#mongodb-pipeline-pipe.-densify)                                                             | 在文档序列中创建新文档，其中缺少字段中的某些值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$documents`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/documents/#mongodb-pipeline-pipe.-documents)                                                       | 从输入表达式返回字面文档。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| [`$facet`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/facet/#mongodb-pipeline-pipe.-facet)                                                                   | 在单个阶段内处理同一组输入文档上的多个[聚合管道](https://www.mongodb.com/zh-cn/docs/manual/core/aggregation-pipeline/#std-label-aggregation-pipeline)。支持创建多分面聚合，能够在单个阶段中跨多个维度或分面描述数据特征。                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$fill`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/fill/#mongodb-pipeline-pipe.-fill)                                                                      | 填充文档中的 `null` 和缺失的字段值。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| [`$geoNear`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/geoNear/#mongodb-pipeline-pipe.-geoNear)                                                             | 根据与地理空间点的接近程度返回有序的文档流。针对地理空间数据，整合了 [`$match`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match)、[`$sort`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sort/#mongodb-pipeline-pipe.-sort) 和 [`$limit`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/limit/#mongodb-pipeline-pipe.-limit) 功能。输出文档包含一个额外的距离字段，并可包含一个位置标识符字段。                                                                                                                                                                        |
| [`$graphLookup`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/graphLookup/#mongodb-pipeline-pipe.-graphLookup)                                                 | 对集合执行递归搜索。为每个输出文档添加一个新数组字段，其中包含该文档的递归搜索遍历结果。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$group`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/group/#mongodb-pipeline-pipe.-group)                                                                   | 按指定的标识符表达式对输入文档进行分组，并将累加器表达式（如果指定）应用于每个群组。接收所有输入文档，并为每个不同群组输出一个文档。输出文档仅包含标识符字段和累积字段（如果指定）。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| [`$indexStats`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/indexStats/#mongodb-pipeline-pipe.-indexStats)                                                    | 返回有关集合的每个索引使用情况的统计信息。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| [`$limit`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/limit/#mongodb-pipeline-pipe.-limit)                                                                   | 将未修改的前 *n 个*文档传递到管道，其中 *n* 为指定的限制。对于每个输入文档，输出一个文档（针对前 *n 个*文档）或零个文档（前 *n 个*文档之后）。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [`$listSampledQueries`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/listSampledQueries/#mongodb-pipeline-pipe.-listSampledQueries)                            | 列出所有集合或特定集合的抽样查询。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$listSearchIndexes`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/listSearchIndexes/#mongodb-pipeline-pipe.-listSearchIndexes)                               | 返回指定集合上现有 [Atlas Search 索引](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/atlas-search-overview/#fts-indexes)的信息。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$listSessions`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/listSessions/#mongodb-pipeline-pipe.-listSessions)                                              | 列出活动时间足够长、足以传播到 `system.sessions` 集合的所有会话。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                          |
| [`$lookup`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/lookup/#mongodb-pipeline-pipe.-lookup)                                                                | 对*同一* 数据库中的另一个集合执行左外连接，以过滤“已连接”集合中的文档以便进行处理。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| [`$match`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match)                                                                   | 筛选文档流以仅允许匹配的文档将未修改的文档传入下一管道阶段。[`$match`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match) 使用标准 MongoDB 查询。对于每个输入文档，输出一个文档（一个匹配项）或零个文档（无匹配项）。                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$merge`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/merge/#mongodb-pipeline-pipe.-merge)                                                                   | 将 aggregation pipeline 的结果文档写入集合。该阶段可将结果（插入新文档、合并文档、替换文档、保留现有文档、操作失败、使用自定义更新管道处理文档）纳入输出集合。要使用 [`$merge`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/merge/#mongodb-pipeline-pipe.-merge) 阶段，它必须是管道中的最后一个阶段。                                                                                                                                                                                                                                                                                                                                                         |
| [`$out`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/out/#mongodb-pipeline-pipe.-out)                                                                         | 将 aggregation pipeline 的结果文档写入集合。要使用 [`$out`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/out/#mongodb-pipeline-pipe.-out) 阶段，它必须是管道中的最后一个阶段。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 |
| [`$planCacheStats`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/planCacheStats/#mongodb-pipeline-pipe.-planCacheStats)                                        | 返回集合的[计划缓存](https://www.mongodb.com/zh-cn/docs/manual/core/query-plans/#std-label-query-plans-query-optimization)信息。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project)                                                             | 重塑流中的每个文档，例如添加新的字段或删除现有字段。对于每个输入文档，输出一个文档。另请参阅 [`$unset`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset) 以了解如何删除现有字段。                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [`$querySettings`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/querySettings/#mongodb-pipeline-pipe.-querySettings)                                           | 返回之前使用 [`setQuerySettings`](https://www.mongodb.com/zh-cn/docs/manual/reference/command/setQuerySettings/#mongodb-dbcommand-dbcmd.setQuerySettings) 添加的查询设置。*8.0版本新增*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$queryStats`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/queryStats/#mongodb-pipeline-pipe.-queryStats)                                                    | 返回已记录查询的运行时统计信息。**警告：**不支持 `$queryStats` 聚合阶段，也不能保证在未来的版本中保持稳定。不要构建依赖于此阶段特定输出格式的功能，因为输出可能会在未来的版本中发生变化。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$redact`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/redact/#mongodb-pipeline-pipe.-redact)                                                                | 根据文档中存储的信息来限制每个文档的内容，从而重塑流中的每个文档。结合了 [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) 和 [`$match`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/match/#mongodb-pipeline-pipe.-match) 的功能。可用于实现字段级别访问控制。对于每个输入文档，输出一个或零个文档。                                                                                                                                                                                                                                                                             |
| [`$replaceRoot`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot)                                                 | 用指定的嵌入文档替换文档。该操作会替换输入文档中的所有现有字段，包括 `_id` 字段。指定嵌入在输入文档中的文档，以将嵌入的文档提升到顶层。[`$replaceWith`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith) 是 [`$replaceRoot`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot) 阶段的别名。                                                                                                                                                                                                                                               |
| [`$replaceWith`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith)                                                 | 用指定的嵌入文档替换文档。该操作会替换输入文档中的所有现有字段，包括 `_id` 字段。指定嵌入在输入文档中的文档，以将嵌入的文档提升到顶层。[`$replaceWith`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceWith/#mongodb-pipeline-pipe.-replaceWith) 是 [`$replaceRoot`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/replaceRoot/#mongodb-pipeline-pipe.-replaceRoot) 阶段的别名。                                                                                                                                                                                                                                               |
| [`$sample`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sample/#mongodb-pipeline-pipe.-sample)                                                                | 从其输入中随机选择指定数量的文件。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                         |
| [`$search`](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/aggregation-stages/search/#mongodb-pipeline-pipe.-search)                                                                | 对 [Atlas](https://www.mongodb.com/zh-cn/docs/atlas/reference/atlas-search/query-syntax/) 集合中的一个或多个字段执行全文搜索。`$search` 仅适用于 MongoDB Atlas 集群，不适用于自托管部署。要了解更多信息，请参阅 [Atlas Search 聚合管道阶段](https://www.mongodb.com/zh-cn/docs/atlas/reference/atlas-search/query-syntax/)。                                                                                                                                                                                                                                                                                                                                                               |
| [`$searchMeta`](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/aggregation-stages/searchMeta/#mongodb-pipeline-pipe.-searchMeta)                                                    | 返回对 [Atlas](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/) 集合进行 [Atlas Search](https://www.mongodb.com/zh-cn/docs/atlas/reference/atlas-search/query-syntax/) 查询时，得到的不同类型的元数据结果文档。`$searchMeta` 仅适用于 MongoDB Atlas 集群，不适用于自托管部署。要了解更多信息，请参阅 [Atlas Search 聚合管道阶段](https://www.mongodb.com/zh-cn/docs/atlas/reference/atlas-search/query-syntax/)。                                                                                                                                                                                                                                                                   |
| [`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set)                                                                         | 为文档添加新字段。与 [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) 类似，[`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set) 重塑了流中的每个文档；具体来说，就是在输出文档中添加新字段，这些输出文档既包含输入文档中的现有字段，也包含新添加的字段。[`$set`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/set/#mongodb-pipeline-pipe.-set) 是 [`$addFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/addFields/#mongodb-pipeline-pipe.-addFields) 阶段的别名。               |
| [`$setWindowFields`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/setWindowFields/#mongodb-pipeline-pipe.-setWindowFields)                                     | 将文档分组到窗口中，并将一个或多个操作符应用于每个窗口中的文档。*版本 5.0 中的新增功能*。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| [`$skip`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/skip/#mongodb-pipeline-pipe.-skip)                                                                      | 跳过前 *n* 个文档，其中 *n* 是指定的跳过编号，并将未修改的剩余文档传递到管道。对于每个输入文档，输出零个文档（对于前 *n* 个文档）或一个文档（如果在前 *n* 个文档之后）。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$sort`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sort/#mongodb-pipeline-pipe.-sort)                                                                      | 按指定的排序键对文档流重新排序。仅顺序会改变，而文档则保持不变。对于每个输入文档，输出一个文档。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           |
| [`$sortByCount`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/sortByCount/#mongodb-pipeline-pipe.-sortByCount)                                                 | 根据指定表达式的值对传入文档进行分组，然后计算每个不同群组中的文档数量。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$unionWith`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/unionWith/#mongodb-pipeline-pipe.-unionWith)                                                       | 执行两个集合的联合；即将两个集合的管道结果合并到一个结果集中。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| [`$unset`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset)                                                                   | 从文档中删除/排除字段。[`$unset`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/unset/#mongodb-pipeline-pipe.-unset) 是删除字段的 [`$project`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/project/#mongodb-pipeline-pipe.-project) 阶段的别名。                                                                                                                                                                                                                                                                                                                                                                                   |
| [`$unwind`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/unwind/#mongodb-pipeline-pipe.-unwind)                                                                | 对输入文档中的某一数组字段进行解构，以便为*每个*元素输出文档。每个输出文档均会将此数组替换为元素值。对于每个输入文档，输出 *n 个*文档，其中 *n* 为数组元素的数量，且对于空数组可为零。                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     |
| [`$vectorSearch`](https://www.mongodb.com/zh-cn/docs/atlas/atlas-vector-search/vector-search-stage/#mongodb-pipeline-pipe.-vectorSearch)                                                   | [对Atlas集合的指定字段中的向量执行](https://www.mongodb.com/zh-cn/docs/atlas/reference/atlas-search/query-syntax/) ANN 或 ENN搜索。`$vectorSearch` 仅适用于运行 MongoDB v6.0.11 或更高版本的 MongoDB Atlas 集群，不适用于自托管部署。*版本 7.0.2 新增内容*。                                                                                                                                                                                                                                                                                                                                                                                                                               |

有关管道阶段中使用的聚合表达式操作符，请参阅[聚合操作符](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/#std-label-aggregation-pipeline-operators)。

## db.aggregate() Stages

MongoDB 还提供了 [`db.aggregate()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.aggregate/#mongodb-method-db.aggregate) 方法：

```
db.aggregate( [ { <stage> }, ... ] )
```

以下阶段使用 [`db.aggregate()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.aggregate/#mongodb-method-db.aggregate)方法，而不是 [`db.collection.aggregate()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.collection.aggregate/#mongodb-method-db.collection.aggregate) 方法。

| 阶段                                                                                                                                                         | 说明                                                                                                                                                                                                                                                                                                                |
| :----------------------------------------------------------------------------------------------------------------------------------------------------------- | :------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------ |
| [`$changeStream`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/changeStream/#mongodb-pipeline-pipe.-changeStream)                | 返回集合的 [Change Stream](https://www.mongodb.com/zh-cn/docs/manual/changeStreams/#std-label-changeStreams) 游标。此阶段只能在 aggregation pipeline 中发生一次，并且必须作为第一阶段发生。                                                                                                                         |
| [`$currentOp`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/currentOp/#mongodb-pipeline-pipe.-currentOp)                         | 返回 MongoDB 部署的活动和/或休眠操作的信息。                                                                                                                                                                                                                                                                        |
| [`$documents`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/documents/#mongodb-pipeline-pipe.-documents)                         | 根据输入值返回字面文档。                                                                                                                                                                                                                                                                                            |
| [`$listLocalSessions`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/aggregation/listLocalSessions/#mongodb-pipeline-pipe.-listLocalSessions) | 列出当前连接的 [`mongos`](https://www.mongodb.com/zh-cn/docs/manual/reference/program/mongos/#mongodb-binary-bin.mongos) 或 [`mongod`](https://www.mongodb.com/zh-cn/docs/manual/reference/program/mongod/#mongodb-binary-bin.mongod) 实例上最近使用的所有活动会话。这些会话可能尚未传播到 `system.sessions` 集合。 |

## 可更新的阶段

| 命令                                                                                                                                | `mongosh`方法                                                                                                                                                          |
| :---------------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`findAndModify`](https://www.mongodb.com/zh-cn/docs/manual/reference/command/findAndModify/#mongodb-dbcommand-dbcmd.findAndModify) | [db.collection.findOneAndUpdate()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.collection.findOneAndUpdate/#std-label-findOneAndUpdate-agg-pipeline) |
|                                                                                                                                     | [db.collection.findAndModify()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.collection.findAndModify/#std-label-findAndModify-agg-pipeline)          |
| [`update`](https://www.mongodb.com/zh-cn/docs/manual/reference/command/update/#mongodb-dbcommand-dbcmd.update)                      | [ db.collection.updateOne()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.collection.updateOne/#std-label-updateOne-example-agg)                      |
|                                                                                                                                     | [db.collection.updateMany()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/db.collection.updateMany/#std-label-updateMany-example-agg)                    |
|                                                                                                                                     | [Bulk.find.update()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/Bulk.find.update/#std-label-example-bulk-find-update-agg)                              |
|                                                                                                                                     | [Bulk.find.updateOne()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/Bulk.find.updateOne/#std-label-example-bulk-find-update-one-agg)                    |
|                                                                                                                                     | [Bulk.find.upsert()](https://www.mongodb.com/zh-cn/docs/manual/reference/method/Bulk.find.upsert/#std-label-bulk-find-upsert-update-agg-example)                       |