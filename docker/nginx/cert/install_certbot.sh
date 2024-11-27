#!/bin/bash
# https://www.inmotionhosting.com/support/website/ssl/lets-encrypt-ssl-ubuntu-with-certbot/

print_colored() {
  echo -e "\x1b[30;44m $1 \x1b[m"
}



brew services stop certbot

sudo certbot certonly --standalone -d "prowebart.co.uk" -d "bible.prowebart.co.uk" -d "bible-be.prowebart.co.uk"



sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/personal_repos/bible-project/docker/nginx/certificates/wild_cert/fullchain.pem
sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/personal_repos/bible-project/docker/nginx/certificates/wild_cert/privkey.pem

Certificate is saved at: /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem
Key is saved at:         /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem
# certbot certonly --standalone -d "bible.prowebart.co.uk" | tee ./cert-details

# sudo cat /etc/letsencrypt/live/prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/cert/prowebart/fullchain.pem
# sudo cat /etc/letsencrypt/live/prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/prowebart/privkey.pem
# /Users/ovidiulucut/work/personal_repos/bible-project/docker/nginx/certificates/wild_cert

# sudo cat /etc/letsencrypt/live/bible.prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-proweb/fullchain.pem
# sudo cat /etc/letsencrypt/live/bible.prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-proweb/privkey.pem

# sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-be-proweb/fullchain.pem
# sudo cat /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem | tee /Users/ovidiulucut/work/Documents/personal_repos/bible-project/docker/nginx/certificates/bible-be-proweb/privkey.pem



# Certificate is saved at: /etc/letsencrypt/live/bible-be.prowebart.co.uk/fullchain.pem
# Key is saved at:         /etc/letsencrypt/live/bible-be.prowebart.co.uk/privkey.pem


