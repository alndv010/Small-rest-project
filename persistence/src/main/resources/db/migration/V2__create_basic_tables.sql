CREATE TABLE example.workflow_status (
  name VARCHAR(100),
  dsc VARCHAR(100),
  CONSTRAINT pk_workflow_status PRIMARY KEY (name)
);

CREATE TABLE example.workflow_type (
  name VARCHAR(100),
  event_type VARCHAR(100) NOT NULL,
  rollback_event_type VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  CONSTRAINT pk_workflow_type PRIMARY KEY (name)
);

CREATE TABLE example.event_type (
  workflow_type_name VARCHAR(100) NOT NULL,
  name VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  is_final BOOLEAN NOT NULL,
  FOREIGN KEY (workflow_type_name) REFERENCES workflow_type(name),
  CONSTRAINT pk_event_type PRIMARY KEY (workflow_type_name, name)
);

CREATE TABLE example.event_type_status (
  workflow_type_name VARCHAR(100) NOT NULL,
  event_type_from VARCHAR(100) NOT NULL,
  status VARCHAR(100) NOT NULL,
  event_type_to VARCHAR(100) NOT NULL,
  event_type_rollback VARCHAR(100),
  FOREIGN KEY (workflow_type_name, event_type_from) REFERENCES event_type(workflow_type_name, name),
  FOREIGN KEY (workflow_type_name, event_type_to) REFERENCES event_type(workflow_type_name, name),
  FOREIGN KEY (workflow_type_name, event_type_rollback) REFERENCES event_type(workflow_type_name, name),
  CONSTRAINT pk_event_type_status PRIMARY KEY (workflow_type_name, event_type_from, status)
);

CREATE TABLE example.workflow (
  workflow_id VARCHAR(100),
  workflow_type_name VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  workflow_status_name VARCHAR(100),
  FOREIGN KEY (workflow_type_name) REFERENCES workflow_type(name),
  FOREIGN KEY (workflow_status_name) REFERENCES workflow_status(name),
  CONSTRAINT pk_workflow PRIMARY KEY (workflow_id)
);

