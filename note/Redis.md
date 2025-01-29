- [redis config](#redis-config)

# redis config
- `daemonize yes`: run redis as a daemon
- `protected-mode yes`: config yes if no clients from other hosts connect
- `bind [ip]`: if ip config `127.0.0.1`, only local host could connect to redis
- `requirepass xxx`: set redis paddword
