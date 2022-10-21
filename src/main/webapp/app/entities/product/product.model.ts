import dayjs from 'dayjs/esm';
import { IStore } from 'app/entities/store/store.model';
import { ICart } from 'app/entities/cart/cart.model';

export interface IProduct {
  id?: number;
  title?: string;
  description?: string | null;
  price?: number | null;
  tag?: string | null;
  expire?: dayjs.Dayjs | null;
  store?: IStore | null;
  carts?: ICart[] | null;
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public title?: string,
    public description?: string | null,
    public price?: number | null,
    public tag?: string | null,
    public expire?: dayjs.Dayjs | null,
    public store?: IStore | null,
    public carts?: ICart[] | null
  ) {}
}

export function getProductIdentifier(product: IProduct): number | undefined {
  return product.id;
}
