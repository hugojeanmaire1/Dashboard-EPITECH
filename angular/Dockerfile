FROM node:latest

WORKDIR /app

RUN npm install
RUN npm install -g @angular/cli

ENV PATH="./node_modules/.bin:$PATH"

CMD ng serve --host 0.0.0.0
EXPOSE 4200
