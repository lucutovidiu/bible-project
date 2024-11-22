import {catchError, filter, finalize, Observable, switchMap, take, tap, throwError,} from 'rxjs';

export class ObservableCallService {
  static cachingUpdate<I, O>(
    data: Observable<I>,
    loadingFn: Loading,
    callingService: CallingService<O>,
    onSuccess: Success<O>,
    onError: ErrorHandle
  ) {
    data
      .pipe(
        take(1),
        filter((data) => !data),
        tap(() => loadingFn(true)),
        switchMap(() => callingService().pipe(take(1))),
        catchError((err) => {
          onError();
          return throwError(() => err);
        }),
        finalize(() => loadingFn(false))
      )
      .subscribe((data) => onSuccess(data));
  }

  static serviceCallStatic<O>(
    inputObservable: InputObservable<O>,
    loadingFn: Loading,
    onSuccess: Success<O>,
    onError: ErrorHandle
  ) {
    loadingFn(true);
    inputObservable()
      .pipe(
        take(1),
        catchError((err) => {
          onError();
          return throwError(() => err);
        }),
        finalize(() => loadingFn(false))
      )
      .subscribe((data) => onSuccess(data));
  }

  static serviceCall<O>(
    inputObservable: InputObservable<O>,
    loadingFn: Loading,
    onError: ErrorHandle
  ): Observable<O> {
    loadingFn(true);
    return inputObservable()
      .pipe(
        take(1),
        catchError((err) => {
          onError();
          return throwError(() => err);
        }),
        finalize(() => loadingFn(false))
      )
  }
}

interface Loading {
  (isCustomerDetailsLoading: boolean): void;
}

interface Success<O> {
  (data: O): void;
}

interface ErrorHandle {
  (): void;
}

interface InputObservable<I> {
  (): Observable<I>;
}

interface CallingService<O> {
  (): Observable<O>;
}
