CREATE TABLE account (
  id              UUID         NOT NULL,
  created         TIMESTAMP    NOT NULL,
  updated         TIMESTAMP,
  email           VARCHAR(255) NOT NULL,
  email_confirmed BOOLEAN      NOT NULL,
  enabled         BOOLEAN      NOT NULL,
  password        VARCHAR(255) NOT NULL,
  role            VARCHAR(255) NOT NULL,
  username        VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE acquisition (
  id               UUID           NOT NULL,
  created          TIMESTAMP      NOT NULL,
  updated          TIMESTAMP,
  cpv_code         VARCHAR(255)   NOT NULL,
  estimated_value  NUMERIC(19, 2) NOT NULL,
  financing_source VARCHAR(255)   NOT NULL,
  objective        VARCHAR(255)   NOT NULL,
  creator_id       UUID           NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE comment (
  id             UUID         NOT NULL,
  created        TIMESTAMP    NOT NULL,
  updated        TIMESTAMP,
  text           VARCHAR(255) NOT NULL,
  account_id     UUID         NOT NULL,
  acquisition_id UUID         NOT NULL,
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
CREATE TABLE direct_acquisition (
  final_date   VARCHAR(255) NOT NULL,
  initial_date VARCHAR(255) NOT NULL,
  status       VARCHAR(255) NOT NULL,
  id           UUID         NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE order_number (
  year              VARCHAR(255) NOT NULL,
  procedures_number INT4         NOT NULL,
  services_number   INT4         NOT NULL,
  works_number      INT4         NOT NULL,
  PRIMARY KEY (year)
);
CREATE TABLE procedure (
  procedure_type VARCHAR(50)  NOT NULL,
  status         VARCHAR(255) NOT NULL,
  id             UUID         NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE service (
  ordering_number INT4 NOT NULL,
  id              UUID NOT NULL,
  PRIMARY KEY (id)
);
CREATE TABLE work (
  ordering_number INT4 NOT NULL,
  id              UUID NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE account
  ADD CONSTRAINT UK_q0uja26qgu1atulenwup9rxyr UNIQUE (email);
ALTER TABLE account
  ADD CONSTRAINT UK_gex1lmaqpg0ir5g1f5eftyaa1 UNIQUE (username);
ALTER TABLE acquisition
  ADD CONSTRAINT FKjm9ajjl0u4koco3h9hl45ft65 FOREIGN KEY (creator_id) REFERENCES account;
ALTER TABLE comment
  ADD CONSTRAINT FKp41h5al2ajp1q0u6ox3i68w61 FOREIGN KEY (account_id) REFERENCES account;
ALTER TABLE comment
  ADD CONSTRAINT FKd9aht3hqev9x5y0xnvqtborya FOREIGN KEY (acquisition_id) REFERENCES acquisition;
ALTER TABLE confirmation_code
  ADD CONSTRAINT FK83lahsu9p8jnqxejtaca7e5ng FOREIGN KEY (account_id) REFERENCES account;
ALTER TABLE direct_acquisition
  ADD CONSTRAINT FK8fecrfhagnexkl0v9rf7y7sc5 FOREIGN KEY (id) REFERENCES acquisition;
ALTER TABLE procedure
  ADD CONSTRAINT FK1ue89wcsoggptb93j1at113td FOREIGN KEY (id) REFERENCES acquisition;
ALTER TABLE service
  ADD CONSTRAINT FK5puqu4mnaluyckt70og9j883r FOREIGN KEY (id) REFERENCES direct_acquisition;
ALTER TABLE work
  ADD CONSTRAINT FKm170nxamggrgusq6kod6ij0mi FOREIGN KEY (id) REFERENCES direct_acquisition;
