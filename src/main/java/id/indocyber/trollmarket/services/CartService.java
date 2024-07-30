package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.cart.CartFieldIndexDto;
import id.indocyber.trollmarket.dtos.cart.CartPurchaseDto;
import id.indocyber.trollmarket.models.CartId;
import id.indocyber.trollmarket.models.Order;
import id.indocyber.trollmarket.models.Seller;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.CartRepository;
import id.indocyber.trollmarket.repositories.OrderRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {
    private CartRepository cartRepository;
    private BuyerRepository buyerRepository;
    private OrderRepository orderRepository;
    private SellerRepository sellerRepository;

    public CartService(CartRepository cartRepository, BuyerRepository buyerRepository, OrderRepository orderRepository, SellerRepository sellerRepository) {
        this.cartRepository = cartRepository;
        this.buyerRepository = buyerRepository;
        this.orderRepository = orderRepository;
        this.sellerRepository = sellerRepository;
    }

    public List<CartFieldIndexDto> get(String username) {
        var carts = cartRepository.findAllCartOfUsername(username);
        return carts.stream().map(
                cart -> CartFieldIndexDto.builder()
                        .id(cart.getId())
                        .product(cart.getProduct().getProductName())
                        .quantity(cart.getQuantity())
                        .shipment(cart.getShipper().getShipperName())
                        .seller(cart.getProduct().getSeller().getName())
                        .totalPrice((cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice())
                        .build()
        ).toList();
    }

    public Boolean isCartEmpty(String username) {
        var cartCount = cartRepository.getCountOfCartProductsOfUsername(username);
        return cartCount == 0;
    }

    public Boolean isEnoughBalance(String username) {
        var carts = cartRepository.findAllCartOfUsername(username);
        var buyer = buyerRepository.findByAccountUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Buyer Not Found!")
        );
        Double totalBalance = carts.stream()
                .mapToDouble(cart ->
                        (cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice())
                .sum();
        return buyer.getBalance() > totalBalance;
    }

    public void purchaseAll(String username) {
        var carts = cartRepository.findAllCartOfUsername(username);
        var buyer = buyerRepository.findByAccountUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Buyer Id not Found!")
        );

        Double buyerBalance = buyer.getBalance();

        List<Order> orders = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();

        for (var cart : carts) {
            orders.add(
                    Order.builder()
                            .buyer(buyer)
                            .product(cart.getProduct())
                            .quantity(cart.getQuantity())
                            .shipper(cart.getShipper())
                            .totalPrice((cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice())
                            .orderDate(LocalDate.now())
                            .build()
            );

            var seller = sellerRepository.findById(cart.getProduct().getSeller().getAccountId()).orElseThrow(
                    () -> new IllegalArgumentException("Seller not found!")
            );
            seller.setBalance(seller.getBalance() + (cart.getProduct().getPrice() * cart.getQuantity()));
            sellers.add(seller);

            buyerBalance = buyerBalance - ((cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice());
        }

        buyer.setBalance(buyerBalance);
        buyerRepository.save(buyer);
        sellerRepository.saveAll(sellers);
        orderRepository.saveAll(orders);
        cartRepository.deleteAll(carts);
    }

    public void purchaseAll(CartPurchaseDto dto) {
        var carts = cartRepository.findAllCartOfUsername(dto.getUsername());
        var buyer = buyerRepository.findByAccountUsername(dto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Buyer Id not Found!")
        );

        // CEK SATU SATU

        Double buyerBalance = buyer.getBalance();

        List<Order> orders = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();

        for (var cart : carts) {
            orders.add(
                    Order.builder()
                            .buyer(buyer)
                            .product(cart.getProduct())
                            .quantity(cart.getQuantity())
                            .shipper(cart.getShipper())
                            .totalPrice((cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice())
                            .orderDate(LocalDate.now())
                            .build()
            );

            var seller = sellerRepository.findById(cart.getProduct().getSeller().getAccountId()).orElseThrow(
                    () -> new IllegalArgumentException("Seller not found!")
            );
            seller.setBalance(seller.getBalance() + (cart.getProduct().getPrice() * cart.getQuantity()));
            sellers.add(seller);

            buyerBalance = buyerBalance - ((cart.getProduct().getPrice() * cart.getQuantity()) + cart.getShipper().getPrice());
        }

        buyer.setBalance(buyerBalance);
        buyerRepository.save(buyer);
        sellerRepository.saveAll(sellers);
        orderRepository.saveAll(orders);
        cartRepository.deleteAll(carts);
    }

    public void delete(Integer product, Integer shipper, Integer buyerId) {
        cartRepository.deleteById(CartId.builder()
                .buyerId(buyerId)
                .shipperId(shipper)
                .productId(product)
                .build());
    }
}

