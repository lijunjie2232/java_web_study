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
```bash
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
```bash
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
```bash
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
```bash
db.inventory.updateOne(
   { item: "paper" },
   {
     $set: { "size.uom": "cm", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```

#### updateMany
```bash
db.inventory.updateMany(
   { "qty": { $lt: 50 } },
   {
     $set: { "size.uom": "in", status: "P" },
     $currentDate: { lastModified: true }
   }
)
```
#### replaceOne
```bash
db.inventory.replaceOne(
   { item: "paper" },
   { item: "paper", instock: [ { warehouse: "A", qty: 60 }, { warehouse: "B", qty: 40 } ] }
)
```