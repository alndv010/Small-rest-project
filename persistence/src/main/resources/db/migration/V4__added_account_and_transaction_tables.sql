CREATE TABLE example.account (
  account_id VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  balance DECIMAL NOT NULL,
  CONSTRAINT pk_account PRIMARY KEY (account_id)
);

CREATE TABLE example.account_balance_status (
  account_id VARCHAR(100) NOT NULL,
  workflow_id VARCHAR(100) NOT NULL,
  is_locked BOOLEAN NOT NULL,
  updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (account_id) REFERENCES example.account(account_id),
  FOREIGN KEY (workflow_id) REFERENCES example.workflow(workflow_id),
  CONSTRAINT pk_account_balance_status PRIMARY KEY (account_id, workflow_id)
);

CREATE TABLE example.account_balance_inc (
  account_id VARCHAR(100),
  workflow_id VARCHAR(100),
  old_value DECIMAL,
  new_value DECIMAL,
  is_new_value_set BOOLEAN NOT NULL,
  FOREIGN KEY (account_id) REFERENCES example.account(account_id),
  FOREIGN KEY (workflow_id) REFERENCES example.workflow(workflow_id),
  CONSTRAINT pk_account_balance_inc PRIMARY KEY (account_id, workflow_id)
);

CREATE TABLE example.transaction_status (
  name VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  CONSTRAINT pk_transaction_status PRIMARY KEY (name)
);


CREATE TABLE example.transaction_info (
  transaction_info_id VARCHAR(100) NOT NULL,
  from_account_id VARCHAR(100) NOT NULL,
  to_account_id  VARCHAR(100) NOT NULL,
  amount DECIMAL NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  transaction_status_name VARCHAR(100)  NULL,
  workflow_id VARCHAR(100) NULL,
  FOREIGN KEY (from_account_id) REFERENCES example.account(account_id),
  FOREIGN KEY (to_account_id) REFERENCES example.account(account_id),
  FOREIGN KEY (transaction_status_name) REFERENCES example.transaction_status(name),
  CONSTRAINT pk_transaction_info PRIMARY KEY (transaction_info_id)
);


