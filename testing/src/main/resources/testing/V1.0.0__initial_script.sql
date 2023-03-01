create table if not exists test_templates
(
    id                          uuid default gen_random_uuid() primary key,
    max_acceptable_duration_sec integer not null,
    max_questions_pool_size     integer not null,
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
