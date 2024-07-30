package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.history.BuyerFormDto;
import id.indocyber.trollmarket.dtos.history.HistoryFieldIndexDto;
import id.indocyber.trollmarket.dtos.history.HistoryGetFilterDto;
import id.indocyber.trollmarket.dtos.history.SellerFormDto;
import id.indocyber.trollmarket.repositories.BuyerRepository;
import id.indocyber.trollmarket.repositories.OrderRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {
    private OrderRepository orderRepository;
    private SellerRepository sellerRepository;
    private BuyerRepository buyerRepository;

    public HistoryService(OrderRepository orderRepository, SellerRepository sellerRepository, BuyerRepository buyerRepository) {
        this.orderRepository = orderRepository;
        this.sellerRepository = sellerRepository;
        this.buyerRepository = buyerRepository;
    }

    public List<SellerFormDto> getSellers(){
        return sellerRepository.findAll().stream().map(
                seller -> SellerFormDto.builder()
                        .sellerId(seller.getAccount().getId())
                        .sellerName(seller.getName())
                        .build()
        ).toList();
    }

    public List<BuyerFormDto> getBuyers(){
        return buyerRepository.findAll().stream().map(
                seller -> BuyerFormDto.builder()
                        .buyerId(seller.getAccount().getId())
                        .buyerName(seller.getName())
                        .build()
        ).toList();
    }

    public Page<HistoryFieldIndexDto> get(HistoryGetFilterDto dto){
        Integer checkedSeller = dto.getSellerId() == null ? null : dto.getSellerId();
        Integer checkedBuyer = dto.getBuyerId() == null ? null : dto.getBuyerId();
        int pageNumber = dto.getPageNumber() == null ? 0 : dto.getPageNumber()-1;
        int pageSize = dto.getPageSize() == null ? 10 : dto.getPageSize();

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        var orders = orderRepository.findAll(pageable, checkedSeller, checkedBuyer).map(
                order -> HistoryFieldIndexDto.builder()
                        .date(order.getOrderDate())
                        .seller(order.getProduct().getSeller().getName())
                        .buyer(order.getBuyer().getName())
                        .product(order.getProduct().getProductName())
                        .quantity(order.getQuantity())
                        .shipment(order.getShipper().getShipperName())
                        .totalPrice(order.getTotalPrice())
                        .build()
        );

        return orders;
    }
}
