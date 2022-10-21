import { IProduct } from 'app/entities/product/product.model';
import { IStore } from 'app/entities/store/store.model';

export interface ICart {
  id?: number;
  referenceId?: string | null;
  productName?: string | null;
  price?: string | null;
  quantity?: number | null;
  product?: IProduct | null;
  store?: IStore | null;
}

export class Cart implements ICart {
  constructor(
    public id?: number,
    public referenceId?: string | null,
    public productName?: string | null,
    public price?: string | null,
    public quantity?: number | null,
    public product?: IProduct | null,
    public store?: IStore | null
  ) {}
}

export function getCartIdentifier(cart: ICart): number | undefined {
  return cart.id;
}
