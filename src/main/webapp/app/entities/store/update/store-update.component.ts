import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IStore, Store } from '../store.model';
import { StoreService } from '../service/store.service';

@Component({
  selector: 'jhi-store-update',
  templateUrl: './store-update.component.html',
})
export class StoreUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    storeName: [],
    storeAddress: [],
    storeRating: [],
  });

  constructor(protected storeService: StoreService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ store }) => {
      this.updateForm(store);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const store = this.createFromForm();
    if (store.id !== undefined) {
      this.subscribeToSaveResponse(this.storeService.update(store));
    } else {
      this.subscribeToSaveResponse(this.storeService.create(store));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStore>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(store: IStore): void {
    this.editForm.patchValue({
      id: store.id,
      storeName: store.storeName,
      storeAddress: store.storeAddress,
      storeRating: store.storeRating,
    });
  }

  protected createFromForm(): IStore {
    return {
      ...new Store(),
      id: this.editForm.get(['id'])!.value,
      storeName: this.editForm.get(['storeName'])!.value,
      storeAddress: this.editForm.get(['storeAddress'])!.value,
      storeRating: this.editForm.get(['storeRating'])!.value,
    };
  }
}
