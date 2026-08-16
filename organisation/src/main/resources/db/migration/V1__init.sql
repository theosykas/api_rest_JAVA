CREATE TABLE organisation (
	id BIGSERIAL PRIMARY KEY,
	name VARCHAR(200)
);

CREATE TABLE member_organisation (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT NOT NULL,
    name            VARCHAR(100),
    organisation_id BIGINT NOT NULL REFERENCES organisation(id),
    role            VARCHAR(30) NOT NULL,
    CONSTRAINT uk_member_organisation UNIQUE (organisation_id, user_id)
);