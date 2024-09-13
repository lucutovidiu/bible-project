import {Environment} from "../../app/model/environment/environment";

const beBaseUrl: string = 'http://localhost:8989';

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
