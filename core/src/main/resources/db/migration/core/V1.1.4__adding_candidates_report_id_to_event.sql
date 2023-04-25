alter table events
    add column if not exists
        candidates_report_file_id uuid
            constraint events_s3files_candidates_report_file_id_fk references s3files (id) on delete set null default null;