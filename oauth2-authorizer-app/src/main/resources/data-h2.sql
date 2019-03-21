INSERT INTO oauth_client_details VALUES (
	'fromTo.aqueteron.com.br',
	'fromto-resource',
	'{noop}secret',
	'read,write,run',
	'client_credentials,refresh_token',
	'https://www.getpostman.com/oauth2/callback',
	'ROLE_SERVER',
	3600,
	86400,
	'{}',
	'');

INSERT INTO oauth_client_details VALUES (
	'fromTo-api.aqueteron.com.br',
	'fromto-api',
	'{noop}secret',
	'read,write,run',
	'password,authorization_code,refresh_token,implicit',
	'https://www.getpostman.com/oauth2/callback',
	'ROLE_CLIENT',
	3600,
	86400,
	'{}',
	'');
	
INSERT INTO user VALUES (
	'user@aqueteron.com.br' ,
	'{bcrypt}$2a$10$HK0Xa/MM6HGp6JC8SajkJ.78.3Z4L1EpAoG6TlqZVjB.LaKpj/nxe' ,
	1 );

INSERT INTO user_role ( user_name, role ) VALUES (
	'user@aqueteron.com.br' ,
	'ROLE_USER' );
