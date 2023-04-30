create table if not exists projects_info
(
    id         uuid default gen_random_uuid() primary key,
    git_link  varchar(50) not null unique,
    miro_link varchar(50) not null unique,
    trello_link varchar(50) not null unique,
    google_drive_link varchar(50) not null unique,
    useful_resources_link varchar(50) not null unique
    );

create table if not exists projects_cards
(
    id         uuid default gen_random_uuid() primary key,
    title      varchar(20) not null unique,
    project_note text not null unique,
    project_info_id uuid not null unique constraint projects_cards_project_account_id_fk references projects_info (id)
);