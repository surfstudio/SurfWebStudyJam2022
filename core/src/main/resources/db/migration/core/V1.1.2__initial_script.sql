create table if not exists accounts
(
    id         uuid default gen_random_uuid() primary key,
    email      text not null unique,
    role       text not null,
    created_at timestamp with time zone not null
);

create table if not exists surf_employees
(
    id         uuid default gen_random_uuid() primary key,
    account_id uuid unique
        constraint surf_employees_accounts_account_id_fk references accounts (id),
    name       text not null
);


create table if not exists event_tags
(
    id          uuid default gen_random_uuid() primary key,
    description text not null unique
);

create table if not exists events
(
    id                 uuid default gen_random_uuid() primary key,
    title              text not null unique,
    description        text not null,
    candidates_number  integer not null,
    trainees_number    integer not null,
    offers_number      integer not null,
    event_initiator_id uuid
        constraint events_surf_employees_event_initiator_id_fk references surf_employees (id)
);

create table if not exists event_states
(
    id         uuid default gen_random_uuid() primary key,
    state_type text not null,
    state_date      timestamp with time zone not null,
    event_id   uuid not null
        constraint candidates_events_events_event_id_fk references events (id),
    unique(state_type, event_id)
);

create table if not exists event_tags_events
(
    event_id     uuid not null
        constraint event_tags_events_events_event_id_fk references events (id),
    event_tag_id uuid not null
        constraint event_tags_events_event_tags_event_tag_id_fk references event_tags (id),
    primary key (event_id, event_tag_id)
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
    applied_at  timestamp with time zone not null,
    is_approved boolean not null,
    event_id    uuid not null
        constraint candidates_events_events_event_id_fk references events (id),
    unique(email, event_id)
);

create table if not exists defences
(
    id            uuid default gen_random_uuid() primary key,
    title         varchar(255),
    description   varchar(300),
    zoom_link    varchar(255),
    date_time         timestamp
);

create table if not exists teams
(
    id                uuid default gen_random_uuid() primary key,
    title             text not null unique,
    project_git_link  text not null unique,
    project_miro_link text not null unique,
    mentor_id         uuid not null
        constraint teams_surf_employees_mentor_id_fk references surf_employees (id),
    defence_id        uuid not null
        constraint teams_defences_defence_id_fk references defences (id)
);

create table if not exists teams_feedbacks
(
    id               uuid default gen_random_uuid() primary key,
    comment          text    not null,
    score            integer not null,
    feedback_date    timestamp with time zone    not null,
    team_id          uuid not null
        constraint teams_feedbacks_teams_team_id_fk references teams (id),
    surf_employee_id uuid not null
        constraint teams_feedbacks_surf_employees_surf_employee_id_fk references surf_employees (id),
    unique(surf_employee_id, team_id)
);

create table if not exists trainees
(
    id           uuid default gen_random_uuid() primary key,
    account_id   uuid unique
        constraint trainees_accounts_account_id_fk references accounts (id),
    candidate_id uuid not null unique
        constraint trainees_candidates_candidate_id_fk references candidates (id),
    team_id      uuid
        constraint trainees_teams_team_id_fk references teams (id)
);

create table if not exists trainees_feedbacks
(
    id               uuid default gen_random_uuid() primary key,
    score            integer not null,
    comment          text    not null unique,
    feedback_date    timestamp with time zone    not null,
    trainee_id       uuid
        constraint trainees_feedbacks_trainees_trainee_id_fk references trainees (id),
    surf_employee_id uuid
        constraint trainees_feedbacks_surf_employees_surf_employee_id_fk references surf_employees (id),
    unique(surf_employee_id, trainee_id)
);


create table if not exists test_templates
(
    id                          uuid default gen_random_uuid() primary key,
    max_acceptable_duration_sec integer not null,
    event_id                    uuid not null unique
);

create table if not exists questions
(
    id             uuid default gen_random_uuid() primary key,
    question_type  text not null,
    title          text not null,
    weight         integer not null,
    test_id        uuid not null
        constraint questions_test_id_fk references test_templates (id)
);

create table if not exists answers
(
    id          uuid default gen_random_uuid() primary key,
    title       text not null,
    weight      integer not null,
    question_id uuid not null
        constraint answers_question_id_fk references questions (id)
);

create table if not exists test_variants
(
    id               uuid default gen_random_uuid() primary key,
    candidate_id     uuid not null unique,
    started_at       timestamp with time zone not null,
    test_template_id uuid not null
        constraint test_variants_test_template_id_fk references test_templates (id)
);

create table if not exists question_variants
(
    id              uuid default gen_random_uuid() primary key,
    question_order  integer not null,
    answered_at     timestamp with time zone not null,
    test_variant_id uuid not null
        constraint question_variants_test_variant_id_fk references test_variants (id),
    question_id uuid not null
        constraint question_variants_question_id_fk references questions (id),
    unique(test_variant_id, question_id),
    unique(test_variant_id, question_order)
);

create table if not exists answer_variants
(
    id                  uuid default gen_random_uuid() primary key,
    question_variant_id uuid not null
        constraint answer_variants_question_variant_id_fk references question_variants (id),
    answer_id           uuid not null
        constraint answer_variants_answer_id_fk references answers (id),
    unique(question_variant_id, answer_id)
);


create table if not exists s3files
(
    id            uuid default gen_random_uuid() primary key,
    content_type  varchar(255),
    s3_key        varchar(255) unique,
    size_in_bytes bigint,
    s3_filename   varchar(300),
    checksum      varchar(255),
    expires_at    timestamp with time zone
);

