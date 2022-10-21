import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'store',
        data: { pageTitle: 'bppondcApp.store.home.title' },
        loadChildren: () => import('./store/store.module').then(m => m.StoreModule),
      },
      {
        path: 'cart',
        data: { pageTitle: 'bppondcApp.cart.home.title' },
        loadChildren: () => import('./cart/cart.module').then(m => m.CartModule),
      },
      {
        path: 'product',
        data: { pageTitle: 'bppondcApp.product.home.title' },
        loadChildren: () => import('./product/product.module').then(m => m.ProductModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
