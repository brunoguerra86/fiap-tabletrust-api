package com.postech.tabletrust.entities;

import com.postech.tabletrust.dto.FeedBackCreateDTO;
import jakarta.persistence.*;
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
    @JoinColumn(name ="restaurantId")
    private UUID restaurantId;

    @NotEmpty(message = "[customer] não pode estar vazio")
    @JoinColumn(name ="customerId")
    private UUID customerId;

    @NotEmpty(message = "[reservation] não pode estar vazio")
    @JoinColumn(name ="reservationId")
    private UUID reservationId;

    private String comment;

    @NotEmpty(message = "[stars] não pode estar vazio")
    private int stars;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public FeedBack(FeedBackCreateDTO feedBackCreateDTO) {
                this.restaurantId = feedBackCreateDTO.restaurantId();
                this.customerId = feedBackCreateDTO.customerId();
                this.reservationId = feedBackCreateDTO.restaurantId();
                this.comment = feedBackCreateDTO.comment();
                this.stars = feedBackCreateDTO.stars();
    }

    public FeedBackCreateDTO convertToDTO() {
        return new FeedBackCreateDTO(
                this.customerId,
                this.restaurantId,
                this.reservationId,
                this.getComment(),
                this.getStars()
        );
    }

}
