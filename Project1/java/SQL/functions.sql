-- Functions

create function generate_userid() returns numeric as $$
    declare
        random_id numeric;
    begin
        loop
            random_id := floor(random() * 1000000000)::integer;
            exit when not exists(select 1 from users where user_id = random_id);
        end loop;
    end;
$$;

create function insert_user(name varchar, p_userid numeric, regis_time timestamp, phone char(15)) returns void as $$
declare
    v_userid numeric;
begin
    v_userid := coalesce(p_userid, generate_userid());
    begin
        insert into users (name, user_id, registration_time, phone) values (name, v_userid, regis_time, phone);
    exception
        when unique_violation then
            -- 如果插入的userid已经存在，这里会捕获错误
            v_userid := generate_userid();
            insert into users (name, user_id, registration_time, phone) values (name, v_userid, regis_time, phone);
    end;
end;
$$