FROM node:10

WORKDIR /usr/src/client

COPY ../../client/package*.json ./

RUN npm install

COPY client .

EXPOSE 8080

CMD ["node", "index.js"]
