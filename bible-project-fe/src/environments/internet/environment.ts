import { Environment } from '../../../libs/env-management/model/environment';

const beBaseUrl: string = 'http://92.16.196.50';

export const environment: Environment = {
  app: {
    environment: 'internet',
    title: 'BibleProject',
    name: 'BibleProject',
  },
  security: {
    baseUrl: beBaseUrl,
    bibleProjectBE: {
      endpoint: beBaseUrl + ':8080',
    },
  },
};
