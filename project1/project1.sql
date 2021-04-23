DROP TABLE users;
DROP TABLE user_roles ;
DROP TABLE reimbursement_type ;
DROP TABLE reimbursement_status ;
DROP TABLE reimbursements ;

CREATE TABLE user_roles (
	  role_id serial PRIMARY KEY
	, user_role VARCHAR(10) UNIQUE
);

CREATE TABLE users (
	  user_id serial PRIMARY KEY
	, user_name VARCHAR(50) UNIQUE
	, pass VARCHAR(50) NOT NULL
	, first_name VARCHAR(100) NOT NULL
	, last_name VARCHAR(20) NOT NULL
	, user_email VARCHAR(150)UNIQUE
	, registration_date date not null default CURRENT_DATE
	, change_password INTEGER default 1
	, user_role_id INTEGER 
	, CONSTRAINT fk_role
      FOREIGN KEY(user_role_id) 
	  REFERENCES user_roles(role_id)
);

CREATE TABLE reimbursement_status (
	  status_id serial PRIMARY KEY
	, reimb_status VARCHAR(10) UNIQUE
);

CREATE TABLE reimbursement_type (
	  type_id serial PRIMARY KEY
	, reimb_type VARCHAR(10) UNIQUE
);

CREATE TABLE reimbursements (
	  reimb_id serial PRIMARY KEY
	, reimb_amount DOUBLE PRECISION NOT NULL
	, reimb_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_DATE
	, reimb_resolved TIMESTAMP
	, reimb_description VARCHAR(250)
	, author_id INTEGER NOT NULL
	, resolver_id INTEGER 
	, status_id INTEGER
	, type_id INTEGER
	, CONSTRAINT fk_author
      FOREIGN KEY(author_id) 
	  REFERENCES users(user_id)
	, CONSTRAINT fk_resolver
      FOREIGN KEY(resolver_id) 
	  REFERENCES users(user_id)
	, CONSTRAINT fk_status
      FOREIGN KEY(status_id) 
	  REFERENCES reimbursement_status(status_id)
	, CONSTRAINT fk_type
      FOREIGN KEY(type_id) 
	  REFERENCES reimbursement_type(type_id)
);

CREATE TABLE images (
	  image_id serial PRIMARY KEY
	, reimb_id INTEGER NOT NULL
	, image BYTEA
	, CONSTRAINT fk_reimbursement
      FOREIGN KEY(reimb_id) 
	  REFERENCES reimbursements(reimb_id)
);


SELECT r.*,(u.first_name || u.last_name) AS authorName
,(u2.first_name || u2.last_name) AS resolverName
,rs.reimb_status AS status
, rt.reimb_type AS typp  FROM reimbursements r 
INNER JOIN users u ON r.author_id = u.user_id 
LEFT JOIN users u2 ON r.resolver_id = u2.user_id
INNER JOIN reimbursement_status rs ON r.status_id = rs.status_id
INNER JOIN reimbursement_type rt ON r.type_id = rt.type_id WHERE r.author_id =1;

SELECT u.*, ur.user_role AS userRole FROM users u 
INNER JOIN user_roles ur ON ur.role_id = u.user_role_id;


INSERT INTO users VALUES(DEFAULT,'sam','1234','Suliman','Sam','saimon.91@hotmail.com',date('10-10-2021'),'12345',null);
INSERT INTO user_roles VALUES(DEFAULT,'any');
INSERT INTO reimbursement_status VALUES(DEFAULT,'any');
INSERT INTO reimbursement_type VALUES(DEFAULT,'any');


SELECT * FROM reimbursements ORDER BY reimb_id ;
SELECT * FROM users;
SELECT * FROM user_roles;
SELECT * FROM reimbursement_status;
SELECT * FROM reimbursement_type;
SELECT * FROM images;

UPDATE reimbursements SET reimb_amount=5.5
                    , reimb_resolved= null
                    , reimb_description= 'rrrrrrrr'
                    , status_id = (SELECT status_id FROM reimbursement_status WHERE reimb_status = 'approved')
                    , type_id = (SELECT type_id FROM reimbursement_type WHERE reimb_type = 'certification')
                    , resolver_id= (SELECT user_id FROM users WHERE user_name ='sam' )
                     WHERE reimb_id = 5;

ALTER TABLE reimbursement_status  ALTER COLUMN reimb_status TYPE VARCHAR(20);
ALTER TABLE reimbursement_type  ALTER COLUMN reimb_type TYPE VARCHAR(20);



ALTER TABLE user_roles ALTER COLUMN user_role TYPE VARCHAR(20);

ALTER TABLE reimbursements ALTER COLUMN status_id SET DEFAULT 1;
ALTER TABLE reimbursements ALTER COLUMN reimb_resolved TYPE TIMESTAMP DEFAULT CURRENT_DATE;

SELECT r.*,u.user_name AS authorName,u2.user_name AS resolverName,rs.reimb_status AS status, rt.reimb_type AS typp FROM reimbursements AS r INNER JOIN users u ON r.author_id = u.user_id
                    INNER JOIN users u2 ON r.resolver_id = u2.user_id
                    INNER JOIN reimbursement_status rs ON r.status_id = rs.status_id
                    INNER JOIN reimbursement_type rt ON r.type_id = rt.type_id ORDER BY reimb_id;
                   
                   
                   
                   
                   
                   
SELECT r.*,u.user_name AS authorName ,u2.user_name AS resolverName,rs.reimb_status AS status, rt.reimb_type AS typp FROM reimbursements AS r
                    INNER JOIN users AS u ON r.author_id = u.user_id
                    INNER JOIN users AS u2 ON r.resolver_id = u2.user_id
                    INNER JOIN reimbursement_status AS rs ON rs.status_id = r.status_id
                    INNER JOIN reimbursement_type AS rt ON rt.type_id = r.type_id ORDER BY r.reimb_id;   
                   

SELECT count(status_id) AS count from reimbursements where status_id = (select status_id from reimbursement_status where reimb_status = 'pending');
select sum(reimb_amount) AS sum from reimbursements where status_id = (select status_id from reimbursement_status where reimb_status = 'approved');             
                   
                   
                   
                   
SELECT u.*, ur.user_role AS userRole FROM users u
                    INNER JOIN user_roles ur ON ur.role_id = u.user_role_id;

                   
select sum(reimb_amount) AS sum from reimbursements 
                    where status_id = (select status_id from reimbursement_status where reimb_status = 'approved')
                    AND author_id = 1;


                   
                   
                   
SELECT r.*,(u.user_name) AS authorName
                    ,(u2.user_name) AS resolverName 
                    ,rs.reimb_status AS status, rt.reimb_type AS typp  FROM reimbursements r
                    INNER JOIN users u ON r.author_id = u.user_id
                    LEFT JOIN users u2 ON r.resolver_id = u2.user_id 
                    INNER JOIN reimbursement_status rs ON r.status_id = rs.status_id
                    INNER JOIN reimbursement_type rt ON r.type_id = rt.type_id where r.reimb_id=1;
                   
                   
                   
                   
SELECT user_email FROM users WHERE user_id = (SELECT author_id FROM reimbursements r WHERE reimb_id = 1);
                    
                    
                    
                    