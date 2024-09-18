### How to install new cert
1. run: ```sudo certbot certonly --standalone -d jettyplus.jettytech.co.uk```
   - choose here option 2, to renew the certificate, and wait for it to finish
2. run: ```sudo certbot renew```
    - now if you receive: "Certificate not yet due for renewal" it means that the certificate has been updated
3. move the new certificates into the nginx/cert folder as per below:
    - sudo cat /etc/letsencrypt/live/artifactory.jettytech.co.uk/fullchain.pem >> ./nginx/cert/privkey.pem
    - sudo cat /etc/letsencrypt/live/artifactory.jettytech.co.uk/privkey.pem >> ./nginx/cert/fullchain.pem
4. docker compose down
5. docker compose up --build -d
6. If above don't work delete the oss image and try again step 5
    - docker image ls
    - docker image rm {IMAGE_ID_OF_artifactory-oss}
