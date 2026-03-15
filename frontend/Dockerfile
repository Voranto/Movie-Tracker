# ---------- STAGE 1: Build ----------
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci
COPY . .
RUN npm run build

# ---------- STAGE 2: Serve ----------
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

COPY nginx.static.conf /etc/nginx/conf.d/default.conf

EXPOSE 80
RUN apk add --no-cache tzdata
ENV TZ=Europe/Berlin
CMD ["nginx", "-g", "daemon off;"]