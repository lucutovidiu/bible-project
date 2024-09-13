import { inject, Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { IndividualConfig } from 'ngx-toastr/toastr/toastr-config';

@Injectable({
  providedIn: 'root',
})
export class BibleToastrService {
  private toastrService: ToastrService = inject(ToastrService);

  public success(
    msg: string,
    title = '',
    persistent = false,
    timeout = 7000
  ): void {
    if (persistent) {
      this.toastrService.success(
        msg,
        title,
        this.createPersistentAnimationConfig()
      );
    } else {
      this.toastrService.success(
        msg,
        title,
        this.createTimeoutAnimationConfig(timeout)
      );
    }
  }

  public info(
    msg: string,
    title = '',
    persistent = false,
    timeout = 7000
  ): void {
    if (persistent) {
      this.toastrService.info(
        msg,
        title,
        this.createPersistentAnimationConfig()
      );
    } else {
      this.toastrService.info(
        msg,
        title,
        this.createTimeoutAnimationConfig(timeout)
      );
    }
  }

  public error(
    msg: string,
    title = '',
    persistent = false,
    timeout = 7000
  ): void {
    if (persistent) {
      this.toastrService.error(
        msg,
        title,
        this.createPersistentAnimationConfig()
      );
    } else {
      this.toastrService.error(
        msg,
        title,
        this.createTimeoutAnimationConfig(timeout)
      );
    }
  }

  public resetToasts() {
    this.toastrService.toasts.forEach((toast) =>
      this.toastrService.remove(toast.toastId)
    );
  }

  private createTimeoutAnimationConfig(
    timeOut: number
  ): Partial<IndividualConfig> {
    return {
      timeOut,
      progressBar: true,
    };
  }

  private createPersistentAnimationConfig(): Partial<IndividualConfig> {
    return {
      progressBar: false,
    };
  }
}
