
CREATE TABLE example.change_type (
  name VARCHAR(100),
  dsc VARCHAR(100),
  CONSTRAINT pk_change_type PRIMARY KEY (name)
);

CREATE TABLE example.change_status_type (
  name VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  CONSTRAINT pk_change_status_type PRIMARY KEY (name)
);

CREATE TABLE example.change_status (
  id BIGINT auto_increment,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  change_status_type_name VARCHAR(100),
  workflow_id VARCHAR(100) NOT NULL,
  workflow_type_name VARCHAR(100) NOT NULL,
  event_type_from VARCHAR(100) NOT NULL,
  status VARCHAR(100) NOT NULL,
  FOREIGN KEY (workflow_id) REFERENCES workflow(workflow_id),
  FOREIGN KEY (workflow_type_name, event_type_from, status)
    REFERENCES event_type_status(workflow_type_name, event_type_from, status),
  FOREIGN KEY (change_status_type_name) REFERENCES change_status_type(name),
  CONSTRAINT pk_change_status PRIMARY KEY (id)
);

CREATE TABLE example.change (
  id BIGINT auto_increment,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  change_status_id BIGINT NOT NULL,
  next_status VARCHAR(100),
  change_type_name VARCHAR(100),
  FOREIGN KEY (change_type_name) REFERENCES change_type(name),
  FOREIGN KEY (change_status_id) REFERENCES change_status(id),
  CONSTRAINT pk_change PRIMARY KEY (id)
);