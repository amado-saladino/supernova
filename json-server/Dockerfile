FROM node:latest
RUN npm install -g json-server

WORKDIR /data
EXPOSE 80
CMD ["sh", "-c", "json-server $DB"]
