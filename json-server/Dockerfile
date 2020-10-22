FROM node:latest
RUN npm install -g json-server

WORKDIR /data
EXPOSE 80
ENTRYPOINT ["json-server"]
CMD ["db.json"]
