import {InjectionToken} from '@angular/core';

import {Environment} from './environment';

export const ENV_TOKEN = new InjectionToken<Environment>(
  'Application Environment config token'
);
