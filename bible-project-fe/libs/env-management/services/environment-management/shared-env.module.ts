import { ModuleWithProviders, NgModule, Provider } from '@angular/core';

import { ENV_TOKEN } from '../../model/injection-token';
import { Environment } from '../../model/environment';

@NgModule({})
export class SharedEnvModule {
  static env: Environment;

  static forRoot(env: Environment): ModuleWithProviders<SharedEnvModule> {
    this.env = env;
    return {
      ngModule: SharedEnvModule,
      providers: [
        {
          provide: ENV_TOKEN,
          useValue: this.env,
        },
      ],
    };
  }

  static forChild(): ModuleWithProviders<SharedEnvModule> {
    return {
      ngModule: SharedEnvModule,
      providers: [
        {
          provide: ENV_TOKEN,
          useValue: this.env,
        },
      ],
    };
  }

  static forChildProvider(): Provider {
    return {
      provide: ENV_TOKEN,
      useFactory: () => this.env,
    };
  }
}
