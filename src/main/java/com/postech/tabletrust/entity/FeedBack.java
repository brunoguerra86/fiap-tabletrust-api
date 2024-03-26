package com.postech.tabletrust.entity;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.jackson.Jacksonized;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "tb_feedback")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Jacksonized
public class FeedBack {

    @Id
    @GenericGenerator(name = "UUID")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotEmpty(message = "[restaurant] não pode estar vazio")
    private UUID restaurantId;

    @NotEmpty(message = "[customer] não pode estar vazio")
    private UUID customerId;

    @NotEmpty(message = "[reservation] não pode estar vazio")
    private UUID reservationId;

    private String comment;

    @Min(value = 0, message = "A nota deve ser no mínimo 0")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private int stars;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public FeedBack(FeedBackCreateDTO feedBackCreateDTO) {
                this.restaurantId = feedBackCreateDTO.restaurantId();
                this.customerId = feedBackCreateDTO.customerId();
                this.reservationId = feedBackCreateDTO.reservationId();
                this.comment = feedBackCreateDTO.comment();
                this.stars = feedBackCreateDTO.stars();
    }

    public FeedBackCreateDTO convertToDTO() {
        return new FeedBackCreateDTO(
                this.restaurantId,
                this.customerId,
                this.reservationId,
                this.getComment(),
                this.getStars()
        );
    }

}
