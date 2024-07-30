package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.profile.AddBalanceDto;
import id.indocyber.trollmarket.dtos.profile.BuyerFieldIndexDto;
import id.indocyber.trollmarket.dtos.profile.SellerFieldIndexDto;
import id.indocyber.trollmarket.dtos.profile.TransactionHistoryDto;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.OrderRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfileService {
    private BuyerRepository buyerRepository;
    private SellerRepository sellerRepository;
    private OrderRepository orderRepository;

    public ProfileService(BuyerRepository buyerRepository, SellerRepository sellerRepository, OrderRepository orderRepository) {
        this.buyerRepository = buyerRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
    }

    public BuyerFieldIndexDto getBuyer(String buyerUsername){
        var buyer = buyerRepository.findByAccountUsername(buyerUsername).orElseThrow(
                () -> new IllegalArgumentException("Buyer not exists!")
        );
        return BuyerFieldIndexDto.builder()
                .name(buyer.getName())
                .role("Buyer")
                .address(buyer.getAddress())
                .balance(buyer.getBalance())
                .build();
    }

    public SellerFieldIndexDto getSeller(String sellerUsername){
        var seller = sellerRepository.findByAccountUsername(sellerUsername).orElseThrow(
                () -> new IllegalArgumentException("Seller not exists!")
        );
        return SellerFieldIndexDto.builder()
                .name(seller.getName())
                .role("Seller")
                .address(seller.getAddress())
                .balance(seller.getBalance())
                .build();
    }

    public void addBalance(AddBalanceDto dto){
        var buyer = buyerRepository.findByAccountUsername(dto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Buyer not exists!")
        );

        buyer.setBalance(buyer.getBalance() + dto.getBalance());

        buyerRepository.save(buyer);
    }

    public List<TransactionHistoryDto> getOrderByBuyer(String username){
        var orders = orderRepository.getOrderByBuyerUsername(username);
        return orders.stream().map(
                order -> TransactionHistoryDto.builder()
                        .date(order.getOrderDate())
                        .product(order.getProduct().getProductName())
                        .quantity(order.getQuantity())
                        .shipment(order.getShipper().getShipperName())
                        .totalPrice(order.getTotalPrice())
                        .build()).toList();
    }

    public List<TransactionHistoryDto> getOrderBySeller(String username){
        var orders = orderRepository.getOrderBySellerUsername(username);
        return orders.stream().map(
                order -> TransactionHistoryDto.builder()
                        .date(order.getOrderDate())
                        .product(order.getProduct().getProductName())
                        .quantity(order.getQuantity())
                        .shipment(order.getShipper().getShipperName())
                        .totalPrice(order.getTotalPrice())
                        .build()).toList();
    }
}
