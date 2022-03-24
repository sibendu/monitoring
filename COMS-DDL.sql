-- public."event" definition

-- Drop table

-- DROP TABLE "event";

CREATE TABLE "event" (
	id int8 NOT NULL,
	code varchar(255) NULL,
	context varchar(255) NULL,
	finish timestamp NULL,
	next_events bool NOT NULL,
	process_id int8 NULL,
	"start" timestamp NULL,
	status varchar(255) NULL,
	created_by varchar(255) NULL,
	created_date timestamp NULL,
	definition varchar(5000) NULL,
	description varchar(255) NULL,
	updated_by varchar(255) NULL,
	updated_date timestamp NULL,
	"version" varchar(255) NULL,
	CONSTRAINT event_pkey PRIMARY KEY (id)
);


-- public.process_defintion definition

-- Drop table

-- DROP TABLE process_defintion;

CREATE TABLE process_defintion (
	id int8 NOT NULL,
	code varchar(255) NULL,
	definition varchar(5000) NULL,
	description varchar(255) NULL,
	status varchar(255) NULL,
	"version" varchar(255) NULL,
	CONSTRAINT process_defintion_pkey PRIMARY KEY (id)
);


-- public.process_instance definition

-- Drop table

-- DROP TABLE process_instance;

CREATE TABLE process_instance (
	id int8 NOT NULL,
	code varchar(255) NULL,
	created timestamp NULL,
	no_end_events int4 NOT NULL,
	status varchar(255) NULL,
	updated timestamp NULL,
	"version" varchar(255) NULL,
	CONSTRAINT process_instance_pkey PRIMARY KEY (id)
);


-- public.task_instance definition

-- Drop table

-- DROP TABLE task_instance;

CREATE TABLE task_instance (
	id int8 NOT NULL,
	activity_id int8 NULL,
	assigned_group varchar(255) NULL,
	assigned_user varchar(255) NULL,
	created timestamp NULL,
	description varchar(255) NULL,
	"name" varchar(255) NULL,
	next_events varchar(255) NULL,
	process_id int8 NULL,
	remark varchar(255) NULL,
	status varchar(255) NULL,
	updated timestamp NULL,
	CONSTRAINT task_instance_pkey PRIMARY KEY (id)
);


-- public.process_activity definition

-- Drop table

-- DROP TABLE process_activity;

CREATE TABLE process_activity (
	id int8 NOT NULL,
	description varchar(255) NULL,
	"event" varchar(255) NULL,
	finish timestamp NULL,
	"handler" varchar(255) NULL,
	message varchar(255) NULL,
	"start" timestamp NULL,
	success varchar(255) NULL,
	variables varchar(2000) NULL,
	instance_id int8 NULL,
	"type" varchar(255) NULL,
	CONSTRAINT process_activity_pkey PRIMARY KEY (id),
	CONSTRAINT fk60qk2r9pvc3496h7cgtapd2dh FOREIGN KEY (instance_id) REFERENCES process_instance(id) ON DELETE CASCADE
);


-- public.task_activity definition

-- Drop table

-- DROP TABLE task_activity;

CREATE TABLE task_activity (
	id int8 NOT NULL,
	message varchar(255) NULL,
	"timestamp" timestamp NULL,
	user_id varchar(255) NULL,
	task_id int8 NULL,
	CONSTRAINT task_activity_pkey PRIMARY KEY (id),
	CONSTRAINT fks5xkfmew4csykuvmvfo26ei4b FOREIGN KEY (task_id) REFERENCES task_instance(id) ON DELETE CASCADE
);


-- public.task_variable definition

-- Drop table

-- DROP TABLE task_variable;

CREATE TABLE task_variable (
	id int8 NOT NULL,
	"name" varchar(255) NULL,
	value varchar(255) NULL,
	task_id int8 NULL,
	CONSTRAINT task_variable_pkey PRIMARY KEY (id),
	CONSTRAINT fkbprrqi2y1y8fqjx0p3iwnal1u FOREIGN KEY (task_id) REFERENCES task_instance(id) ON DELETE CASCADE
);

-- public.customer definition

-- Drop table

-- DROP TABLE customer;

CREATE TABLE customer (
	customer_id int4 NOT NULL,
	account_number varchar(255) NULL,
	company_name varchar(255) NULL,
	first_name varchar(255) NULL,
	last_name varchar(255) NULL,
	CONSTRAINT customer_pkey PRIMARY KEY (customer_id)
);

-- public.product definition

-- Drop table

-- DROP TABLE product;

CREATE TABLE product (
	id int8 NOT NULL,
	code varchar(255) NULL,
	description varchar(255) NULL,
	sku varchar(255) NULL,
	CONSTRAINT product_pkey PRIMARY KEY (id)
);

