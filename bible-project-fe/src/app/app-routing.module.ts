import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { HomeComponent } from './components/home/home.component';
import { SearchComponent } from './components/search/search.component';
import { ResultDisplayFullChapterComponent } from './components/result-display-full-chapter/result-display-full-chapter.component';
import { SettingsPageComponent } from './components/settings-page/settings-page.component';

const routes: Routes = [
  {
    path: '',
    component: HomeComponent,
  },
  {
    path: 'search/:searchText',
    component: SearchComponent,
  },
  {
    path: 'display-chapter',
    component: ResultDisplayFullChapterComponent,
  },
  {
    path: 'settings',
    component: SettingsPageComponent,
  },
  { path: '**', redirectTo: '/' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
