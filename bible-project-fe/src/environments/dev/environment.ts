import { Environment } from '../../../libs/env-management/model/environment';

const beBaseUrl: string = 'http://192.168.1.166:8989';

export const environment: Environment = {
  app: {
    environment: 'development',
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
