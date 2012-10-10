------------------------------------------------
-- Create table TFR_AUDIT
------------------------------------------------
create table TFR_AUDIT(
	CORRELATION_IDENTIFIER varchar2( 50 ) not null,
    FE_ID number(19,0) not null,
    REQUESTDATA clob,
	RESPONSEDATA clob
);