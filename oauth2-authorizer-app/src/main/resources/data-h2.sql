INSERT INTO oauth_client_details VALUES (
	'fromTo.aqueteron.com.br',
	'fromto',
	'{bcrypt}$2a$10$BuCkR6xa5NJn5JnpCAJVF.7YAdhOFrMiToICd0LMtRrTOHPb7oWyy',
	'read,write,run', 
	'implicit',
	'https://www.getpostman.com/oauth2/callback', 
	'ROLE_CLIENT',
	120, 
	600, 
	'{}', 
	'');	
	
INSERT INTO user VALUES (
	'user' ,
	'{bcrypt}$2a$10$HK0Xa/MM6HGp6JC8SajkJ.78.3Z4L1EpAoG6TlqZVjB.LaKpj/nxe' ,
	1 );

INSERT INTO user_role ( user_name, role ) VALUES (
	'user' ,
	'ROLE_USER' );