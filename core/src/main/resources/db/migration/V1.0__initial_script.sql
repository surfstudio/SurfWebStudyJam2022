create table roles
(
    id   integer primary key generated always as identity,
    role text not null unique
);

create table accounts
(
    id       integer primary key generated always as identity,
    email    text not null unique,
    password text not null,
    role_id integer references roles(id)
);

create table surf_employees
(
    id         integer primary key generated always as identity,
    name       text not null,
    account_id integer references accounts (id)
);


create table event_types
(
    id   integer primary key generated always as identity,
    type text unique not null
);

create table state_types
(
    id   integer primary key generated always as identity,
    type text unique not null
);

create table events
(
    id                integer primary key generated always as identity,
    about             text,
    candidates_number integer,
    trainee_numbers   integer,
    offers_number     integer,
    event_type_id     integer references event_types (id),
    event_initiator   integer references surf_employees (id)
);

create table states_events
(
    state_id integer references state_types (id),
    event_id integer references events (id),
    date     date not null,
    primary key (event_id, state_id)
);

create table candidates
(
    id         integer primary key generated always as identity,
    name       text not null,
    email      text not null unique,
    is_new     boolean,
    hr_from_id integer references surf_employees (id)
);

create table candidates_events
(
    candidate_id integer references candidates (id),
    event_id     integer references events (id),
    primary key (event_id, candidate_id)
);


create table teams
(
    id                integer primary key generated always as identity,
    about             text,
    project_git_link  text,
    project_miro_link text,
    mentor_id         integer references surf_employees (id)
);

create table teams_feedbacks
(
    mentor_id integer references surf_employees (id),
    team_id   integer references teams (id),
    comment   text,
    score     integer,
    date      date,
    primary key (mentor_id, team_id)
);

create table trainees
(
    id           integer primary key generated always as identity,
    score        integer,
    is_active    boolean,
    event_id     integer references events (id),
    candidate_id integer references candidates (id),
    account_id   integer references accounts (id),
    team_id      integer references teams (id)
);

create table trainees_feedbacks
(
    id               integer primary key generated always as identity,
    score            integer,
    comment          text,
    date             date,
    surf_employee_id integer references surf_employees (id),
    trainee_id       integer references surf_employees (id)
);


create table tests
(
    id           integer primary key generated always as identity,
    link         text,
    score        integer,
    start_date   timestamp,
    end_date     timestamp,
    candidate_id integer references candidates (id),
    event_id     integer references events (id)
);

create table question_types
(
    id   integer primary key generated always as identity,
    type text not null unique
);

create table answers
(
    id     integer primary key generated always as identity,
    answer text
);

create table questions
(
    id              integer primary key generated always as identity,
    question        text,
    question_type   integer references question_types (id),
    right_answer_id integer references answers (id)
);

create table answers_questions
(
    answer_id   integer references answers (id),
    question_id integer references questions (id),
    primary key (question_id, answer_id)
);

create table tests_questions
(
    test_id     integer references tests (id),
    question_id integer references questions (id),
    primary key (test_id, question_id)
);
