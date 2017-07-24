
INSERT INTO example.workflow_type(name, event_type, rollback_event_type, dsc)
VALUES('MONEY_TRANSFER', 'START', 'FAIL', 'money transfer from one account to another');


INSERT INTO example.event_type(workflow_type_name, name,  is_final)
VALUES('MONEY_TRANSFER', 'START', false);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'FAIL', true);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'CREATE_CREDIT', false);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'CREATE_CREDIT_ROLLBACK', true);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'CREATE_DEBIT', false);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'PROCESS_NOT_ENOUGH_MONEY', true);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'VALIDATE_TRANSFER', true);

INSERT INTO example.event_type(workflow_type_name, name, is_final)
VALUES('MONEY_TRANSFER', 'REPEAT_VALIDATION', true);



INSERT INTO example.event_type_status(workflow_type_name, event_type_from, status, event_type_to, event_type_rollback)
VALUES('MONEY_TRANSFER', 'START', 'SUCCESS', 'CREATE_CREDIT', 'CREATE_CREDIT_ROLLBACK');

INSERT INTO example.event_type_status(workflow_type_name, event_type_from, status, event_type_to, event_type_rollback)
VALUES('MONEY_TRANSFER', 'CREATE_CREDIT', 'SUCCESS', 'CREATE_DEBIT', 'REPEAT_VALIDATION');

INSERT INTO example.event_type_status(workflow_type_name, event_type_from, status,event_type_to, event_type_rollback)
VALUES('MONEY_TRANSFER', 'CREATE_CREDIT', 'NOT_ENOUGH_MONEY', 'PROCESS_NOT_ENOUGH_MONEY', null);

INSERT INTO example.event_type_status(workflow_type_name, event_type_from, status, event_type_to, event_type_rollback)
VALUES('MONEY_TRANSFER', 'CREATE_DEBIT', 'SUCCESS', 'VALIDATE_TRANSFER', null);

INSERT INTO example.event_type_status(workflow_type_name, event_type_from, status, event_type_to, event_type_rollback)
VALUES('MONEY_TRANSFER', 'CREATE_DEBIT', 'ACCOUNT_LOCKED', 'CREATE_CREDIT_ROLLBACK', null);



INSERT INTO example.workflow_status(name) VALUES('STARTED');
INSERT INTO example.workflow_status(name) VALUES('COMPLETED');
INSERT INTO example.workflow_status(name) VALUES('FAILED');
INSERT INTO example.workflow_status(name) VALUES('ROLLBACK_STARTED');
INSERT INTO example.workflow_status(name) VALUES('ROLLBACK_COMPLETED');
INSERT INTO example.workflow_status(name) VALUES('ROLLBACK_FAILED');




