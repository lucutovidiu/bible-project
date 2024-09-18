import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { persistState } from '@datorama/akita';

import { AppModule } from './app/app.module';

persistState({
  include: ['site-store'],
  storage: sessionStorage,
});

persistState({
  include: ['site-preferences-store'],
  storage: localStorage,
});

platformBrowserDynamic()
  .bootstrapModule(AppModule)
  .catch((err) => console.error(err));
