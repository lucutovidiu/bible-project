import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import {
  ChapterDisplayPageComponent,
  HomePageComponent,
  SearchResultsDisplayPageComponent,
  SettingsPageComponent,
} from '@bible/pages';

const routes: Routes = [
  {
    path: '',
    component: HomePageComponent,
  },
  {
    path: 'search/:searchText',
    component: SearchResultsDisplayPageComponent,
  },
  {
    path: 'display-chapter',
    component: ChapterDisplayPageComponent,
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
