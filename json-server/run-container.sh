# use either of these methods
docker run -d -p 80:80 -v $PWD/users.json:/data/db.json clue/json-server

# or use this command with the Dockerfile
docker build -t server .
docker run -d -p 80:80 -v $PWD:/data server users.json

# json file from env. variable
docker run -d -p 81:80 -v $PWD:/data -e "DB=users.json" json-server:v1