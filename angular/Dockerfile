FROM node:14.15.0-alpine

WORKDIR /app

COPY ./package.json /app/package.json

RUN npm install -g @angular/cli
RUN npm install

ENV PATH="./node_modules/.bin:$PATH"

CMD ng serve --host 0.0.0.0
EXPOSE 4200
