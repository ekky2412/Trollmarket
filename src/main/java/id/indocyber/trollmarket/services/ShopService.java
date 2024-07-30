package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.history.HistoryFieldIndexDto;
import id.indocyber.trollmarket.dtos.history.HistoryGetFilterDto;
import id.indocyber.trollmarket.dtos.shop.*;
import id.indocyber.trollmarket.models.Cart;
import id.indocyber.trollmarket.models.CartId;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.CartRepository;
import id.indocyber.trollmarket.repositories.ProductRepository;
import id.indocyber.trollmarket.repositories.ShipperRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShopService {

    private ProductRepository productRepository;
    private ShipperRepository shipperRepository;
    private CartRepository cartRepository;
    private BuyerRepository buyerRepository;

    public ShopService(ProductRepository productRepository, ShipperRepository shipperRepository, CartRepository cartRepository, BuyerRepository buyerRepository) {
        this.productRepository = productRepository;
        this.shipperRepository = shipperRepository;
        this.cartRepository = cartRepository;
        this.buyerRepository = buyerRepository;
    }

    public Page<ShopFieldIndexDto> get(ShopGetFilterDto dto, String username){
        String checkedName = dto.getName() == null || dto.getName().isEmpty() ? null : dto.getName();
        String checkedCategory = dto.getCategory() == null || dto.getCategory().isEmpty() ? null : dto.getCategory();
        String checkedDescription = dto.getDescription() == null || dto.getDescription().isEmpty() ? null : dto.getDescription();
        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber()-1;
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var products = productRepository.findAllProductExceptUsernameOf(pageable, username, checkedName, checkedCategory, checkedDescription).map(
                product -> ShopFieldIndexDto.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .price(product.getPrice())
                        .build()
        );

        return products;
    }

    public ShopDetailDto getDetail(Integer id){
        var product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product ID not found!")
        );
        return ShopDetailDto.builder()
                .id(product.getId())
                .name(product.getProductName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .seller(product.getSeller().getName())
                .build();
    }

    public List<ShipperDropdownDto> getShippers(){
        var shippers = shipperRepository.getAllActiveShippers();
        return shippers.stream().map(
                shipper -> ShipperDropdownDto.builder()
                        .id(shipper.getId())
                        .name(shipper.getShipperName())
                        .build()
        ).toList();
    }
    public void addToCart(AddToCartDto dto, String username){
        var cart = cartRepository.findCartByBuyerProductAndShipper(username, dto.getProductId(), dto.getShipperId());
        if(cart.isPresent()){
            var existingCart = cart.get();
            existingCart.setQuantity(existingCart.getQuantity() + dto.getQuantity());
            cartRepository.save(existingCart);
            return;
        }

        var buyer = buyerRepository.findByAccountUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Buyer Id not Found!")
        );

        var product = productRepository.findById(dto.getProductId()).orElseThrow(
                () -> new IllegalArgumentException("Product Id not Found!")
        );

        var shipper = shipperRepository.findById(dto.getShipperId()).orElseThrow(
                () -> new IllegalArgumentException("Shipper Id not Found!")
        );

        var newCart = Cart.builder()
                .id(CartId.builder()
                        .buyerId(buyer.getAccountId())
                        .productId(product.getId())
                        .shipperId(shipper.getId())
                        .build())
                .buyer(buyer)
                .shipper(shipper)
                .product(product)
                .quantity(dto.getQuantity())
                .build();
        cartRepository.save(newCart);
    }
}
