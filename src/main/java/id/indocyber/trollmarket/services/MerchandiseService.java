package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.merchandise.MerchandiseDetailDto;
import id.indocyber.trollmarket.dtos.merchandise.MerchandiseFieldIndexDto;
import id.indocyber.trollmarket.dtos.merchandise.MerchandiseFormDto;
import id.indocyber.trollmarket.dtos.shop.ShopFieldIndexDto;
import id.indocyber.trollmarket.dtos.shop.ShopGetFilterDto;
import id.indocyber.trollmarket.models.Product;
import id.indocyber.trollmarket.repositories.OrderRepository;
import id.indocyber.trollmarket.repositories.ProductRepository;
import id.indocyber.trollmarket.repositories.SellerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MerchandiseService {
    private ProductRepository productRepository;
    private SellerRepository sellerRepository;
    private OrderRepository orderRepository;

    public MerchandiseService(ProductRepository productRepository, SellerRepository sellerRepository, OrderRepository orderRepository) {
        this.productRepository = productRepository;
        this.sellerRepository = sellerRepository;
        this.orderRepository = orderRepository;
    }

    public List<MerchandiseFieldIndexDto> get(String username){
        var products = productRepository.findAllProductOfUsername(username).stream().map(
                product -> MerchandiseFieldIndexDto.builder()
                        .id(product.getId())
                        .productName(product.getProductName())
                        .categoryName(product.getCategory())
                        .totalOrders(orderRepository.getOrderCountByProduct(product.getId()))
                        .discontinue(product.getIsDiscontinue())
                        .build()
        ).toList();

        return products;
    }

    public MerchandiseDetailDto getDetail(Integer id){
        var product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product Id not found!")
        );
        return MerchandiseDetailDto.builder()
                .id(product.getId())
                .name(product.getProductName())
                .category(product.getCategory())
                .description(product.getDescription())
                .price(product.getPrice())
                .discontinue(product.getIsDiscontinue())
                .build();
    }

    public MerchandiseFormDto get(Integer id){
        var product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product Id not found!")
        );
        return MerchandiseFormDto.builder()
                .id(product.getId())
                .name(product.getProductName())
                .price(product.getPrice())
                .description(product.getDescription())
                .isDiscontinue(product.getIsDiscontinue())
                .category(product.getCategory())
                .build();
    }

    public Integer getOrderCountByProduct(Integer id){
        return orderRepository.getOrderCountByProduct(id);
    }

    public void upsert(MerchandiseFormDto dto, String username){
        var seller = sellerRepository.findByAccountUsername(username).orElseThrow(
                () -> new IllegalArgumentException("Seller tidak ditemukan!")
        );
        var product = Product.builder()
                .id(dto.getId())
                .productName(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .isDiscontinue(dto.getIsDiscontinue() != null && dto.getIsDiscontinue())
                .category(dto.getCategory())
                .seller(seller)
                .build();

        productRepository.save(product);
    }

    public void discontinueProduct(Integer id){
        var product = productRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Product ID not Found!")
        );
        product.setIsDiscontinue(true);
        productRepository.save(product);
    }

    public void delete(MerchandiseFormDto dto){
        productRepository.deleteById(dto.getId());
    }
}
