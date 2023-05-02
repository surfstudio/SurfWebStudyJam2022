alter table teams
    add column if not exists
        event_id uuid
            constraint teams_events_event_id_fk references events (id) on delete cascade default null;