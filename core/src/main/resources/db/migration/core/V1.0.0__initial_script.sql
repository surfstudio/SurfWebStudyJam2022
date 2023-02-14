create table if not exists roles
(
    id   uuid default gen_random_uuid() primary key,
    role text not null unique
);

create table if not exists accounts
(
    id       uuid default gen_random_uuid() primary key,
    email    text not null unique,
    password text unique,
    role_id  uuid
        constraint accounts_roles_role_id_fk references roles (id)
);

create table if not exists surf_employees
(
    id         uuid default gen_random_uuid() primary key,
    name       text not null,
    account_id uuid
        constraint surf_employees_accounts_account_id_fk references accounts (id)

);


create table if not exists event_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists state_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists events
(
    id                 uuid default gen_random_uuid() primary key,
    about              text unique,
    candidates_number  integer,
    trainees_number    integer,
    offers_number      integer,
    event_type_id      uuid
        constraint events_event_types_event_type_id_fk references event_types (id),
    event_initiator_id uuid
        constraint events_surf_employees_event_initiator_id_fk references surf_employees (id)

);

create table if not exists states_events
(
    id       uuid default gen_random_uuid() primary key,
    state_id uuid
        constraint states_events_state_types_state_id_fk references state_types (id),
    event_id uuid
        constraint states_events_events_event_id_fk references events (id),
    date     date not null
);

create table if not exists candidates
(
    id          uuid default gen_random_uuid() primary key,
    first_name  text not null,
    last_name   text not null,
    university  text not null,
    faculty     text not null,
    course      text not null,
    experience  text not null,
    vcs         text not null,
    email       text not null,
    telegram    text not null,
    feedback    text not null,
    is_new      boolean,
    is_approved boolean,
    hr_from_id  uuid
        constraint candidates_surf_employees_hr_from_id_fk references surf_employees (id)

);

create table if not exists candidates_events
(
    candidate_id uuid
        constraint candidates_events_candidates_candidate_id_fk references candidates (id),
    event_id     uuid
        constraint candidates_events_events_event_id_fk references events (id),
    primary key (event_id, candidate_id)
);


create table if not exists teams
(
    id                uuid default gen_random_uuid() primary key,
    about             text unique,
    project_git_link  text unique,
    project_miro_link text unique,
    mentor_id         uuid
        constraint teams_surf_employees_mentor_id_fk references surf_employees (id)
);

create table if not exists teams_feedbacks
(
    id            uuid default gen_random_uuid() primary key,
    mentor_id     uuid
        constraint teams_feedbacks_surf_employees_mentor_id_fk references surf_employees (id),
    team_id       uuid
        constraint teams_feedbacks_teams_team_id_fk references teams (id),
    comment       text    not null unique,
    score         integer not null,
    feedback_date date    not null
);

create table if not exists trainees
(
    id           uuid default gen_random_uuid() primary key,
    score        integer,
    is_active    boolean not null,
    event_id     uuid
        constraint trainees_events_event_id_fk references events (id),
    candidate_id uuid
        constraint trainees_candidates_candidate_id_fk references candidates (id),
    account_id   uuid
        constraint trainees_accounts_account_id_fk references accounts (id),
    team_id      uuid
        constraint trainees_teams_team_id_fk references teams (id)
);

create table if not exists trainees_feedbacks
(
    id               uuid default gen_random_uuid() primary key,
    score            integer not null,
    comment          text    not null unique,
    date             date    not null,
    surf_employee_id uuid
        constraint trainees_feedbacks_surf_employees_surf_employee_id_fk references surf_employees (id),
    trainee_id       uuid
        constraint trainees_feedbacks_surf_employees_trainee_id_fk references surf_employees (id)
);


create table if not exists tests
(
    id           uuid default gen_random_uuid() primary key,
    link         text unique,
    score        integer,
    start_date   timestamp not null,
    end_date     timestamp not null,
    candidate_id uuid
        constraint tests_candidates_candidate_id_fk references candidates (id),
    event_id     uuid
        constraint tests_events_event_id_fk references events (id)
);

create table if not exists question_types
(
    id   uuid default gen_random_uuid() primary key,
    type text not null unique
);

create table if not exists answers
(
    id     uuid default gen_random_uuid() primary key,
    answer text not null unique
);

create table if not exists questions
(
    id               uuid default gen_random_uuid() primary key,
    question         text unique,
    question_type_id uuid
        constraint questions_question_types_question_type_id_fk references question_types (id),
    right_answer_id  uuid
        constraint questions_answers_right_answer_id_fk references answers (id)
);

create table if not exists questions_answers
(
    question_id uuid
        constraint questions_answers_questions_question_id_fk references questions (id),
    answer_id   uuid
        constraint questions_answers_answers_answer_id_fk references answers (id),
    primary key (question_id, answer_id)
);

create table if not exists tests_questions
(
    test_id     uuid
        constraint tests_questions_tests_test_id_fk references tests (id),
    question_id uuid
        constraint tests_questions_questions_question_id_fk references questions (id),
    primary key (test_id, question_id)
);

create table if not exists s3files
(
    id            uuid default gen_random_uuid() primary key,
    content_type  varchar(255),
    s3_key        varchar(255) unique,
    size_in_bytes bigint,
    s3_filename   varchar(300),
    checksum      varchar(255) unique,
    expires_at    date
);
