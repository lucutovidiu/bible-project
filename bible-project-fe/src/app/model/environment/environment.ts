export interface Environment {
  app: {
    environment: string;
    name: string;
    title: string;
  };
  security: {
    baseUrl: string;
    bibleProjectBE: {
      endpoint: string;
    };
  }
}
