DROP TABLE users;
DROP TABLE user_roles ;
DROP TABLE reimbursement_type ;
DROP TABLE reimbursement_status ;
DROP TABLE reimbursements ;

CREATE TABLE user_roles (
	  role_id serial PRIMARY KEY
	, user_role VARCHAR(20) UNIQUE
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
	, reimb_status VARCHAR(20) UNIQUE
);

CREATE TABLE reimbursement_type (
	  type_id serial PRIMARY KEY
	, reimb_type VARCHAR(20) UNIQUE
);

CREATE TABLE reimbursements (
	  reimb_id serial PRIMARY KEY
	, reimb_amount DOUBLE PRECISION NOT NULL
	, reimb_submitted TIMESTAMP NOT NULL DEFAULT CURRENT_DATE
	, reimb_resolved TYPE TIMESTAMP DEFAULT CURRENT_DATE
	, reimb_description VARCHAR(250)
	, author_id INTEGER NOT NULL
	, resolver_id INTEGER 
	, status_id INTEGER DEFAULT 1
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

SELECT * FROM reimbursements ORDER BY reimb_id ;
SELECT * FROM users;
SELECT * FROM user_roles;
SELECT * FROM reimbursement_status;
SELECT * FROM reimbursement_type;
SELECT * FROM images;



             

                    
                    
                    
                    