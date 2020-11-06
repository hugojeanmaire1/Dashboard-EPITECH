FROM node:14.15.0-alpine

WORKDIR /app

COPY ./package.json /app/package.json

RUN npm install -g @angular/cli

ENV PATH="./node_modules/.bin:$PATH"

RUN npm install

CMD ng serve --host 0.0.0.0
EXPOSE 4200
