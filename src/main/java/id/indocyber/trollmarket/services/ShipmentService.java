package id.indocyber.trollmarket.services;

import id.indocyber.trollmarket.dtos.shipment.ShipmentFieldIndexDto;
import id.indocyber.trollmarket.dtos.shipment.ShipmentFormDto;
import id.indocyber.trollmarket.models.Shipper;
import id.indocyber.trollmarket.repositories.OrderRepository;
import id.indocyber.trollmarket.repositories.ShipperRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Service
public class ShipmentService {
    private ShipperRepository shipperRepository;
    private OrderRepository orderRepository;

    public ShipmentService(ShipperRepository shipperRepository, OrderRepository orderRepository) {
        this.shipperRepository = shipperRepository;
        this.orderRepository = orderRepository;
    }

    public List<ShipmentFieldIndexDto> getShippers(){
        var shippers = shipperRepository.findAll();
        return shippers.stream().map(
                shipper -> ShipmentFieldIndexDto.builder()
                        .id(shipper.getId())
                        .shipperName(shipper.getShipperName())
                        .price(shipper.getPrice())
                        .isService(shipper.getIsService())
                        .totalOrders(orderRepository.getOrderCountByShipper(shipper.getId()))
                        .build()
        ).toList();
    }

    public ShipmentFormDto getShipperDetail(Integer id){
        var shipper = shipperRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Shipper ID not Found!")
        );
        return ShipmentFormDto.builder()
                .id(shipper.getId())
                .name(shipper.getShipperName())
                .price(shipper.getPrice())
                .isService(shipper.getIsService())
                .build();
    }

    public void upsert(ShipmentFormDto dto){
        var shipper = Shipper.builder()
                .id(dto.getId())
                .shipperName(dto.getName())
                .price(dto.getPrice())
                .isService(dto.getIsService() != null && dto.getIsService())
                .build();
        shipperRepository.save(shipper);
    }

    public Integer getOrderCountByShipper(Integer id){
        return orderRepository.getOrderCountByShipper(id);
    }

    public void delete(ShipmentFormDto dto){
        shipperRepository.deleteById(dto.getId());
    }

    public void stopService(Integer id){
        var shipper = shipperRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("Shipper ID not Found!")
        );
        shipper.setIsService(false);
        shipperRepository.save(shipper);
    }

}
