CREATE TABLE example.account (
  account_id VARCHAR(100) NOT NULL,
  dsc VARCHAR(100),
  balance DECIMAL NOT NULL,
  CONSTRAINT pk_account PRIMARY KEY (account_id)
);


CREATE TABLE example.transfer_info (
  transfer_info_id VARCHAR(100) NOT NULL,
  from_account_id VARCHAR(100) NOT NULL,
  to_account_id  VARCHAR(100) NOT NULL,
  amount DECIMAL NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
  FOREIGN KEY (from_account_id) REFERENCES example.account(account_id),
  FOREIGN KEY (to_account_id) REFERENCES example.account(account_id),
  CONSTRAINT pk_transfer_info PRIMARY KEY (transfer_info_id)
);


