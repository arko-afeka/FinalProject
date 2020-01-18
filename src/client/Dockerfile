FROM node:10

WORKDIR /usr/src/client

COPY ./package.json ./

RUN npm install

COPY . .

EXPOSE 8081

CMD ["node", "index.js"]