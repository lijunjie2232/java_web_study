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
#### 数据库级角色
这些角色适用于特定数据库。

- **read**：允许用户读取指定数据库中的所有集合。
- **readWrite**：允许用户读取和写入指定数据库中的所有集合。
- **dbAdmin**：允许用户执行管理操作，如创建和删除集合、索引等，但不包括用户管理。
- **dbOwner**：允许用户执行所有数据库级别的操作，包括用户管理。
- **userAdmin**：允许用户管理指定数据库中的用户和角色。

#### 集合级角色
这些角色适用于特定集合。

- **read**：允许用户读取指定集合。
- **readWrite**：允许用户读取和写入指定集合。

#### 跨数据库角色
这些角色适用于所有数据库。

- **readAnyDatabase**：允许用户读取所有数据库中的所有集合。
- **readWriteAnyDatabase**：允许用户读取和写入所有数据库中的所有集合。
- **dbAdminAnyDatabase**：允许用户执行管理操作，如创建和删除集合、索引等，但不包括用户管理，适用于所有数据库。
- **userAdminAnyDatabase**：允许用户管理所有数据库中的用户和角色。
- **clusterAdmin**：允许用户执行集群级别的管理操作，如备份、恢复、分片管理等。
- **clusterMonitor**：允许用户监控集群状态。
- **hostManager**：允许用户管理服务器和操作系统级别的操作。
- **backup**：允许用户执行备份操作。
- **restore**：允许用户执行恢复操作。
- **root**：允许用户执行所有操作，包括用户管理、数据库管理、集群管理等。

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


## Insert
### insertOne
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

### insertMany
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

