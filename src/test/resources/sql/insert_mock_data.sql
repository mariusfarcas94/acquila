INSERT INTO account (id, created, updated, email, email_confirmed, enabled, password, role, username) VALUES
  ('1167bbea-d054-11e7-abc4-cec278b6b50a', '2018-01-23 15:00:00', NULL, 'bobi@gmail.com', TRUE, TRUE, 'parola', 'ADMIN', 'bobi123');

INSERT INTO acquisition (id, created, updated, cpv_code, estimated_value, financing_source, objective, creator_id) VALUES
  ('1623acbd-c153-11d3-cab2-ada278b6b63b', '2018-01-23 15:00:00', NULL, '123asd', 2500.2, 'Bani publici', 'Renovare birt', '1167bbea-d054-11e7-abc4-cec278b6b50a');

INSERT INTO direct_acquisition (final_date, initial_date, status, id) VALUES
  ('maine', 'azi', 'PLANNED', '1623acbd-c153-11d3-cab2-ada278b6b63b');

INSERT INTO service (ordering_number, id) VALUES (1, '1623acbd-c153-11d3-cab2-ada278b6b63b');