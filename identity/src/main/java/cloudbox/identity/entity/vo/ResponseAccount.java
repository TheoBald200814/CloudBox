package cloudbox.identity.entity.vo;

import cloudbox.identity.entity.dto.Account;
import lombok.Data;

@Data
public class ResponseAccount extends Account {

    private String res;
}
