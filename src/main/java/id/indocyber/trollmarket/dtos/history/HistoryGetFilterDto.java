package id.indocyber.trollmarket.dtos.history;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class HistoryGetFilterDto {
    private Integer pageNumber;
    private Integer pageSize;
    private Integer sellerId;
    private Integer buyerId;
}
