CREATE DATABASE peerspace_clone WITH ENCODING 'UTF8';


CREATE USER peerspace_app WITH PASSWORD '1234';

GRANT ALL PRIVILEGES ON DATABASE peerspace_clone TO peerspace_app;

--set peerspace_clone as default

GRANT ALL ON SCHEMA public TO peerspace_app;