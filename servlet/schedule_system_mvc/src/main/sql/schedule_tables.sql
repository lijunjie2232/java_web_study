SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -------------------------
-- CREATE user table
-- -------------------------
DROP TABLE
IF
	EXISTS `sys_user`;
CREATE TABLE
IF
	NOT EXISTS `sys_user` (
		`uid` INT NOT NULL AUTO_INCREMENT,
		`username` VARCHAR ( 30 ) CHARACTER 
		SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
		`password` VARCHAR ( 100 ) CHARACTER 
		SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
		PRIMARY KEY ( `uid` ) USING BTREE,
		UNIQUE INDEX `username` ( `username` ) USING BTREE 
	) ENGINE = INNODB CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- -------------------------
-- INSERT user data
-- -------------------------
INSERT INTO `sys_user` ( `username`, `password` )
VALUES
	( 'root', MD5('root') );
INSERT INTO `sys_user` ( `username`, `password` )
VALUES
	( 'admin', MD5('admin') );

-- -------------------------
-- CREATE schedule table
-- -------------------------
DROP TABLE
IF
	EXISTS `sys_schedule`;
CREATE TABLE
IF
	NOT EXISTS `sys_schedule` (
		`sid` INT NOT NULL AUTO_INCREMENT,
		`uid` INT NULL DEFAULT NULL,
		`tilte` VARCHAR ( 20 ) CHARACTER 
		SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
		`completed` INT ( 1 ) NULL DEFAULT NULL,
		PRIMARY KEY ( `sid` ) USING BTREE,
		FOREIGN KEY ( `uid` ) REFERENCES `sys_user` ( `uid` ) 
	) ENGINE = INNODB AUTO_INCREMENT = 1 CHARACTER 
	SET utf8mb4 COLLATE utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;
	
SET FOREIGN_KEY_CHECKS = 1;
