# use either of these methods
docker run -d -p 80:80 -v $PWD/users.json:/data/db.json clue/json-server

# or use this command with the Dockerfile
docker build -t server .
docker run -d -p 80:80 -v $PWD:/data server users.json