# How to start

请先配置好 MySQL, 新建数据库: `jpa_example`.

启动 spring boot 时会自动新建 `user` 表, 会自动向 `user` 表插入示例数据.

```
MYSQL_USER=your_db_user_name MYSQL_PASSWORD=your_db_password ./mvnw spring-boot:run
```
