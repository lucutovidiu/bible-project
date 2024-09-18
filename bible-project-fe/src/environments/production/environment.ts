import { Environment } from '../../app/model/environment/environment';

const beBaseUrl: string = 'https://bible-be.prowebart.co.uk';

export const environment: Environment = {
  app: {
    environment: 'production',
    title: 'BibleProject',
    name: 'BibleProject',
  },
  security: {
    baseUrl: beBaseUrl,
    bibleProjectBE: {
      endpoint: beBaseUrl,
    },
  },
};
