package freela.order.service.domain.model.response;

import freela.order.service.domain.model.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserNameDetails {
    private Integer id;
    private String name;
    private byte[] photo;

    public UserNameDetails(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.photo = user.getPhoto();
    }
}
