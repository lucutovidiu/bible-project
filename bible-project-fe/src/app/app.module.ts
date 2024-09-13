import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';
import {ToastrModule} from "ngx-toastr";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {provideHttpClient, withInterceptorsFromDi} from "@angular/common/http";

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {HomeComponent} from "./components/home/home.component";
import {NavBarComponent} from "./components/nav-bar/nav-bar.component";
import {
  ResultDisplayFullChapterComponent
} from './components/result-display-full-chapter/result-display-full-chapter.component';
import {LoadingIndicatorBoxComponent} from "./components/loading-indicator-box/loading-indicator-box.component";
import {SharedEnvModule} from "./services/environment-management/shared-env.module";
import { environment } from '../environments/local/environment';

@NgModule({
  declarations: [
    AppComponent,
    ResultDisplayFullChapterComponent,
  ],
  bootstrap: [AppComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    SharedEnvModule.forRoot(environment),
    HomeComponent,
    NavBarComponent,
    LoadingIndicatorBoxComponent,
    BrowserAnimationsModule,
    ToastrModule.forRoot({
      positionClass: 'toast-bottom-center',
      timeOut: 0,
      tapToDismiss: false,
      closeButton: true,
    }),
  ],
  providers: [provideHttpClient(withInterceptorsFromDi())]
})
export class AppModule {
}
