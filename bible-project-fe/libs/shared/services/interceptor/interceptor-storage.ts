import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class InterceptorStorage {
  private readonly apiKey = 'apiKey';
  private storageOption = localStorage;

  public storeApiKey(apiKey: string) {
    this.storageOption.setItem(this.apiKey, apiKey);
  }

  public retrieveApiKey(): string {
    return this.storageOption.getItem(this.apiKey) || '';
  }

  resetToken() {
    this.storageOption.removeItem(this.apiKey);
  }
}
