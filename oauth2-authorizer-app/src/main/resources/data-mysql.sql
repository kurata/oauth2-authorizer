INSERT INTO oauth_client_details VALUES (
  'fromTo.aqueteron.com.br',
  'fromto-api',
  '{noop}secret',
	'read,write,run',
	'authorization_code,implicit',
	'https://www.getpostman.com/oauth2/callback', 
	'ROLE_CLIENT',
	3600,
	86400,
	'{}', 
	'');
	
INSERT INTO user VALUES (
	'user' ,
	'{bcrypt}$2a$10$HK0Xa/MM6HGp6JC8SajkJ.78.3Z4L1EpAoG6TlqZVjB.LaKpj/nxe' ,
	1 );

INSERT INTO user_role ( user_name , role ) VALUES (
	'user' ,
	'ROLE_USER' );

INSERT INTO oauth_client_details VALUES (
	'fromTo.api.aqueteron.com.br',
	'fromto-resource',
	'{noop}secret',
	'read,write,run',
	'client_credentials,refresh_token',
	'https://www.getpostman.com/oauth2/callback',
	'ROLE_APPLICATION',
	3600,
	86400,
	'{}',
	'');