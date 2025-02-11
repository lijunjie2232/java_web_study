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
- `db.<collection_name>.drop()`: drop a collection

### collection info
- `db.<collection_name>.stats()`: show collection info
- `db.printCollections()`: show all collections in current database
- count
  - `db.<collection_name>.count()`: (deprecated) show collection size
  - `db.<collection_name>.countDocuments()`: show collection size
  - `db.<collection_name>.estimatedDocumentCount()`: show collection size
- `db.<collection_name>.totalSize()`: show collection size

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


## 查询选择器
### Usage
```javascript
db.inventory.find( { <field1>: { <operator1>: <value1> }, ... } )
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
```javascript
// $eq / $ne / $gt / $gte / $lt / $lte 
{ <field>: { $eq: <value> } }
// in / $nin
{ <field>: { $in: [<value1>, <value2>, ... <valueN> ] } }
```

### 逻辑

| 名称                                                                                                    | 说明                                                              |
| :------------------------------------------------------------------------------------------------------ | :---------------------------------------------------------------- |
| [`$and`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/and/#mongodb-query-op.-and) | 使用逻辑 `AND` 连接查询子句将返回与两个子句的条件匹配的所有文档。 |
| [`$not`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/not/#mongodb-query-op.-not) | 反转查询谓词的效果，并返回与查询谓词*不*匹配的文档。              |
| [`$nor`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/nor/#mongodb-query-op.-nor) | 使用逻辑 `NOR` 的联接查询子句会返回无法匹配这两个子句的所有文档。 |
| [`$or`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/or/#mongodb-query-op.-or)    | 使用逻辑 `OR` 连接多个查询子句会返回符合任一子句条件的所有文档。  |

### 元素

| 名称                                                                                                             | 说明                             |
| :--------------------------------------------------------------------------------------------------------------- | :------------------------------- |
| [`$exists`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/exists/#mongodb-query-op.-exists) | 匹配具有指定字段的文档。         |
| [`$type`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/type/#mongodb-query-op.-type)       | 如果字段为指定类型，则选择文档。 |

### 求值

| 名称                                                                                                                         | 说明                                                                                                                                                                                                                    |
| :--------------------------------------------------------------------------------------------------------------------------- | :---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$expr`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/expr/#mongodb-query-op.-expr)                   | 允许在查询语言中使用聚合表达式。                                                                                                                                                                                        |
| [`$jsonSchema`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/jsonSchema/#mongodb-query-op.-jsonSchema) | 根据给定的 JSON 模式验证文档。                                                                                                                                                                                          |
| [`$mod`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/mod/#mongodb-query-op.-mod)                      | 对字段值执行模运算，并选择具有指定结果的文档。                                                                                                                                                                          |
| [`$regex`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/regex/#mongodb-query-op.-regex)                | 选择值匹配指定正则表达式的文档。                                                                                                                                                                                        |
| [`$text`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/text/#mongodb-query-op.-text)                   | 执行文本搜索。`$text` 提供了自管理（非 Atlas）部署的文本查询功能。对于托管在 MongoDB Atlas 上的数据，MongoDB 提供了一种改进的全文查询解决方案，[Atlas Search](https://www.mongodb.com/zh-cn/docs/atlas/atlas-search/)。 |
| [`$where`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/where/#mongodb-query-op.-where)                | 匹配满足 JavaScript 表达式的文档。                                                                                                                                                                                      |

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



## 其他操作符

| 名称                                                                                                                       | 说明                                                                                                                                                                                                                                                                                           |
| :------------------------------------------------------------------------------------------------------------------------- | :--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| [`$rand`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/rand/#mongodb-query-op.-rand)                 | 生成介于 0 和 1 之间的随机浮点数。                                                                                                                                                                                                                                                             |
| [`$natural`](https://www.mongodb.com/zh-cn/docs/manual/reference/operator/query/natural/#mongodb-operator-metaOp.-natural) | 可通过 [`sort()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/cursor.sort/#mongodb-method-cursor.sort) 或 [`hint()`](https://www.mongodb.com/zh-cn/docs/manual/reference/method/cursor.hint/#mongodb-method-cursor.hint) 方法提供的特殊提示，可用于强制执行正向或反向集合扫描。 |

