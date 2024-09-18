#!/bin/bash
# https://www.inmotionhosting.com/support/website/ssl/lets-encrypt-ssl-ubuntu-with-certbot/

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}



brew services stop certbot

certbot certonly --standalone -d "bible.prowebart.co.uk" | tee ./cert-details

sudo cat /etc/letsencrypt/live/prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/cert/prowebart/fullchain.pem
sudo cat /etc/letsencrypt/live/prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/prowebart/privkey.pem


sudo cat /etc/letsencrypt/live/bible.prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-proweb/fullchain.pem
sudo cat /etc/letsencrypt/live/bible.prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-proweb/privkey.pem

sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-be-proweb/fullchain.pem
sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-be-proweb/privkey.pem



Certificate is saved at: /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem
Key is saved at:         /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem