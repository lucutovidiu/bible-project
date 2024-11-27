FROM nginx:1.23.3-alpine
WORKDIR /nginx
COPY /etc/nginx/conf.d/default.conf /etc/nginx/conf.d/default.conf
COPY /etc/nginx/nginx.conf /etc/nginx/nginx.conf
COPY /certificates/wild_cert/fullchain.pem /nginx/cert/wild_cert/fullchain.pem
COPY /certificates/wild_cert/privkey.pem /nginx/cert/wild_cert/privkey.pem
# COPY /certificates/bible-proweb/fullchain.pem /nginx/cert/bible-proweb/fullchain.pem
# COPY /certificates/bible-proweb/privkey.pem /nginx/cert/bible-proweb/privkey.pem
# COPY /certificates/bible-be-proweb/fullchain.pem /nginx/cert/bible-be-proweb/fullchain.pem
# COPY /certificates/bible-be-proweb/privkey.pem /nginx/cert/bible-be-proweb/privkey.pem
# Expose Port 80 443
EXPOSE 443
# Start the Nginx server
CMD ["nginx", "-g", "daemon off;"]
