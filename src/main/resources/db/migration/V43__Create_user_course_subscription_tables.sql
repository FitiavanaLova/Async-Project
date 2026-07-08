create table if not exists app_user
(
    id uuid primary key,
    first_name varchar not null,
    last_name varchar not null,
    user_name varchar not null,
    email varchar not null
);

create table if not exists course
(
    id uuid primary key,
    title varchar not null,
    start_date timestamp with time zone not null,
    end_date timestamp with time zone not null
);

create table if not exists user_course_subscription
(
    user_id uuid not null,
    course_id uuid not null,

    constraint user_course_subscription_pk
        primary key (user_id, course_id),

    constraint user_course_subscription_user_fk
        foreign key (user_id)
            references app_user (id),

    constraint user_course_subscription_course_fk
        foreign key (course_id)
            references course (id)
);

insert into course (id, title, start_date, end_date)
values (
           '96484a80-8027-436e-a0a7-534e0b277b59',
           'Introduction à Spring Boot',
           '2026-07-06T08:00:00Z',
           '2026-07-06T10:00:00Z'
       )
on conflict (id) do nothing;