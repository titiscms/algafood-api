CREATE TABLE oauth_code (
	code VARCHAR(256),
	authentication BLOB
)  ENGINE=INNODB DEFAULT CHARSET=utf8;