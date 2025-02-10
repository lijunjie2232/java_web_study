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

