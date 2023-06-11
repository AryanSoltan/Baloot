FROM node:12.22.9-alpine as builder

LABEL authors="saba"

# copy the package.json to install dependencies
COPY package.json package-lock.json ./

# Install the dependencies and make the folder
RUN npm install && mkdir /baloot_ui && mv ./node_modules ./baloot_ui

WORKDIR /baloot_ui

COPY . .

RUN yarn run build
# Build the project and copy the files


FROM nginx:alpine

#!/bin/sh

COPY ./nginx.conf /etc/nginx/nginx.conf

## Remove default nginx index page
RUN rm -rf /usr/share/nginx/html/*


# Copy from the stahg 1
COPY --from=builder /baloot_ui/build /usr/share/nginx/html

EXPOSE 3000 80

ENTRYPOINT ["nginx", "-g", "daemon off;"]


#docker build -f Dockerfile -t baloot_ui .
#docker run -d --name balootui -p 3000:80 baloot_ui


