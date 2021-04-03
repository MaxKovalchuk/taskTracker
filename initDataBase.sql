CREATE TABLE public.department (
	id serial primary key,
	"name" varchar(30) NOT NULL
);

CREATE TABLE public."user" (
	id serial primary key,
	"name" varchar(30) NOT NULL,
	department_id int NOT null, 
	foreign key (department_id) references department (id)
);

CREATE TABLE public.task (
	id serial primary key,
	title varchar(30) NOT NULL,
	description varchar(500) null,
	created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	user_created_id int NOT NULL,
	user_performer_id int null,
	status int null default 0,
	estimate int,
	foreign key (user_created_id) references "user" (id),
	foreign key (user_performer_id) references "user" (id)
);

CREATE TABLE public."comment" (
	id serial primary key,
	text varchar(1000) NOT NULL,
	author_id int NOT NULL,
	task_id int not null,
	created_at TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP,
	is_edited bool NULL DEFAULT false,
	foreign key (author_id) references "user" (id),
	foreign key (task_id) references task (id)
);

CREATE TABLE public.attachment (
	id serial primary key,
	author_id int NOT NULL,
	task_id int NOT NULL,
	file_name varchar(30) not null,
	bytes bytea not null,
	foreign key (author_id) references "user" (id),
	foreign key (task_id) references task (id)
);

CREATE OR REPLACE FUNCTION user_by_id(integer)
RETURNS SETOF "user" AS $$
  select * from "user" u where u.id = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION department_by_id(integer)
RETURNS SETOF department AS $$
  select * from department d where d.id = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION task_by_id(integer)
RETURNS SETOF task AS $$
  select * from task t where t.id = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION comment_by_task_id(integer)
RETURNS SETOF "comment" AS $$
  select * from "comment" c where c.task_id = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE FUNCTION attachment_by_task_id(integer)
RETURNS SETOF attachment AS $$
  select * from attachment a where a.task_id = $1;
$$ LANGUAGE sql;

CREATE OR REPLACE VIEW public.all_tasks
AS SELECT t.id, t.title, t.description, t.created_at, t.user_created_id, t.user_performer_id, t.status, t.estimate, 
		  user_created.name AS user_created_name, 
		  department_created.id as department_created_id, department_created.name AS department_created_name, 
		  user_performer.name AS user_performer_name, 
		  department_performed.id as department_performed_id, department_performed.name AS department_performed_name
   FROM task t
   LEFT JOIN "user" user_created ON user_created.id = t.user_created_id
   LEFT JOIN "user" user_performer ON user_created.id = t.user_performer_id
   LEFT JOIN department department_created ON department_created.id = user_created.department_id
   LEFT JOIN department department_performed ON department_performed.id = user_performer.department_id;
ALTER TABLE public.all_tasks OWNER TO postgres;
GRANT ALL ON TABLE public.all_tasks TO postgres;