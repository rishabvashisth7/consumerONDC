import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CartService } from '../service/cart.service';
import { ICart, Cart } from '../cart.model';
import { IProduct } from 'app/entities/product/product.model';
import { ProductService } from 'app/entities/product/service/product.service';
import { IStore } from 'app/entities/store/store.model';
import { StoreService } from 'app/entities/store/service/store.service';

import { CartUpdateComponent } from './cart-update.component';

describe('Cart Management Update Component', () => {
  let comp: CartUpdateComponent;
  let fixture: ComponentFixture<CartUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let cartService: CartService;
  let productService: ProductService;
  let storeService: StoreService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CartUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(CartUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CartUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    cartService = TestBed.inject(CartService);
    productService = TestBed.inject(ProductService);
    storeService = TestBed.inject(StoreService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Product query and add missing value', () => {
      const cart: ICart = { id: 456 };
      const product: IProduct = { id: 38286 };
      cart.product = product;

      const productCollection: IProduct[] = [{ id: 16929 }];
      jest.spyOn(productService, 'query').mockReturnValue(of(new HttpResponse({ body: productCollection })));
      const additionalProducts = [product];
      const expectedCollection: IProduct[] = [...additionalProducts, ...productCollection];
      jest.spyOn(productService, 'addProductToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      expect(productService.query).toHaveBeenCalled();
      expect(productService.addProductToCollectionIfMissing).toHaveBeenCalledWith(productCollection, ...additionalProducts);
      expect(comp.productsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Store query and add missing value', () => {
      const cart: ICart = { id: 456 };
      const store: IStore = { id: 82627 };
      cart.store = store;

      const storeCollection: IStore[] = [{ id: 75978 }];
      jest.spyOn(storeService, 'query').mockReturnValue(of(new HttpResponse({ body: storeCollection })));
      const additionalStores = [store];
      const expectedCollection: IStore[] = [...additionalStores, ...storeCollection];
      jest.spyOn(storeService, 'addStoreToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      expect(storeService.query).toHaveBeenCalled();
      expect(storeService.addStoreToCollectionIfMissing).toHaveBeenCalledWith(storeCollection, ...additionalStores);
      expect(comp.storesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const cart: ICart = { id: 456 };
      const product: IProduct = { id: 72210 };
      cart.product = product;
      const store: IStore = { id: 2873 };
      cart.store = store;

      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(cart));
      expect(comp.productsSharedCollection).toContain(product);
      expect(comp.storesSharedCollection).toContain(store);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cart>>();
      const cart = { id: 123 };
      jest.spyOn(cartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cart }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(cartService.update).toHaveBeenCalledWith(cart);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cart>>();
      const cart = new Cart();
      jest.spyOn(cartService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: cart }));
      saveSubject.complete();

      // THEN
      expect(cartService.create).toHaveBeenCalledWith(cart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Cart>>();
      const cart = { id: 123 };
      jest.spyOn(cartService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ cart });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(cartService.update).toHaveBeenCalledWith(cart);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProductById', () => {
      it('Should return tracked Product primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProductById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackStoreById', () => {
      it('Should return tracked Store primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackStoreById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
