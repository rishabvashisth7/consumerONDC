import { IProduct } from 'app/entities/product/product.model';
import { ICart } from 'app/entities/cart/cart.model';

export interface IStore {
  id?: number;
  storeName?: string | null;
  storeAddress?: string | null;
  storeRating?: number | null;
  products?: IProduct[] | null;
  carts?: ICart[] | null;
}

export class Store implements IStore {
  constructor(
    public id?: number,
    public storeName?: string | null,
    public storeAddress?: string | null,
    public storeRating?: number | null,
    public products?: IProduct[] | null,
    public carts?: ICart[] | null
  ) {}
}

export function getStoreIdentifier(store: IStore): number | undefined {
  return store.id;
}
