CREATE TABLE account (
  id              UUID         NOT NULL,
  created         TIMESTAMP    NOT NULL,
  updated         TIMESTAMP,
  email           VARCHAR(255) NOT NULL,
  email_confirmed BOOLEAN      NOT NULL,
  enabled         BOOLEAN      NOT NULL,
  password        VARCHAR(255) NOT NULL,
  role            INT4         NOT NULL,
  username        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE confirmation_code (
  id          UUID        NOT NULL,
  created     TIMESTAMP   NOT NULL,
  updated     TIMESTAMP,
  expire_date TIMESTAMP   NOT NULL,
  hashed_code VARCHAR(64) NOT NULL,
  used        BOOLEAN     NOT NULL,
  account_id  UUID        NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE account
  ADD CONSTRAINT UK_q0uja26qgu1atulenwup9rxyr UNIQUE (email);
ALTER TABLE account
  ADD CONSTRAINT UK_gex1lmaqpg0ir5g1f5eftyaa1 UNIQUE (username);
ALTER TABLE confirmation_code
  ADD CONSTRAINT FK83lahsu9p8jnqxejtaca7e5ng FOREIGN KEY (account_id) REFERENCES account;
