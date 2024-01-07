import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class Inventory {
    @Id
    private int itemId;
    private String name;
    private String description;
    private double unitPrice;
    private int quantity;

    @OneToMany(mappedBy = "inventory", cascade = CascadeType.ALL )
    @JsonManagedReference
    private List<OrderItem> orderItems;
}
