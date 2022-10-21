import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IStore } from '../store.model';
import { StoreService } from '../service/store.service';

@Component({
  templateUrl: './store-delete-dialog.component.html',
})
export class StoreDeleteDialogComponent {
  store?: IStore;

  constructor(protected storeService: StoreService, protected activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.storeService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
