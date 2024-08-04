package com.gear2go_frontend.view.component;

import com.gear2go_frontend.domain.CartItem;
import com.gear2go_frontend.dto.ProductResponse;
import com.gear2go_frontend.service.CartService;
import com.gear2go_frontend.service.ProductService;
import com.gear2go_frontend.view.CartView;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.BigDecimalField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.net.URL;


public class CartItemCard extends HorizontalLayout {

    private Image productImage = new Image();
    private TextField name = new TextField();
    NumberField quantity = new NumberField();
    BigDecimalField price = new BigDecimalField();
    BigDecimalField totalPrice = new BigDecimalField();
    ProductResponse product;
    CartView cartView;

    private final ProductService productService;
    private final CartService cartService;

    public CartItemCard(CartItem cartItem, ProductService productService, CartService cartService, CartView cartView) {
        this.productService = productService;
        this.cartService = cartService;
        this.cartView = cartView;


        productService.getProductById(cartItem.productId(),
                success -> product = success,
                error -> error.getMessage());

        productImage.setMaxWidth("80px");
        productImage.setMaxHeight(LumoUtility.Height.AUTO);
        productImage.addClassNames(LumoUtility.Padding.MEDIUM);
        try {
            new URL(product.imageUrl()).toURI();
            productImage.setSrc(product.imageUrl());
        } catch (Exception e) {
            productImage.setSrc("https://images.unsplash.com/photo-1519681393784-d120267933ba?ixid=MXwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80");
        }

        name.setValue(product.name());
        name.setReadOnly(true);
        price.setValue(product.price());
        price.setReadOnly(true);
        price.setLabel("Unit Price/Day");
        totalPrice.setLabel("Total Item Price In Chosen Period");
        totalPrice.setValue(cartItem.price());
        quantity.setLabel("Quantity");
        quantity.setStep(1);
        quantity.setValue(Double.valueOf(cartItem.quantity()));
        quantity.setStepButtonsVisible(true);

        VerticalLayout verticalLayout = new VerticalLayout();
        verticalLayout.add(name);
        verticalLayout.add(quantity);

        add(productImage, verticalLayout, price);
        addClassNames(LumoUtility.Border.BOTTOM);

        quantity.addValueChangeListener(event -> {
            Integer quantity = (int) (event.getValue() - event.getOldValue());
            cartService.addCartItem(new CartItem(product.id(), quantity, null),
                    success -> cartView.refresh(),
                    error -> {
                    });
        });
    }


}
