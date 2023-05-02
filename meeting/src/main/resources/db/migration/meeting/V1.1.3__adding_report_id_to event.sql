alter table events
    add column if not exists
        report_file_id uuid
            constraint events_s3files_report_file_id_fk references s3files (id) on delete set null default null;