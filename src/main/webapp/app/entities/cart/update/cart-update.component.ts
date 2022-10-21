import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICart, Cart } from '../cart.model';
import { CartService } from '../service/cart.service';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IStore } from 'app/entities/store/store.model';
import { StoreService } from 'app/entities/store/service/store.service';

@Component({
  selector: 'jhi-cart-update',
  templateUrl: './cart-update.component.html',
})
export class CartUpdateComponent implements OnInit {
  isSaving = false;

  productsSharedCollection: IProduct[] = [];
  storesSharedCollection: IStore[] = [];

  editForm = this.fb.group({
    id: [],
    referenceId: [],
    productName: [],
    price: [],
    quantity: [],
    product: [],
    store: [],
  });

  constructor(
    protected cartService: CartService,
    protected productService: ProductService,
    protected storeService: StoreService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ cart }) => {
      this.updateForm(cart);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const cart = this.createFromForm();
    if (cart.id !== undefined) {
      this.subscribeToSaveResponse(this.cartService.update(cart));
    } else {
      this.subscribeToSaveResponse(this.cartService.create(cart));
    }
  }

  trackProductById(_index: number, item: IProduct): number {
    return item.id!;
  }

  trackStoreById(_index: number, item: IStore): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICart>>): void {
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

  protected updateForm(cart: ICart): void {
    this.editForm.patchValue({
      id: cart.id,
      referenceId: cart.referenceId,
      productName: cart.productName,
      price: cart.price,
      quantity: cart.quantity,
      product: cart.product,
      store: cart.store,
    });

    this.productsSharedCollection = this.productService.addProductToCollectionIfMissing(this.productsSharedCollection, cart.product);
    this.storesSharedCollection = this.storeService.addStoreToCollectionIfMissing(this.storesSharedCollection, cart.store);
  }

  protected loadRelationshipsOptions(): void {
    this.productService
      .query()
      .pipe(map((res: HttpResponse<IProduct[]>) => res.body ?? []))
      .pipe(
        map((products: IProduct[]) => this.productService.addProductToCollectionIfMissing(products, this.editForm.get('product')!.value))
      )
      .subscribe((products: IProduct[]) => (this.productsSharedCollection = products));

    this.storeService
      .query()
      .pipe(map((res: HttpResponse<IStore[]>) => res.body ?? []))
      .pipe(map((stores: IStore[]) => this.storeService.addStoreToCollectionIfMissing(stores, this.editForm.get('store')!.value)))
      .subscribe((stores: IStore[]) => (this.storesSharedCollection = stores));
  }

  protected createFromForm(): ICart {
    return {
      ...new Cart(),
      id: this.editForm.get(['id'])!.value,
      referenceId: this.editForm.get(['referenceId'])!.value,
      productName: this.editForm.get(['productName'])!.value,
      price: this.editForm.get(['price'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      product: this.editForm.get(['product'])!.value,
      store: this.editForm.get(['store'])!.value,
    };
  }
}
