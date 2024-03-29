package com.camisola10.camisolabackend.application.service;

import com.camisola10.camisolabackend.application.port.in.ImagesQueryService;
import com.camisola10.camisolabackend.application.port.in.ProductsCommandService;
import com.camisola10.camisolabackend.application.port.in.ProductsQueryService;
import com.camisola10.camisolabackend.application.port.in.command.product.CreateProductCommand;
import com.camisola10.camisolabackend.application.port.in.command.product.RemoveProductCommand;
import com.camisola10.camisolabackend.application.port.out.ProductDB;
import com.camisola10.camisolabackend.domain.events.SettingsUpdatedEvent;
import com.camisola10.camisolabackend.domain.events.SettingsUpdatedListener;
import com.camisola10.camisolabackend.domain.product.Badge;
import com.camisola10.camisolabackend.domain.product.Product;
import com.camisola10.camisolabackend.domain.product.Product.ProductId;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class ProductService implements ProductsCommandService, ProductsQueryService, SettingsUpdatedListener {

    private final ImagesQueryService imagesQueryService;
    private final ProductDB db;

    @Override
    public List<Product> getAll() {
        return db.findAll();
    }

    @Override
    public Product createProduct(CreateProductCommand command) {
        var images = imagesQueryService.findImagesById(command.getImages());
        var newProduct =  Product.builder()
                .id(ProductId.create())
                .name(command.getName())
                .images(images)
                .categories(command.getCategories())
                .sizes(command.getSizes())
                .badges(command.getBadges())
                .customizable(command.isCustomizable())
                .visible(command.isVisible())
                .defaultPrice(command.getDefaultPrice())
                .description(command.getDescription())
                .prebooking(command.isPrebooking())
                .build();

        db.save(newProduct);
        log.info("Product created: {}", newProduct);
        return newProduct;
    }

    @Override
    public Product updateProduct(UpdateProductCommand command) {
        var productId = ProductId.from(command.getId());
        Product product = db.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("unable to find product with id: " + command.getId()));

        var images = imagesQueryService.findImagesById(command.getImageIds());
        var newProduct = Product.builder()
                .id(product.getId())
                .name(command.getName())
                .categories(command.getCategories())
                .sizes(command.getSizes())
                .badges(command.getBadges())
                .images(images)
                .defaultPrice(command.getDefaultPrice())
                .description(command.getDescription())
                .customizable(command.isCustomizable())
                .visible(command.isVisible())
                .prebooking(command.isPrebooking())
                .build();

        db.save(newProduct);
        log.info("product {} updated", product.getId().asString());
        return newProduct;
    }

    @Override
    public void removeProduct(RemoveProductCommand command) {
        db.deleteById(command.getProductId());
        log.info("Product deleted: {}", command);
    }

    @Override
    public Optional<Product> findProductById(ProductId productId) {
        return db.findById(productId);
    }

    @Async
    @EventListener
    @Override
    public void handleEvent(SettingsUpdatedEvent event) {
        List<Badge> badges = event.getSettings().getProductSettings().getBadges();
        db.deleteUnmatchedBadges(badges);
    }
}
