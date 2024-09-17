import {Query} from "@datorama/akita";
import {Injectable} from "@angular/core";
import {Observable} from "rxjs";

import {BibleVerseStored, SelectedBibleBook, SiteReferencesState} from "./site-references-state";
import {SiteStoreService} from "./site-store.service";
import {BibleBook} from "../model/bible-book";
import {BibleVerse} from "../model/bible-verse";

@Injectable({
  providedIn: 'root',
})
export class SiteQueryService extends Query<SiteReferencesState> {


  constructor(protected override store: SiteStoreService) {
    super(store);
  }

}
