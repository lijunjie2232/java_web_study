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
- `max`: max number of documents in the collection, if the collection is capped, it will delete the oldest documents when the collection reaches the max size

### show
- use `show collections` or `db.getCollectionNames()` to show all collections in current database

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
use sample_mflix

db.movies.insertMany([
   {
      title: "Jurassic World: Fallen Kingdom",
      genres: [ "Action", "Sci-Fi" ],
      runtime: 130,
      rated: "PG-13",
      year: 2018,
      directors: [ "J. A. Bayona" ],
      cast: [ "Chris Pratt", "Bryce Dallas Howard", "Rafe Spall" ],
      type: "movie"
    },
    {
      title: "Tag",
      genres: [ "Comedy", "Action" ],
      runtime: 105,
      rated: "R",
      year: 2018,
      directors: [ "Jeff Tomsic" ],
      cast: [ "Annabelle Wallis", "Jeremy Renner", "Jon Hamm" ],
      type: "movie"
    }
])
```

