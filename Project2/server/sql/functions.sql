CREATE OR REPLACE FUNCTION change_password (p_username VARCHAR, p_old_password VARCHAR, p_new_password VARCHAR)
RETURNS BOOLEAN AS
$$
DECLARE
    v_current_password VARCHAR;
BEGIN
    SELECT password INTO v_current_password FROM data.users WHERE name = p_username;

    IF v_current_password = p_old_password THEN
        UPDATE data.users SET password = p_new_password WHERE name = p_username;
        return true;
    END IF;
    return false;
END;
$$
LANGUAGE plpgsql;
--DROP FUNCTION change_password(character varying,character varying,character varying);

CREATE OR REPLACE FUNCTION gen_new_postid() RETURNS numeric AS $$
DECLARE
    new_post_id numeric;
BEGIN
    LOOP
        new_post_id := floor(random() * 1000000 + 1)::numeric;
        IF NOT EXISTS (SELECT 1 FROM data.posts WHERE post_id = new_post_id) THEN
            EXIT;
        END IF;

    END LOOP;

    -- return the new post_id
    RETURN new_post_id;

END;
$$ LANGUAGE plpgsql;


-- 创建触发器函数
CREATE OR REPLACE FUNCTION increase_likes_count()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE data.posts
    SET likes = likes + 1
    WHERE post_id = NEW.post_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION increase_favorites_count()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE data.posts
    SET favorites = favorites + 1
    WHERE post_id = NEW.post_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION increase_shares_count()
    RETURNS TRIGGER AS $$
BEGIN
    UPDATE data.posts
    SET shares = shares + 1
    WHERE post_id = NEW.post_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER increase_likes
    AFTER INSERT ON relation.like_relation
    FOR EACH ROW EXECUTE PROCEDURE increase_likes_count();

CREATE TRIGGER increase_favorites
    AFTER INSERT ON relation.favorite_relation
    FOR EACH ROW EXECUTE PROCEDURE increase_favorites_count();

CREATE TRIGGER increase_shares
    AFTER INSERT ON relation.share_relation
    FOR EACH ROW EXECUTE PROCEDURE increase_shares_count();
